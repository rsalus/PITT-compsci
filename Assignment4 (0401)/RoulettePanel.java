/*
Reed Salus
Ramirez CS401, Tues/Thurs 1pm
*/
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class RoulettePanel extends JPanel implements Activatable
{
	private JButton makeBet, spinWheel, showInfo, quit;
	private JLabel messageLabel;
	private JPanel buttonPanel, wheelPanel;
	private RouletteWheel theWheel;
	private Activatable activ = this;
	private GameInterface game;
	private RoulettePlayer player;
	private boolean madeBet = false;
	private double betAmount = 0, money, modifier;
	private String betValue; 
	private RBets betType;
	
	public RoulettePanel(RoulettePlayer rp, GameInterface gi){
		//Initializations
		game = gi;
		player = rp;
		money = player.getMoney(); 
		JOptionPane.showMessageDialog(this, "Welcome to Roulette " + rp.getName() + "!");
		ActionListener listener = new ButtonListener();
		
		//Master panel
		this.setLayout(new GridLayout(1, 2));	
		
		//Wheel subpanel
		wheelPanel = new JPanel();
		wheelPanel.setLayout(new GridLayout(1, 1));		
		theWheel = new RouletteWheel(activ);
		wheelPanel.add(theWheel);
		
		//Button subpanel
		buttonPanel = new JPanel();
		Font serif = new Font("Serif", Font.BOLD, 50);
		messageLabel = new JLabel("Welcome "+ player.getName());
		makeBet = new JButton("Make Bet");
		spinWheel = new JButton("Spin Wheel");
		showInfo = new JButton("Show Info");
		quit = new JButton("Quit");
		messageLabel.setFont(new Font("Serif", Font.PLAIN, 25));
		makeBet.setFont(serif);
		spinWheel.setFont(serif);
		showInfo.setFont(serif);
		quit.setFont(serif);
		makeBet.addActionListener(listener);
		spinWheel.addActionListener(listener);
		showInfo.addActionListener(listener);
		quit.addActionListener(listener);
		buttonPanel.setLayout(new GridLayout(5, 1));
		buttonPanel.add(messageLabel);
		buttonPanel.add(makeBet);
		buttonPanel.add(spinWheel);
		buttonPanel.add(showInfo);
		buttonPanel.add(quit);
		
		if(madeBet == false)
			spinWheel.setEnabled(false);
		
		this.add(buttonPanel);
		this.add(wheelPanel);
		this.setVisible(true);
	}
	
	private class ButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			if(e.getSource() == makeBet){
				betType = null;
				theWheel.set();
				//BET CALL
				while(betAmount <= 0 || betAmount > player.getMoney()){
					betAmount = Integer.parseInt(JOptionPane.showInputDialog("Enter Bet Amount"));
					if(betAmount < 0 || betAmount > player.getMoney())
						JOptionPane.showMessageDialog(null, "INVALID BET" + "\nYour bet was less than zero or greater than the total money you possess");
				}
				while(betType == null){betType = RBets.valueOf(JOptionPane.showInputDialog("Enter Bet Type: [Value, Color, Parity, Range]"));}
				betValue = null;
				if(betType.equals(RBets.Color)){betValue = JOptionPane.showInputDialog("Choose Type: [Red, Black]");}
				else if(betType.equals(RBets.Parity)){betValue = JOptionPane.showInputDialog("Choose Type: [Even, Odd]");}
				else if(betType.equals(RBets.Range)){betValue = JOptionPane.showInputDialog("Choose Type: [Low, High]");}
				else{betValue = JOptionPane.showInputDialog("Enter Value: ");}
				
				//Enable or disables spinwheel
				if(betAmount > 0){
					messageLabel.setText("You bet $" + betAmount + " on " + betValue.toString());
					madeBet = true;
					spinWheel.setEnabled(true);
				}
				else{madeBet = false; spinWheel.setEnabled(false);}
			}
			else if(e.getSource() == spinWheel){
				//SPIN CALL
				makeBet.setEnabled(false);
				spinWheel.setEnabled(false);
				quit.setEnabled(false);
				
				theWheel.set();
				theWheel.spin();
			}
			else if(e.getSource() == showInfo){/*INFO CALL*/ JOptionPane.showMessageDialog(null, player.toString());}
			else{/*QUIT CALL*/ game.gameOver();}
		}
	}
	
	public void activate(){
		makeBet.setEnabled(true);
		quit.setEnabled(true);
		
		RouletteBet betObj = new RouletteBet(betType, betValue);
		RouletteResult spinResult = theWheel.getResult();
		int betResult = theWheel.checkBet(betObj);
		String message = null;
		
		switch(betResult)				//Calculates bet totals and updates them in the RoulettePlayer obj
		{
			case 0:
				JOptionPane.showMessageDialog(null, spinResult.toString());
				JOptionPane.showMessageDialog(null, "Losing bet, sorry");
				modifier = (betAmount * -1);
				player.updateMoney(modifier);
				money = player.getMoney();
				message = "You have a total of " + money + " remaining";
				JOptionPane.showMessageDialog(null, "You lost $" + (modifier*-1));
				break;
			case 1:
				JOptionPane.showMessageDialog(null, spinResult.toString());
				JOptionPane.showMessageDialog(null, "Even money winner!");
				modifier = (betAmount * 1);
				player.updateMoney(modifier);
				money = player.getMoney();
				message = "You have a total of " + money + " remaining";
				JOptionPane.showMessageDialog(null, "You won $" + modifier);
				break;
			case 35:
				JOptionPane.showMessageDialog(null, spinResult.toString());
				JOptionPane.showMessageDialog(null, "Value winner!");
				modifier = (betAmount * 35);
				player.updateMoney(modifier);
				money = player.getMoney();
				message = "You have a total of " + money + " remaining";
				JOptionPane.showMessageDialog(null, "You won $" + modifier);
				break;
		}
		messageLabel.setText(message);
		if(money <= 0){makeBet.setEnabled(false); spinWheel.setEnabled(false);}
	}
}