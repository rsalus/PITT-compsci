/*
Reed Salus
Ramirez CS401, Tues/Thurs 1pm

Assignment 2 project for CS401
I used several methods for importing/exporting/creating player objects from a file
I completed the extra credit (multiple users with same first name) by naming the file after a custom userID created by the user. 
Users may not create multiple of the same userID. Users data (Name, Money) is stored inside the file.
 */

import java.io.*;
import java.util.*;

public class Assig2 
{
	private static Scanner scan = new Scanner(System.in);
	public static void main(String[] args)
	{
		double bet = 1;
		RoulettePlayer player = null;		//Imports or creates user data, uses userID to separate duplicate users
		String userID = "";
		
		System.out.println("Please select one of the following: \n1. Import previous game \n2. Create a new game");
		int answer = scan.nextInt();
		if(answer != 1 && answer != 2){System.out.println("Neither option selected. Exiting..."); System.exit(0);}
		while(answer == 1){
			System.out.println("Please enter your userID: ");
			userID = scan.next();
			File nFile = new File(userID);
			if(nFile.exists()){System.out.println("Welcome back " + userID); player = importPlayer(nFile); answer = 0;}
			else{
				System.out.println("File not found, please try again or type '2' to create a new userID");
				userID = scan.next();
				if(userID.length() < 2){answer = Integer.parseInt(userID);}
			}
		}
		if(answer == 2){
			System.out.println("Please enter your preferred userID");
			userID = scan.next();
			File nFile = new File(userID);
			if(nFile.exists()){
				while(nFile.getName().equals(userID)){
					System.out.println("That userID already exists, please enter a new one");
					userID = scan.next();
				}
			}
			player = createPlayer(userID);
		}
		double money = player.getMoney();
		double originalMoney = player.getMoney(), endMoney = 0;
		int counter = 0;
		
		while(money > 0)
		{
			System.out.println("Enter Bet (0 to quit): ");
			bet = scan.nextDouble();
			if(bet <= 0){exportPlayer(player, userID); displayOutput(counter, originalMoney, endMoney); System.exit(0);}
			else if(bet > money){System.out.println("You have " + money + " and bet " + bet + "; enter a new bet: "); bet = scan.nextDouble();}
			
			System.out.println("Enter Bet Type: [Value, Color, Parity, Range]");	//Takes bet type and creates an RBets variable
			String userBetStr = scan.next();
			RBets userBet = RBets.valueOf(userBetStr);
			System.out.print("You chose a " + userBet + " bet" + "\nEnter your " + userBet + ": ");
			String betValue = null;
			if(userBet.equals(RBets.Color)){System.out.print("[Red, Black, Green]\n"); betValue = scan.next();}
			else if(userBet.equals(RBets.Parity)){System.out.print("[Even, Odd]\n"); betValue = scan.next();}
			else if(userBet.equals(RBets.Range)){System.out.print("[Low, High]\n"); betValue = scan.next();}
			else{betValue = scan.next();}
			
			RouletteBet betObj = new RouletteBet(userBet, betValue);		//Creates bet, wheel, and result objects and runs the game
			RouletteWheel wheel = new RouletteWheel();
			RouletteResult spinResult = (wheel.spinWheel());
			counter++;
			System.out.println("\nSpinning wheel..." + "\n" + spinResult.toString());
			int betResult = wheel.checkBet(betObj);
			double modifier;
			
			switch(betResult)				//Calculates bet totals and updates them in the RoulettePlayer obj
			{
				case 0:
					System.out.println("Losing bet, sorry");
					modifier = (bet * -1);
					player.updateMoney(modifier);
					money = player.getMoney();
					endMoney = money;
					System.out.println("You lost " + bet + ", and have a total of " + money + " remaining\n");
					break;
				case 1:
					System.out.println("Even money winner!");
					modifier = (bet * 1);
					player.updateMoney(modifier);
					money = player.getMoney();
					endMoney = money;
					System.out.println("You won " + bet + ", and have a total of " + money + " remaining\n");
					break;
				case 35:
					System.out.println("Value winner!");
					modifier = (bet * 35);
					player.updateMoney(modifier);
					money = player.getMoney();
					endMoney = money;
					System.out.println("You won " + modifier + ", and have a total of " + money + " remaining\n");
					break;
			}
		}
		if(money <= 0){System.out.println("Sorry, you ran out of money!");}
		displayOutput(counter, originalMoney, endMoney);
		exportPlayer(player, userID);
	}
	private static RoulettePlayer createPlayer(String fileName)	//Creates new RoulettePlayer object, creates new file with that 
	{															//RoulettePlayer's toString method, then returns RoulettePlayer object
		RoulettePlayer newPlayer = null;
		try
		{
			System.out.println("Please enter your name: ");
			String userName = scan.next();
			System.out.println("Please enter your initial capital: ");
			double initialMoney = scan.nextDouble();
			while(initialMoney <= 0){System.out.println("Please enter a value greater than zero: "); initialMoney = scan.nextDouble();}
			newPlayer = new RoulettePlayer(userName, initialMoney);
			
			PrintWriter toPrint = new PrintWriter(fileName);
			toPrint.println(newPlayer.toString());
			toPrint.close();
		}
		catch(IOException e){System.out.println("Error: Could not access file");}
		
		return newPlayer;
	}
	
	private static RoulettePlayer importPlayer(File f)		//Creates RoulettePlayer from file, returns RoulettePlayer object
	{
		RoulettePlayer importPlayer = null;
		try
		{
			Scanner scanFile = new Scanner(f);
			String fileContent = scanFile.nextLine();
			scanFile.close();
			double playerMoney = Double.parseDouble(fileContent.substring(fileContent.indexOf("y:")+2, fileContent.length()));
			String playerName = fileContent.substring(fileContent.indexOf("e:")+3, fileContent.indexOf(","));

			importPlayer = new RoulettePlayer(playerName, playerMoney);
		}
		catch (IOException e) {System.out.println("Error: Could not access file");}
		
		return importPlayer;
	}
	private static void exportPlayer(RoulettePlayer p, String ID)		//Exports player data to file
	{
		try 
		{	
			PrintWriter exporter = new PrintWriter(ID);
			exporter.println(p.toString());
			exporter.close();
		} 
		catch (IOException e) {System.out.println("Unable to export player data to file");}
	}
	private static void displayOutput(int counter, double originalMoney, double endMoney)
	{
		System.out.println("You played " + counter + " round(s) \nYou started with $" + originalMoney + "\nYou ended with $" + endMoney);
		double netWin = endMoney - originalMoney;
		if(netWin < 0){System.out.println("You lost $" + (netWin * -1));}
		else{System.out.println("You won $" + netWin);}
	}
}