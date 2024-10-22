//Imports.
import java.util.*;

//The Main Class.
class ONE{
    public static void main(String[] args){

        //Getting the user input.
        Scanner scan = new Scanner(System.in);
        System.out.print("\nPlease give me your list (separation token must be \",\"): ");
        String list = scan.nextLine();         
        scan.close();                         

        //Making the input from string to array.     
        String[] token = list.split(",");
        int[] lister = new int[token.length];
        int pos = 0;
        for(String i : token){ 
            lister[pos] = Integer.valueOf(i);
            pos++;
        }

        //Executing the Algorithms.
        Algo algorithms = new Algo(lister);
    }
}
