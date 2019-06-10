/*
Reed Salus
Ramirez CS401, Tues/Thurs 1pm

Assig3 class for Assignment 3
Inherits Assig2 in order to use some functionality from Assig2 (such as game code)
Login and User creation handled by their own methods
*/
import java.util.*;

public class Assig3 extends Assig2		//assig3 extends assig2 in order to use previous game code, allowing assig3 to cover mainly user login and RPlist interaction
{
	private static RPList playerList;
	private static RoulettePlayer playerCurr;
	private static Scanner scan = new Scanner(System.in);
	
	public static void main(String[] args)
	{
		playerList = new RPList(args[0]);		//RPlist created
		int counter = 1;
		
		while(playerList != null)
		{
			if(counter > 1){playerList = new RPList(args[0]);}		//if more than one round has been played, pulls new data from updated file
			//Login Phase, mostly handled by methods
			System.out.println("Welcome. Please select an option, or type any other number to exit: \n1. Login to an account \n2. Create an account");
			int answer = scan.nextInt();
			if(answer == 1){
				System.out.println("Login selected. Please enter your userID: ");
				String userID = scan.next();
				System.out.println("Please enter your password: ");
				String userPass = scan.next();
				loginUser(userID, userPass);
			}
			else if(answer == 2){
				createUser();
			}
			else{System.out.println("Neither option selected. Exiting..."); System.exit(0);}
			
			playRound(playerCurr);		//game method call
			playerList.saveList();		//saves data to file
			counter++;
			System.out.println();
		}
	}
	
	public static void loginUser(String ID, String password)		//user login method
	{
		playerCurr = playerList.getPlayerPassword(ID, password);
		
		int pAttempt = 1, idAttempt = 1;
		boolean exists = playerList.checkId(ID);
		while(exists == false && idAttempt < 2){		//ID does not exist case
			System.out.println("userID cannot be found, please try again: ");
			ID = scan.next();
			exists = playerList.checkId(ID);
			idAttempt++;
		}
		while(playerCurr == null && pAttempt < 2){	//password re-entry attempts
			System.out.println("Login failed. Please enter your password again: ");
			password = scan.next();
			playerCurr = playerList.getPlayerPassword(ID, password);
			pAttempt++;
		}
		if(pAttempt >= 2 && playerCurr == null && exists == true){	//security questions
			System.out.println("Password attempts has exceeded the limit. Please answer your security questions: ");
			scan.nextLine();
			
			Question[] questArr = new Question[2];
			String[] answerStr = new String[2];
			String[] questStr = playerList.getQuestions(ID);
			
			System.out.println(questStr[0]);
			answerStr[0] = scan.nextLine();
			System.out.println(questStr[1]);
			answerStr[1] = scan.nextLine();
			
			Question Q1 = new Question(questStr[0], answerStr[0]);
			Question Q2 = new Question(questStr[1], answerStr[1]);
			questArr[0] = Q1;
			questArr[1] = Q2;
			
			playerCurr = playerList.getPlayerQuestions(ID, questArr);
			if(playerCurr != null){		//Reset password & all cases for password resets
				System.out.println("Login successful. Please change your password: ");
				String p1 = scan.next();
				System.out.println("Enter your new password one more time to confirm: ");
				String p2 = scan.next();
				
				while(p1.equals(p2) == false){
					System.out.println("Passwords do not match. Please try again: ");
					p1 = scan.next();
					System.out.println("Enter your new password one more time to confirm: ");
					p2 = scan.next();
				}
				playerCurr.setPassword(p1); 
				System.out.println("Password updated");
			}
			else{
				System.out.println("Your account could not be retrieved. Please create a new account: ");
				createUser();
			}
		}
		else if(playerCurr != null){System.out.println("Login successful");}
		else{System.out.println("Your account could not be found. Please create an account: "); createUser();}
	}
	
	public static void createUser()		//user creation method
	{
		System.out.println("Please enter a userID: ");
		String userID = scan.next();
		boolean duplicate = playerList.checkId(userID);
		while(duplicate == true){
			System.out.println("That ID already exists, please enter a different one: ");
			userID = scan.next();
			duplicate = playerList.checkId(userID);
		}
		
		System.out.println("Please enter a password: ");
		String p1 = scan.next();
		System.out.println("Enter your new password one more time to confirm: ");
		String p2 = scan.next();
		
		while(p1.equals(p2) == false){
			System.out.println("Passwords do not match. Please try again: ");
			p1 = scan.next();
			System.out.println("Enter your new password one more time to confirm: ");
			p2 = scan.next();
		}
		
		System.out.println("Please enter your initial capital: ");
		int money = scan.nextInt();
		int debt = 0;
		
		System.out.println("Would you like to enter any security questions? (y/n)");
		String answer = scan.next().toLowerCase().substring(0, 1);
		scan.nextLine();
		Question[] questArr = null;
		if(answer.equals("y")){
			System.out.println("First Question: ");
			String[] qArr = new String[4];
			qArr[0] = scan.nextLine();
			System.out.println("First Answer: ");
			qArr[1] = scan.nextLine();
			System.out.println("Second Question: ");
			qArr[2] = scan.nextLine();
			System.out.println("Second Answer: ");
			qArr[3] = scan.nextLine();
			
			questArr = new Question[2];
			Question Q1 = new Question(qArr[0], qArr[1]);
			Question Q2 = new Question(qArr[2], qArr[3]);
			
			questArr[0] = Q1;
			questArr[1] = Q2;
		}
		
		playerCurr = new RoulettePlayer(userID, p1, money, debt);		//creates new RP obj from entered info
		if(questArr != null){playerCurr.addQuestions(questArr);}		//adds questions if they exist
		playerList.add(playerCurr);										//adds RP obj to RPList
	}
}