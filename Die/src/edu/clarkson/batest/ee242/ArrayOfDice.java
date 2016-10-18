package edu.clarkson.batest.ee242;

//import java.lang.reflect.Array;
import java.util.Arrays;
/**
 * ArrayOfDice is used to test the functionality of the Die and loadedDie Classes and make sure that they are sortable
 * @author Troy
 */
public class ArrayOfDice {
	public static void main (String[] args){
		Die[] dieArray = new Die[5];
		for (int i=0; i<5; i++){
			if(i<3)
				dieArray[i]= new Die(12);
			else
				dieArray[i]= new LoadedDie(12,6,100); //creates loaded die that will always roll a 6
		}
		for (int i=0; i<5; i++){
			dieArray[i].roll(); //every die is rolled
		}
		System.out.println("Rolls before sort:");
		for (int i=0; i<5; i++){
			System.out.println(dieArray[i]); //all of the dies are printed
		}
		
		Arrays.sort(dieArray); //these dies are sorted
		System.out.println("Rolls after sort:");
		for (int i=0; i<5; i++){
			System.out.println(dieArray[i]); //all of the dies are printed again
		}
	}
}
