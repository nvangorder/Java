/*
 * this class will hold all of the path stacks that have been created
 */
public class Paths 
{
  private EdgesStack[] paths = new EdgesStack[100];
  private int index = -1;
  
  //push-pop-peek methods
  public void push(EdgesStack stack)
  {
    index++;
    paths[index] = stack;
  }
  public EdgesStack pop()
  {
    EdgesStack tempStack = paths[index];
    index--;
    return tempStack;
  }
  public EdgesStack peek()
  {
    return paths[index];
  }
  
  public int getIndex()
  {
    return index;
  }
}
