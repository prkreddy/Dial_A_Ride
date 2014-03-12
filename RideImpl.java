import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author prashanth
 * 
 */
public class RideImpl
{

	int vehs_not_running;
	int veh_Lap_Time[];
	boolean is_vehicle_started[];
	Map<Integer, List<NodeToNodeData>> n_to_n_dist;
	Map<Integer, List<Integer>> loc_to_veh;
	Map<Integer, Vehicle> veh_data;
	Map<String, Integer> passengerCount;

	public void findRevenue(RideInputData data)
	{

		int vehs_count = data.getNoOfVehicles();
		int locs_count = data.getNoOfLocations();
		veh_Lap_Time = new int[vehs_count + 1];
		is_vehicle_started = new boolean[vehs_count + 1];
		vehs_not_running = vehs_count;
		List<Request> requests = new ArrayList<Request>();
		Vehicle vehicle = null;
		String path = "";
		passengerCount = new HashMap<String, Integer>();
		n_to_n_dist = data.getLocDistances();
		loc_to_veh = data.getVehiclesTracker();
		veh_data = data.getVehicleData();
		int i = 0;
		for (i = 0; i < locs_count; i++)
		{

			if (data.getRequests().get(i) != null)
			{
				requests.addAll(data.getRequests().get(i));

			}

		}
		// sorting the requests in the increasing order of lower pickup time of
		// requests
		Collections.sort(requests);

		// for (Request req : requests)
		// {
		//
		// System.out.println("RequestAlloted_Time: " + req.getReq_alloc_time()
		// + ": " + ", Req_Start_Point:"
		// + req.getSrc_point() + ", Req_Dest_point:" + req.getDest_point() +
		// ": Request_Interval_Time << "
		// + req.getLow_pktime() + " , " + req.getUp_pktime() + " >>" +
		// " , RevenueGenerated:"
		// +
		// data.getLocDistances().get(req.getSrc_point()).get(req.getDest_point()).getShortDistance()
		// + ", Path: " + req.getPath());
		// }

		int req_start_point, req_dest_point, rq_lw_bound_time, rq_up_bound_time;
		for (Request request : requests)
		{
			req_start_point = request.getSrc_point();
			req_dest_point = request.getDest_point();
			rq_lw_bound_time = request.getLow_pktime();
			rq_up_bound_time = request.getUp_pktime();

			// checking whether any vehicle is idle before start of the each
			// request and resetting the vehicle that can be allocated to other
			// requests.
			for (i = 1; i <= vehs_count; i++)
			{

				if (is_vehicle_started[i] && veh_Lap_Time[i] < rq_up_bound_time)
				{

					++vehs_not_running;
					is_vehicle_started[i] = false;
					vehicle = veh_data.get(i);
					for (Integer loc : vehicle.getTraversePoint().keySet())
					{

						if (loc_to_veh.get(loc).contains(vehicle.getVehicleNo()))

							loc_to_veh.get(loc).remove(loc_to_veh.get(loc).indexOf(vehicle.getVehicleNo()));

					}
					vehicle.getTraversePoint().clear();
					vehicle.setStrtTime(veh_Lap_Time[i]);

					vehicle.setLocationPoint(vehicle.getDestPt());

				}

			}

			boolean vehicleAlloted = false;

			Object vehicles[];
			List<Integer> loc_times;

			// processing the request based on whether vehicle is in the
			// direction of the request startPoint.
			if (loc_to_veh.get(req_start_point) != null && loc_to_veh.get(req_start_point).size() > 0)
			{

				vehicles = loc_to_veh.get(req_start_point).toArray();

				for (Object vehilceNo : vehicles)
				{

					loc_times = new ArrayList<Integer>(veh_data.get(vehilceNo).getTraversePoint().get(req_start_point));
					for (Integer loc_time : loc_times)
					{

						if (rq_lw_bound_time <= loc_time && loc_time <= rq_up_bound_time)
						{

							vehicle = veh_data.get(vehilceNo);

							boolean vehiclethroughDest = false;
							if (vehicle.getTraversePoint().get(req_dest_point) != null)
								for (Integer time : vehicle.getTraversePoint().get(req_dest_point))
								{

									if (time > loc_time)
									{
										vehiclethroughDest = true;
									}
								}

							if (passengerCount.get(vehicle.getVehicleNo() + "-" + req_start_point + "-" + loc_time) < data
									.getVehicleCapacity())
							{
								if (vehiclethroughDest)
								{

									vehicle.setFareEarned(vehicle.getFareEarned()
											+ n_to_n_dist.get(req_start_point).get(req_dest_point).getShortDistance());
									vehicleAlloted = true;

									request.setReq_alloc_time(loc_time);
									request.setPath(vehicle.getTravelPath());

									vehicle.getRequests().add(request);
									updatePassengerCount(vehicle, req_start_point, req_dest_point, data);

								} else
								{

									if (n_to_n_dist.get(req_start_point).get(req_dest_point).getShortDistance() < n_to_n_dist
											.get(vehicle.getDestPt()).get(req_dest_point).getShortDistance())
										continue;
									vehicleAlloted = true;
									request.setReq_alloc_time(loc_time);
									vehicle.setFareEarned(vehicle.getFareEarned()
											+ n_to_n_dist.get(req_start_point).get(req_dest_point).getShortDistance());

									// System.out.println("vehicle Destination:"
									// + vehicle.getDestPt());
									path = getPath(vehicle.getDestPt(), req_dest_point, data);

									updatePassengerCount(vehicle, req_start_point, vehicle.getDestPt(), data);

									updatePaths(path, vehicle, data, request, Constants.OLD_PATH);
									if (path != null)
										vehicle.setTravelPath(vehicle.getTravelPath()
												+ path.substring((vehicle.getDestPt() + "").toString().length(),
														path.length()));

									request.setPath(vehicle.getTravelPath());

									vehicle.setEndTime(vehicle.getEndTime()
											+ n_to_n_dist.get(vehicle.getDestPt()).get(req_dest_point)
													.getShortDistance() * 2);
									vehicle.setDestPt(req_dest_point);
									vehicle.getRequests().add(request);

									if (veh_Lap_Time[vehicle.getVehicleNo()] < vehicle.getEndTime())
									{
										veh_Lap_Time[vehicle.getVehicleNo()] = vehicle.getEndTime();
									}
								}
							}
							break;
						}
					}
					if (vehicleAlloted == true)
					{
						break;
					}
				}

			}

			// checking whether any vehicle is free to run the request if the
			// above block is failed
			if (!vehicleAlloted && vehs_not_running > 0)
			{

				boolean vehicleAvailable = false;
				int minDist = Constants.MAX_VAL, index = 0;
				for (i = 1; i <= vehs_count; i++)
				{

					if (!is_vehicle_started[i])
					{
						vehicle = veh_data.get(i);

						if (rq_lw_bound_time >= (vehicle.getStrtTime() + n_to_n_dist.get(vehicle.getLocationPoint())
								.get(req_start_point).getShortDistance() * 2)
								&& minDist > (vehicle.getStrtTime() + n_to_n_dist.get(vehicle.getLocationPoint())
										.get(req_start_point).getShortDistance() * 2))
						{

							index = i;
							minDist = vehicle.getStrtTime()
									+ n_to_n_dist.get(vehicle.getLocationPoint()).get(req_start_point)
											.getShortDistance() * 2;
							vehicleAvailable = true;

						}
					}
				}

				if (vehicleAvailable)
				{
					vehicle = veh_data.get(index);
					is_vehicle_started[index] = true;
					--vehs_not_running;
				}

				// if vehicle is available allocating new cab to the request.
				if (vehicleAvailable)
				{
					request.setReq_alloc_time(rq_lw_bound_time);
					path = getPath(req_start_point, req_dest_point, data);
					// System.out.println("Vehicle going in path: " + path);
					request.setPath(path);

					vehicle.setTravelPath(path);
					vehicle.setDestPt(req_dest_point);
					vehicle.setStrtTime(rq_lw_bound_time);
					vehicle.setEndTime(rq_lw_bound_time
							+ n_to_n_dist.get(req_start_point).get(req_dest_point).getShortDistance() * 2);
					vehicle.getRequests().add(request);

					vehicle.setFareEarned(vehicle.getFareEarned()
							+ n_to_n_dist.get(req_start_point).get(req_dest_point).getShortDistance());

					updatePaths(path, vehicle, data, request, Constants.NEW_PATH);

					if (veh_Lap_Time[vehicle.getVehicleNo()] < vehicle.getEndTime())
					{
						veh_Lap_Time[vehicle.getVehicleNo()] = vehicle.getEndTime();
					}
				}

			}

		}

		System.out.println("\n\n");

		int totalRevenue = 0;
		for (Integer veh : veh_data.keySet())
		{
			System.out.println("\n\nvehicle::" + veh);

			for (Request req : veh_data.get(veh).getRequests())
			{

				System.out.println("RequestAlloted_Time: " + req.getReq_alloc_time() + ": " + ", Req_Start_Point:"
						+ req.getSrc_point() + ", Req_Dest_point:" + req.getDest_point()
						+ ": Request_Interval_Time << " + req.getLow_pktime() + " , " + req.getUp_pktime() + " >>"
						+ " , RevenueGenerated:"
						+ data.getLocDistances().get(req.getSrc_point()).get(req.getDest_point()).getShortDistance()
				/* + ", Path: " + req.getPath() */);

			}
			System.out.println("Vehicle Earned:" + veh_data.get(veh).getFareEarned());

			totalRevenue = totalRevenue + veh_data.get(veh).getFareEarned();
		}

		String revenue = "Total_Revenue" + totalRevenue;

		System.out.println();
		for (i = 0; i < revenue.length() + 4; i++)
			System.out.print("*");

		System.out.println("\n Total_Revenue:" + totalRevenue);

		for (i = 0; i < revenue.length() + 4; i++)
			System.out.print("*");
		System.out.println();

	}

