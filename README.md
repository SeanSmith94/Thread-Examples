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
