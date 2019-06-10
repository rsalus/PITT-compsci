//Programmed by Reed Salus for John Ramirez CS401 (Tues & Thurs, 9:30 to 10:45)
public class Lingo
{
	private String phrase;	
	private char fchar;
	
	public Lingo(String word)	//Sets private string to the input string, so it can be referenced throughout the program
	{
		phrase = word;
	}
	
	public int [] guessWord(String guess)
	{
		int [] guessInteger = new int[5];	
		int arrayVal = 0;
		char guessChar, phraseChar, lastChar = 36;
		char [] correctChars = new char[5];
		
		if(guess.length() > 5)	//Truncates any word longer than 5 characters
		{
			guess = guess.substring(0, 4);
		}
		for(int i = 0; i < guess.length(); i++)		//For loop to check the entire guess string
		{
			if(guess.charAt(i) == phrase.charAt(i))		//If the character in the guess string matches the character in the phrase at 
			{											//the specified location, sets the value in the array to 2
				guessInteger[i] = 2;
				correctChars[i] = guess.charAt(i);		//Adds any correct characters to a char array for later usage
			}
			else if(guess.charAt(i) != phrase.charAt(i))	//If the character in the guess string does not match the character in the phrase
			{												//at the specified location, does the following...
				guessChar = guess.charAt(i);				//Sets the char value in the guess string to a variable, for easier use
				for(int index = 0; index < guess.length(); index++)		//Loops through the guess string again
				{
					phraseChar = phrase.charAt(index);					//Sets the phrase character to the index of this loop
					if(guessChar == phraseChar && guessChar != lastChar && guessChar != correctChars[0] && guessChar != correctChars[1] && guessChar != correctChars[2] && guessChar != correctChars[3] && guessChar != correctChars[4])	//Tests to see if the guessChar appears in the phrase string, eliminates non-consecutive and consecutive letters in the process.	
					{
						arrayVal = 1;
						index = 5;
						lastChar = guessChar;		//Variable used to check for consecutive letters
					}
					else
					{
						arrayVal = 0;
					}
				}
				guessInteger[i] = arrayVal;			//Sets whatever the test resulted in into the array
			}
		}
		return guessInteger;
	}
	
	public char first()	
	{
		fchar = phrase.charAt(0);
		return fchar;
	}
	
	public String toString()
	{
		return phrase;
	}
}