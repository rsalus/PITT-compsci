// Your BST variations must all implement this interface.  Many of the
// methods are already implemented but you will need to add the missing
// ones.  Note that the implementations of the added methods should be the
// same for all of your trees.

public interface A1Interface<Key extends Comparable<Key>, Value> 
{
    // is the symbol table empty?
    public boolean isEmpty();

    // return number of key-value pairs in symbol table
    public int size();

   /***********************************************************************
    *  Search ST for given key, and return associated value if found,
    *  return null if not found
    ***********************************************************************/
    // does there exist a key-value pair with given key?
    public boolean contains(Key key);

    // return value associated with the given key, or null if no such key exists
    public Value get(Key key);

   /***********************************************************************
    *  Insert key-value pair into BST
    *  If key already exists, update with new value
    ***********************************************************************/
    public void put(Key key, Value val);

   /***********************************************************************
    *  Delete
    ***********************************************************************/
    public void delete(Key key);

    // Return the height of the tree.  This is the maximum path length from
    // the root to any leaf.  A singleton root node will have a height of 1
    // and an empty tree will have a height of 0
    public int height();
    
    // Return the average path length in the tree.  This is the sum of all of
    // the path lengths to all of the nodes divided by the number of nodes.
    // Define a path length by the number of nodes in the path, so the
    // shortest path in the tree (i.e. path to the root) will be 1.  Note that
    // for an empty tree this method should return 0.
    public double avePathLength();
    
    // Return the type of this tree (as a String)
    public String treeType();
    
   /***********************************************************************
    *  Min, max, floor, and ceiling
    ***********************************************************************/
    public Key min();

    public Key max();

    public Key floor(Key key);

    public Key ceiling(Key key);

   /***********************************************************************
    *  Rank and selection
    ***********************************************************************/
    public Key select(int k);

    public int rank(Key key);

   /***********************************************************************
    *  Range count and range search.
    ***********************************************************************/
    public Iterable<Key> keys();

    public Iterable<Key> keys(Key lo, Key hi);

    public int size(Key lo, Key hi);

 }