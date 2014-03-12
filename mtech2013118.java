import java.io.IOException;

/**
 * @author prashanth
 * 
 */
public class mtech2013118
{

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException
	{

		RideInputData inputData = new RideInputData();

		// read inpudata from the give file path name from commandline argument
		// and set it to RideInputData

		if (args.length == 0 || args[0].equals(""))
		{
			System.out.println("Improper input filepath, please provide correct filepath");

		}
		new InputDataReader().setData(inputData, args[0]);

		new Floyds().runShortestPath(inputData);

		new RideImpl().findRevenue(inputData);

	}

}
