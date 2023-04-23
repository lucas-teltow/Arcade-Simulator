/******************************************************************************

Lucas Teltow
BinTree.java

*******************************************************************************/
import java.util.*;
import java.io.*;
import java.lang.*;
import java.text.*;

public class BinTree
{
    /*      NOTES
    *
    *   When traversing for any reason, equals goes left
    *
    */
    
    //variables
    Node<Game> root;
    
    
    //constructors
    //defualt constructor
    public BinTree()
    {
        root = null;
    }//end of defualt constructor
    
    //overloaded constructor given a node
    public BinTree(Node<Game> n)
    {
        root = n;
    }//end of overloaded constructor for a Node
    
    
    //getter and setter functions
    public Node<Game> getRoot()
    {
        return root;
    }//end of getRoot function
    
    public void setRoot(Node<Game> n)
    {
        root = n;
    }//end of setRoot function
    
    
    //primary tree functions
    
    //insert function intial caller
    //starts to call the recursive function, used to check for if the root is empty
    public void insertCall(Node<Game> newNode)
    {
        root = insert(newNode, root);
    }//end of insertCall
    
    //insert function
    //takes in a node to be insert and a root node and returns the initally inputed node
    private Node<Game> insert(Node<Game> newNode, Node<Game> current)
    {
        //checking to see if the tree is empty atm
        if(current == null)
        {
            current = newNode;
            return current;
        }//checking if the tree is empty
        
        //checking which side of the current node the newNode needs to go to
        if(newNode.compareTo(current) > 0)
        {
            if(current.getRight() == null)
            {
                current.setRight(newNode);
            }
            else insert(newNode, current.getRight());
        }//if the newNode needs to go to the right of the current node
        else    //if the newNode needs to got to the left of the current node
        {
            if(current.getLeft() == null)
            {
                current.setLeft(newNode);
            }
            else insert(newNode, current.getLeft());
        }//end of if the newNode goes to the left
        
        return current;
    }//end of insert function
    
