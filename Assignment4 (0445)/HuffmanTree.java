import java.io.*;
import java.util.*;

public class HuffmanTree
{
	public BinaryNode<Character> root;
	private File inputFile;
	private Index ind = new Index();
	private ArrayList<String> nodeType = new ArrayList<String>();
	private ArrayList<Character> nodeData = new ArrayList<Character>();
	private ArrayList<String> table = new ArrayList<String>();
	
	public HuffmanTree(File input){
		inputFile = input;
		sortData();
	}
	
	private void sortData(){	//Sorts data from textfile into two separate ArrayLists in order to create the tree later on
		try {
			Scanner scan = new Scanner(inputFile);
			while(scan.hasNextLine()){
				nodeType.add(scan.nextLine());
				nodeData.add(null);
			}
			if(!nodeType.contains("I")){System.out.println("File is invalid: does not contain any interior nodes\n");}
			else{System.out.println("Huffman Tree restored successfully\n");}
			scan.close();
		} 
		catch (FileNotFoundException e){}	
		for(int i = 0; i < nodeType.size(); i++){
			if(nodeType.get(i).equals("I")){
				nodeData.set(i, '#');
			}
			else if(nodeType.get(i) != null && nodeType.get(i).contains("L")){
				char[] c = nodeType.get(i).substring(2, 3).toCharArray();
				nodeData.set(i, c[0]);
			}
		}
	}
	
	public ArrayList<String> getNodeType(){
		return nodeType;
	}
	
	public ArrayList<Character> getNodeData(){
		return nodeData;
	}

	private BinaryNode<Character> constructTreeRec(ArrayList<Character> nodeData, ArrayList<String> nodeType, Index ind_rec, int i, BinaryNode<Character> temp){
		int index = ind_rec.index;	//recursive function for binary tree construction
		if(index == i)
			return null;
		temp = new BinaryNode<Character>(nodeData.get(index));
		(ind_rec.index)++;
		if(nodeType.get(index).contains("I")){
			temp.setLeftChild(constructTreeRec(nodeData, nodeType, ind_rec, i, temp.getLeftChild()));
			temp.setRightChild(constructTreeRec(nodeData, nodeType, ind_rec, i, temp.getRightChild()));
		}
		return temp;
	}

	public BinaryNode<Character> constructTree(ArrayList<Character> nodeData, ArrayList<String> nodeType, int n, BinaryNode<Character> node){
		return constructTreeRec(nodeData, nodeType, ind, n, node); 
	}
	
	public ArrayList<String> buildCodeTable(BinaryNode<Character> root){
		StringBuilder builder = new StringBuilder();
		buildRecTable(root, builder);
		return table;
	}
	
	private void buildRecTable(BinaryNode<Character> root, StringBuilder input)	//recursive function for code table
	{
		if(root.getData().equals('#')){
			input.append(0);
			buildRecTable(root.getLeftChild(), input);
			input.deleteCharAt(input.length()-1);
			input.append(1);
			buildRecTable(root.getRightChild(), input);
			input.deleteCharAt(input.length()-1);
		}
		else if(root.getData() != null){
			table.add(input.toString() + root.getData());
		}
	}
	private class Index {int index = 0;}
}