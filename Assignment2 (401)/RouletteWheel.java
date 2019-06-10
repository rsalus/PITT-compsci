/*
Reed Salus
Ramirez CS401, Tues/Thurs 1pm

Assignment 2 project for CS401
RouletteWheel class which handles all bet/spinning logic. I recognized that there was a pattern in the spinWheel values
allowing for a small if/else field to handle all cases. In order for the checkBet method to function I needed to have
the color/range/parity/value variables to be global, and constantly updated. I handled the latter through some short methods.
checkBet uses a switch statement to return the required numbers, generated from the RouletteBet object passed into it.
 */

import java.util.Random;

public class RouletteWheel 
{
	private RColors color = null;
	private RRanges range = null;
	private RParities parity = null;
	private int value = 0;
	
	public RouletteResult spinWheel()
	{
		Random rand = new Random();
		int spinValue = rand.nextInt(37);
		
		if(spinValue == 0){				//Number logic. Basically 3 main cases: 0 which has its own unique properties, 1-10 & 19-28 which share the same properties except for parity, and
			color = RColors.Green;		//11-18 & 29-36 which also share the same properties except for their parity. Thus I don't have to check for 37 cases
			parity = RParities.None;
			range = RRanges.None;
		}
		else if(spinValue <= 10 || spinValue >= 19 && spinValue < 29){
			if(spinValue % 2 == 0){
				color = RColors.Black;
				parity = RParities.Even;
			}
			else{
				color = RColors.Red;
				parity = RParities.Odd;
			}
			
			if(spinValue < 11){range = RRanges.Low;}
			else{range = RRanges.High;}
		}
		else if(spinValue >= 11 && spinValue < 19 || spinValue > 28){
			if(spinValue % 2 == 0){
				color = RColors.Red;
				parity = RParities.Even;
			}
			else{
				color = RColors.Black;
				parity = RParities.Odd;
			}
			
			if(spinValue < 19){range = RRanges.Low;}
			else{range = RRanges.High;}
		}
		
		updateColor(color);		//Updates global values to be passed to checkBet method
		updateRange(range);
		updateParity(parity);
		updateValue(spinValue);
		return new RouletteResult(color, range, parity, spinValue);
	}
	
	public int checkBet(RouletteBet b)
	{
		RBets betType = b.getBetType();
		String betValue = b.getBetValue();
		int returnValue = 0;
		
		switch(betType)		//Checks if bet is winner by parsing enum bet types to usable types
		{
			case Value:
				int parsedValue = Integer.parseInt(betValue);
				if(parsedValue == value){returnValue = 35;}
				else{returnValue = 0;}
				break;
			case Color:
				RColors parsedColor = RColors.valueOf(betValue);
				if(parsedColor.equals(color)){returnValue = 1;}
				else{returnValue = 0;}
				break;
			case Range:
				RRanges parsedRange = RRanges.valueOf(betValue);
				if(parsedRange.equals(range)){returnValue = 1;}
				else{returnValue = 0;}
				break;
			case Parity:
				RParities parsedParity = RParities.valueOf(betValue);
				if(parsedParity.equals(parity)){returnValue = 1;}
				else{returnValue = 0;}
				break;
		}
		
		return returnValue;
	}
	
	private void updateColor(RColors c){
		color = c;
	}
	private void updateRange(RRanges r){
		range = r;
	}
	private void updateParity(RParities p){
		parity = p;
	}
	private void updateValue(int v){
		value = v;
	}
}