    //delete function
    //takes in a node to be deleted and a root node and the parent of the root node and returns void
    /*
     *  Case 1: the given tree is empty, returns null to original function
     *  Case 2: the tree only has a root
     *  Case 3: the node to be deleted is a leaf
     *  Case 4: the node has 2 children
     *  Case 5: the node has 1 child
     *  Case 6: the current node is not the one we want, so it calls delete again
     */
    public Node<Game> delete(Node<Game> badNode, Node<Game> current, Node<Game> parent)
    {
        //variables
        Node<Game> returnNode = null;         //a node pointer for the return value
        Node<Game> tempNode = null;           //for temporarily holding a node pointer
        
        //case 1, the tree is empty
        if(current == null)
            return returnNode;
            
        //if the current node is the node that needs to be deleted
        if(current.compareTo(badNode) == 0)
        {
            //checking if there is any nodes after 
            if(current.getLeft() == null && current.getRight() == null)
            {
                returnNode = current;       //setting the returnNode so we don't lose the current node
                
                //checking if this node is the root, and if so the root needs to be updated
                if(parent == null)
                {
                    if(current.getLeft() != null)
                        root = current.getLeft();
                    if(root.getRight() != null && root != current.getLeft())
                        root = current.getRight();
                    
                    return current;
                }//end of if the tree only has 1 node (case 2)
                
                //making sure that no null pointers are generated
                if(parent.getLeft() == null)
                {
                    parent.setRight(null);
                }//end of setting deleting the right leaf off the parent
                
                if(parent.getLeft().compareTo(current) == 0)
                {
                    parent.setLeft(null);
                }//end of if the current leaf is on the left
                else parent.setRight(null);
                
                return returnNode;
            }//end of if there is no node after the current node (case 2 or 3)
            
            //checking if the current node has 2 children (case 4)
            if(current.getLeft() != null && current.getRight() != null)
            {
                tempNode = findSuccessor(current.getRight());
                returnNode.setStuff(current.getStuff());
                
                //checking if the successor is a leaf or if it has a right child
                if(tempNode.getRight() == null)
                {
                    //making sure that no null pointers are generated
                    if(parent != null)
                    {
                    if(parent.getLeft() == null)
                    {
                        parent.setRight(tempNode);
                    }//end of setting deleting the right leaf off the parent
                
                    if(parent.getLeft().compareTo(current) == 0)
                    {
                        parent.setLeft(tempNode);
                    }//end of if the current leaf is on the left
                    else parent.setRight(tempNode);
                    }//end of if the parent isnt null
                    
                    tempNode.setRight(returnNode.getRight());
                    returnNode.setRight(null);
                    return returnNode;
                }//end of if the successor is a leaf
                
                //otherwise we need to swap the location of the successor and the current node and then call the delete again
                current.setStuff(tempNode.getStuff());
                tempNode.setStuff(returnNode.getStuff());
                
                return delete(badNode, parent, null);
            }//end of if the current node has 2 children
            
            //if the current node has only 1 child (case 5)
            if(current.getRight() == null)
            {
                //checking if the current node is the left or right child of the parent
                if(parent != null)
                {
                    if(parent.getRight() != null)
                        if(parent.getRight().compareTo(current) == 0)
                        {
                            parent.setRight(current.getLeft());
                            current.setLeft(null);
                            return current;
                        }//end of if the current node is the right child
                
                    //if the current node is the left child
                    parent.setLeft(current.getLeft());
                    current.setLeft(null);
                    return current;
                }//end of if the parent isnt null
                
                current.setRight(null);
                root = null;
                return current;
            }//end of if the current node only has a left node
            
            //if the current node only has a right node
            //checking if the current node is the left or right child of the parent
            if(parent != null)
            {
                if(parent.getRight() != null)
                    if(parent.getRight().compareTo(current) == 0)
                    {
                        parent.setRight(current.getRight());
                        current.setRight(null);
                        return current;
                    }//end of if the current node is the right child
                
                //if the current node is the left child
                parent.setLeft(current.getRight());
                current.setRight(null);
                return current;
            }//end of if the parent isnt null
            
            current.setRight(null);
            root = null;
            return current;
        }//end of if the current node is the one that needs to be deleted
        
        tempNode = delete(badNode, current.getRight(), current);
        
        if(tempNode != null)
            return tempNode;
            
        tempNode = delete(badNode, current.getLeft(), current);
        
        if(tempNode != null)
            return tempNode;
        
        return null;
    }//end of delete function
    
    //a helper function for finding the successor of a given node (the largest node of the right subtree)
    //takes in the node to the right of the thing we need the successor of
    public Node<Game> findSuccessor(Node<Game> tree)
    {
        Node<Game> returnNode = new Node<Game>();
        returnNode = tree;
        
        //looping through until there is no more
        while(returnNode.getLeft() != null)
        {
            returnNode = returnNode.getLeft();
        }//end of loop
        
        return returnNode;
    }//end of findSuccessor function
    
    //for traversing and printing the output of a breath first traversal to the output file
    //takes in a root node, a queue to interact with, and a printwriter to 
    public void traversal(Node root, Queue data, PrintWriter output)
    {     
      
      if(root == null)
        return;

      data.push(root);
      
      while(!data.isEmpty())
      {
        Node temp = data.pop();
        output.print(((Game)temp.getStuff()).toString(1));
        
        if(temp.getLeft() != null)
          data.push(temp.getLeft());
        
        if(temp.getRight() != null)
          data.push(temp.getRight());
      }
    }
    
    //tostring function
    //takes in the current node as input and returns a string
    public String toString(Node<Game> current)
    {
        String output = "";      //for storing the output
        
        if(current == null)
            return output;
        
        output = output + toString(current.getLeft());
        output = output + current.toString();
        output = output + toString(current.getRight());
        return output;
    }//end of toString(node) function
    
    //overriden tostring to take no inputs, just calls the correct tostring
    //takes in nothing and returns a string
    public String toString()
    {
        String output = toString(root);
        return output;
    }//end of toString function
}//end of BinTree class