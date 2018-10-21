/**
 * 
 * Student Name: Seán Smith
 * Student number: 2904982
 *
 */

import java.util.concurrent.*;
import java.util.*;

public class Assignment2 {
	public static void main(String[] args) {
		//Variables
		int freq = 0; int N = 10000000; int largest = 0;
		int numbers[] = new int[N]; int freq2 = 0;
		int nProc = Runtime.getRuntime().availableProcessors(); //Gets the available amount of processors 
		ExecutorService pool = Executors.newFixedThreadPool(nProc); //Creates a Thread Pool with a fixed amount of Threads
		ExecutorService pool2 = Executors.newFixedThreadPool(nProc); //Creates a Thread Pool with a fixed amount of Threads
		ArrayList <Future<Integer>> future =new ArrayList <Future<Integer>>();
		ArrayList<Future<Integer>> future2 =new ArrayList <Future<Integer>>();
		
		//Question 1 
		init(numbers, N);
		
		//Divide the work over the processors
		int[] index = new int[nProc+1];
		for (int i=0; i<=nProc; i++) {
			index[i] = (i*numbers.length)/nProc;
		}
		
		for(int j = 0; j < index.length-1; j++){
			Future<Integer> f = pool.submit(new EvenFreq(numbers,index[j],index[j+1]));
			future.add(f);
		}
		
		for(int j = 0; j < (index.length-1); j++){
			try {
			Future<Integer> f = future.get(j);
			freq += f.get();
		}
			catch (InterruptedException e) {}
			catch (ExecutionException e) {};
		}
		
		pool.shutdown();
		System.out.println("Frequency of the even numbers is: "+freq);
    //============================================================================================
    //Question 2
	
	//Divide the work over the processors
	int[] index2 = new int[nProc+1];
	for (int i=0; i<=nProc; i++) {
		index2[i] = (i*numbers.length)/nProc;
	}
	
	for(int j = 0; j < index.length-1; j++){
		Future<Integer> f = pool2.submit(new Largest(numbers,index[j],index[j+1]));
		future2.add(f);
	}
	
	for(int j = 0; j < (index.length-1); j++){
		try {
		Future<Integer> f = future2.get(j);
		if(f.get() > largest) largest = f.get();
	}
		catch (InterruptedException e) {}
		catch (ExecutionException e) {};
	}
	
	for(int j = 0; j < (index.length-1); j++){
		try {
		Future<Integer> f = future2.get(j);
		if(f.get() == largest) freq2++;
	}
		catch (InterruptedException e) {}
		catch (ExecutionException e) {};
	}
    	
	pool2.shutdown();
	System.out.println("Largest number is: "+ largest +" and it's frequency is: "+freq2);
    //=======================================
	}
	
	public static void init(int numbers[], int N) {
		for(int j = 0; j < numbers.length;j++) numbers[j] = (int)(Math.random()*N);
	}
	
}
//Code for threads for Question 1=========================
class EvenFreq implements Callable<Integer>{
	private int from, to;
	private int freq;
	private int data[];
	
	EvenFreq(int d[], int st, int en){
		data = d;
		from = st;
		to = en;
		freq = 0;
	}
	
	public Integer call() {
		for(int i = from; i < to; i++)
			if(data[i] % 2 == 0 && data[i] != 0) freq++;
		return freq;
	}
}

//=======================================================
//Code for Callable class here
class Largest implements Callable<Integer>{
	private int from, to;
	private int largest;
	private int data[];
	
	Largest(int d[], int st, int en){
		data = d;
		from = st;
		to = en;
		largest = 0;
	}
	
	public Integer call() {
		for(int i = from; i < to; i++)
			if(data[i] > largest) largest = data[i];
		return largest;
	}
}

//========================================================
