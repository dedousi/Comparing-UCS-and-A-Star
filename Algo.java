/* 
Important notes for this exersise that one should know
before reading the rest of the code:

    1. The UCS algorithm operates with the best g(n).
    Yet, all priorities are the same -> UCS becomes BFS. 

    2. The A* algorithm operates with the best e(n) = g(n)+h(n).
    Yet, all priorities are the same -> A* operates now with the best h(n).

    3. Also in this exersise, we use Nodes. (Node.java) -> More info there.
        
    4. Worth mentioning here is the .getName() and .setName() methods (from Node.java).
    Here we thought about having an easy way to see if a node is our final, without having to go through its array.
    Therefore, we came up with the "Name". 
    A node's "Name" is a String that has all the elements of its array together.
    And we make this transition (from array to string) using the method "namer" from this class (Algo). 
    (ex. [1,4,2] will make a name "142")
*/

//Imports.
import java.util.*;
import java.lang.*;

//Algo (sort for Algorithms) Class.
public class Algo{

    //Private fields.
    private LinkedHashMap<String,Node> visited = new LinkedHashMap<String,Node>();      //this is a list of our visited nodes. (HashMap <Name of node, array of node> -> Linked as in "in order" that we added them)
    private LinkedList<Node> path = new LinkedList<Node>();                             //this is a list in which we keep our final path.
    private Node fin;                                                                   //this is the goal Node (we will use this node for making comparisons).
    private Node start;                                                                 //this is the starting Node. (the node with the given list -> our AK = Arxiki Katastasi)
    
    //Constructor.
    public Algo(int[] start){
        
        long start1, start2, end1, end2, calc1, calc2;                                  //long values we will need in order to calculate the time that each algorithm takes.

        this.start = new Node(start);                                                   //initialising the starting node.
        this.start.setName(this.namer(this.start.getValue()));                          //setting the starting node's Name.
		this.start.setK(1);                                                             //setting the starting node's parent K (has no parent so we put an invalid value there)
        
        int[] fin = new int[start.length];                                              //making the array of the goal node.
		for(int i=0; i<start.length; i++){ fin[i] = start[i]; }                         //copying the start array to another array -> in order to sort it and get the goal array for the goal node.
        Arrays.sort(fin);
        this.fin = new Node(fin);                                                       //initialising the goal node.
        this.fin.setName(this.namer(this.fin.getValue()));                               //setting the goal's name.

        start1 = System.nanoTime();                                                     //starting the timer
        initUCS();                                                                      //UCS Algorithm.
        end1 = System.nanoTime();                                                       //ending timer
        calc1 = end1 - start1;                                                           //calculating the time.

        //clearing up the global lists and HashMaps that were used. (in order to use them as brand new with A*)
        this.visited.clear();
        this.path.clear();
        
        start2 = System.nanoTime();
        initAstro();                                                                    //Alpha Astro Algorithm.
        end2 = System.nanoTime();
        calc2 = end2 - start2;

        //Printing the timer results.
        System.out.println("UCS time: "+calc1+" ns.");
		System.out.println("A* time: "+calc2+" ns.");
		System.out.println("A* was faster for: "+(calc1-calc2)+" ns.\n");
    }

