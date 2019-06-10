package cs445.assignment2;

// CS 0445 Spring 2019
// Read this class and its comments very carefully to make sure you implement
// the class properly.  Note the items that are required and that cannot be
// altered!  Generally speaking you will implement your MyStringBuilder using
// a singly linked list of nodes.  See more comments below on the specific
// requirements for the class.

// For more details on the general functionality of most of these methods, 
// see the specifications of the similar method in the StringBuilder class.  
public class MyStringBuilder
{
	// These are the only three instance variables you are allowed to have.
	// See details of CNode class below.  In other words, you MAY NOT add
	// any additional instance variables to this class.  However, you may
	// use any method variables that you need within individual methods.
	// But remember that you may NOT use any variables of any other
	// linked list class or of the predefined StringBuilder or 
	// or StringBuffer class in any place in your code.  You may only use the
	// String class where it is an argument or return type in a method.
	private CNode firstC;	// reference to front of list.  This reference is necessary
							// to keep track of the list
	private CNode lastC; 	// reference to last node of list.  This reference is
							// necessary to improve the efficiency of the append()
							// method
	private int length;  	// number of characters in the list

	// You may also add any additional private methods that you need to
	// help with your implementation of the public methods.

	// Create a new MyStringBuilder initialized with the chars in String s
	public MyStringBuilder(String s)
	{
		if (s == null || s.length() == 0) // Special case for empty String
		{					 			  // or null reference
			firstC = null;
			lastC = null;
			length = 0;
		}
		else
		{
			// Create front node to get started
			firstC = new CNode(s.charAt(0));
			length = 1;
			CNode currNode = firstC;
			// Create remaining nodes, copying from String.  Note
			// how each new node is simply added to the end of the
			// previous one.  Trace this to see what is going on.
			for (int i = 1; i < s.length(); i++)
			{
				CNode newNode = new CNode(s.charAt(i));
				currNode.next = newNode;
				currNode = newNode;
				length++;
			}
			lastC = currNode;
		}
	}

	// Create a new MyStringBuilder initialized with the chars in array s
	public MyStringBuilder(char [] s)
	{
		if(s == null || s.length == 0){
			firstC = null;
			lastC = null;
			length = 0;
		}
		else{
			firstC = new CNode(s[0]);
			length = 1;
			CNode currNode = firstC;
			for(int i = 1; i < s.length; i++){
				CNode newNode = new CNode(s[i]);
				currNode.next = newNode;
				currNode = newNode;
				length++;
			}
			lastC = currNode;
		}
	}

	// Create a new empty MyStringBuilder
	public MyStringBuilder()
	{
		firstC = null;
		lastC = null;
		length = 0;
	}

	// Append MyStringBuilder b to the end of the current MyStringBuilder, and
	// return the current MyStringBuilder.  Be careful for special cases!
	public MyStringBuilder append(MyStringBuilder b)
	{
		if(b != null && b.length() != 0 && this.length > 0)
		{
			CNode currNode = lastC;
			for(int i = 0; i < b.length; i++){
				CNode newNode = new CNode(b.charAt(i));
				currNode.next = newNode;
				currNode = newNode;
				length++;
			}
			lastC = currNode;
		}
		else if(this.length <= 0){
			firstC = new CNode(b.charAt(0));
			length = 1;
			CNode currNode = firstC;
			for (int i = 1; i < b.length(); i++)
			{
				CNode newNode = new CNode(b.charAt(i));
				currNode.next = newNode;
				currNode = newNode;
				length++;
			}
			lastC = currNode;
		}
		return this;
	}


