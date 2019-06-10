/*
Reed Salus
Ramirez CS401, Tues/Thurs 1pm

RPList class for Assignment 3
Initialization of the RPList will attempt to create an RPList from a data file
*/
import java.io.*;
import java.util.*;

public class RPList
{
	private String fName;
	private int playerAmount;
	private RoulettePlayer [] playerArray;
	
	public RPList(String fileName)
	{
		fName = fileName;
		try
		{
			Scanner scanFile = new Scanner(new FileInputStream("players.txt"));

			playerAmount = Integer.parseInt(scanFile.nextLine());
			String[] playerData = new String[playerAmount];
		
			for(int i = 0; playerAmount > i; i++)		//Scans all the player data into a string array
			{
				playerData[i] = scanFile.nextLine();
			}
			
			playerArray = new RoulettePlayer[playerAmount];
			
			for(int i = 0; playerData.length > i; i++)		//Begins translating player data into usable values, and then into RoulettePlayer objects 
			{
				String[] indivData = new String[8];
				for(int ii = 0; indivData.length > ii; ii++)		//Breaks down each playerData block into its individual data values (name, money, questions, etc)
				{
					int end = playerData[i].indexOf(",");
					if(end == -1){end = playerData[i].length();}
					indivData[ii] = playerData[i].substring(0, end);
					playerData[i] = playerData[i].substring(playerData[i].indexOf(",")+1, playerData[i].length());
					if(ii > 3){
						if(indivData[3].equals(indivData[4])){indivData[4] = null; indivData[5] = null; indivData[6] = null; indivData[7] = null; ii = 7;}
					}
				}
				playerArray[i] = new RoulettePlayer(indivData[0], indivData[1], Double.parseDouble(indivData[2]), Double.parseDouble(indivData[3]));	//Creates RoulettePlayer Objects
				if(indivData[4] != null)	//Adds questions into an RP object when available
				{
					Question Q1 = new Question(indivData[4], indivData[5]);
					Question Q2 = new Question(indivData[6], indivData[7]);
					Question[] tempQArr = new Question[2];
					tempQArr[0] = Q1;
					tempQArr[1] = Q2;
					playerArray[i].addQuestions(tempQArr);
				}
			}
			scanFile.close();
		}
	 	catch (FileNotFoundException e) {System.out.println("File not found, exiting"); System.exit(0);}
	}
	
	public String toString(){
		StringBuilder str = new StringBuilder();
		for(int i = 0; playerArray.length > i; i++)
		{
			 if(playerArray[i] != null){str.append(playerArray[i].toString() + "\n");}
		}
		return str.toString();
	}
	
	public int getSize(){
		return playerAmount;
	}
	
	public int getASize(){
		return playerArray.length;
	}
	
	public boolean add(RoulettePlayer p){
		boolean duplicate = checkId(p.getName());
		if(playerAmount == playerArray.length && duplicate == false){
			RoulettePlayer [] nArr = new RoulettePlayer[playerArray.length*2];
			for(int i = 0; playerArray.length > i; i++)
			{
				nArr[i] = playerArray[i];
			}
			playerArray = nArr;
		}
		
		boolean result = false;
		if(duplicate == false){
			playerArray[playerAmount+1] = p;
			playerAmount++;
			result = true;
		}
		return result;
	}
	
	public boolean checkId(String ID){
		boolean result = false;
		for(int i = 0; playerArray.length > i; i++)
		{
			if(playerArray[i] != null){
				if(playerArray[i].getName().equals(ID)){result = true;}
			}
		}
		return result;
	}
	
	public int getIndex(String ID){
		int result = -1;
		for(int i = 0; playerArray.length > i; i++)
		{
			if(playerArray[i] != null){
				if(playerArray[i].getName().equals(ID)){result = i;}
			}
		}
		return result;
	}
	
	public RoulettePlayer getPlayerPassword(String ID, String pass){
		int index = -1;
		for(int i = 0; playerArray.length > i; i++)
		{
			if(playerArray[i] != null){
				if(playerArray[i].getName().equals(ID) && playerArray[i].getPassword().equals(pass)){index = i;}
			}
		}
		RoulettePlayer nPlayer = null;
		if(index != -1){nPlayer = playerArray[index];}
		return nPlayer;
	}
	
	public String[] getQuestions(String ID){
		int index = getIndex(ID);
		String[] questions = new String[2];
		if(index != -1){questions = playerArray[index].getQuestions();}
		return questions;
	}
	
	public RoulettePlayer getPlayerQuestions(String ID, Question[] questArr){
		int index = getIndex(ID);
		boolean equal = false;
		Question[] impArr = playerArray[index].getQuestionArray();		//custom method call which returns a question array for the rouletteplayer obj
		
		if(impArr != null){
			for(int i = 0; questArr.length > i; i++)
			{
				if(questArr[i].equals(impArr[i])){equal = true;}
				else{equal = false;}
			}
			
			if(equal == true){return playerArray[index];}
			else{return null;}
		}
		else{
			return null;
		}	
	}
	
	public void saveList(){
		try
		{
			PrintWriter exporter = new PrintWriter(fName);
			exporter.println(playerAmount);
			for(int i = 0; playerArray.length > i; i++)
			{
				if(playerArray[i] != null){exporter.println(playerArray[i].saveString());}
			}
			exporter.close();
		}
		catch(IOException e){System.out.println("Unable to export file");}
	}
}