    //Initialisation of the UCS algorithm. (BFS)
    private void initUCS(){
        
		LinkedList<Node> q = new LinkedList<>();                                        //creating a queque for BFS. (Linked -> keeps the order)
        
        q.add(this.start);		 														//adding the starting node in the queque. (AK)	
        visited.put(this.start.getName(), this.start);                                  //puting the node inside our dictionary with the visited nodes. 

    	Node nn = this.start;
    	while (!q.isEmpty()){                                                           //while the list is not empty...
            nn = q.removeFirst();                                                       //take the first element from the linked list and remove it from it.
            if(!(nn.getName().equals(this.fin.getName()))){                             //if it's not our goal node... 
                for (int k = 2; k <= this.start.getValue().length; k++){                //create its children...
 
                    int[] temp = this.T(k, nn.getValue());		                        //getting the array of the child.
                    String tempInt = namer(temp);                                       //getting the name of the child.
                
                    if(!(visited.containsKey(tempInt))){                                //if we have not visited the child...
                        if(!(nn.getCreatedK().contains(k))){                            //don't create the child that has the same k with the one that it was created because we will go back to the parent.
                            Node c = new Node(temp);                                    //creating a new node for the child (with the child's array).
                            c.setK(k);                                                  //set the parent K that it was used to create it.
                            c.setName(tempInt);                                         //set the childs' name.
                            nn.addFriend(k,c);		                                    //add the child to the neightbors of the parent.
                            visited.put(tempInt, c);                                    //add the child to the nodes that we visited.
                            q.add(c);                                                   //add the child to the queque.
                        }
                    }
                }
            }else{ break; }                                                             //if it is our goal node, then break the loop and finish the algorithm.
        }
        this.pathFinder();                                                              //getting the final path.
        this.res(0);                                                                    //printing the results.
    }

    //Initialisation of the A* algorithm. (oparates similarly in many parts -> we won't explain the same code twice.)
    private void initAstro(){
        ArrayList<Node> MA = new ArrayList<Node>();

        this.h(this.start);                                                             //finding the h(AK).          
        visited.put(start.getName(), start);                                            //puting the node inside our dictionary with the visited nodes.
    	MA.add(this.start);                                                             //adding AK to MA.
        
        Node nn = this.start;
    	while(!MA.isEmpty()){                                                           //while our MA is not empty...
            nn = MA.get(0);                                                             //get the first element of MA. (the one with the best h(n) -> because we are sorting MA according the h(n) of each node)
            if(!(nn.getName().equals(this.fin.getName()))){                             //if we don't have our goal node...
                for (int k = 2; k <= this.start.getValue().length; k++){                //create the children just like before...
                    
                    int[] temp = this.T(k, nn.getValue());		
                    String tempInt = namer(temp);
                    
                    if(!(visited.containsKey(tempInt))){ 
                        if(!(nn.getCreatedK().contains(k))){
                            Node c = new Node(temp);
                            c.setK(k);
                            c.setName(tempInt);
                            nn.addFriend(k,c);	
                            visited.put(tempInt, c);
                            this.h(c);                                                  //but this time, while it creates the child, find the h(child). 
                            MA.add(c);                                                  //and add it to MA.
                        }
                    }
                }
                MA.remove(0);                                                           //then remove the node we visited from MA.
                this.MAsorter(MA);                                                      //and sort the MA accordingly.
            }else{ break; }                                                             //if we have our goal node, break the loop and give results.
        }
        this.pathFinder();                                                              //finding the path.
        this.res(1);                                                                    //printing the results.
    }

    //The is T(k) (method).
    public int[] T(int k, int[] a){
        
        int N = a.length;                                     //the length of the array we gave.
        int M = N-k;                                          //the number of the elements that are left in the array if we take the k first elements.

        int[] b = new int[N];                                 //the final result.
        int[] aR = new int[M];                                //the right array (the left over elements).
        int[] raL = new int[k];                               //the left array (the k first elements).

        for (int i = 0; i < k; i++){ raL[k-i-1] = a[i]; }     //getting the list with the first k elements.
        for (int i = 0; i < M; i++){ aR[i] = a[k+i]; }        //getting the list with the last M elements.
        for (int i = 0; i < N; i++){                          //reversing the raL list and rejoining aR and raL together into the final list b.
            if (i < k) { b[i] = raL[i]; }
            if (i >= k) { b[i] = aR[i-k]; }
        }
        return b;
    }

    //Method that sorts the MA(metopo anazitisis).
    public void MAsorter(ArrayList<Node> MA){
        for(int i=0; i<MA.size()-1; i++){
            Node one = MA.get(i);
            Node two = MA.get(i+1);
            if(one.getH() > two.getH()){  Collections.swap(MA, i, i+1); }
        }
    }
   
