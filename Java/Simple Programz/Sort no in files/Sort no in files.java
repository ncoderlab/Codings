import java.io.*;
public class Sort1App{
public static void main(String[] args){
 String filename="file1.txt";
 int arraySize=30;
 int numItems=0;
 int[] myArray=new int[arraySize];

 try{
  BufferedReader in=new BufferedReader(new FileReader(filename));
  String s;

  while((s=in.readLine())!= null && numItems<30){
    myArray[numItems]=Integer.parseInt(s);
     numItems ++;
   }//while.

  }
 catch(IOException e){
      System.out.println("Error:"+e);
    }
 catch(NumberFormatException e){
      System.out.println("Error: "+e);
    }

       selectionSort(myArray,numItems);

        for (int i=0;i<numItems;i++)
         {
            System.out.println(myArray[i]);
           }//for.


    }//main.

     public static void selectionSort(int numbers[], int array_size)
{
 int i, j;
 int min, temp;

 for (i = 0; i < array_size-1; i++)
 {
  min = i;
  for (j = i+1; j < array_size; j++)
  {
   if (numbers[j] < numbers[min])
    min = j;
  }
  temp = numbers[i];
  numbers[i] = numbers[min];
  numbers[min] = temp;
  }
  }



}//class