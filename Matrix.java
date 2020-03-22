import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.io.FileWriter;

/*
 * This program will read a text file containing matrices
 * and produce an output showing the relationship between
 * the nodes present.
 */

public class Matrix 
{
  public static void main(String[] args)
  {
    //user file inputs
    System.out.println("Input File Location: ");
    Scanner input = new Scanner(System.in);
    
    String filelocation = input.nextLine();
    
    System.out.println("Output File Location: ");
    String filelocation2 = input.nextLine();
    
    //creates a stack of all of the characters from the file
    StackQueue matrixStack = readFile(filelocation);
    
    //creates a queue of linked lists from the stack
    Queue matrixQueue = new Queue();
    
    //tracks how many matrices there are
    int matrixes = matrixStack.getIndex();
    
    try
    {
    //starts writing
    PrintWriter writer = new PrintWriter(filelocation2, "UTF-8");
    //while there are matrixes left
    while(matrixes >= 0)
    {
    matrixes--;
    //creates the linked list from the given stack
    matrixQueue.push(createLinkedList(matrixStack.pop()).pop());
    
    //identifies all edges from the linked list
    EdgesStack edges = getEdges(matrixQueue.peek());
    
    //gets all paths using the edges
    Paths paths = findPaths(edges);
    
    //pops out the first node from the matrix queue
    Node firstNode = matrixQueue.pop();
    Node previousNode = firstNode;
    //begins writing
    writer.println("Matrix:");

    //prints out the matrix
    while(firstNode.getNextNode().getNodeNumber() != -1)
    {
      //identifies if the end of the row has been met and adds a new line if so
      if(previousNode.getNodeNumber() < firstNode.getNodeNumber())
      {
        writer.println(" ");
      }
      previousNode = firstNode;
      writer.print(firstNode.getLinked()+" ");
      firstNode = firstNode.getNextNode();
    }
    //prints the last character
    previousNode = firstNode;
    writer.print(firstNode.getLinked()+" ");
    firstNode = firstNode.getNextNode();
    writer.println(" ");
    writer.println(" ");
    //begins the path section
    writer.print("--Paths-- ");
    
    //prints out all of the paths
    while(paths.getIndex() != -1)
    {
      //a temps stack that houses all of the steps in the current path
      EdgesStack tempStack = paths.pop();
      writer.println("");
      
      //prints out all steps in the current path
      while(tempStack.getIndex() != -1)
      {
        int[] tempArray = tempStack.pop();
        if(tempStack.getIndex() == -1)
        {
          writer.print(tempArray[0]+" -> "+tempArray[1]);
        }
        else
        {
          writer.print(tempArray[0]+" -> ");
        }
      }
    }
    //ends matrix, so that the next is more visabile.
    writer.println("");
    writer.print("------------End Matrix-------------");
    writer.println(" ");
    writer.println(" ");
    }
    //closes the file
    writer.close();
    }
    catch(Exception exceptions)
    {
      System.out.println("An error occurred during output writing.");
    }
  }
  
  //this method will be used to find all possible paths within the matrix
  public static Paths findPaths(EdgesStack edges)
  {
    Paths completePaths = new Paths();
    int size = 0;
    
    EdgesStack masterEdges = new EdgesStack();
    EdgesStack firstPull = new EdgesStack();
    EdgesStack secondPull = new EdgesStack();
    
    while(edges.getIndex() != -1)
    {
      int[] tempLink = edges.pop();
      
      //creating three new edge stacks that will be used to find available paths
      if(tempLink[0] > size)
      {
        size = tempLink[0]+1;
      }
      masterEdges.push(tempLink[0], tempLink[1]);
      firstPull.push(tempLink[0], tempLink[1]);
      secondPull.push(tempLink[0], tempLink[1]);
    }
    
    //identifies what the size of the matrix should be
    int[] visitedNodes = new int[size];
    for(int index = 0 ; index < size ; index++)
    {
      visitedNodes[index] = 0;
    }
    
    //makes every link the front, to ensure that we are accurately capturing the paths
    while(firstPull.getIndex() != -1)
    {
      EdgesStack path = new EdgesStack();
      int[] currentLink = firstPull.pop();
      visitedNodes[currentLink[0]] = 1;
      
      //pushes the first link onto the path
      path.push(currentLink[0], currentLink[1]);
      
      //cycles through all next paths to see what the option is for paths
      while(secondPull.getIndex() != -1)
      {
        int[] proposedLink = secondPull.pop();
        
        //logic showing that if a viable path if found to take it
        if(proposedLink[0] == currentLink[1])
        {
          //checks to see if the node has been visited before
          if(visitedNodes[proposedLink[1]] == 1)
          {
            //ensures that same node loops are captured
            if(currentLink[0] == currentLink[1])
            {
              completePaths.push(path);
              break;
            }
            //adds in the last link and closes the loop
            path.push(proposedLink[0], proposedLink[1]);
            completePaths.push(path);
            break;
          }
          
          //adds the link to the path
          visitedNodes[proposedLink[1]] = 1;
          path.push(proposedLink[0], proposedLink[1]);
          currentLink = proposedLink;
        }
        
        //if no next link is found, then the loop is closed
        else if(secondPull.getIndex() == -1)
        {
          completePaths.push(path);
        }
      }
      
      //resets the visited nodes
      for(int index = 0 ; index < size ; index++)
      {
        visitedNodes[index] = 0;
      }
      
      
      EdgesStack temp = new EdgesStack();
      
      //clearing out the second pull stack
      while(secondPull.getIndex() != -1)
      {
        secondPull.pop();
      }
      
      //refreshing the second pull stack
      while(masterEdges.getIndex() != -1)
      {
        int[] tempArray = masterEdges.pop();
        
        temp.push(tempArray[0], tempArray[1]);
        secondPull.push(tempArray[0], tempArray[1]);
      }
      
      //refreshing master edges stack
      while(temp.getIndex() != -1)
      {
        int[] holderArray = temp.pop();
        masterEdges.push(holderArray[0], holderArray[1]);
      }
    }
    return completePaths;
  }
  
