/**
 * @author prashanth
 * 
 */
public class Request implements Comparable<Request>
{
	// request lower interval pickup time
	private int low_pktime;

	public Request(RideInputData data)
	{
		super();
		this.data = data;
	}

	// request upper interval pickup time
	private int up_pktime;
	// request pickup point
	private int src_point;

	// request drop point
	private int dest_point;
	// requested passenger passing through the path
	private String path;
	// vehicle alloted time for this request
	private int req_alloc_time;

	private RideInputData data;

	public String getPath( )
	{
		return path;
	}

	public void setPath(String path)
	{
		this.path = path;
	}

	public int getReq_alloc_time( )
	{
		return req_alloc_time;
	}

	public void setReq_alloc_time(int req_alloc_time)
	{
		this.req_alloc_time = req_alloc_time;
	}

	public int getLow_pktime( )
	{
		return low_pktime;
	}

	public void setLow_pktime(int low_pktime)
	{
		this.low_pktime = low_pktime;
	}

	public int getUp_pktime( )
	{
		return up_pktime;
	}

	public void setUp_pktime(int up_pktime)
	{
		this.up_pktime = up_pktime;
	}

	public int getSrc_point( )
	{
		return src_point;
	}

	public void setSrc_point(int src_point)
	{
		this.src_point = src_point;
	}

	public int getDest_point( )
	{
		return dest_point;
	}

	public void setDest_point(int dest_point)
	{
		this.dest_point = dest_point;
	}

	public boolean validateRequest( )
	{

		boolean validationPass = true;

		if (this.low_pktime < 0 || this.low_pktime > 1440 || this.up_pktime < 0 || this.up_pktime > 1440)
		{
			validationPass = false;
		}
		return validationPass;
	}

	@Override
	public int compareTo(Request o)
	{

		int value = 0;

		if (this.up_pktime == o.up_pktime)
		{
			value = this.data.getLocDistances().get(o.getSrc_point()).get(o.getDest_point()).getShortDistance()
					- this.data.getLocDistances().get(this.getSrc_point()).get(this.getDest_point()).getShortDistance();

		} else
		{
			value = this.up_pktime - o.up_pktime;
		}

		return value;
	}
}
