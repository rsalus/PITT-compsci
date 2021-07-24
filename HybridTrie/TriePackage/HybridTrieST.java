// HybridTrieST<V> class

package TriePackage;

import java.util.*;
import java.io.*;

public class HybridTrieST<V> {

    private TrieNodeInt<V> root;
    int treeType = 0;
    	// treeType = 0 --> multiway trie
    	// treeType = 1 --> DLB
    	// treeType = 2 --> hybrid

	// You must supply the methods for this class.  See test program
	// HybridTrieTest.java for details on the methods and their
	// functionality.  Also see handout TrieSTMT.java for a partial
	// implementation.

	//constructors
	public HybridTrieST(){ root = null; }
	public HybridTrieST(int type){
		treeType = type;
		if(type == 0) root = new MTAlphaNode<>();
		else root = new DLBNode<>();
	}

	public int[] degreeDistribution(){
		int[] dist = new int[27]; 	//k+1
		return degreeDistribution(root, dist);
	}
	
	private int[] degreeDistribution(TrieNodeInt<V> x, int[] d){
		if(x == null) return d;
		else{
			d[x.getDegree()]++;
			for(TrieNodeInt<V> c : x.children()) degreeDistribution(c, d);
			return d;
		}
	}
	
	public int countNodes(int key){
		if(key == 1) return countMT(root);
		else if(key == 2) return countDLB(root);
		else return 0;
	}

	private int countMT(TrieNodeInt<V> x){
		if(x == null) return 0;
		int count = 0;
		if(x instanceof MTAlphaNode<?>) count++;
		for(TrieNodeInt<V> c : x.children()) count += countMT(c);
		return count;
	}

	private int countDLB(TrieNodeInt<V> x){
		if(x == null) return 0;
		int count = 0;
		if(x instanceof DLBNode<?>) count++;
		for(TrieNodeInt<V> c : x.children()) count += countDLB(c);
		return count;
	}

	public void save(String args){
		try{
			PrintWriter output = new PrintWriter(args);
			save(root, output);
			output.close();
		} catch (FileNotFoundException e){}
	}

	private void save(TrieNodeInt<V> x, PrintWriter output){
		if(x.getData() != null) output.println(x.getData());
		for(TrieNodeInt<V> c : x.children()) save(c, output);
	}

   /****************************************************
    * Is the key in the symbol table?
    ****************************************************/
    public boolean contains(String key) {
        return get(key) != null;
    }

    public V get(String key) {
        TrieNodeInt<V> x = get(root, key, 0);
        if (x == null) return null;
        return x.getData();
    }

    private TrieNodeInt<V> get(TrieNodeInt<V> x, String key, int d) {
        if (x == null) return null;
        if (d == key.length()) return x;
        char c = key.charAt(d);
        return get(x.getNextNode(c), key, d+1);
    }
	
	//getSize() methods specified in HybridTrieTest.java
	public int getSize(){
		return getSize(root);
	}

	private int getSize(TrieNodeInt<V> x){   //recursive
        if(x == null) return 0;
		else{
			int size = x.getSize();
			for(TrieNodeInt<V> c : x.children()) size += getSize(c);
			return size;
		}
    }

	// Compare to searchPrefix in TrieSTNew.  Note that in this class all
	// of the accesses to a node are via the interface methods.
	public int searchPrefix(String key)
	{
		return searchPrefix(root, key, 0);
	}
	
	private int searchPrefix(TrieNodeInt<V> x, String key, int d)
	{
		if (x == null)  // null reference means not found and not prefix
			return 0;
		else
		{
			if (d == key.length()) // all chars in the key are found
			{
				int total = 0;
				if (x.getData() != null)
					total += 2;  // val is non-null so key is found
				if (x.getDegree() > 0)
					total += 1;  // x has at least one child so key
							     // is a prefix
				return total;
			}
			else return searchPrefix(x.getNextNode(key.charAt(d)), key, d+1);
		}
	}

   /****************************************************
    * Insert key-value pair into the symbol table.
    ****************************************************/
    public void put(String key, V val) {
        root = put(root, key, val, 0);
    }

	// This method requires us to create a new node -- which in turn requires
	// a class.  This is the only place where a MTNode<V> object is explicitly
	// used.
    private TrieNodeInt<V> put(TrieNodeInt<V> x, String key, V val, int d) {
        if (x == null){
			if(treeType == 0) x = new MTAlphaNode<>();
			else x = new DLBNode<>();
		}
        if (d == key.length()) {
            x.setData(val);
            return x;
        }
        char c = key.charAt(d);
        x.setNextNode(c, put(x.getNextNode(c), key, val, d+1));

		//check degree... use instanceof keyword
		if(x instanceof DLBNode<?> && (x.getDegree() > 10 && treeType == 2)){
			x = new MTAlphaNode<>((DLBNode<V>) x);
		}
        return x;
    }
}