/******************************************************************************

Lucas Teltow
Node.java
Node for a binary search tree

*******************************************************************************/

public class Node<Data extends Comparable<Data>> implements Comparable<Node<Data>> 
{
    //variables
    private Node<Data> left;
    private Node<Data> right;
    private Node<Data> next;
    private Data stuff;
    
    //constructors
    //default constructor
    public Node()
    {
        left = null;
        right = null;
        stuff = null;
    }//end of default constructor
    
    //input a driver object constructor
    public Node(Data d)
    {
        left = null;
        right = null;
        stuff = d;
    }//end of constructor with a driver input
    
    //input another node object constructor
    public Node(Node<Data> n)
    {
        left = n.getLeft();
        right = n.getRight();
        stuff = ((Data)n.getStuff());
    }//end of constructor with another node input
    
    
    //getter methods
    //returns the driver object in the node
    public Data getStuff()
    {
        return stuff;
    }//end of getStuff
    
    //returns the next object
    public Node getLeft()
    {
        return left;
    }//end of end of getLeft
    
    //returns the previous object
    public Node getRight()
    {
        return right;
    }//end of getRight
    
    
    //setter methods
    //takes in a driver to update stuff
    public void setStuff(Data d)
    {
        stuff = d;
    }//end of setStuff
    
    //takes in a node to update the next pointer
    public void setRight(Node<Data> n)
    {
        right = n;
    }//end of setRight
    
    //takes in a node to update the previous pointer
    public void setLeft(Node<Data> n)
    {
        left = n;
    }//end of setLeft
    
    //for use in a queue, for getting the next
    public Node getNext()
    { return next;  }//end of getNext
    
    //for use in a queue, setting the next
    public void setNext(Node n)
    {  next = n;    }//end of setNext
    
    //toString method
    public String toString()
    {
        return stuff.toString();
    }//end of the toString method
    
    //compare function
    @Override public int compareTo(Node<Data> n)
    {
        return stuff.compareTo(n.getStuff());
    }//end of compareTo function
}//end of node class