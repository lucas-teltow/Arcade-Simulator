/******************************************************************************

Lucas Teltow
Main.java

*******************************************************************************/
import java.util.*;
import java.io.*;
import java.lang.*;
import java.text.*;

public class Main
{
    public static BinTree treeFiller(Scanner file)
    {
        BinTree games = new BinTree();      //the binary tree being outputed
        String temp;                        //for storing the current line of input
        
        //checking if the file has at least 1 line
        if(!(file.hasNextLine()))
            return games;
        
        //loops through as long as the file has more data and stores the name of the drivers and stores the coordinates on that line
        while(file.hasNextLine())
        {
            temp = file.nextLine();
            games.insertCall(makeNode(temp));
        }//end of looping through the file
        
        return games;
    }//end of treeFiller
    
    private static Node<Game> makeNode(String input)
    {
        Node<Game> tempNode = new Node<Game>();         //the node being outputed
        Game tempGame = new Game();                     //the game being put into the node being outputed
        String[] splitInput = new String[5];            //for storing the input after splitting it by " "
        
        splitInput = input.split(", ");
        
        //setting the values of each part of the game object
        tempGame.setName(splitInput[0]);
        tempGame.setScore(Integer.parseInt(splitInput[1]));
        tempGame.setInitials(splitInput[2]);
        tempGame.setPlays(Integer.parseInt(splitInput[3]));

        tempNode.setStuff(tempGame);
        
        return tempNode;
    }//end of addNode
    
    //function to handle reading in and doing the commands in the command file
    //takes in a scanner object for the file, and a BinTree to work with, returns void, and prints to command line
    public static void commandReader(Scanner file, BinTree games)
    {
        String temp;                        //for storing the current line of input
        
        //checking if the file has at least 1 line
        if(!(file.hasNextLine()))
            return;
        
        //loops through as long as the file has more data and stores the name of the drivers and stores the coordinates on that line
        while(file.hasNextLine())
        {
            temp = file.nextLine();
            //System.out.println("Temp is: " + temp);
            System.out.println(commandHandler(temp, games));
        }//end of looping through the file
    }//end of commandReader
    
    private static String commandHandler(String input, BinTree games)
    {
        //variables
        String output = "";                             //for storing the output of the command
        Game tempGame = new Game();                     //for making the game to be outputed
        Node<Game> tempNode = new Node<Game>();         //for storing a made node for outputing
        int i = 0;                                      //counter variable
        int quote1 = -1;                                //for storing the location of the quotes in the string
        int quote2 = -1;                                //for storing the location of the quotes in the string
        boolean outputFlag = false;                     //for checking if a function returned true or false
        
        //switch for telling which command is being called
        switch(input.charAt(0))
        {
            case '1':
                //parsing the new string to find the name
                while(i < input.length())
                {
                    if(input.charAt(i) == '"')
                        if(quote1 == -1)
                            quote1 = i;
                        else quote2 = i;
                    
                    i++;
                }//grabbing the indices of the ""
                
                //setting the name
                quote1++;
                tempGame.setName(input.substring(quote1, quote2));
                
                //setting the high score
                i = quote2 + 2;
                while(Character.isDigit(input.charAt(i)))
                {
                    output = output + input.substring(i, (i + 1));
                    i++;
                }//end of grabbing the high score
                tempGame.setScore(Integer.parseInt(output));
                
                //grabbing the initials
                i++;
                output = input.substring(i, (i + 3));
                tempGame.setInitials(output);
                
                //grabbing the plays (also updating the revenue)
                output = "";
                i = i + 4;
                while(Character.isDigit(input.charAt(i)))
                {
                    output = output + input.substring(i, (i + 1));
                    i++;
                }//end of grabbing the plays
                tempGame.setPlays(Integer.parseInt(output));
                
                tempNode.setStuff(tempGame);
                games.insertCall(tempNode);
                System.out.println("RECORD ADDED\n" + tempNode.toString());
                
                output = "";
                break;
            case '2':
                outputFlag = search(input.substring(2), games.getRoot());
                
                //checking if the node was found
                if(!outputFlag)
                {
                    System.out.println(input.substring(2) + " NOT FOUND");
                    output = "";
                }//end of if the node wasnt found
                break;
            case '3':
                edit(input, games.getRoot());
                break;
            case '4':
                Node<Game> temp = new Node<Game>();
                tempGame.setName(input.substring(2));
                temp.setStuff(tempGame);
                temp = games.delete(temp, games.getRoot(), null);
                
                //checking if the game is actually there
                if(temp == null)
                    System.out.println(input.substring(2) + " NOT FOUND");
                else System.out.println("RECORD DELETED\n" + temp.toString());
                break;
            case '5':
                if(input.charAt(2) == 'a' && input.charAt(3) == 's' && input.charAt(4) == 'c')
                    {
                    System.out.println("RECORDS SORTED ASCENDING");
                    ascSort(games.getRoot());
                    //System.out.println("Ascending");
                    }
                    
                if(input.charAt(2) == 'd' && input.charAt(3) == 'e' && input.charAt(4) == 's')
                    {
                    System.out.println("RECORDS SORTED DESCENDING");
                    desSort(games.getRoot());
                    //System.out.println("Decending");
                    }
                break;
            default:
                output = "Invalid Command\n";
                break;
        }//end of the switch statment
        //System.out.println("Command Handler");
        return output;
    }//end of commandHandler
    
