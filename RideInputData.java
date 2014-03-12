import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author prashanth
 * 
 */
public class RideInputData
{

	private int noOfLocations;

	private int noOfVehicles;

	private int vehicleCapacity;

	private int noOfRequests;

	private Map<Integer, List<NodeToNodeData>> locDistances;

	private Map<Integer, Vehicle> vehicleData;

	private Map<Integer, List<Request>> requests;

	private Map<Integer, List<Integer>> vehiclesTracker;

	public Map<Integer, List<Integer>> getVehiclesTracker( )
	{
		return vehiclesTracker;
	}

	public void setVehiclesTracker(Map<Integer, List<Integer>> vehiclesTracker)
	{
		this.vehiclesTracker = vehiclesTracker;
	}

	public Map<Integer, Vehicle> getVehicleData( )
	{
		return vehicleData;
	}

	public void setVehicleData(Map<Integer, Vehicle> vehicleData)
	{
		this.vehicleData = vehicleData;
	}

	public Map<Integer, List<NodeToNodeData>> getLocDistances( )
	{
		return locDistances;
	}

	public void setLocDistances(Map<Integer, List<NodeToNodeData>> locDistances)
	{
		this.locDistances = locDistances;
	}

	public RideInputData()
	{

		locDistances = new HashMap<Integer, List<NodeToNodeData>>();

		requests = new HashMap<Integer, List<Request>>();
		// TODO Auto-generated constructor stub

		vehiclesTracker = new HashMap<Integer, List<Integer>>();

		vehicleData = new HashMap<Integer, Vehicle>();
	}

	public Map<Integer, List<Request>> getRequests( )
	{
		return requests;
	}

	public void setRequests(Map<Integer, List<Request>> requests)
	{
		this.requests = requests;
	}

	public int getNoOfLocations( )
	{
		return noOfLocations;
	}

	public void setNoOfLocations(int noOfLocations)
	{
		this.noOfLocations = noOfLocations;
	}

	public int getNoOfVehicles( )
	{
		return noOfVehicles;
	}

	public void setNoOfVehicles(int noOfVehicles)
	{
		this.noOfVehicles = noOfVehicles;
	}

	public int getVehicleCapacity( )
	{
		return vehicleCapacity;
	}

	public void setVehicleCapacity(int vehicleCapacity)
	{
		this.vehicleCapacity = vehicleCapacity;
	}

	public int getNoOfRequests( )
	{
		return noOfRequests;
	}

	public void setNoOfRequests(int noOfRequests)
	{
		this.noOfRequests = noOfRequests;
	}
}
