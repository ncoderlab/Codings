// 	Selection Sort

import java.io.*;

class select

{
	public static void main(String args[])throws IOException
	{
		int[] array = new int[100];
		int max = 9;
		int low , temp;
		int a =  0;

	try
		{
			BufferedReader keyb = new BufferedReader (new
InputStreamReader(System.in));
			System.out.print("Enter max number to be sort:");
			max = Integer.parseInt(keyb.readLine());
			for (int x = 0 ; x < max ; x++)
			{
				System.out.print("Enter number:");
				array[x] = Integer.parseInt(keyb.readLine());
			}
					for (int m = 0 ; m < max ; m++)
						{

							System.out.print(array[m] + "  ");
						}
					System.out.print("
");
			//for (int a = max  - 1; a > 0; a--)
			{

			for( int b = 0 ; b < max ; b++)
				{
						low = array[b];
				for (int c = b ; c < max ; c++)
						{
							if (array[c] < low)
							{
								low  = array[c];
								//System.out.print("<" + b + "><" + c + "><" + low + ">
");
							}
							else
								{
								//	System.out.print("<" + b + "><" + c + "><" + low + ">
");
								}


							for (int d = b ; d < max ; d++)
								{
									if (array[d] == low)
									{
										temp = array[b];
										array[b] = array[d];
										array[d] = temp;
										//System.out.print("*" + c + "*");
									}
								}
						}

							System.out.print("
");
					for (int m = 0 ; m < max ; m++)
						{

							System.out.print(array[m] + "  ");
						}

				System.out.print("
");


				}




			}