    //search function so that a given term can be found in the names of the stored games
    //takes in a term to be searched for and the root of a binary tree to be searched, and returns a string that is "0" if the record isnt found
    public static boolean search(String term, Node<Game> rootNode)
    {
        //just in case
        if(rootNode == null)
            return false;
        
        boolean output = false;         //the output boolean
        boolean rightFlag = false;      //for checking if the right found the value
        boolean leftFlag = false;       //for checking if the left found the value
        
        //checking if this node has it, and if it does, printing it
        if(containString(term, rootNode.getStuff().getName()))
        {
            System.out.print(rootNode.getStuff().getName() + " FOUND\nHigh Score: " + rootNode.getStuff().getScore() + "\nInitials: " + rootNode.getStuff().getInitials() + "\nPlays: " + rootNode.getStuff().getPlays() + "\nRevenue: $");
            System.out.printf("%.2f\n\n", rootNode.getStuff().getRevenue());
            output = true;
        }//end of if the current node is contains the term
                
        //going in both directions in case either has the a node with the term
        if(rootNode.getRight() != null)
            rightFlag = search(term, rootNode.getRight());
        if(rootNode.getLeft() != null)
            leftFlag = search(term, rootNode.getLeft());
            
        if(output || rightFlag || leftFlag)
            return true;
        else return false;
    }//end of search function
    
    //helper function to check if a given term is contained within a string
    //takes in a term and a string to be checked, and returns a boolean value
    private static boolean containString(String searchTerm, String line)
    {
        int k = 0;
        int i = 0;
        boolean flag = false;
        //loops through and checks each character to see if they are the same as the first character of the search term, and if they are,then
        //it checks to see if the remaining characters of the searchTerm follow, and if they do it returns true
        for(i = 0; i < line.length(); i++)
        {
            k = 0;
            flag = false;
            
            //checking to see if the first character is the same as the current char in line
            if(line.charAt(i) == searchTerm.charAt(k))
                while(i < line.length() && k < searchTerm.length())
                {
                    flag = true;
                    
                    if(line.charAt(i) != searchTerm.charAt(k))
                    {
                        flag = false;
                        break;
                    }
                        
                    i++;
                    k++;
                }//end of checking if the rest of the search term follows the first charater of it
            
            //checking to see if the loop went all the way to the end
            if(flag)
                return true;
        }//end of checking each element in the given line
        
        return false;
    }//end of containString
    
