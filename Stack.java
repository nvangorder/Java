/*
 * this class will implement a stack that is used to hold the file
 * inputs for further reading
 */
public class Stack 
{
  private int[] stack = new int[500];
  private int index = -1;
  
  //push-pop-peek methods
  public void push(int integer)
  {
    index++;
    stack[index] = integer;
  }
  public int pop()
  {
    int temp = stack[index];
    index--;
    return temp;
  }
  public int peek()
  {
    return stack[index];
  }
  
  public int getIndex()
  {
    return index;
  }
}
