 /* Assignment 1
 *
 * Student Name: Seán Smith
 * Student Number: 2904982
 *
 */

public class Assignment1{
    public static void main(String[] args) {
       //==================================================
       //Test code for Question 1
    	int num1 = 1 + (int) (Math.random()*1000000); 	//Randomly Generate the 1st number of dice throws
    	int num2 = 1 + (int) (Math.random()*1000000);	//Randomly Generate the 2nf number of dice throws
    	int num3 = 1 + (int) (Math.random()*1000000);	//Randomly Generate the 3rd number of dice throws
    	int num4 = 1 + (int) (Math.random()*1000000);	//Randomly Generate the 4th number of dice throws
    	int arrayLength = num1 + num2 + num3 + num4;	//Get the array length by adding
    	
    	Result arr = new Result(arrayLength);			//Create the result class with an array the thread will share
    	
    	//Declaring Threads and Start them
    	RollDice t1 = new RollDice(arr, 0, num1);
    	RollDice t2 = new RollDice(arr, num1, (num1+num2));
    	RollDice t3 = new RollDice(arr, (num1+num2), (num1+num2+num3));
    	RollDice t4 = new RollDice(arr, (num1+num2+num3), (arrayLength));
    	t1.start(); t2.start(); t3.start(); t4.start();
    	
    	//Make the main wait for the completion of the treads
    	try{
	    	t1.join(); t2.join(); t3.join(); t4.join();
    	} catch(InterruptedException e){}

    	//Get the array and the Frequency of the rolls
    	int[] temp = arr.get();
    	int[] freq = new int[6]; //Frequency result array Hard coded 6 for the size as there are 6 sides to a die
    	for(int i = 0; i < arrayLength; i++) freq[(temp[i]-1)]++; //use the array value - 1 as the index and simply increment instead of having if or switch statements
    	
    	System.out.println("------------------Statistics on Dice Rolling---------------------\nTotal Dice Rolls: "+arrayLength);
    	for(int j = 0; j < freq.length; j++) System.out.println("Frequency of "+(j+1)+": "+freq[j]);
       //==================================================
       //Test code for Question2
    	System.out.println("\n--------------------Index of Left Most Zero-----------------------");
    	DataArray data = new DataArray(); //create a new class with the data array for the threads to get access to
    	int x = 0;  //Looking for 0	
    	Found find = new Found(data.getLength()); //Found class so the Threads can talk to each other to see if they even need to run
    	int dataLength = data.getLength(); //Getting the length of the data array
    	int segment = dataLength / 4;	//Getting the size of the segments
    	
    	//Creating the threads
    	FindLeftmostZero t5 = new FindLeftmostZero(data.get(), find, 0, segment, x, "Thread 1");
    	FindLeftmostZero t6 = new FindLeftmostZero(data.get(), find, segment, (segment*2), x, "Thread 2");
    	FindLeftmostZero t7 = new FindLeftmostZero(data.get(), find, (segment*2), (segment*3), x, "Thread 3");
    	FindLeftmostZero t8 = new FindLeftmostZero(data.get(), find, (segment*3), dataLength, x, "Thread 4");
    	t5.start(); t6.start(); t7.start(); t8.start();
    	//Setting Priorities for the lower segment threads to run first.
    	t5.setPriority(9);
    	t6.setPriority(7);
    	t7.setPriority(5);
    	t8.setPriority(3);
    	
    	//Make the main wait for the completion of the treads
    	try{
	    	t5.join(); t6.join(); t7.join(); t8.join();
    	} catch(InterruptedException e){}
    	
    	//for(int i = 0; i < data.get().length; i++) System.out.println("Index: "+i+", Value: "+data.get()[i]); //For debugging 
    	//if a zero is found display the index else display that no zero was found
    	if(find.found() == true) System.out.println("Index of Left Most Zero is: "+find.getIndex());
    	else System.out.println("There is no Zero's in the Data Array");
    }
}

//=========================================================
// Code for thread classes here
//Question 1
class RollDice extends Thread{
	
	private Result arr;
	private int lb;
	private int ub;
	
	RollDice(Result r, int x, int y){ //Constructor 
		arr = r;			//The Result class that has the array
		lb = x;				//Lower limit index
		ub = y;				//Upper limit index
	}
	
	public void run() {
		for(int i = lb; i < ub; i++) arr.set((1+(int)(Math.random()*6)), i);
	}
	
}

class Result{	//Result class 
	private int[] list;
	
	Result(int arr){
		list = new int[arr];
	}
	
	public void set(int res, int index) {
		list[index] = res;
	}
	
	public int[] get() {
		return list;
	}
}

//Question 2
class FindLeftmostZero extends Thread{
	private int[] data;
	private Found fnd;
	private int lb;
	private int ub;
	private int find;
	
	FindLeftmostZero(int[] d, Found fd, int x, int y, int f, String n){ //Constructor 
		super(n);			//Name of the Thread
		data = d;			//The DataArray class that has the array
		fnd = fd;			//Found class so that the Threads can talk to each other
		lb = x;				//Lower limit index
		ub = y;				//Upper limit index
		find = f;			//Value to find
	}
	
	public void run() {
		System.out.println("I am Starting: "+this.getName());
		int k = lb;	//Assign a variable the lower bound given to this segment
		//Run the while loop while the counter is still between the lower bound and upper bound and if the value of the lower bound is
		// smaller that the index of a FOUND zero. If it is bigger than one of the other threads that has a lower segment found a zero
		//and the thread does not run.
		while (k < ub && lb < fnd.getIndex()){
			//Since we look through the segment left to right if a Zero is found there is no need to continue looking
			if(data[k] == find) {
				fnd.set(k); 
				break;
			}
			k++;
		}
		//Message to let me know when the thread stop and at what index //To see if it even ran
		System.out.println("My name is "+this.getName()+" I am done. I got to index: "+k+" and my lower bound was: "+lb);
	}
}

class DataArray { //Class that has the array of values
	private static final int N = 10000000;
	private int[] data;
	
	DataArray(){
		data = new int[N];
		for(int j = 0; j < N; j++) data[j] = (int)(Math.random()*N);
	}
	
	public int[] get(){
		return data;
	}
	
	public int getLength() {
		return N;
	}

}

class Found{ //Class that the thread use to talk to one another to see if a zero was found
	private boolean found;
	private int index;
	
	Found(int x){
		found = false;
		index = x;
	}
	
	public void set(int k){
		found = true;
		if(k < index) index = k;
	}
	
	public boolean found(){
		return found;
	}
	
	public int getIndex() {
		return index;
	}
}
