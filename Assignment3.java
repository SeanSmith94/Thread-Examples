/**
*
* Student Name: Seán Smith
* Student Number: 2898514
* 
*/
import java.util.concurrent.*;
public class Assignment3 {
	public static void main(String[] args) {
        // Test code is here below
		int N = 1000000;
        int array[] = new int[N]; //Array with Length N
        for(int j = 0; j < array.length; j++) array[j] = (int)(Math.random()*N); //Fill the array with random numbers
        ForkJoinPool fjPool = new ForkJoinPool(); //Create a new Fork join pool
        
        long start = System.currentTimeMillis();
        fjPool.invoke(new MergeSort(array,0,array.length));
        long end = System.currentTimeMillis();
        long runTime = end-start;
        System.out.println("The run time is "+ runTime + " millisecondes ");   
    }
}

class MergeSort extends RecursiveAction{
    
	int [] data;
    int lb;
    int ub;

    MergeSort(int dt[], int lower, int upper){
        data = dt;
        lb = lower;
        ub = upper;
    }

    protected void compute(){
    		//if bigger than 100 use Merge Sort
            if((lb + 100) < ub){
                int mid = (lb + ub)/2;
                MergeSort  right = new MergeSort(data, lb, mid);
                MergeSort left = new MergeSort(data,mid, ub);
                invokeAll(right, left);
                merge(data,lb,mid,ub);
            }else	// if smaller than 100 use insertion Sort instead 
                insertionSort(data, lb, ub);
    }
    
    private void merge(int f[], int lb, int mid, int ub){
   	  int c[] = new int[ub-lb];
  		int k = 0;int j = lb; int h = mid;
  		while(j < mid && h < ub){
  			if(f[j] <= f[h]){
  				c[k] = f[j];
  				j++;
  			}
  			else{
  				c[k] = f[h];
  				h++;
  			}
  			k++;
  		}
  		while(j < mid){ c[k] = f[j];  k++; j++; }
  		while(h < ub){c[k] = f[h]; k++; h++;}
  		//Now copy data back to array
  		for(int p = 0; p < c.length;p++)
  			f[lb+p] = c[p];
   }
    
    //Insertion Sort
    private void insertionSort(int dt[], int a, int b){
    	 for(int i = a; i < b; i++){
	   		 int j = i;
	   		 while(j > a && dt[j] < dt[j-1]){
	   				int temp = dt[j]; dt[j] = dt[j-1]; dt[j-1] = temp;
	   				j--;
	   		 }
    	 }
    }
}