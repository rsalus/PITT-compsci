import java.io.*;
import java.math.BigInteger;
import java.net.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class SecureChatClient extends JFrame implements Runnable, ActionListener {
    /* INIT */
    public static final int PORT = 8765;
    ObjectInputStream myReader;
    ObjectOutputStream myWriter;
    JTextArea outputArea;
    JLabel prompt;
    JTextField inputField;
    String myName, serverName, symType;
	Socket connection;
    private BigInteger E, N, key;
    private SymCipher symCipher;
    private byte[] name;

    /* CLIENT */
    public SecureChatClient(){
        try{
            /* HANDSHAKE START */
            myName = JOptionPane.showInputDialog(this, "Enter your user name: ");
            serverName = JOptionPane.showInputDialog(this, "Enter the server name: ");
            InetAddress addr = InetAddress.getByName(serverName);
            connection = new Socket(addr, PORT);   // Connect to server with new Socket
            
            myWriter = new ObjectOutputStream(connection.getOutputStream());
            myWriter.flush();
            myReader = new ObjectInputStream(connection.getInputStream());

            //init inputs, print
            E = (BigInteger) myReader.readObject();
            N = (BigInteger) myReader.readObject();
            symType = (String) myReader.readObject();
            System.out.println("E: " + E + "\nN: " + N + "\nSymCipher Type: " + symType);

            //create cipher obj
            switch(symType){
                case "Add": symCipher = new Add128(); break;
                case "Sub": symCipher = new Substitute(); break;
                default: throw new IllegalArgumentException("Invalid Type");
            }

            //create key, write
            key = new BigInteger(1, symCipher.getKey());
            key = key.modPow(E, N);
            myWriter.writeObject(key);
            myWriter.flush();

            //encode name, write
            name = symCipher.encode(myName);
            myWriter.writeObject(name);
            myWriter.flush();

            System.out.println("Output Key: " + key + "\n\nConnecting...");
            /* HANDSHAKE FINISH */
            /* GUI INIT */
            this.setTitle(myName);      // Set title to identify chatter
            Box b = Box.createHorizontalBox();  // Set up graphical environment for
            outputArea = new JTextArea(8, 30);  // user
            outputArea.setEditable(false);
            b.add(new JScrollPane(outputArea));

            outputArea.append("Welcome to the Chat Group, " + myName + "\n");

            inputField = new JTextField("");  // This is where user will type input
            inputField.addActionListener(this);

            prompt = new JLabel("Type your messages below:");
            Container c = getContentPane();

            c.add(b, BorderLayout.NORTH);
            c.add(prompt, BorderLayout.CENTER);
            c.add(inputField, BorderLayout.SOUTH);

            Thread outputThread = new Thread(this);  // Thread is to receive strings
            outputThread.start();                    // from Server

            addWindowListener(
                new WindowAdapter(){
                    public void windowClosing(WindowEvent e){
                        byte[] exit = symCipher.encode("CLIENT CLOSING");
                        try{
                            myWriter.writeObject(exit);
                            myWriter.flush();
                        } catch(IOException ex){}
                        System.exit(0);
                    }
                }
            );
            setSize(500, 200);
            setVisible(true);
        }
        catch (Exception e){
            System.out.println("Problem starting client!");
        }
    }

	// Wait for a message to be received, then show it on the output area
    // In your SecureChatClient you will need to decode the message before
    // appending it.
    public void run(){
        while(true){
            try{
                byte[] input = (byte[]) myReader.readObject();
                String output = symCipher.decode(input);
                System.out.println("Decoding..." + "\nInput: " + input + 
                "\nOutput: " + output + "\nString: " + input.toString());
			    outputArea.append("\n" + output);
            } catch (Exception e){
                System.out.println(e +  ", closing client!");
                break;
            }
        }
        System.exit(0);
    }

	// Get message typed in from user (from inputField) then add name and send
	// it to the server after encoding it.
    public void actionPerformed(ActionEvent e){
        String currMsg = e.getActionCommand();      // Get input value
        byte[] output = symCipher.encode(myName + ": " + currMsg);
        System.out.println("Encoding..." + "\nString: " + myName + ": " + currMsg +
        "\nInput: " + (myName + ": " + currMsg).getBytes() + "\nOutput: " + output);
        inputField.setText("");
        try{ 
            myWriter.writeObject(output);
            myWriter.flush();
        } catch(IOException ex){}
    }

	// Start things off by creating an ImprovedChatClient object.
    public static void main(String [] args){
         SecureChatClient JR = new SecureChatClient();
         JR.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    }
}