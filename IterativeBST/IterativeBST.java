import java.util.ArrayList;
import java.util.Stack;

/*************************************************************************
 *  Compilation:  javac BST.java
 *  Execution:    java BST
 *
 *  A symbol table implemented with a binary search tree.
 * 
 *  % more tiny.txt
 *  S E A R C H E X A M P L E
 *  
 *  % java BST < tiny.txt
 *  A 8
 *  C 4
 *  E 12
 *  H 5
 *  L 11
 *  M 9
 *  P 10
 *  R 3
 *  S 0
 *  X 7
 *
 *************************************************************************/

public class IterativeBST<Key extends Comparable<Key>, Value>
    implements A1Interface<Key, Value> {
    private Node root;             // root of BST

    private class Node {
        private Key key;           // sorted by key
        private Value val;         // associated data
        private Node left, right;  // left and right subtrees
        private int N;             // number of nodes in subtree

        public Node(Key key, Value val, int N) {
            this.key = key;
            this.val = val;
            this.N = N;
        }
    }

    // is the symbol table empty?
    public boolean isEmpty() {
        return size() == 0;
    }

    // return number of key-value pairs in BST
    public int size() {
        return size(root);
    }

    // return number of key-value pairs in BST rooted at x
    private int size(Node x) {
        if (x == null) return 0;
        else return x.N;
    }

    public int height(){    //maximum path length...
        return height(root);
    }

    private int height(Node x){
        if(x == null) return 0;
        else{
            int left = height(x.left);
            int right = height(x.right);
            if(left > right) return (left + 1);
            else return (right + 1);
        }
    }

    /*
    public double avePathLength(){ //calculates average pathlength from root to leaf
        ArrayList<Integer> arr = new ArrayList();
        Stack<Node> stack = new Stack();
        Node x = root;
        int height = height();
        
        avePathLength(arr, stack, x, height);
        int pathSum = 0;
        for(int i = 0; i < arr.size(); i++){
            pathSum += arr.get(i);
        }
        System.out.println("pathSum: " + pathSum + " Size: " + arr.size());
        return (double) pathSum / arr.size();
    }

    private void avePathLength(ArrayList<Integer> arr, Stack<Node> stack, Node x, int height){
        if(x == null) return;
        stack.push(x);
        if(x.left == null && x.right == null){
            arr.add(stack.size());
        }
        avePathLength(arr, stack, x.left, height);
        avePathLength(arr, stack, x.right, height);
        stack.pop();
    }
    */

    public double avePathLength(){      //sum of subtrees % total nodes
        if(root == null) return 0;
        int subtrees = countSubtree(root);
        int total = countLength(root);
        return (double) subtrees/total;
    }

    private int countSubtree(Node x){   //recursively counts subtree total
        if(x == null) return 0;
        else return countSubtree(x.left) + countSubtree(x.right) + x.N;
    }

    private int countLength(Node x){    //recursively counts node total
        if(x == null) return 0;
        else return countLength(x.left) + countLength(x.right) + 1;
    }

    public String treeType(){   //this works
        return this.getClass().getSimpleName();
    }

   /***********************************************************************
    *  Search BST for given key, and return associated value if found,
    *  return null if not found
    ***********************************************************************/
    // does there exist a key-value pair with given key?
    public boolean contains(Key key) {
        return get(key) != null;
    }

    // return value associated with the given key, or null if no such key exists
    public Value get(Key key){      //credit pg.417 'Algorithms'
        Node x = root;
        while (x != null){
            int cmp = key.compareTo(x.key);
            if (cmp == 0) return x.val;
            else if (cmp < 0) x = x.left;
            else if (cmp > 0) x = x.right;
        }
        return null; 
    }

    private Node getNode(Key key){      //credit pg.417 'Algorithms'
        Node x = root;
        while (x != null){
            int cmp = key.compareTo(x.key);
            if (cmp == 0) return x;     //return node object instead of val
            else if (cmp < 0) x = x.left;
            else if (cmp > 0) x = x.right;
        }
        return null; 
    }

    private Node getParent(Key key){    //return parent node; iterative
        Node x = root, parent;
        while (x != null){
            parent = x;
            int cmp = key.compareTo(x.key);
            if (cmp == 0) return null;
            else if (cmp < 0){
                x = x.left;
                cmp = key.compareTo(x.key);
                if (cmp == 0) return parent;
            }
            else if (cmp > 0){
                x = x.right;
                cmp = key.compareTo(x.key);
                if (cmp == 0) return parent;
            }
        }
        return null; 
    }

    private Node getSuccessor(Node x){  //find inorder successor
        x = x.right;
        while(x.left != null) x = x.left;
        return x;
    }

    private void updateN(Node x){   //update N values in nodes
        if(x == null) return;
        x.N = countLength(x);
        updateN(x.left);
        updateN(x.right);
    }

    private boolean isLeft(Node x, Node parent){    //helper function to find orientation of node-parent couple
        if(parent == null) return false;
        if(parent.right == null) return true;
        else if(parent.left == null) return false;
        else{
            int cmp = x.key.compareTo(parent.left.key);
            if(cmp == 0) return true;
            else return false;
        }
    }

   /***********************************************************************
    *  Insert key-value pair into BST
    *  If key already exists, update with new value
    ***********************************************************************/
    public void put(Key key, Value val){
        Node curr = root;   //initializations
        Node parent = null;
        
        if(this.contains(key)) return;  //no duplicates

        if(root == null){   //root edge case
            root = new Node(key, val, 1);
            return;
        }

        while(curr != null){    //similar to author's get function...
            parent = curr;  //save parent ref
            parent.N++;     //since every node in the path will be affected...
            if(key.compareTo(curr.key) < 0) curr = curr.left;
            else if(key.compareTo(curr.key) > 0) curr = curr.right;
            else{curr.val = val; return;}
        }

        //assign pointers and node
        if(key.compareTo(parent.key) < 0){
            parent.left = new Node(key, val, 1);
        }
        else if(key.compareTo(parent.key) > 0){
            parent.right = new Node(key, val, 1);
        }
    }

   /***********************************************************************
    *  Delete
    ***********************************************************************/

    public void deleteMin() {
        if (isEmpty()) throw new RuntimeException("Symbol table underflow");
        root = deleteMin(root);
        assert isBST();
    }

    private Node deleteMin(Node x) {
        if (x.left == null) return x.right;
        x.left = deleteMin(x.left);
        x.N = size(x.left) + size(x.right) + 1;
        return x;
    }

    public void deleteMax() {
        if (isEmpty()) throw new RuntimeException("Symbol table underflow");
        root = deleteMax(root);
        assert isBST();
    }

    private Node deleteMax(Node x) {
        if (x.right == null) return x.left;
        x.right = deleteMax(x.right);
        x.N = size(x.left) + size(x.right) + 1;
        return x;
    }

    public void delete(Key key){
        Node x = null, del = getNode(key), parent = null;   //initializations
        if(del != null) parent = getParent(del.key);

        if(del.left == null || del.right == null){  //first case: node has max 1 child
            if(del.left == null) x = del.right;     //find the child
            else x = del.left;
            if(root.key.compareTo(del.key) == 0){   //edge cases if root
                if(root.left == null){
                    root = root.right;
                    parent = null;
                } else if(root.right == null){
                    root = root.left;
                    parent = null;
                } else if(root.right == null && root.left == null) {
                    root = null;
                    return;
                } else { throw new RuntimeException("Error deleting root"); }
            } 
            if(isLeft(del, parent)) parent.left = x;    //reassign pointers
            else if(parent != null) parent.right = x;

        } else {    //second case: node has two children
            x = getSuccessor(del);
            parent = getParent(x.key);

            //we want to replace del with x, while preserving del's pointers...
            //easiest way is to just overwrite del's data then
            //however we also need to reassign parent and child pointers for x

            //first, overwrite del values with corresponding x values
            del.key = x.key;
            del.val = x.val;
            
            //next, reassign pointers for x
            if(isLeft(x, parent)){
                if(x.left != null) parent.left = x.left;
                else parent.left = null;
            } else {
                if(x.right != null) parent.right = x.right;
                else parent.right = null;
            }
        }
        updateN(root);
        assert isBST();
    }

   /***********************************************************************
    *  Min, max, floor, and ceiling
    ***********************************************************************/
    public Key min() {
        if (isEmpty()) return null;
        return min(root).key;
    } 

    private Node min(Node x) { 
        if (x.left == null) return x; 
        else                return min(x.left); 
    } 

    public Key max() {
        if (isEmpty()) return null;
        return max(root).key;
    } 

    private Node max(Node x) { 
        if (x.right == null) return x; 
        else                 return max(x.right); 
    } 

    public Key floor(Key key) {
        Node x = floor(root, key);
        if (x == null) return null;
        else return x.key;
    } 

    private Node floor(Node x, Key key) {
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if (cmp == 0) return x;
        if (cmp <  0) return floor(x.left, key);
        Node t = floor(x.right, key); 
        if (t != null) return t;
        else return x; 
    } 

    public Key ceiling(Key key) {
        Node x = ceiling(root, key);
        if (x == null) return null;
        else return x.key;
    }

    private Node ceiling(Node x, Key key) {
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if (cmp == 0) return x;
        if (cmp < 0) { 
            Node t = ceiling(x.left, key); 
            if (t != null) return t;
            else return x; 
        } 
        return ceiling(x.right, key); 
    } 

   /***********************************************************************
    *  Rank and selection
    ***********************************************************************/
    public Key select(int k) {
        if (k < 0 || k >= size())  return null;
        Node x = select(root, k);
        return x.key;
    }

    // Return key of rank k. 
    private Node select(Node x, int k) {
        if (x == null) return null; 
        int t = size(x.left); 
        if      (t > k) return select(x.left,  k); 
        else if (t < k) return select(x.right, k-t-1); 
        else            return x; 
    } 

    public int rank(Key key) {
        return rank(key, root);
    } 

    // Number of keys in the subtree less than x.key. 
    private int rank(Key key, Node x) {
        if (x == null) return 0; 
        int cmp = key.compareTo(x.key); 
        if      (cmp < 0) return rank(key, x.left); 
        else if (cmp > 0) return 1 + size(x.left) + rank(key, x.right); 
        else              return size(x.left); 
    }

   /***********************************************************************
    *  Range count and range search.
    ***********************************************************************/
    public Iterable<Key> keys() {
        return keys(min(), max());
    }

    public Iterable<Key> keys(Key lo, Key hi) {
        Queue<Key> queue = new Queue<Key>();
        keys(root, queue, lo, hi);
        return queue;
    } 

    private void keys(Node x, Queue<Key> queue, Key lo, Key hi) { 
        if (x == null) return; 
        int cmplo = lo.compareTo(x.key); 
        int cmphi = hi.compareTo(x.key); 
        if (cmplo < 0) keys(x.left, queue, lo, hi); 
        if (cmplo <= 0 && cmphi >= 0) queue.enqueue(x.key); 
        if (cmphi > 0) keys(x.right, queue, lo, hi); 
    } 

    public int size(Key lo, Key hi) {
        if (lo.compareTo(hi) > 0) return 0;
        if (contains(hi)) return rank(hi) - rank(lo) + 1;
        else              return rank(hi) - rank(lo);
    }

  /*************************************************************************
    *  Check integrity of BST
    *************************************************************************/
    // is this tree a BST?
    private boolean isBST() {
        if (isEmpty()) return true;
        if (!isBinaryTree())     StdOut.println("Subtree counts not consistent");
        if (!isOrdered())        StdOut.println("Not in symmetric order");
        if (!hasNoDuplicates())  StdOut.println("Has duplicate keys");
        if (!isRankConsistent()) StdOut.println("Rank not consistent");
        return isBinaryTree() && isOrdered() && hasNoDuplicates() && isRankConsistent();
    }

    // are the size fields correct (and consequently is it a binary tree)
    private boolean isBinaryTree() { return isBinaryTree(root); }
    private boolean isBinaryTree(Node x) {
        if (x == null) return true;
        if (x.N != size(x.left) + size(x.right) + 1) return false;
        return isBinaryTree(x.left) && isBinaryTree(x.right);
    } 

    // does this binary tree satisfy symmetric order?
    private boolean isOrdered() {
        assert isBinaryTree();
        return isOrdered(root, min(), max());
    }

    // are all the values in the BST rooted at x between min and max, and recursively?
    private boolean isOrdered(Node x, Key min, Key max) {
        if (x == null) return true;
        if (less(x.key, min) || less(max, x.key)) return false;
        return isOrdered(x.left, min, x.key) && isOrdered(x.right, x.key, max);
    } 

    // check that there are no duplicate keys
    // precondition: inorder traversal gives keys in order
    private boolean hasNoDuplicates() {
        assert isOrdered();
        for (int i = 1; i < size(); i++) {
            if (select(i).compareTo(select(i-1)) == 0) return false;
        }
        return true;
    }

    // check that ranks are consistent
    private boolean isRankConsistent() {
        for (int i = 0; i < size(); i++)
            if (i != rank(select(i))) return false;
        for (Key key : keys())
            if (key.compareTo(select(rank(key))) != 0) return false;
        return true;
    }

    private boolean less(Key x, Key y) {
        return x.compareTo(y) < 0;
    }


   /*****************************************************************************
    *  Test client
    *****************************************************************************/
    public static void main(String[] args) { 
        BST<String, Integer> st = new BST<String, Integer>();
        for (int i = 0; !StdIn.isEmpty(); i++) {
            String key = StdIn.readString();
            st.put(key, i);
        }
        for (String s : st.keys())
            StdOut.println(s + " " + st.get(s));
    }
}