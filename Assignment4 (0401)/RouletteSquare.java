/*
Reed Salus
Ramirez CS401, Tues/Thurs 1pm
*/
import java.awt.*;
import javax.swing.*;

public class RouletteSquare extends JLabel
{
	private int value;
	private String color, range, parity;
	private boolean cVar;
	private JLabel number2;
	
	public RouletteSquare(int val){
		value = val;
		this.setLayout(new GridLayout(1, 1));
		
		number2 = new JLabel();
		number2.setText(String.valueOf(value));
		number2.setFont(new Font("Serif", Font.BOLD, 50));
		number2.setForeground(getColorType(getColor()));
		number2.setHorizontalAlignment(CENTER);
		number2.setOpaque(true);
		number2.setBackground(Color.WHITE);

		this.setPreferredSize(new Dimension(75, 75));
		this.add(number2);
	}

	public boolean isChosen(){return cVar;}
	
	public void unChoose(){
		cVar = false;
		number2.setBackground(Color.WHITE);
	}

	public void choose(){
		cVar = true;
		number2.setBackground(Color.CYAN);
	}

	public String getValue(){return String.valueOf(value);}

	public String getColor(){
		getData();
		return color;
	}

	public Color getColorType(String color){
		Color nCol = null;
		switch(color)
		{
			case "Red":
				nCol = Color.RED;
				break;
			case "Black":
				nCol = Color.BLACK;
				break;
			case "Green":
				nCol = Color.GREEN;
				break;
		}
		return nCol;
	}
	
	public String getParity(){
		getData();
		return parity;
	}

	public String getRange(){
		getData();
		return range;
	}
	private void getData() 
	{
		if(value == 0){				//Number logic. Basically 3 main cases: 0 which has its own unique properties, 1-10 & 19-28 which share the same properties except for parity, and
			color = "Green";		//11-18 & 29-36 which also share the same properties except for their parity. Thus I don't have to check for 37 cases
			parity = null;
			range = null;
		}
		else if(value <= 10 || value >= 19 && value < 29){
			if(value % 2 == 0){
				color = "Black";
				parity = "Even";
			}
			else{
				color = "Red";
				parity = "Odd";
			}
			
			if(value < 11){range = "Low";}
			else{range = "High";}
		}
		else if(value >= 11 && value < 19 || value > 28){
			if(value % 2 == 0){
				color = "Red";
				parity = "Even";
			}
			else{
				color = "Black";
				parity = "Odd";
			}
			
			if(value < 19){range = "Low";}
			else{range = "High";}
		}
	}
}