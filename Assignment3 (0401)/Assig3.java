//Programmed by Reed Salus for John Ramirez CS401 (Tues & Thurs, 9:30 to 10:45)
import java.util.Scanner;

public class Assig3 
{
	public static void main (String args[])
	{
		LingoServer server = new LingoServer(args[0]);	//Creates a LingoServer object from the command line argument
		Scanner scan = new Scanner(System.in);
		int [] guessArr = new int[5];
		String answer, guess, guessStr = "";
		int guessCount = 1;
		boolean won = false;
		System.out.println("Welcome to Lingo!");
		System.out.println("Would you like to play? (Yes/No)");
		answer = scan.next();
		
		while(answer.startsWith("y") || answer.startsWith("Y"))		//Begins the loop
		{
			won = false;	//Reset variable, in case the user plays multiple times
			System.out.println("You have 5 tries to guess a 5-letter word" + "\nLetters that are in the word in the correct location are in CAPS" + "\nLetters that are in the word in the INcorrect location are in lower case" + "\nLetters that do not appear in the word are shown as hyphens");
			Lingo current = server.getLingo();	//Sets a lingo variable to the server-fed lingo object, for easier usage
			System.out.println("\nYour word begins with " + current.first());
			while(guessCount < 6 && won == false)	//The user is allowed to guess as long as they have not won and have not run out of guesses
			{
				System.out.println("Please enter guess #" + guessCount + ":");
				guess = scan.next();
				guessCount++;
				guessArr = current.guessWord(guess);	//Calls the guessWord method, and sets the array guessArr to the output
				System.out.println("Here are your results:" + "\n---------");
				for(int index = 0; index < guessArr.length; index++)	//Converts the output of guessWord into the relevant text
				{
					if(guessArr[index] == 2)
					{
						guessStr = guessStr + guess.substring(index, index + 1).toUpperCase() + " ";
					}
					else if(guessArr[index] == 1)
					{
						guessStr = guessStr + guess.substring(index, index + 1).toLowerCase() + " ";
					}
					else if(guessArr[index] == 0)
					{
						guessStr = guessStr + "-" + " ";
					}
				}
				System.out.println(guessStr);
				if(guessStr.contains("-"))	//Checks to see if the user has won or not. If he hasn't, the game continues unless they are out of guesses
				{
					won = false;
					if(guessCount >= 6)
					{
						System.out.println("Sorry, but you didn't guess the correct word. The word was " + current.toString() + ".");
					    System.out.println("Would you like to play again? (Yes/No)");
					    answer = scan.next();
					    guessCount = 1;
					    guessStr = "";
					}
				}
				else
				{
					won = true;
					System.out.println("Congratulations, you've guessed the word. It was " + current.toString() + ".");
					System.out.println("Would you like to play again? (Yes/No)");
				    answer = scan.next();
				    guessCount = 1;
				    guessStr = "";
				}
				guessStr = "";
			} 
		}
	}
}