	// to find the intermediate nodes/path of shortest path between src, dest
	// nodes.
	public String getPath(int src, int dest, RideInputData data)
	{

		int temp = n_to_n_dist.get(src).get(dest).getPreviousNode();
		StringBuilder pathNodes = new StringBuilder(getReverse(dest) + "-" + getReverse(temp));
		if (dest == src)
		{
			return null;
		}

		while (true)
		{
			if (temp == src)
			{
				break;
			} else
			{
				temp = n_to_n_dist.get(src).get(temp).getPreviousNode();
				pathNodes.append("-" + getReverse(temp));

			}

		}
		//
		// System.out.println(src + " to " + dest + " ::: "
		// + pathNodes.reverse().toString());

		return pathNodes.reverse().toString();
	}

	private String getReverse(int n)
	{

		int sum = 0, r = 0, count = 0;
		boolean isNonZeroStarted = false;
		StringBuilder br = new StringBuilder();
		while (n > 0)
		{
			r = n % 10;
			if (r == 0 && !isNonZeroStarted)
			{
				count++;
			} else if (!isNonZeroStarted)
			{
				isNonZeroStarted = true;
			}
			sum = sum * 10 + r;
			n = n / 10;
		}

		for (int i = 0; i < count; i++)
		{
			br.append("0");
		}

		return br.toString() + "" + sum;
	}

