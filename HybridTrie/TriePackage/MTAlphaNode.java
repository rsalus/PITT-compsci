// MultiWay Trie Node implemented as an external class which
// implements the TrieNodeInt InterfaceAddress.  For this
// class it is assumed that all characters in any key will
// be letters between 'a' and 'z'.

package TriePackage;

import java.util.*;
public class MTAlphaNode<V> implements TrieNodeInt<V>
{
	private static final int R = 26;	// 26 letters in
										// alphabet

    protected V val;
    protected TrieNodeInt<V> [] next;
	protected int degree;

	// You must supply the methods for this class.  See TrieNodeInt.java
	// for the specifications.  See also handout MTNode.java for a
	// partial implementation.  Don't forget to include the conversion
	// constructor (passing a DLBNode<V> as an argument).
	
	// Note the syntax for creating the array below.  This is
	// because it is an array of a parameterized type.  If we
	// tried to make a new TrieNodeInt<V>[R]
	// we would get a compilation error due to the differences
	// in type checking of array objects and other Java objects.
	// For more information on this, Google:
	// creating java generic arrays
	// By using the <?> we are in effect allowing any type for the
	// parameter rather than requiring it to match V.  This is legal
	// but results in a warning during compilation (unchecked cast).
	public MTAlphaNode(DLBNode<V> toConvert){
		val = toConvert.val;
		degree = toConvert.degree;
		next = (TrieNodeInt<V> []) new TrieNodeInt<?>[R];
		for(char c = toConvert.front.cval; c <= 'z'; c++){
			next[c - toConvert.front.cval] = toConvert.getNextNode(c);
		}
	}
	
	public MTAlphaNode(V data)
	{
		val = data;
		degree = 0;
		next = (TrieNodeInt<V> []) new TrieNodeInt<?>[R];
	}
	
	public MTAlphaNode()
	{
		val = null;
		degree = 0;
		next = (TrieNodeInt<V> []) new TrieNodeInt<?>[R];
	}
	
	// Return the reference corresponding to character c
	public TrieNodeInt<V> getNextNode(char c)
	{
		return next[c - 'a'];
	}
	
	// Assign the argument node to the location corresponding
	// to character c.  If the reference had been null we
	// increase the degree of the current node by one.
	public void setNextNode(char c, TrieNodeInt<V> node)
	{
		if (next[c - 'a'] == null)
			degree++;
		next[c - 'a'] = node;
	}
	
	// Return the value of this node
	public V getData()
	{
		return val;
	}
	
	// Set the value of this node
	public void setData(V data)
	{
		val = data;
	}
	
	// Return the degree of this node
	public int getDegree()
	{
		return degree;
	}

	public int getSize() {
		// four refs of four bits apiece * 26
		return (4 + 4 + 4 + 4 * 26);
	}

	public Iterable<TrieNodeInt<V>> children() {
		Queue<TrieNodeInt<V>> q = new LinkedList<TrieNodeInt<V>>();
		for(int i = 0; i < R; i++){
			if(next[i] != null) q.add(next[i]);
		} return q;
	}
}