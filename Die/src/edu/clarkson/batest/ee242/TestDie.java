package edu.clarkson.batest.ee242;
/**
 * Test Die is used in order to test out the functionality of the Die and loadedDie Classes
 * @author Troy
 */
public class TestDie {
	
	/**
	 * Constructer left blank, this is run as the main program
	 */
	
	TestDie(){
		
	}
	public static void main (String[] args){
		
		testRoll();
		testLoadedRoll();
		testGetTop();
		System.out.println(testEquals());
	}
	/**
	 * This test is designed to simulate rolling the die.
	 * It shows that the die rolls between 1 and 6. 
	 * Every time the test is complete it shows a different probability of each roll, which shows how it is properly loaded.
	 */
	public static void testRoll(){
		System.out.println("Die test:");
		Die D6= new Die();
		int currentRoll = 0;
		int[] count= {0,0,0,0,0,0};
		for(int i = 0; i < 10000; i++){
			currentRoll=D6.roll();
			count[currentRoll-1]++;
		}
		for(int j = 0; j<6; j++){
		System.out.println("The Odds of rolling a "+ (j+1)+": "+ (float)count[j]/10000);
		}
	}
	/**
	 * This test is designed in order to simulate a rolling loaded die
	 * This shows a 6 sided loaded die on side 2, it shows that it rolls 80% on side 2. This shows the probability for each side.
	 */
	public static void testLoadedRoll(){
		System.out.println("Loaded Die test:");
		Die D6= new LoadedDie(6,2,80);
		int currentRoll = 0;
		int[] count= {0,0,0,0,0,0};
		for(int i = 0; i < 10000; i++){
			currentRoll=D6.roll();
			count[currentRoll-1]++;
		}
		for(int j = 0; j<6; j++){
		System.out.println("The Odds of rolling a "+ (j+1)+": "+ (float)count[j]/10000);
		}
	}	
	/**
	 * testGetTop rolls the die and then checks it to insure that they are equal
	 */
	public static void testGetTop(){
		Die D6= new Die();
		System.out.println("Roll the dice and get: " +D6.roll() + " look at the top of die again and get: " + D6.getTop());
	}
	public static Boolean testEquals(){
		System.out.println("test Equals");
		Die die1=new Die();
		Die die2=new Die();
		die1.roll();
		die2.roll();
		System.out.println(die1);
		System.out.println(die2);
		return die1.equals(die2);
	}
}
