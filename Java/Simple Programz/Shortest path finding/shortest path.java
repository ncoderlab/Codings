//shortest path using Breadth First Search
Algorithm






import java.io.*;
import java.util.*;
class shortpath
{
	public static int x1,x2,y1,y2,n,m;
	public static int a[][]=new int[10][10],parent[][]=new
int[10][10],dist[][]=new int[10][10],i,j;
	public static void main(String s[])   throws IOException
	{
		int x,y,num;
		LinkedList l=new LinkedList();
		LinkedList path=new LinkedList();
		BufferedReader br=new BufferedReader(new 
InputStreamReader(System.in));
		System.out.println("Enter the value for n & m 1st matrix");
		BufferedReader bf=new BufferedReader(new 
InputStreamReader(System.in));
		n=Integer.parseInt(bf.readLine());
		m=Integer.parseInt(bf.readLine());
		System.out.println("Enter the elements for 1st matrix(0 or 1)");
		for(i=0;i<n;i++)
			for(j=0;j<m;j++)
			{
				a[i][j]=Integer.parseInt(bf.readLine());
				parent[i][j]=-1;
				dist[i][j]=n*m;
			}
		System.out.println("Enter the position of source(x,y)");
		x1=Integer.parseInt(bf.readLine());
		y1=Integer.parseInt(bf.readLine());
		System.out.println("Enter the position of Destination(x,y)");
		x2=Integer.parseInt(bf.readLine());
		y2=Integer.parseInt(bf.readLine());
		l.add(getnum(x1,y1));
		dist[x1][y1]=0;
		while(l.size()!=0)
		{
			num=Integer.parseInt(l.removeLast().toString());
			x=num/m;
			y=num%m;
			if(x>0&&a[x-1][y]!=1&&(dist[x-1][y]>(dist[x][y])+1))
			{
				l.add(getnum(x-1,y));
				dist[x-1][y]=dist[x][y]+1;
				parent[x-1][y]=num;
			}
			if(y>0&&a[x][y-1]!=1&&(dist[x][y-1]>(dist[x][y])+1))
			{
				l.add(getnum(x,y-1));
				dist[x][y-1]=dist[x][y]+1;
				parent[x][y-1]=num;
			}
			if(x<n-1&&a[x+1][y]!=1&&(dist[x+1][y]>(dist[x][y])+1))
			{
				l.add(getnum(x+1,y));
				dist[x+1][y]=dist[x][y]+1;
				parent[x+1][y]=num;
			}
			if(y<m-1&&a[x][y+1]!=1&&(dist[x][y+1]>(dist[x][y])+1))
			{
				l.add(getnum(x,y+1));
				dist[x][y+1]=dist[x][y]+1;
				parent[x][y+1]=num;
			}
		}

		path.add("("+x2+","+y2+")");
		x=x2;y=y2;
		do{
		num=parent[x][y];
		x=num/m;
		y=num%m;

		path.add("("+x+","+y+")");
		}while(!(x==x1&&y==y1));
		while(path.size()!=0)
			System.out.println(path.removeLast());
	}
	public static Integer getnum(int x,int y)
	{
		return new Integer(x*m+y);
	}

}