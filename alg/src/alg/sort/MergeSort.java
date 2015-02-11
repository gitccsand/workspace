package alg.sort;

import java.util.Arrays;

public class MergeSort {

	public static void main(String[] args) {
		int[] M = {96,25,1,6,48,6,9,45,75};

		System.out.println(Arrays.toString(M));	
	
		Msort(M, 0, M.length-1);
		
		System.out.println(Arrays.toString(M));
		
	}
	
	private static void Msort(int[] m,int p,int r){
		if (p<r){
			int q = (p+r)/2;
			Msort(m, p, q);
			Msort(m, q+1, r);
			Merge(m, p, q, r);
		}
	}
	
	private static void Merge(int[] m,int p,int q,int r){
		int n1 = q-p+1;
		int n2 = r-q;
		int L[] = new int[n1+1],R[]= new int[n2+1];
		int i,j;
		for ( i = 0;i<n1;i++)
			L[i]=m[p+i];
		for ( j = 0;j<n2;j++)
			R[j]=m[q+1+j];
		L[i]=Integer.MAX_VALUE;
		R[j]=Integer.MAX_VALUE;
		i=0;
		j=0;
		for (int k = p;k<=r;k++){
			if(L[i]<=R[j]){
				m[k]=L[i];
				i++;
			}else{
				m[k]=R[j];
				j++;
			}
			
		}
	}

}
