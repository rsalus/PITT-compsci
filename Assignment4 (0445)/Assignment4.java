import java.io.*;
import java.util.*;

public class Assignment4 
{
	public static void main(String[] args)
	{
		File inputFile = new File(args[0]);		//creates tree from file, outputs node info to arraylists for later use
		HuffmanTree hTree = new HuffmanTree(inputFile);
		ArrayList<String> nodeType = hTree.getNodeType();
		ArrayList<Character> nodeData = hTree.getNodeData();
		int val = nodeData.size();
		
		BinaryNode<Character> node = hTree.constructTree(nodeData, nodeType, val, hTree.root);	//constructs binary tree and tables for encoding/decoding
		ArrayList<String> codeTable = hTree.buildCodeTable(node);
		ArrayList<String> characterTable = new ArrayList<String>();
		for(int i = 0; i < codeTable.size(); i++){
			characterTable.add(codeTable.get(i).substring(codeTable.get(i).length() - 1));
			codeTable.set(i, codeTable.get(i).substring(0, codeTable.get(i).length() - 1));
		}
		
		Scanner scan = new Scanner(System.in);
		int choice = 0;
		while(choice != 3)
		{
			System.out.println("Select one of the following:\n1. Encode a string\n2. Decode a string\n3. Quit");
			choice = scan.nextInt();
			
			switch(choice)
			{
				case 1:		//encode case
					System.out.println("Enter a string from the following characters:\n" + characterTable.toString());
					String str = scan.next().toUpperCase();
					boolean check = true;
					for(int i = 0; i < str.length(); i++){
						if(!characterTable.contains(Character.toString(str.charAt(i)))){
							check = false;
						}
					}
					if(check == true){
						StringBuilder huffman = findHuffmanString(str, codeTable, characterTable); 
						System.out.println(huffman.toString());
					}
					else{System.out.println("Error: invalid string");}
					break;
				case 2:		//decode case
					System.out.println("Enter a huffman code:");
					String c = scan.next();
					int[] codeArr = new int[c.length()];
					char[] god = c.toCharArray();
					for(int i = 0; i < god.length; i++){
						codeArr[i] = Character.getNumericValue(god[i]);
					}
					StringBuilder huff = decodeHuffman(codeArr, node);
					System.out.println(huff.toString());
					break;
				case 3:		//quit case
					System.out.println("Quitting...");
					break;
			}
		}
		scan.close();
	}
	private static StringBuilder findHuffmanString(String input, ArrayList<String> codeTable, ArrayList<String> characterTable){
		StringBuilder huff = new StringBuilder();
		for(int i = 0; i < input.length(); i++){		//iterates thru table & compares against input before appending
			for(int n = 0; n < characterTable.size(); n++){
				if(characterTable.get(n).equals(Character.toString(input.charAt(i)))){
					huff.append(codeTable.get(n) + "\n");
					break;
				}
			}
		}
		return huff;
	}
	private static StringBuilder decodeHuffman(int[] code, BinaryNode<Character> root){
		StringBuilder str = new StringBuilder();
		BinaryNode<Character> curr = root;		//iterates thru both tree until leaf is found & appends data
		for(int i = 0; i < code.length; i++){
			if(code[i] == 0){curr = curr.getLeftChild();}
			else if(code[i] == 1){curr = curr.getRightChild();}
			else{str.delete(0, str.length()); str.append("Error: invalid code"); break;}
				
			if(curr.getLeftChild() == null && curr.getRightChild() == null){
				str.append(curr.getData());
				curr = root;
			}
			else if(i == code.length - 1){str.delete(0, str.length()); str.append("Error: invalid code"); break;}
		}
		str.append("\n");
		return str;
	}
}