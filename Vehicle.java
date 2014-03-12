import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author prashanth
 * 
 */
public class Vehicle
{

	// storing unique identification # for each vehicle
	private int vehicleNo;
	// location where vehicle is available.
	private int locationPoint;
	// to keep totalfare earned by the vechile of all the requests it served
	private int fareEarned;
	// vehicle started time when the request is accepted
	private int strtTime;
	// vehilce ended time when the request is served
	private int endTime;
	// vehicle location once after the request is completetly served
	private int destPt;
	// path along which vehicle is passing through
	private String travelPath;
	// vehicle location-time map representing the time at which vehicle
	// passes through particular location
	private Map<Integer, List<Integer>> traversePoint;

	// to store the list of requests it served
	private List<Request> requests;

	public int getStrtTime( )
	{
		return strtTime;
	}

	public void setStrtTime(int strtTime)
	{
		this.strtTime = strtTime;
	}

	public int getEndTime( )
	{
		return endTime;
	}

	public void setEndTime(int endTime)
	{
		this.endTime = endTime;
	}

	public Map<Integer, List<Integer>> getTraversePoint( )
	{
		return traversePoint;
	}

	public void setTraversePoint(Map<Integer, List<Integer>> traversePoint)
	{
		this.traversePoint = traversePoint;
	}

	public int getDestPt( )
	{
		return destPt;
	}

	public void setDestPt(int destPt)
	{
		this.destPt = destPt;
	}

	public String getTravelPath( )
	{
		return travelPath;
	}

	public void setTravelPath(String travelPath)
	{
		this.travelPath = travelPath;
	}

	public List<Request> getRequests( )
	{
		return requests;
	}

	public void setRequests(List<Request> requests)
	{
		this.requests = requests;
	}

	public int getVehicleNo( )
	{
		return vehicleNo;
	}

	public void setVehicleNo(int vehicleNo)
	{
		this.vehicleNo = vehicleNo;
	}

	public int getLocationPoint( )
	{
		return locationPoint;
	}

	public void setLocationPoint(int locationPoint)
	{
		this.locationPoint = locationPoint;
	}

	public Vehicle(int vehicleNo, int locationPoint)
	{
		this.vehicleNo = vehicleNo;
		this.locationPoint = locationPoint;
		this.requests = new ArrayList<Request>();
		this.traversePoint = new HashMap<Integer, List<Integer>>();

	}

	public int getFareEarned( )
	{
		return fareEarned;
	}

	public void setFareEarned(int fareEarned)
	{
		this.fareEarned = fareEarned;
	}
}
