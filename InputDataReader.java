import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @author prashanth
 * 
 */
public class InputDataReader
{

	String line, tokens[];
	int i = 0, j = 0;
	List<NodeToNodeData> nodesList;
	List<Vehicle> vehicleList;
	Vehicle vehicle;

	BufferedReader br;

	public void setData(RideInputData inputData, String filePath) throws IOException
	{

		br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath)));

		line = br.readLine();

		// reading and setting number of locations, vehicles, requests,
		// vehicleCapacity

		if (line != null)
		{
			tokens = line.trim().split(" ");

			if (tokens.length != 4)
			{
				System.exit(0);
			} else
			{
				inputData.setNoOfLocations(Integer.parseInt(tokens[0].trim()));
				inputData.setNoOfVehicles(Integer.parseInt(tokens[1].trim()));
				inputData.setVehicleCapacity(Integer.parseInt(tokens[2].trim()));
				inputData.setNoOfRequests(Integer.parseInt(tokens[3].trim()));

			}

		} else
		{
			System.exit(0);
		}

		// reading location distances

		for (i = 0; i < inputData.getNoOfLocations(); i++)
		{

			nodesList = new ArrayList<NodeToNodeData>();
			vehicleList = new ArrayList<Vehicle>();

			line = br.readLine();

			tokens = line.trim().split(" ");
			for (int j = 0; j < tokens.length; j++)
			{

				if (tokens[j] != null && tokens[j].trim().length() > 0)
					nodesList.add(new NodeToNodeData(Integer.parseInt(tokens[j].trim())));
			}
			inputData.getLocDistances().put(i, nodesList);
		}

		// reading vehicles location points
		line = br.readLine();
		tokens = line.split(" ");
		for (i = 0, j = 0; i < tokens.length; i++)
		{
			if (tokens[i] != null && tokens[i].trim().length() > 0)
			{
				vehicle = new Vehicle(j + 1, Integer.parseInt(tokens[i].trim()) - 1);

				inputData.getVehicleData().put(j + 1, vehicle);
				++j;
			}
		}

		int request[] = new int[4];
		List<Request> requests;
		Request req;
		// reading the requests
		for (i = 0; i < inputData.getNoOfRequests(); i++)
		{
			line = br.readLine();
			tokens = line.split(" ");

			for (int j = 0, k = 0; j < tokens.length; j++)
			{

				if (tokens[j] != null && tokens[j].trim().length() > 0)
				{

					if (k == 0 || k == 1)
					{
						request[k] = Integer.parseInt(tokens[j].trim()) - 1;
					} else
						request[k] = Integer.parseInt(tokens[j].trim());
					++k;
				}

			}

			if (inputData.getRequests().get(request[0]) == null)
			{
				inputData.getRequests().put(request[0], new ArrayList<Request>());
			}

			requests = inputData.getRequests().get(request[0]);
			req = new Request(inputData);
			req.setDest_point(request[1]);
			req.setSrc_point(request[0]);
			req.setLow_pktime(request[2]);
			req.setUp_pktime(request[3]);
			requests.add(req);
		}

	}
}
