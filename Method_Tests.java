import java.util.*;
import java.lang.*;
public class Method_Tests {
    public static int Brute(int[] array) {
        int maxSum = array[0];                              //set maxSum to first item in list
        int total;                                          //declare total
        for (int i = 0; i < array.length; i++) {            //iterate through list
            for (int j = i; j < array.length + 1; j++) {    //iterate from i to end of list
                total = 0;                                  //set total back to 0
                for (int k = i; k < j; k++) {               //iterate from i-j
                    total += array[k];                      //add i to total
                    if (maxSum < total) {                   //is total bigger with this new if yes set new total
                        maxSum = total;
                    }
                }
            }
        }
        return maxSum;
    }

    //Kadane Method
    public static int Kadane(int[] array) {
        int best = array[0];                                //establish variables
        int current = 0;
        for (int j : array) {                               //iterate through all variables in array
            current = Math.max(j, j + current);             //Kadane solution
            best = Math.max(best, current);
        }
        return best;
    }
    public static void main(String[] args) {
        int[] arr5 = {5, -3, -4, 5, 5};
        int[] arr10 = {10, -2, 5, 3, -10, 13, 4, 5, -9, -8};

        int Brute = Brute(arr5);
        int Kadane = Kadane(arr5);
        if (Brute == Kadane) {
            System.out.println("Array len 5 returns true");
        }
        Brute = Brute(arr10);
        Kadane = Kadane(arr10);

        if (Brute == Kadane) {
            System.out.println("Array len 10 returns true");
        }
    }
}
