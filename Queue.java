/*
 * This class will implement in queue to hold the first element in each matrix
 */
public class Queue 
{
  private Node[] matrixQueue = new Node[100];
  private int index = -1;
  private int front = 0;
  private int back = 0;
  
  //push-pop-peek methods
  public void push(Node node)
  {
    index++;
    matrixQueue[index] = node;
  }
  public Node pop()
  {
    Node tempNode = matrixQueue[front];
    front++;
    return tempNode;
  }
  public Node peek()
  {
    return matrixQueue[front];
  }
  
  public int getIndex()
  {
    return index;
  }
  
}
