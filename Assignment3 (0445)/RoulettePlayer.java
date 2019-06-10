/*
Reed Salus
Ramirez CS401, Tues/Thurs 1pm

Modified RoulettePlayer class for Assignment 3
RoulettePlayer class which is used to create the required player object. 
Added various functionality such as payback and borrow methods
 */


public class RoulettePlayer
{
	private String name, password;
	private double money, debt;
	private Question[] pQuest;
	
	public RoulettePlayer(String nameIn, double moneyIn){
		name = nameIn;
		money = moneyIn;
	}
	
	public RoulettePlayer(String nameIn, String passwordIn, double moneyIn, double debtIn){
		name = nameIn;
		password = passwordIn;
		money = moneyIn;
		debt = debtIn;
	}
	
	public RoulettePlayer(String nameIn, String passwordIn){
		name = nameIn;
		password = passwordIn;
	}
	
	public void updateMoney(double delta){
		money = delta + money;
	}
	
	public double getMoney(){
		return money;
	}
	
	public double getDebt(){
		return debt;
	}

	public String getName(){
		return name;
	}
	
	public String toString(){
		return new String("Name: " + name + ", Money: " + money + ", Debt: " + debt);
	}
	
	public void setPassword(String passwordIn){
		password = passwordIn;
	}
	
	public String getPassword(){
		return password;
	}
	
	public void borrow(double amount){
		money = amount + money;
		debt = amount + debt;
	}
	
	public void payBack(double amount){
		if(debt >= amount && money >= amount){
			money = money - amount;
			debt = debt - amount;
		}
		else if(amount > debt && money >= amount){
			System.out.println(amount + " was greater than total debt of " + debt + ". Paying " + debt);
			money = money - (amount - debt);
			debt = 0;
		}
		else if(amount > money && debt >= amount){
			System.out.println(amount + " was greater than total money of " + money + ". Paying " + money);
			debt = debt - (amount - money);
			money = 0;
		}
	}
	
	public boolean equals(RoulettePlayer other){
		if(name.equals(other.getName()) && password.equals(other.password)){return true;}
		else{return false;}
	}
	
	public void showAllData(){
		System.out.println("Name: " + name + "\nPassword: " + password + "\nMoney: " + money + "\nDebt: " + debt);
		if(pQuest != null){System.out.println("Q: " + pQuest[0].getQ() + " A: " + pQuest[0].getA() + "\nQ: " + pQuest[1].getQ() + " A: " + pQuest[1].getA());}
		else{System.out.println("Questions: None");}
	}
	
	public void addQuestions(Question [] quest){
		pQuest = new Question[(quest.length)];
		for(int i = 0; quest.length > i; i++)
			pQuest[i] = quest[i];
	}
	
	public String[] getQuestions(){
		if(pQuest != null){
			String [] str = new String[pQuest.length];
			for(int i = 0; pQuest.length > i; i++)
				str[i] = pQuest[i].getQ();
			return str;
		}
		else{
			return null;
		}
	}
	
	public Question[] getQuestionArray(){			//returns question array for rp obj, used in the getPlayerQuestions method in RPList
		if(pQuest != null){
			Question[] quest = new Question[pQuest.length];
			for(int i = 0; pQuest.length > i; i++)
				quest[i] = new Question(pQuest[i].getQ(), pQuest[i].getA());
			return quest;
		}
		else{
			return null;
		}
	}
	
	public String saveString(){
		StringBuilder nStr = new StringBuilder();
		nStr.append(name + "," + password + "," + money + "," + debt);
		if(pQuest != null){nStr.append("," + pQuest[0].getQ() + "," + pQuest[0].getA() + "," + pQuest[1].getQ() + "," + pQuest[1].getA());}
		return nStr.toString();
	}
	
	public boolean matchQuestions(Question[] quest){
		boolean tfVar = false;
		if(pQuest.length == quest.length){
			for(int i = 0; quest.length > i; i++){
				if(pQuest[i].getQ().equals(quest[i].getQ()) && pQuest[i].getA().equals(quest[i].getA())){tfVar = true;}
			}
		}
		return tfVar;
	}
	
	public boolean hasMoney(){
		boolean aboveZero = true;
		if(money <= 0){aboveZero = false;}
		return aboveZero;
	}
}