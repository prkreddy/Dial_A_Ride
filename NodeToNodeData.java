
// it holds all the data between two nodes of given all nodes
/**
 * @author prashanth
 * 
 */
public class NodeToNodeData
{

	// it represents given distance between src node to destination node if they
	// are neighbours else it is 0
	private int inputDistance;

	// it holds shortest distance calculated between src node to destination
	// node
	private int shortDistance;

	// it holds the previous node of dest node along shortest path
	private int previousNode;

	public NodeToNodeData(int pathDistance)
	{
		this.inputDistance = pathDistance;
	}

	public int getInputDistance( )
	{
		return inputDistance;
	}

	public void setInputDistance(int inputDistance)
	{
		this.inputDistance = inputDistance;
	}

	public int getShortDistance( )
	{
		return shortDistance;
	}

	public void setShortDistance(int shortDistance)
	{
		this.shortDistance = shortDistance;
	}

	public int getPreviousNode( )
	{
		return previousNode;
	}

	public void setPreviousNode(int previousNode)
	{
		this.previousNode = previousNode;
	}

}