    //edit a record function
    //takes in the data needed to store 
    public static void edit(String update, Node<Game> root)
    {
        if(root == null)
            return;
        if(root.getStuff() == null)
            return;
        
        //System.out.println(update);
        
        //variables
        String name = "";       //name of the thing to be searched for
        int fieldNum = -1;      //the field value to edit
        String newVal = "";     //the new thing to update
        int i = 0;              //the counter variable
        
        
            while(update.charAt(i) != '"')
            {
                i++;
                
                if(i == update.length())
                    return;
            }//looping through till it hits a "
            
            i++;
            while(update.charAt(i) != '"')
            {
                name = name + update.charAt(i);
                i++;
                
                if(i == update.length())
                    return;
            }//end of looping through to get the name
            
            fieldNum = Integer.parseInt(update.substring((i + 2), (i + 3)));
            
            i = i + 4;
            
            if(i == update.length())
                return;
                
            while(i < update.length())
            {
                newVal = newVal + update.charAt(i);
                i++;
            }//end of grabbing the new value into a string
            
            //if this is correct node, other wise it goes onto the next nodes
            if(root.getStuff().getName().compareTo(name) == 0)
            {
                switch (fieldNum)
                {
                    case 1:
                        root.getStuff().setScore(Integer.parseInt(newVal));
                        System.out.print(name + " UPDATED\nUPDATE TO high score - VALUE " + newVal + "\nHigh Score: " + root.getStuff().getScore() + "\nInitials: " + root.getStuff().getInitials() + "\nPlays: " + root.getStuff().getPlays() + "\nRevenue: $");
                        System.out.printf("%.2f\n", root.getStuff().getRevenue());
                        break;
                    case 2:
                        root.getStuff().setInitials(newVal);
                        System.out.print(name + " UPDATED\nUPDATE TO initials - VALUE " + newVal + "\nHigh Score: " + root.getStuff().getScore() + "\nInitials: " + root.getStuff().getInitials() + "\nPlays: " + root.getStuff().getPlays() + "\nRevenue: $");
                        System.out.printf("%.2f\n", root.getStuff().getRevenue());
                        break;
                    case 3:
                        root.getStuff().setPlays(Integer.parseInt(newVal));
                        System.out.print(name + " UPDATED\nUPDATE TO plays - VALUE " + newVal + "\nHigh Score: " + root.getStuff().getScore() + "\nInitials: " + root.getStuff().getInitials() + "\nPlays: " + root.getStuff().getPlays() + "\nRevenue: $");
                        System.out.printf("%.2f\n", root.getStuff().getRevenue());
                        break;
                    default:
                        //System.out.println("Hi");
                        return;
                }//end of the switch 
                
                
                return;
            }//end of if the root is the node to be updated
            else
            {
                if(root.getRight() != null && name.compareTo(root.getStuff().getName()) > 0)
                    edit(update, root.getRight());
                else if(root.getLeft() != null)
                    edit(update, root.getLeft());
                else return;
            }//end of if new node is being grabbed
    }//end of edit function
    
    //function for printing out a given BinTree in ascending order
    //takes in a node for the root and prints all output to command line, returning void
    private static void ascSort(Node<Game> current)
    {
        //if current is null
        if(current == null)
            return;
        
        //going left
        if(current.getLeft() != null)
            ascSort(current.getLeft());
        
        //current node
        System.out.print(current.getStuff().toString(0));
        
        //going right
        if(current.getRight() != null)
            ascSort(current.getRight());
    }//end of ascSort
    
    //function for printing out a given BinTree in decending order
    //takes in a node for the root and prints all output to command line, returning void
    private static void desSort(Node<Game> current)
    {
        //if current is null
        if(current == null)
            return;
        
        //going right
        if(current.getRight() != null)
            desSort(current.getRight());
        
        //current node
        System.out.print(current.getStuff().toString(0));
        
        //going left
        if(current.getLeft() != null)
            desSort(current.getLeft());
    }//end of desSort
    
    //for checking/testing my tree
    //takes in a tree and returns a string that is basically an error code (or lack there of)
    public static String treeTest(BinTree games)
    {
        String output = games.toString();
        
        // if(games.getRoot() == null)
        //     return "Nothing";
        // else
        // {
        //     Node current = games.getRoot();
        //     if(current.getRight() != null)
        //         output = output + "Right";
        //     if(current.getLeft() != null)
        //         output = output + "Left";
        // }//end of if there is a root
        return output;
    }//end of treeTest
    