  /*this method will return a stack with all of the edges in the matrix*/
  public static EdgesStack getEdges(Node node)
  {
    Node currentNode = node;
    EdgesStack tempStack = new EdgesStack();
    
    //while the end isn't reached
    while(currentNode.getNodeNumber() != -1)
    {
      //captures if the node is linked
      if(currentNode.getLinked() == 1)
      {
        //adds it to the stack if linked
        tempStack.push(currentNode.getNodeNumber(), currentNode.getEndingNode());
      }
      //
      currentNode = currentNode.getNextNode();
    }
    return tempStack;
  }
  
  //this method will take the information from the stack
  //and create the linked list used for the matrix
  public static Queue createLinkedList(Stack stack)
  { 
    //keeps track of the matrix size, from the file
    int matrixSize = stack.pop();
    
    //tracks the row and column number
    int column = 0;
    int row = 0;
    
    //creating a few leverage nodes
    Node previousNode = new Node();
    Node holderNode = new Node();
    holderNode.setNodeNumber(-1);
    
    //creates a queue that all of the first nodes will enter
    Queue queue = new Queue();
    
    
    while(stack.getIndex() != -1)
    {
      Node currentNode = new Node();
      //identifies the first node
      if(column == 0 && row == 0)
      {
        //sets some node specific information
        currentNode.setLinked(stack.pop());
        currentNode.setEndingNode(column);
        currentNode.setNodeNumber(row);
        //sets the previous node to the current node
        //so that it can be used in the next pass
        previousNode = currentNode;
        //pushes the previous (first) node into the queue
        queue.push(previousNode);
        //moves the column over 1
        column++;
      }
      //used for everything after the first pass
      else
      {
        //sets some node specific information
        currentNode.setLinked(stack.pop());
        currentNode.setEndingNode(column);
        currentNode.setNodeNumber(row);
        
        //takes the previous node information and links it to the just created node
        previousNode.setNextNode(currentNode);
        
        //sets the previous node to the current node
        //so that it can be used in the next pass
        previousNode = currentNode;
        
        //sets the temperary next node to the holder node
        //which as node number -1, which is used to identify
        //the end of the linked structure
        currentNode.setNextNode(holderNode);
        
        //used to move through the columns and rows to ensure
        //that the nodes are currently placed
        if(column < matrixSize-1)
        {
          column++;
        }
        else
        {
          row++;
          column = 0;
        }
      }
    }
    
    //returns the queue, which has all of the first nodes within each linked list
    return queue;
  }
  
  
  //this method takes the file information and breaks it apart by matrix
  public static StackQueue readFile(String filelocation)
  {

    //input stream
    FileReader inputStream = null;
    StackQueue stackQueue = new StackQueue();
    
    try 
    {
      //designates the file location
      inputStream = new FileReader(filelocation); // input file 
      Stack holderStack = new Stack();
      
      int c;
      //reads the file character by character
      while ((c = inputStream.read()) != -1) 
      { 
        //changes the character to an integer
        char character = (char) c;
        int characterinteger = Character.getNumericValue(character);
        
        //this if is used to identify if this is the start of a new matrix
        if(characterinteger > 1)
        {
          //purges the holder stack into the official matrix stack
          if(holderStack.getIndex() > -1)
          {
            Stack tempStack = new Stack();
            while(holderStack.getIndex() != -1)
            {
              tempStack.push(holderStack.pop());
              
            }
            stackQueue.push(tempStack);
            holderStack.push(characterinteger);
          }
          //if this is not part of the same matrix
          else
          {
            holderStack.push(characterinteger);
          }
        }
        
        //catches if the element is a 0 or a 1
        else if(characterinteger == 0 || characterinteger == 1)
        {
           holderStack.push(characterinteger);
        }
      }
      
      Stack tempStack = new Stack();
      while(holderStack.getIndex() != -1)
      {
        tempStack.push(holderStack.pop());
      }
      stackQueue.push(tempStack);
      
      //closes the read
      inputStream.close();
    } 
    catch(Exception except)
    {
      //lets me know an error occurred
      System.out.println("An error occurred.");
    }
    
    return stackQueue;
  }
}
