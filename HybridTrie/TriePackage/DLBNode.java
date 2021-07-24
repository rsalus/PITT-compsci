// DLB Trie Node implemented as an external class which
// implements the TrieNodeInt<V> Interface

package TriePackage;

import java.util.*;
public class DLBNode<V> implements TrieNodeInt<V>
{
    protected Nodelet front;
    protected int degree;
	protected V val;
	
    protected class Nodelet
    {
    	protected char cval;
    	protected Nodelet rightSib;
    	protected TrieNodeInt<V> child;
	}
	
	// You must supply the methods for this class.  See TrieNodeInt.java for the
	// interface specifications.  You will also need a constructor or two.

	//constructors
	public DLBNode(V data){
		val = data;
		degree = 0;
	}
	
	public DLBNode(){
		val = null;
		degree = 0;
	}

	public TrieNodeInt<V> getNextNode(char c) {
		if(front == null) return null;
		Nodelet curr = front;
		while(curr != null){	//iterate thru LL
			if(curr.cval == c) return curr.child;
			curr = curr.rightSib;
		} return null;
	}

	public void setNextNode(char c, TrieNodeInt<V> node) {//review
		/*	case 1: if     front does not exist
			case 2: if     front char matches c
			case 3: elseif front > c
			case 4: else   front < c	*/
		if(front == null){
			front = new Nodelet();	//create front nodelet, make assignments
			front.cval = c;
			front.child = node;
			degree++;
		}
		else if(front.cval == c){	//make assignments
			front.child = node;
			if(front.child == null) degree++;
		}
		else if(front.cval > c){
			Nodelet curr = front;	//create nodelet, make assignments
			front = new Nodelet();
			front.cval = c;
			front.child = node;
			front.rightSib = curr;
			degree++;
		}
		else{
			Nodelet curr = front;
			while((curr != null && curr.rightSib != null) && (curr.rightSib.cval <= c)){
				curr = curr.rightSib;
			}
			if(curr.cval == c){ curr.child = node; }
			else{		//dne
				Nodelet swap = curr.rightSib;
				Nodelet temp = new Nodelet();
				degree++;
				temp.cval = c;
				temp.child = node;
				temp.rightSib = swap;
				curr.rightSib = temp;
				//curr.rightSib = swap;
				//swap.child = node;
				//degree++;
			}
		}		
	}

	public V getData() {
		return val;
	}

	public void setData(V data) {
		val = data;
	}

	public int getDegree() {
		return degree;
	}

	public int getSize() {
		return (12 + (10 * degree));	//3 refs * 4... childrefs (2+4+4) * degree
	}

	public Iterable<TrieNodeInt<V>> children() {
		Queue<TrieNodeInt<V>> q = new LinkedList<TrieNodeInt<V>>();
		for(Nodelet add = front; add != null; add = add.rightSib) q.add(add.child);
		return q;
	}
}