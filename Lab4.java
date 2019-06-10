package cs401;

import java.util.Scanner;
import java.text.DecimalFormat;
import java.util.Random;

public class Lab4 
{
	private static int diceValue(Random rand){
		return rand.nextInt(6) + rand.nextInt(6) + 2;}
    
    private static int[] getCount(int[] arr)
    {
    	int[] countArr = new int[11];
    	for(int dice : arr){
    		countArr[dice - 2]++;
    	}
    	return countArr;
    }
    
	public static void RollDice(int amount, Random rand)
	{
		int[] diceRollTracker = new int[amount];
		for(int n = 0; n < amount; n++){
			diceRollTracker[n] = diceValue(rand);
		}
	
		DecimalFormat df = new DecimalFormat("#.00");
		int[] diceRollResults = getCount(diceRollTracker);
		for(int i = 0; i < diceRollResults.length; i++){
			double percent = ((double)(diceRollResults[i]) / (double)(amount)) * 100;
			System.out.println((i+2) + " was rolled " + diceRollResults[i] + " times, or " + df.format(percent) + "% of the total rolls");
		}
	}

	public static void main (String[] args)
	{
		Random ran = new Random();
		Scanner scan = new Scanner(System.in);
		boolean cont = true;

		while(cont == true)
		{
			System.out.println("How many times would you like to roll the dice?");
			int rollAmount = scan.nextInt();
			RollDice(rollAmount, ran);
			System.out.println("\nWould you like to continue? (y/n)");
			String answer = scan.next().toLowerCase();
			if(answer.startsWith("y")){cont = true;}
			else{cont = false;}
		}	
	}
}