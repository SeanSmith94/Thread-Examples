# Thread-Examples
Questions for Assignment 1:

Question 1:

Write a program that uses 4 threads that each toss a die a given number of times. The
result of each toss is stored in a shared array. The array is deemed to be large enough to
store the result of each throw and each thread should only write to its own array segment.
Once the threads have completed their work then the main program counts the frequency
of each throw and prints it on the screen.

Question 2:

Given below is a single threaded program that computes the index of the leftmost zero in
a huge array. Your task is to write a parallel solution that distributes the workload fairly
over 4 threads. It is important to try to write an optimal solution. Note: it is not required to
optimize the initializtion phase by using threads.

            public class FindLeftmostZero {
              static final int N = 10000000;
              public static void main(String[] args) {
                int data[] = new int[N];
                //assume occurrence of zero equally likely for all numbers generated
                for(int j = 0; j < N; j++)
                  data[j] = (int)(Math.random()*N);
                  int index = 0;
                  while(index < data.length && data[index] != 0) index++;
                    if(index == data.length)
                      System.out.println("No zero");
                    else
                      System.out.println(index);
              }
            }
            
Questions for Assignment 2:
 
Question 1:

Given below is a sequential program that calculates the frequency of even values in a large array. Your
task is to write a parallel solution that distributes all of the workload over the number of processors on
the machine executing your program. The work should be distributed so that each thread deals with
block segments that do not differ in size by more than 1.

                public static void main(String[] args) {
                  int f[] = new int[1000000];
                  for(int j = 0; j < f.length;j++) f[j] = (int)(Math.random()*100000);
                  int freq = 0;
                  for(int j = 0; j < f.length; j++)
                    if(f[j] % 2 == 0) freq++;
                      System.out.println(freq);
                }

Question 2:

A program is required to find the largest value together with the frequency of its occurrence in a huge
integer array. This program will be executed on a multi-core machine and the program must maximize
the use of this resource. Using a Threadpool and the Callable interface write a solution to this problem.

Questions for Assignment 3:

Question 1:

MergeSort continuously divides the data into segments until segments of size 1 are reached. It then begins
the merging phase. This is the expensive part. Improvements could be made if we could reduce the cost
of merging. It turns out that InsertionSort is very efficient for small data sequences (say sequences of 100
values) where the data is partially ordered in the correct order and the displacement is small.
The idea is to combine MergeSort and InsertionSort to reduce the overhead of merging. To do this we
terminate the MergeSort division when segments of some given size are reached, use InsertionSort to sort
the segments and then do the merging as before.

Your task is to implement a second version of this solution using the Fork/Join framework. Compare the
performance (running time) of both solutions using an integer array of size 10000000.

            static void mergeSort(int f[], int lb, int ub){
                  //termination reached when a segment of size 1 reached - lb+1 = ub
                  if(lb+1 < ub){
                       int mid = (lb+ub)/2;
                       mergeSort(f,lb,mid);
                       mergeSort(f,mid,ub);
                       merge(f,lb,mid,ub);
                   }
            }
                        
            static void merge(int f[], int p, int q, int r){
                  //p<=q<=r
                  int i = p; int j = q;
                  //use temp array to store merged sub-sequence
                  int temp[] = new int[r-p]; int t = 0;
                  while(i < q && j < r){
                        if(f[i] <= f[j]){
                           temp[t]=f[i];i++;t++;
                        }else{
                            temp[t] = f[j]; j++; t++;
                        }
                  }
            
                  //tag on remaining sequence
                  while(i < q){ temp[t]=f[i];i++;t++;}
                  while(j < r){ temp[t] = f[j]; j++; t++;}
                  //copy temp back to f
                  i = p; t = 0;
                  while(t < temp.length){ f[i] = temp[t]; i++; t++;}
            }
