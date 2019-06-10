/*
Reed Salus
Ramirez CS401, Tues/Thurs 1pm

I overwrote the previous RouletteWheel class required for Assignment 2 & 3. The methods are included below.
 */
import javax.swing.*;
import java.awt.*;
import java.util.*;

public class RouletteWheel extends JPanel implements Runnable
{
	private RColors color = null;
	private RRanges range = null;
	private RParities parity = null;
	private int value = 0, i;
	private Activatable activ;
	private final RouletteSquare [] squareArray = new RouletteSquare[37];
	private Boolean spun = false;
	
	public RouletteWheel(Activatable X){
		activ = X;
		createGUI();
	}
	
	public void set(){		//iterates through the roulettesquare array and unchooses them if theyre chosen
		for(int i = 0; i <= 36; i++){
			if(squareArray[i].isChosen())
				squareArray[i].unChoose();
		}
	}
	
	public void spin(){
		spun = true;
		new Thread(this).start();
	}
	
	public void run(){
		Random rand = new Random();
		int prev = -1;
		for(int k = 0; k < 8; k++)	//highlights 8 numbers before selecting an actual one
		{
			prev = i;		//Checks if previous number selected is equal to current number selected, and if so, rolls again
			i = rand.nextInt(37);
			while(prev == i)
				i = rand.nextInt(37);
			squareArray[i].choose();
			try {Thread.sleep(415);}
			catch (InterruptedException e){}
			squareArray[i].unChoose();
		}
		i = rand.nextInt(37);		//roll number
		squareArray[i].choose();
		
		updateValue(Integer.parseInt(squareArray[i].getValue()));		//updates global values for redundancy
		updateParity(getParityType(squareArray[i].getParity()));
		updateRange(getRangeType(squareArray[i].getRange()));
		updateColor(getColorType(squareArray[i].getColor()));
		activ.activate();		//spin is finished, makes activate call
	}
	
	public RouletteResult getResult(){		//returns rouletteresult based off of data from roulettesquare
		if(spun == true){
			RParities par = getParityType(squareArray[value].getParity());
			RRanges ran = getRangeType(squareArray[value].getRange());
			RColors col = getColorType(squareArray[value].getColor());
			
			updateParity(par);
			updateRange(ran);
			updateColor(col);
			
			return new RouletteResult(col, ran, par, i);
		}
		else{return null;}
	}
	
	private RColors getColorType(String colorStr){
		RColors col = null;
		switch(colorStr){
			case "Red":
				col = RColors.Red;
				break;
			case "Black":
				col = RColors.Black;
				break;
			case "Green":
				col = RColors.Green;
				break;
		}
		return col;
	}

	private RRanges getRangeType(String rangeStr){
		RRanges ran = null;
		switch(rangeStr){
			case "High":
				ran = RRanges.High;
				break;
			case "Low":
				ran = RRanges.Low;
				break;
		}
		return ran;
	}

	private RParities getParityType(String parityStr){
		RParities par = null;
		switch(parityStr){
			case "Even":
				par = RParities.Even;
				break;
			case "Odd":
				par = RParities.Odd;
				break;
		}
		return par;
	}
	
	private void createGUI(){	//Creates array of 36 roulettesquares and adds them to the panel
		super.removeAll();
		this.setLayout(new GridLayout(6, 6));
		for(int i = 0; i <= 36; i++){
			squareArray[i] = new RouletteSquare(i);
			this.add(squareArray[i]);
		}
	}

	//************** OLD METHODS FOR ASSIGNMENT 2 & 3 **************//
	
	public RouletteWheel(){
	}
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
	private void updateColor(RColors c){color = c;}
	private void updateRange(RRanges r){range = r;}
	private void updateParity(RParities p){parity = p;}
	private void updateValue(int v){value = v;}
}