
/**
 * Write a description of class SilkRoad here.
 *
 * @author Maria Katalina Leyva Diaz - Michelle Dayanna Ruiz Carranza
 * @version (a version number or a date)
 */

import java.lang.Math;
import java.util.ArrayList;
import java.util.ArrayList;
public class SilkRoad{
    private final ArrayList<Integer> nDays;
    private int n;
    public SilkRoad(int n){
        this.nDays = new ArrayList<>();
        if (n < 1  || n > 200000){
            System.out.println("Number days between 2 and 200000");
        } else {
            for (int j : nDays){
                int[] element = new ArrayList<>();
                getConfiguration(element);
                nDays.add(element);
            }

        }
    }
    public void getConfiguration(int[] element){
        Scanner sc = new Scanner(System.in);
        tring input = sc.nextLine();
        this.nDays = input.split(" ");
        if (this.nDays.get(0) != 1 || this.nDays.get(0) != 2){
            System.out.println("enter 1 to add a new robot or 2 to add a new store");
        } else {
            if (this.nDays.get(0) == 1){
                int i = this.nDays.get(0); //Obtain if is a new store or new robot
                int x = this.nDays.get(1); //Obtain the location
            } else {
                int i = this.nDays.get(0); //Obtain if is a new store or new robot
                int x = this.nDays.get(1); //Obtain the location
                int c = this.nDays.get(2); //Obtain the number of tenges at the store
            }
        }

    }
}