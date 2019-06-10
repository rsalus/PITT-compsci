/*
Reed Salus
Ramirez CS401, Tues/Thurs 1pm

Uses 3 subpanels to create an overall login panel. Subpanels include one for the id field, one for the password field, and one for the two buttons. Ability to create and add a new rouletteplayer is included for extra credit.
*/
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class LoginPanel extends JPanel 
{
	private char[] password;
	private String userID;
	private StringBuilder pwStr;
	private JButton sButton, npButton;
	private JPasswordField pwField;
	private JTextField idField;
	private JLabel idText, pwText;
	private RPList gList;
	private LoginInterface gInterface;
	private RoulettePlayer currPlayer;

	public LoginPanel(RPList list, LoginInterface I){
		gList = list;
		gInterface = I;
		createGUI();
	}
	private void createGUI(){	
		//ID panel
		JPanel idPanel = new JPanel();
		idPanel.setLayout(new GridLayout(1, 2));
		idText = new JLabel("UserID: ");
		idField = new JTextField();
		idPanel.add(idText);
		idPanel.add(idField);
		idPanel.setVisible(true);
		
		//PW panel
		JPanel pwPanel = new JPanel();
		pwPanel.setLayout(new GridLayout(1, 2));
		pwText = new JLabel("Password: ");
		pwField = new JPasswordField();
		pwPanel.add(pwText);
		pwPanel.add(pwField);
		pwPanel.setVisible(true);
		
		//Button Panel
		JPanel bPanel = new JPanel();
		bPanel.setLayout(new BorderLayout());
		sButton = new JButton("Submit");
		sButton.setPreferredSize(new Dimension(50, 50));
		npButton = new JButton("Create User");
		npButton.setPreferredSize(new Dimension(50, 50));
		bPanel.add(sButton, BorderLayout.NORTH);
		bPanel.add(npButton, BorderLayout.SOUTH);
		
		//Font setting
		idText.setFont(new Font("Serif", Font.BOLD, 40));
		pwText.setFont(new Font("Serif", Font.BOLD, 40));
		pwField.setFont(new Font("Serif", Font.PLAIN, 20));
		sButton.setFont(new Font("Serif", Font.PLAIN, 30));
		npButton.setFont(new Font("Serif", Font.PLAIN, 30));
		
		//Add to panel
		this.setLayout(new BorderLayout());
		this.add(idPanel, BorderLayout.NORTH);
		this.add(pwPanel, BorderLayout.CENTER);
		this.add(bPanel, BorderLayout.SOUTH);
		this.setVisible(true);
		
		//Add Listener
		dataListener d = new dataListener();
		npButton.addActionListener(d);
		sButton.addActionListener(d);
		pwField.addActionListener(d);
		idField.addActionListener(d);
	}
	private void loginUser(String id, String pw){
		boolean exists = gList.checkId(id);
		if(exists != true){
			JOptionPane.showMessageDialog(this, id + " was not found");
			idField.setText("");
			pwField.setText("");
		}
		else if(exists == true){
			currPlayer = gList.getPlayerPassword(id, pw);
			if(currPlayer == null){
				JOptionPane.showMessageDialog(this, "Incorrect Password entered");
				pwField.setText("");
			}
			else{
				JOptionPane.showMessageDialog(this, "Welcome " + id + "!");
				gInterface.setPlayer(currPlayer);		//Uses the logininterfact setPlayer method
			}
		}
	}
	private void createUser(String id, String pw){
		boolean exists = gList.checkId(id);
		if(exists == true){
			JOptionPane.showMessageDialog(this, id + " already exists");
			idField.setText("");
			pwField.setText("");
		}
		else{
			double money = Double.parseDouble(JOptionPane.showInputDialog(this, "Enter your initial capital:"));
			RoulettePlayer nPlayer = new RoulettePlayer(id, pw, money, 0);
			boolean success = gList.add(nPlayer);
			if(success == true){
				JOptionPane.showMessageDialog(this, "Welcome " + id + "!");
				currPlayer = nPlayer;
				gInterface.setPlayer(currPlayer);		//Uses the logininterfact setPlayer method
			}
			else{
				JOptionPane.showMessageDialog(this, "Failed creating new user");
				idField.setText("");
				pwField.setText("");
			}	
		}
	}
	private class dataListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			if(e.getSource() == sButton){
				userID = idField.getText();
				password = pwField.getPassword();
				pwStr = new StringBuilder();
				for(int i = 0; password.length > i; i++)
					pwStr.append(password[i]);
				
				loginUser(userID, pwStr.toString());
			}
			else if(e.getSource() == npButton){
				userID = idField.getText();
				password = pwField.getPassword();
				pwStr = new StringBuilder();
				for(int i = 0; password.length > i; i++)
					pwStr.append(password[i]);
				
				createUser(userID, pwStr.toString());
			}
		}
	}
}