/* 
Some information about the code:
	i) The .getName() and .setName() methods and the String name.
    We thought about having an easy way to see if a node is our final (goal node) without having to go 
	through its array every single time. Therefore, we came up with the "Name". 
    A node's name is a String that has all the elements of its array all together.
    (ex. [1,4,2] will make a name "142")

	ii) The .getValue() method and the int[] array.
	Each node has inside of it an array (constructed with it), 
	this method helps us get this array from inside the node.

	iii) The .getH() and .setH() methods and the int h.
	For the A* algorithm, we will use an h for every node in order to get to our final node.
	Those are methods for setting and getting the H, as well as the h value itself.

	iv) The .getFriends and .addFriend methods and the Linked HashMap friends.
	The hashmap friends is a hashmap with all the neighbors of a node.
	The methods are for adding to the hashmap and getting all the neighbors.

	v) Finally, the .getCreatedK() and .setK() methods and the ArrayList createdK.
	Every node can create as many children as the diffent T(k) that it can use. 
	Yet, one of those T(k) is the one that created himself. 
	If we use it again then we will return to the parent node. And because we don't want that,
	we created a list that has all the k that can created the node.
	(ex. 132 can create with k=2 -> T(2) -> the node 312, BUT the node 312 with T(2) -> creates 132 the parent.)
----------------------------------------
*/

//Imports.
import java.util.*;

//Node class.
public class Node{

	//Private fields.
    private int h;																		//the h(n).
	private int[] array;													            //the list (inside every node).
	private String name;                                                                //the "name" of the node.
	private LinkedHashMap<Integer,Node> friends = new  LinkedHashMap<Integer,Node>();   //the neighbors.(friends)
	private ArrayList<Integer> createdK = new ArrayList<>();				            //the k that was used to create the node.

    //Constructor.
    public Node(int[] array){ this.array = array; }
        
	//Getters.
    public LinkedHashMap<Integer,Node> getFriends(){ return friends; }          	    //getting the neighbors.
    public int[] getValue(){ return this.array; }					                    //getting the list in that step.
    public String getName(){ return this.name; }						                //getting the name of a node.
	public ArrayList getCreatedK(){ return this.createdK; }			                    //getting the k that was used to create the node.
	public int getH(){return this.h;}

	//Setters.
	public void setName(String name){ this.name = name; }				                //setting the name.
	public void setK(int K){ createdK.add(K); }						                    //setting the parent k.
	public void setH(int H){ this.h = H; }

	//Other Methods.
    public void addFriend(int k, Node c){ friends.put(k, c); }		                    //adding a new neighbor.(friend)
}
	