    //for outputing to the file, mostly just calles the recursive function
    //takes in a tree to be added to the file, and returns a string
    public static void fileString(BinTree games, PrintWriter output)
    {
        fileStringRecursive(games.getRoot(), output);
    }//end of fileString function
    
    //helper function for traversing the tree and returning the relevent information
    //takes in a node and returns a string
    private static void fileStringRecursive(Node<Game> n, PrintWriter output)
    {
        
        if(n == null || n.getStuff() == null)
            return;
        
        //making sure to check if the node has a game before trying to read from it
        if(n.getStuff() != null)
            output.print(((Game)n.getStuff()).toString(1));
            
        if(n.getLeft() != null && n.getLeft().getStuff() != null)
            fileStringRecursive(n.getLeft(), output);
            
        if(n.getRight() != null && n.getRight().getStuff() != null)
            fileStringRecursive(n.getRight(), output);
    }//end of fileStringRecursive function
    
	public static void main(String[] args) throws IOException
	{
		//variables
        Scanner in = new Scanner(System.in);    //scanner for the console inputs
        String fileName;                        //for storing the name of the input files
        BinTree games = new BinTree();          //the tree with the data in it
        
        //getting the file name
        System.out.println("Input Inital State File Name");
        fileName = in.nextLine();

        //opens input file and checks to see if it is valid
        File inFile = new File(fileName);
        if(!inFile.exists())
            return;
        Scanner inFileScanner = new Scanner(inFile);
        
        //filling the binary tree
        games = treeFiller(inFileScanner);
        
        //printing out the filled binary tree
        System.out.println(games.toString() + "\n");
        
        //getting the file for the commands
        //getting the command file name
        System.out.println("Input Commands File Name");
        fileName = in.nextLine();
        
        //opens command file and checks to see if it is valid
        File commandFile = new File(fileName);
        if(!commandFile.exists())
            return;
        Scanner commandFileScanner = new Scanner(commandFile);
        
        //calling the command function
        commandReader(commandFileScanner, games);
        
        //creating the output to a file
        PrintWriter outputFileWriter = new PrintWriter("cidercade.dat");
        Queue q = new Queue();
        games.traversal(games.getRoot(), q, outputFileWriter);
        outputFileWriter.close();
	}//end of main function
}//end of Main class

//for use with a breath first traversal
class Queue {
  //variables
  Node head;    //the head
  Node tail;    //the tail

  //constructors
  public Queue()
  {
    head = null;
    tail = null;
  }//end of default constructor

  //overloaded to have take a node
  public Queue(Node n)
  {
    head = n;
    tail = n;
  }//end of constructor(node)

  //getters and setters
  public Node getHead()
  {
    return head;
  }//end of getHead

  public Node getTail()
  {
    return tail;
  }//end of getTail

  private void setHead(Node n)
  {
    //checking to see if the head is null
    if(head == null)
    {
      head = n;
      tail = n;
      n.setNext(null);
      return;
    }//end of if the head is null
    
    n.setNext(head);
    head = n;
  }//end of setHead

  public void setTail(Node n)
  {
    tail.setNext(n);
    tail = n;
  }//end of setTail

  //for checking if the queue is empty
  public boolean isEmpty()
  {
    if(head == null) return true;
    else return false;
  }//end of isEmpty

  //add and remove functions
  public void push(Node n)
  {
    if(n == null)
      return;

    //n.setLeft(null);
    setHead(n);
  }//end of push

  public Node pop()
  {
    Node tempNode = null;
    Node tempNode2 = null;
    
    if(head == null)
    {
      return null;
    }

    if(head == tail)
    {
      tempNode = head;
      head = null;
      tail = null;
      return tempNode;
    }
    
    tempNode = head;
    while(tempNode.getNext() != tail)
    {
      tempNode = tempNode.getNext();
    }
    tempNode.setNext(null);
    tempNode2 = tail;
    tail = tempNode;

    return tempNode2;
  }//end of pop

  //peak function
  public Node peak()
  {
    if(head == null || tail == null)
      return null;
    return tail;
  }//end of peak
}//end of queue class
