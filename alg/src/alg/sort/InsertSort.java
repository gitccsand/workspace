package alg.sort;

import java.awt.print.Printable;
import java.util.Arrays;

public class InsertSort {

	public static void main(String[] args) {
		
		int[] A = {6,8,4,55,63,14};
		System.out.println(Arrays.toString(A));
		
		for (int j = 1; j < A.length; j++) {
			
			int key = A[j];
			
			int i;
			for (i = j-1 ; i >= 0 ; i-- ) {				
				if (key<A[i]) 
					A[i+1]=A[i];				
				else
					break;
				
			}			
			A[i+1]=key;
			
		}		
		System.out.println(Arrays.toString(A));
	}		
	
}

//private static void printArray(int[] A){
//for (int i = 0; i < A.length; i++) {
//	System.out.print("--"+A[i]);
//}
//System.out.println();
//}
//