	public void updatePaths(String path, Vehicle vehicle, RideInputData data, Request request, String category)
	{

		int time = 0;
		if (path == null)
		{
			return;
		}
		String[] nodeTokens = path.split("-");

		for (int i = 0; i < nodeTokens.length; i++)
		{

			if (vehicle.getTraversePoint().get(Integer.parseInt(nodeTokens[i])) == null)
				vehicle.getTraversePoint().put(Integer.parseInt(nodeTokens[i]), new ArrayList<Integer>());

			if (category.equals(Constants.OLD_PATH))
			{
				time = vehicle.getEndTime()
						+ n_to_n_dist.get(vehicle.getDestPt()).get(Integer.parseInt(nodeTokens[i])).getShortDistance()
						* 2;
				if (!vehicle.getTraversePoint().get(Integer.parseInt(nodeTokens[i])).contains(time))
					vehicle.getTraversePoint().get(Integer.parseInt(nodeTokens[i])).add(time);

			} else
			{

				time = vehicle.getStrtTime()
						+ n_to_n_dist.get(request.getSrc_point()).get(Integer.parseInt(nodeTokens[i]))
								.getShortDistance() * 2;

				vehicle.getTraversePoint().get(Integer.parseInt(nodeTokens[i])).add(time);

			}

			if (request.getDest_point() != Integer.parseInt(nodeTokens[i]))
				passengerCount.put(vehicle.getVehicleNo() + "-" + Integer.parseInt(nodeTokens[i]) + "-" + time, 1);
			else
			{
				passengerCount.put(vehicle.getVehicleNo() + "-" + Integer.parseInt(nodeTokens[i]) + "-" + time, 0);
			}
			if (loc_to_veh.get(Integer.parseInt(nodeTokens[i])) == null)
				loc_to_veh.put(Integer.parseInt(nodeTokens[i]), new ArrayList<Integer>());
			if (!loc_to_veh.get(Integer.parseInt(nodeTokens[i])).contains(vehicle.getVehicleNo()))
				loc_to_veh.get(Integer.parseInt(nodeTokens[i])).add(vehicle.getVehicleNo());

		}

	}

