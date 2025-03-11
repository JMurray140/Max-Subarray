import java.util.*;
import java.lang.*;
public class Knapsack {

    public static int DynamicKnapsack (int[][] itemlist, int item_count, int sack_limit) {
        ArrayList<Integer> test = new ArrayList<>();
        int[][] sack = new int[item_count][sack_limit + 1];
        int sum = 0;
        //Dynamic Programming Solution
        for (int i = 0; i < sack.length; i++) {
            sack[i][0] = 0;
            for (int j = 0; j < sack[i].length; j++) {
                if (j < itemlist[1][0]) {
                    sack[0][j] = 0;
                } else {
                    sack[0][j] = itemlist[0][0];
                }
            }
        }
        for(int i = 1; i < sack.length; i++) {
            for (int j = 1; j < sack[i].length; j++) {
                if (j < itemlist[1][i]) {
                    sack[i][j] = sack[i-1][j];
                }
                else {
                    sack[i][j] = Math.max(sack[i-1][j], sack[i-1][j-itemlist[1][i]] + itemlist[0][i]);
                }
            }
        }
        sum = sack[item_count-1][sack_limit];
        printer(sack);
        System.out.println("Sum of Dynamic Programming Knapsack = " + sum);
        System.out.println();
        return sum;
    }
    public static int WeightFirstKnapsack(int[][] itemlist, int max_size, int sack_limit) {
        int[][] sack = new int[2][max_size];
        int sum = 0;
        //Greedy Heuristic 1: Decreasing Value
        int minidx;
        for (int i = 0; i < itemlist[0].length; i++) {
            minidx = i;
            for (int j = i + 1; j < itemlist[0].length; j++) {
                if (itemlist[1][minidx] > itemlist[1][j]){
                    minidx = j;
                }
            }
            int tempWeight = itemlist[0][i];
            int tempVal = itemlist[1][i];
            itemlist[0][i] = itemlist[0][minidx];
            itemlist[1][i] = itemlist[1][minidx];
            itemlist[0][minidx] = tempWeight;
            itemlist[1][minidx] = tempVal;
        }
        int limit = 0;
        for (int i = 0; i < itemlist[1].length; i++) {
            if (limit + itemlist[1][i] > sack_limit) {
                break;
            }
            sum += itemlist[0][i];
            sack[0][i] = itemlist[0][i];
            limit += itemlist[1][i];
            sack[1][i] = itemlist[1][i];
        }
        System.out.println("adjusted sack");
        printer(sack);
        System.out.println("Sum of sack = " + sum);
        System.out.println();
        return sum;
        //END KNAPSACK
    }
    public static int ValueFirstKnapsack(int[][] itemlist, int max_size, int sack_limit) {
        int[][] sack = new int[2][max_size];
        int sum = 0;
        //Greedy Heuristic 2: increasing weight
        int minidx;
        for (int i = 0; i < itemlist[0].length; i++) {
            minidx = i;
            for (int j = i + 1; j < itemlist[0].length; j++) {
                if (itemlist[0][minidx] < itemlist[0][j]){
                    minidx = j;
                }
            }
            int tempWeight = itemlist[0][i];
            int tempVal = itemlist[1][i];
            itemlist[0][i] = itemlist[0][minidx];
            itemlist[1][i] = itemlist[1][minidx];
            itemlist[0][minidx] = tempWeight;
            itemlist[1][minidx] = tempVal;
        }
        int limit = 0;
        for (int i = 0; i < itemlist[1].length; i++) {
            if (limit + itemlist[1][i] > sack_limit) {
                break;
            }
            sum += itemlist[0][i];
            sack[0][i] = itemlist[0][i];
            limit += itemlist[1][i];
            sack[1][i] = itemlist[1][i];
        }
        System.out.println("adjusted sack");
        printer(sack);
        System.out.println("Sum of sack = " + sum);
        System.out.println();
        return sum;
    //END KNAPSACK
    }
    public static void printer(int[][] array) {
        for (int i = 0; i < array.length; i++) {
            if (i == 0) System.out.print("Value:  ");
            else if (i == 1) System.out.print("Weight: ");
            else System.out.print("        ");
            for (int j = 0; j < array[i].length; j++) {
                System.out.printf("|%-5d ", array[i][j]);
            }
            System.out.print("| \n");
        }
        System.out.println();
    }
    //Method to randomly create a new item list with weights and values;
    public static int[][] Randomizer(int size, int max_weight, int max_value) {
        Random rand = new Random();
        int[][] item_list = new int[2][size];
        for (int i = 0; i < item_list[0].length; i++){
            item_list[0][i] = rand.nextInt(1, max_value + 1);
            for (int j = 0; j < item_list[1].length; j++) {
                item_list[1][j] = rand.nextInt(1, max_weight + 1);
            }
        }
        printer(item_list);
        return item_list;
        //END RANDOMIZER
    }
    public static void main(String[] args ) {
        ArrayList<Double> TimesW = new ArrayList<Double>();
        ArrayList<Double> TimesV = new ArrayList<Double>();
        ArrayList<Double> TimesDP = new ArrayList<>();
        ArrayList<Float> greedy1err = new ArrayList<>();
        ArrayList<Float> greedy2err = new ArrayList<>();
        //BASE VARIABLES//
        double avgVal = 0;
        Scanner input = new Scanner(System.in);
        //COLLECT INFORMATION//
        System.out.print("ITERS: ");
        int iteration_count = input.nextInt();
        System.out.print("NUM ITEMS: ");
        int item_count = input.nextInt();
        System.out.print("BAG MAX WEIGHT: ");
        int sack_limit = input.nextInt();
        System.out.print("MAX ITEM WEIGHT: ");
        int max_weight = input.nextInt();
        System.out.print("MAX VALUE: ");
        int max_value = input.nextInt();
        System.out.println();

        //For Loop Weight First Heuristic
        for (int i = 0; i < iteration_count; i++) {
            System.out.println("Iteration Count: " + (i + 1));
            System.out.println("Random List");
            int[][] item_list = Randomizer(item_count, max_weight, max_value);
            // DYNAMIC PROGRAMMING //
            System.out.println("Dynamic Programming");
            double start_time = System.nanoTime();
            int optimal = DynamicKnapsack(item_list, item_count, sack_limit);
            double end_time = System.nanoTime();
            TimesDP.add(end_time - start_time);
            // WEIGHT FIRST //
            System.out.println("Weight First");
            start_time = System.nanoTime();
            int soln = WeightFirstKnapsack(item_list, item_count, sack_limit);
            end_time = System.nanoTime();
            TimesW.add(end_time - start_time);
            greedy1err.add((float)soln/optimal);
            // VALUE FIRST //
            System.out.println("Value First");
            start_time = System.nanoTime();
            soln = ValueFirstKnapsack(item_list, item_count, sack_limit);
            end_time = System.nanoTime();
            TimesV.add(end_time - start_time);
            greedy2err.add((float)soln/optimal);
        }
        //Calc Times Weight First
        double avgTime = 0;
        for (int i = 0; i < TimesW.size(); i++) {
            avgTime += TimesW.get(i);
        }
        avgTime /= TimesV.size();
        System.out.printf("GH1: %.7f \n", avgTime/1000000);
        //Calc Times Value First
        avgTime = 0;
        for (int i = 0; i < TimesV.size(); i++) {
            avgTime += TimesV.get(i);
        }
        avgTime /= TimesV.size();
        System.out.printf("GH2: %.7f \n", avgTime/1000000);
        avgTime = 0;
        for (int i = 0; i < TimesDP.size(); i++) {
            avgTime += TimesDP.get(i);
        }
        avgTime /= TimesDP.size();
        System.out.printf("DP: %.7f \n", avgTime/1000000);
        //Calculate Verification //

        for (int i = 0; i < greedy1err.size(); i++) {
            avgVal += greedy1err.get(i);
        }
        avgVal /= greedy1err.size();
        System.out.printf("Weight First Verification: %f \n", avgVal);
        // CALC AVG 2 //
        avgVal = 0;
        for (int i = 0; i < greedy2err.size(); i++) {
            avgVal += greedy2err.get(i);
        }
        avgVal /= greedy2err.size();
        System.out.printf("Value First Verification: %f \n", avgVal);
    //END MAIN
    }
//END PROGRAM
}
