/*
Reed Salus
Ramirez CS401, Tues/Thurs 1pm

Assignment 2 project for CS401
RoulettePlayer class which is used to create the required player object. 
 */


public class RoulettePlayer
{
	private String name;
	private double money;
	
	public RoulettePlayer(String nameIn, double moneyIn)
	{
		name = nameIn;
		money = moneyIn;
	}
	
	public void updateMoney(double delta){
		money = delta + money;
	}
	
	public double getMoney(){
		return money;
	}
	
	public String getName(){
		return name;
	}
	
	public String toString(){
		return new String("Name: " + name + ", Money: " + money);
	}
	
	public boolean hasMoney(){
		boolean aboveZero = true;
		if(money <= 0){aboveZero = false;}
		return aboveZero;
	}
}