    //Method that sets the h of the node.
    public void h(Node s){
        
        int N = s.getValue().length;                            //the array length.
        int[] array = s.getValue();                             //the array.
        int h=0;                                                //h1
		int h2=0;                                               //h2 (we compare the two of them and the smaller one gets to be our final h).
        //both h and h2 are the number of the non-sorted-in-duos elements inside the array.
		
        //pointers. (helper variables)
        int p1=0;
        int p2=N-1;
        	
        while((array[p1]!=1 || array[p1]!=N )&& p1<p2){			//going through the array - stops when we find the max or min element of the list.
			if(Math.abs(array[p1]-array[p1+1])>1){ h++; h2++;}  //h = h2 = number of non sorted duos until the min or max element.
            p1++;
		}
		
        while((array[p1]!=1 || array[p1]!=N )&& p1<p2){         //going through the rest of the array - stops when we find the opposite of the max or min element we had found. (ex. if we had max then min and vise versa)
			if(Math.abs(array[p1]-array[p1+1])>1){ h=h+1/2; h2++;} //h and h2 are basically the same count but in h we add 1/2 instead of 1. (more info on report.pdf)
            p1++;
		}
		
		while(p1<p2){                                           //checking the sorted elements from end to start of the array.
			if(Math.abs(array[p1]-array[p1+1])>1){ h++; h2++;}
            p1++;		
		}

		while(array[p2]==p2){ p2--;	}							//finds how much the array is sorted.
		if(array[0]==p2-1){	h--; }                              //if the next element is the first one (in sorted array) then substract 1 from h.
		if(h2<h){h=h2;}											//deciding on the h that we want.
        if(h==1 && array[0]==1){h=0;}  
        if(h==0 && array[0]!=1){h=1;}
        s.setH(h);                                              //setting the h(child).
    }

    //Path Finding Method.
    public void pathFinder(){
        //we have the final node. We need to find its path and calculate the cost.
        //we will search the parent of the final node, and then the parents of that parent and so on until we find the starting node.
        Node parent = this.visited.get(this.fin.getName());
        this.path.add(parent);
        while(parent != this.start){
            for(Node i : this.visited.values()){
                if(i.getFriends().containsValue(parent)){
                    this.path.add(i);
                    parent = i;
                    break;
                }
            }
        }  
    }

    //Printing the results. (number == 0 -> UCS, number == 1 -> A*)
    public void res(int number){
        
        int expands = this.visited.size()-1;            //number of expands.
        int path = this.path.size()-1;                  //path size == total cost (since all the priorities are equal).
        Collections.reverse(this.path);                 //the path was found from finish to start, but we want it in reverse.

        if(number == 0){
            System.out.println("\n------------------------ UCS RESULTS ------------------------");
            System.out.println("Number of expands: "+ expands);
            System.out.println("The total cost is: "+ path);
            System.out.print("And the path is: ");
            System.out.print(this.start.getName());
            for(Node i : this.path){ 
                if(i.getName().equals(this.start.getName())){continue;}
                System.out.print(" -> (T"+i.getCreatedK()+") " + i.getName()); 
            }
        }else{
            System.out.println("\n------------------------ A* RESULTS ------------------------");
            System.out.println("Number of expands: "+ expands);
            System.out.println("The total cost is: "+ path);
            System.out.print("And the path is: ");
            System.out.print(this.start.getName());
            for(Node i : this.path){ 
                if(i.getName().equals(this.start.getName())){continue;}
                System.out.print(" -> (T"+i.getCreatedK()+") " + i.getName()); 
            }
        }
        System.out.print("\n");
        System.out.println("-------------------------------------------------------------");    
        System.out.println("");
    }

    //Method that gets the numbers from a given list and sets them as an int (that will be the Name of the Node).
    private String namer(int[] a){
    	String temp = "";                                       //setting an empty string.
    	for (int i = 0; i < a.length; i++){ temp += a[i]; }     //putting the numbers of the list in the string in order.
    	return temp;
    }
}