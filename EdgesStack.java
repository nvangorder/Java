/*
 * this stack will store all of the edges in a matrix
 */
public class EdgesStack 
{
  private int[][] stack = new int[100][2];
  private int index = -1;
  
  //push-pop-peek methods for the stack
  public void push(int start, int end)
  {
    index++;
    stack[index][0] = start;
    stack[index][1] = end;
  }
  public int[] pop()
  {
    int[] tempArray = stack[index];
    index--;
    return tempArray;
  }
  public int[] peek()
  {
    return stack[index];
  }
  
  public int getIndex()
  {
    return index;
  }
}