	// Append String s to the end of the current MyStringBuilder, and return
	// the current MyStringBuilder.  Be careful for special cases!
	public MyStringBuilder append(String s)
	{
		if(s != null && s.length() != 0 && this.length > 0){
			CNode currNode = lastC;
			for(int i = 0; i < s.length(); i++){
				CNode newNode = new CNode(s.charAt(i));
				currNode.next = newNode;
				currNode = newNode;
				length++;
			}
			lastC = currNode;
		}
		else if(this.length <= 0){
			firstC = new CNode(s.charAt(0));
			length = 1;
			CNode currNode = firstC;
			for (int i = 1; i < s.length(); i++)
			{
				CNode newNode = new CNode(s.charAt(i));
				currNode.next = newNode;
				currNode = newNode;
				length++;
			}
			lastC = currNode;
		}
		return this;
	}

	// Append char array c to the end of the current MyStringBuilder, and
	// return the current MyStringBuilder.  Be careful for special cases!
	public MyStringBuilder append(char [] c)
	{
		if(c != null && c.length != 0 && this.length > 0){
			CNode currNode = lastC;
			for(int i = 0; i < c.length; i++){
				CNode newNode = new CNode(c[i]);
				currNode.next = newNode;
				currNode = newNode;
				length++;
			}
			lastC = currNode;
		}
		else if(this.length <= 0){
			firstC = new CNode(c[0]);
			length = 1;
			CNode currNode = firstC;
			for (int i = 1; i < c.length; i++)
			{
				CNode newNode = new CNode(c[i]);
				currNode.next = newNode;
				currNode = newNode;
				length++;
			}
			lastC = currNode;
		}
		return this;
	}

	// Append char c to the end of the current MyStringBuilder, and
	// return the current MyStringBuilder.  Be careful for special cases!
	public MyStringBuilder append(char c)
	{
		if(this.length > 0){
			CNode newNode = new CNode(c);
			lastC.next = newNode;
			lastC = newNode;
			length++;
		}
		else if(this.length <= 0){
			firstC = new CNode(c);
			length = 1;
			lastC = firstC;
		}
		return this;
	}

	// Return the character at location "index" in the current MyStringBuilder.
	// If index is invalid, throw an IndexOutOfBoundsException.
	public char charAt(int index)
	{
		if(index >= 0 && index <= this.length)
		{
			if(index == 0){
				return firstC.data;
			}
			else if(index == this.length){
				return lastC.data;
			}
			else{
				CNode currNode = firstC;
				for(int i = 1; i <= index; i++){
					currNode = currNode.next;
				}
				return currNode.data;
			}
		}
		else{
			throw new IndexOutOfBoundsException("Index " + index + "is illegal");
		}
	}

	// Delete the characters from index "start" to index "end" - 1 in the
	// current MyStringBuilder, and return the current MyStringBuilder.
	// If "start" is invalid or "end" <= "start" do nothing (just return the
	// MyStringBuilder as is).  If "end" is past the end of the MyStringBuilder, 
	// only remove up until the end of the MyStringBuilder. Be careful for 
	// special cases!
	public MyStringBuilder delete(int start, int end)
	{
		if(start < 0 || end <= start)
			return this;
		else if(end < this.length && start != 0){		//normal case
			CNode startNode = firstC;
			for(int i = 1; i < start; i++){
				startNode = startNode.next;
			}
			CNode endNode = startNode.next;
			for(int i = start; i < end; i++){
				endNode = endNode.next;
			}
			length = length - (end - start);
			startNode.next = endNode;
		}
		else if(end < this.length && start == 0){		//0 case
			CNode endNode = firstC.next;
			for(int i = 1; i < end; i++){
				endNode = endNode.next;
			}
			length = length - (end - start);
			firstC = endNode;
		}
		else{											//end > length case
			CNode startNode = firstC;
			for(int i = 1; i < start; i++){
				startNode = startNode.next;
			}
			CNode endNode = startNode.next;
			for(int i = start; i < this.length; i++){
				endNode = endNode.next;
			}
			length = length - (length - start);
			startNode.next = endNode;
			lastC = endNode;
		}
		return this;
	}

