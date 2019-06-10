//Programmed by Reed Salus for John Ramirez CS401 (Tues & Thurs, 9:30 to 10:45)
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class LingoServer 
{
	private Lingo [] lingoArray = new Lingo[10];	//Creates an array of lingo objects
    public LingoServer(String fileName)
    {
    	String fileString;
    	try						//Scans the file for words, which are subsequently added into the lingoArray as lingo objects
		{						//Surrounded by a try + catch loop, in case the file is not found
    		int i = 0;
			File lFile = new File(fileName);
			Scanner lFileScan = new Scanner(lFile);
			while(lFileScan.hasNextLine())
			{
				fileString = lFileScan.nextLine();
				Lingo fileLingo = new Lingo(fileString);
				lingoArray[i] = fileLingo;
				i++;
				if(lingoArray.length == i)
				{
					lingoArray = resize(lingoArray);		//Calls the array resize method in case the array is filled
				}
			}
			lFile.delete();
			lFileScan.close();
		}
		catch(IOException e)
		{
			System.out.println("Error -- file not found.");
		}
    }
    public boolean hasLingo()
    {
    	boolean tf = false;
    	int counter2 = 0;
    	while(counter2 < lingoArray.length && counter2 >= 0)	//Iterates through the lingoArray to see if there are any lingo objects remaining
    	{
    		if(lingoArray[counter2] != null)	//Once a lingo object is found, the while loop is exited (counter2 = -2) and the boolean is returned
    		{
    			tf = true;
    			counter2 = -2;
    		}
    		else
    		{
    			tf = false;
    		}
    		counter2++;
    	}
    	return tf;
    }
    public Lingo getLingo()
    {
    	int counter = 1, counter4 = 0, counter3 = 0, randNum;
		boolean tf2 = true;
    	Random rand = new Random();
    	Lingo fetcher;
    	String fStore = null;
    	while(counter == 1 && tf2 == true)		
    	{
    		randNum = rand.nextInt(lingoArray.length);	//Generates a random number based on the length of the lingoArray
    		fetcher = lingoArray[randNum];		//Lingo fetcher is set to the lingo object located at the index of said random number in lingoArray
    		if(fetcher != null)					//If the location is not null, fetcher is converted to a string and the location is then set as null
    		{
    			fStore = fetcher.toString();
    			lingoArray[randNum] = null;	
    			counter = 0;					//The while loop is then exited, having found a valid lingo object
    		}
    		counter4++;							//Until a valid lingo object is found, the program continues to iterate through the array
    		if(counter4 >= lingoArray.length)	//Once counter4 becomes equal or greater than the length of lingo array, the program makes a check to
    		{									//see if there are any valid lingo objects in the array. This is more efficient than checking every time
    			while(counter3 < lingoArray.length && counter3 >= 0)
            	{
            		if(lingoArray[counter3] != null)
            		{
            			tf2 = true;
            			counter3 = -2;
            		}
            		else
            		{
            			tf2 = false;
            		}
            		counter3++;
            	}
    		}
    	}
    	fetcher = new Lingo(fStore);	//fetcher is reconverted into a lingo object
    	return fetcher;
    }
    public String toString()
    {
    	String statement;
    	int count = 0, placeholder;
    	for(int ip = 0; ip < lingoArray.length; ip++)		//Counts the objects through a reverse if/else field
    	{
    		if(lingoArray[ip] == null || lingoArray[ip].equals(""))
    		{
    			placeholder = 0;	//Nothing happens
    		}
    		else
    		{
    			count++;	//Count is increased
    		}
    	}
    	statement = "There are " + count + " Lingo objects in LingoServer, and the capacity of LingoServer is " + lingoArray.length + ".";
    	return statement;
    }
    public static Lingo [] resize(Lingo [] oldLingo)	//Resize lingo method, extremely similar to the one in resizedemo.java
	{
		int oldL = oldLingo.length;
		int newL = 2 * oldL;
		Lingo [] newLingo = new Lingo[newL];
		for (int i = 0; i < oldLingo.length; i++)
		{
			newLingo[i] = oldLingo[i];
		}
		return newLingo;
	}
}