	private void updatePassengerCount(Vehicle vehicle, int start, int dest, RideInputData data)
	{

		// System.out.println(vehicle.getTravelPath() + ", start:" + start +
		// ",dest:" + dest);

		String path = "-" + vehicle.getTravelPath() + "-";

		int destIndex = path.lastIndexOf("-" + dest + "-");

		int startIndex = 0;
		int tempStartIndex = 0;
		int time1 = vehicle.getTraversePoint().get(dest).get(vehicle.getTraversePoint().get(dest).size() - 1);
		int count = 0;

		if (start == dest)
		{

			passengerCount.put(vehicle.getVehicleNo() + "-" + start + "-" + time1,
					passengerCount.get(vehicle.getVehicleNo() + "-" + start + "-" + time1) + 1);
			// System.out.println(passengerCount.get(vehicle.getVehicleNo() +
			// "-"
			// + start + "-" + time1));
			return;

		}

		while (destIndex > startIndex && startIndex >= 0)
		{

			tempStartIndex = startIndex;

			if (startIndex != 0)
			{
				startIndex = path.indexOf("-" + start + "-", startIndex + ("-" + start + "-").length());
			} else
			{
				startIndex = path.indexOf("-" + start + "-");
				if (startIndex == 0)
					startIndex = ("-" + start + "-").length();
				if (startIndex > destIndex)
				{
					startIndex = startIndex - ("-" + start).length();

				}
			}
			++count;
		}

		if (tempStartIndex == ("-" + start + "-").length())
		{
			tempStartIndex = ("-" + start).length();
		}

		path = path.substring(tempStartIndex + 1, ("-" + dest).length() + destIndex);

		String tokens[] = path.split("-");

		int destTime = vehicle.getTraversePoint().get(dest).get(vehicle.getTraversePoint().get(dest).size() - 1);

		int startTime = vehicle.getTraversePoint().get(start).get(count - 2);

		for (String token : tokens)
		{
			if (token.equals(""))
			{
				continue;
			}

			if (Integer.parseInt(token) == dest)
			{
				break;
			}

			for (Integer time : vehicle.getTraversePoint().get(Integer.parseInt(token)))
			{

				if (time >= startTime && time <= destTime)
				{

					passengerCount.put(vehicle.getVehicleNo() + "-" + token + "-" + time,
							passengerCount.get(vehicle.getVehicleNo() + "-" + token + "-" + time) + 1);

					// System.out.println(passengerCount.get(vehicle
					// .getVehicleNo() + "-" + token + "-" + time));

				}

			}

		}

	}

}