	// Delete the character at location "index" from the current
	// MyStringBuilder and return the current MyStringBuilder.  If "index" is
	// invalid, do nothing (just return the MyStringBuilder as is).
	// Be careful for special cases!
	public MyStringBuilder deleteCharAt(int index)
	{
		if(index < 0 || index > this.length)
			return this;
		else if(index != 0){
			CNode prevNode = firstC;
			for(int i = 1; i < index; i++){
				prevNode = prevNode.next;
			}
			CNode subNode = prevNode.next;
			subNode = subNode.next;
			prevNode.next = subNode;
			length--;
		}
		else if(index == 0){
			firstC = firstC.next;
			length--;
		}
		return this;
	}

	// Find and return the index within the current MyStringBuilder where
	// String str first matches a sequence of characters within the current
	// MyStringBuilder.  If str does not match any sequence of characters
	// within the current MyStringBuilder, return -1.  Think carefully about
	// what you need to do for this method before implementing it.
	public int indexOf(String str)
	{
		int matches = -1;
		for(int i = 0; i < this.length; i++){
			char [] test = new char[str.length()];
			for(int ix = 0; ix < str.length(); ix++){
				if(ix + i <= this.length){test[ix] = this.charAt(ix + i);};
			}
			String testStr = new String(test);
			if(testStr.equals(str)){
				matches = i;
				break;
			}
		}
		return matches;
	}

	// Insert String str into the current MyStringBuilder starting at index
	// "offset" and return the current MyStringBuilder.  if "offset" == 
	// length, this is the same as append.  If "offset" is invalid
	// do nothing.
	public MyStringBuilder insert(int offset, String str)
	{
		if(offset == this.length){
			this.append(str);
		}
		else if(offset > 0){
			CNode prevNode = firstC;
			for(int i = 1; i < offset; i++){
				prevNode = prevNode.next;
			}
			CNode subNode = prevNode.next;
			for (int i = 0; i < str.length(); i++)
			{
				CNode newNode = new CNode(str.charAt(i));
				prevNode.next = newNode;
				prevNode = newNode;
				length++;
			}
			prevNode.next = subNode;
		}
		else if(offset == 0){
			CNode frontNode = new CNode(str.charAt(0));
			CNode saveNode = frontNode;
			for (int i = 1; i < str.length(); i++)
			{
				CNode newNode = new CNode(str.charAt(i));
				frontNode.next = newNode;
				frontNode = newNode;
			}
			frontNode.next = firstC;
			firstC = saveNode;
			length = length + str.length();
		}
		return this;
	}

	// Insert character c into the current MyStringBuilder at index
	// "offset" and return the current MyStringBuilder.  If "offset" ==
	// length, this is the same as append.  If "offset" is invalid, 
	// do nothing.
	public MyStringBuilder insert(int offset, char c)
	{
		if(offset == this.length){
			this.append(c);
		}
		else if(offset == 0){
			CNode frontNode = new CNode(c);
			CNode saveNode = frontNode;
			frontNode.next = firstC;
			firstC = saveNode;
			length++;
		}
		return this;
	}

	// Insert char array c into the current MyStringBuilder starting at index
	// index "offset" and return the current MyStringBuilder.  If "offset" is
	// invalid, do nothing.
	public MyStringBuilder insert(int offset, char [] c)
	{
		if(offset == this.length){
			this.append(c);
		}
		else if(offset > 0){
			CNode prevNode = firstC;
			for(int i = 1; i < offset; i++){
				prevNode = prevNode.next;
			}
			CNode subNode = prevNode.next;
			for (int i = 0; i < c.length; i++)
			{
				CNode newNode = new CNode(c[i]);
				prevNode.next = newNode;
				prevNode = newNode;
				length++;
			}
			prevNode.next = subNode;
		}
		else if(offset == 0){
			CNode frontNode = new CNode(c[0]);
			CNode saveNode = frontNode;
			for (int i = 1; i < c.length; i++)
			{
				CNode newNode = new CNode(c[0]);
				frontNode.next = newNode;
				frontNode = newNode;
			}
			frontNode.next = firstC;
			firstC = saveNode;
			length = length + c.length;
		}
		return this;
	}

