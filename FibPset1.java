import java.io.*;
import java.util.*;

public class FibPset1 {
	public static void main(String[] args) throws IOException{
		
		long nanoRecStartTime = System.nanoTime();
			recMethod();
		long nanoRecEndTime = System.nanoTime();
		
		long nanoIterateStartTime = System.nanoTime();
			iterateMethod();
		long nanoIterateEndTime = System.nanoTime();
	
		
	}
	
	public static void recMethod(){
		
	}
	
	public static void iterateMethod(){
		
		//to save memory space, we can just store the latest 3 numbers in the fibonacci sequence 
		long[] memory = new long[3]; 
		
		int n=1;
		memory[0]=0;
		memory[1]=1;
		
		boolean done = false;
		while(!done)
		{
			n++;
			//the nth term in the Fibonnaci sequence is the sum of the previous two terms 
			memory[n % 3] = memory[(n + 1) % 3] + memory[(n+2)%3];
		}
		
	}
	
	public static void matrixMethod(){
		
	}
}
