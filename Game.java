/******************************************************************************

Lucas Teltow
Game.java

*******************************************************************************/
public class Game implements Comparable<Game>
{
    //variables
    String name;
    int highScore;
    String initials;
    int plays;
    double revenue;
    
    
    //constructors
    //default constructor
    public Game()
    {
        name = "";
        highScore = 0;
        initials = "";
        plays = 0;
        revenue = 0.0;
    }//end of default constructor
    
    //overloaded constructor taking in a name
    public Game(String n)
    {
        name = n;
        highScore = 0;
        initials = "";
        plays = 0;
        revenue = 0.0;
    }//end of overloaded constructor for a string
    
    //getters and setters
    public String getName()
    {
        return name;
    }//end of getName
    
    public int getScore()
    {
        return highScore;
    }//end of getScore
    
    public String getInitials()
    {
        return initials;
    }//end of getInitials
    
    public int getPlays()
    {
        return plays;
    }//end of getPlays
    
    public double getRevenue()
    {
        return revenue;
    }//end of getRevenue
    
    public void setName(String n)
    {
        name = n;    
    }//end of setName
    
    public void setScore(int n)
    {
        highScore = n;    
    }//end of setScore
    
    public void setInitials(String n)
    {
        initials = n;    
    }//end of setInitials
    
    public void setPlays(int n)
    {
        plays = n;
        revenue = plays * .25;
    }//end of setPlays
    
    
    //actual functions:
    //tostring
    //takes in nothing and outputs a String
    public String toString()
    {
        String output = "Name: " + name + "\nHigh Score: " + highScore + "\nInitials: " + initials + "\nPlays: " + plays + "\nRevenue: $";
        
        
        double temp = revenue * 100;
        double temp2 = temp;
        
        
        //grabbing the remainder behind the second decimal point
        temp = Math.floor(temp);
        temp2 = temp2 - temp;
        
        //if the remainder is greater than .5, round up and add 1
        if(temp2 >= .5000000000000)
        {
            temp = (revenue * 100) + 1;
            temp = Math.floor(temp);
            temp = temp / 100;
        }
        else    //otherwise dont round up
        {
            temp = revenue * 100;
            temp = Math.floor(temp);
            temp = temp / 100;
        }
        output = output + temp;
        
        if(output.charAt((output.length() - 2)) == '.')
            output = output + "0";
        
        return output  + "\n";
    }//end of the toString
    
    //alt tostring for different formating
    //takes in nothing and outputs a String
    public String toString(int i)
    {
        String output = name + ", " + highScore + ", " + initials + ", " + plays + ", $";
        
        
        double temp = revenue * 100;
        double temp2 = temp;
        
        
        //grabbing the remainder behind the second decimal point
        temp = Math.floor(temp);
        temp2 = temp2 - temp;
        
        //if the remainder is greater than .5, round up and add 1
        if(temp2 >= .5000000000000)
        {
            temp = (revenue * 100) + 1;
            temp = Math.floor(temp);
            temp = temp / 100;
        }
        else    //otherwise dont round up
        {
            temp = revenue * 100;
            temp = Math.floor(temp);
            temp = temp / 100;
        }
        output = output + temp;
        
        if(output.charAt((output.length() - 2)) == '.')
            output = output + "0";
        
        return output  + "\n";
    }//end of the toString
    
    //Comparable function
    //takes in another game Object and returns an int (positive means this is larger than the input, negative vice versa, and 0 means they are even)
    @Override
    public int compareTo(Game game2) 
    {
        return name.compareTo(game2.getName());
    }//end of compareTo
}//end of the game class