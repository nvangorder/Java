/*
 * this class will store the node specific information
 */
public class Node 
{
  private Node nextNode;
  private int linked;
  private int nodeNumber;
  private int endingNode;
  
  //set-get method for next node
  public void setNextNode(Node node)
  {
    nextNode = node;
  }
  public Node getNextNode()
  {
    return nextNode;
  }
  
  //set-get method for linked
  public void setLinked(int linked)
  {
    this.linked = linked;
  }
  public int getLinked()
  {
    return this.linked;
  }
  
  //set-get method for node number
  public void setNodeNumber(int number)
  {
    nodeNumber = number;
  }
  public int getNodeNumber()
  {
    return nodeNumber;
  }
  
  //set-get method for ending node
  public void setEndingNode(int node)
  {
    endingNode = node;
  }
  public int getEndingNode()
  {
    return endingNode;
  }
  
  
}
