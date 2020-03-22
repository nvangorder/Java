/*
 * this queue will hold the stack
 */
public class StackQueue 
{
  private Stack[] stackQueue = new Stack[10];
  private int index = -1;
  private int front = 0;
  
  //push-pop-peek methods
  public void push(Stack stack)
  {
    index++;
    stackQueue[index] = stack;
  }
  public Stack pop()
  {
    Stack temp = stackQueue[front];
    front++;
    return temp;
  }
  public Stack peek()
  {
    return stackQueue[front];
  }
  public int getIndex()
  {
    return index;
  }
  
}