	// Return the length of the current MyStringBuilder
	public int length()
	{
		return length;
	}

	// Delete the substring from "start" to "end" - 1 in the current
	// MyStringBuilder, then insert String "str" into the current
	// MyStringBuilder starting at index "start", then return the current
	// MyStringBuilder.  If "start" is invalid or "end" <= "start", do nothing.
	// If "end" is past the end of the MyStringBuilder, only delete until the
	// end of the MyStringBuilder, then insert.  This method should be done
	// as efficiently as possible.  In particular, you may NOT simply call
	// the delete() method followed by the insert() method, since that will
	// require an extra traversal of the linked list.
	public MyStringBuilder replace(int start, int end, String str)
	{
		if(start < 0 || end <= start)
			return this;
		else if(end < this.length && start != 0){		//normal case
			CNode startNode = firstC;
			for(int i = 1; i < start; i++){
				startNode = startNode.next;
			}
			CNode endNode = startNode;
			for(int i = start; i <= end; i++)
			{
				endNode = endNode.next;
				length--;
			}
			for (int i = 0; i < str.length(); i++)
			{
				CNode newNode = new CNode(str.charAt(i));
				startNode.next = newNode;
				startNode = newNode;
				length++;
			}
			length++;
			startNode.next = endNode;
		}
		else if(end < this.length && start == 0){	//0 case
			CNode startNode = firstC;
			CNode endNode = startNode;
			for(int i = start; i <= end; i++)
			{
				endNode = endNode.next;
				length--;
			}
			for (int i = 0; i < str.length(); i++)
			{
				CNode newNode = new CNode(str.charAt(i));
				startNode.next = newNode;
				startNode = newNode;
				length++;
			}
			length++;
			startNode.next = endNode;
			firstC = endNode;
		}
		else{										//end > length case
			CNode startNode = firstC;
			for(int i = 1; i < start; i++){
				startNode = startNode.next;
			}
			length = length - (length - start);
			for (int i = 0; i < str.length(); i++)
			{
				CNode newNode = new CNode(str.charAt(i));
				startNode.next = newNode;
				startNode = newNode;
				length++;
			}
		}
		return this;
	}

	// Reverse the characters in the current MyStringBuilder and then
	// return the current MyStringBuilder.
	public MyStringBuilder reverse()
	{
		CNode prevNode = null;
		CNode currNode = firstC;
		CNode nextNode = null;
		while(currNode != null){
			nextNode = currNode.next;
			currNode.next = prevNode;
			prevNode = currNode;
			currNode = nextNode;
		}
		firstC = prevNode;
		return this;
	}
	
	// Return as a String the substring of characters from index "start" to
	// index "end" - 1 within the current MyStringBuilder
	public String substring(int start, int end)
	{
		String str = "";
		CNode currNode = firstC;
		for(int i = 1; i <= start; i++){
			currNode = currNode.next;
		}
		for(int i = start; i < end; i++){
			str = str + currNode.data;
			currNode = currNode.next;
		}
		return str;
	}

	// Return the entire contents of the current MyStringBuilder as a String
	public String toString()
	{
		String str = "";
		CNode currNode = firstC;
		for(int i = 1; i <= this.length; i++){
			str = str + currNode.data;
			currNode = currNode.next;
		}
		return str;
	}

	// You must use this inner class exactly as specified below.  Note that
	// since it is an inner class, the MyStringBuilder class MAY access the
	// data and next fields directly.
	private class CNode
	{
		private char data;
		private CNode next;

		public CNode(char c)
		{
			data = c;
			next = null;
		}

		public CNode(char c, CNode n)
		{
			data = c;
			next = n;
		}
	}
}