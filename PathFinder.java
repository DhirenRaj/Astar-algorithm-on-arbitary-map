import java.io.*;
import java.util.StringTokenizer;
import java.util.Scanner;
import java.util.*;
import java.lang.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
class PathFinder
{List<List<Integer>> paths=new ArrayList<List<Integer>>();
static String ex;
   static List<List<Integer>> s1=new ArrayList<List<Integer>>();
static List<String> s2=new ArrayList<String>();
List<Integer> exclude=new ArrayList<Integer>();
static int size=0,spi,epi,opt,heu;
static String sp,ep;
int[][] m;
 public void Input()
 {
     try
     {
String s;
Scanner sc=new Scanner(System.in);
FileReader fr = new FileReader("locations.txt");
BufferedReader br = new BufferedReader(fr);
while((s=br.readLine()).compareTo("END")!=0)
{
StringTokenizer st=new StringTokenizer(s," ");
s2.add(st.nextToken());
List<Integer> temp =new ArrayList<Integer>();
temp.add(Integer.parseInt(st.nextToken()));
temp.add(Integer.parseInt(st.nextToken()));
s1.add(temp);
size++;
}
for(int i=0;i<size-1;i++){
    for(int j=i+1;j<size;j++)

    {
        if(s2.get(i).compareTo(s2.get(j))>0)
        {
            String temp1=s2.get(j);
            s2.set(j,s2.get(i));
            s2.set(i,temp1);
            List<Integer> temp2=s1.get(j);
            s1.set(j,s1.get(i));
            s1.set(i,temp2);

        }
    }
}
 m=new int[size][size];
 fr = new FileReader("connections.txt");
 br = new BufferedReader(fr);
while((s=br.readLine()).compareTo("END")!=0)
{
StringTokenizer st=new StringTokenizer(s," ");
int i=s2.indexOf(st.nextToken());
st.nextToken();
while(st.hasMoreTokens()){
int j=s2.indexOf(st.nextToken());
m[i][j]=1;
m[j][i]=1;
}
}
System.out.println("Available nodes are :\n"+s2);

}catch(Exception e){e.printStackTrace();}
}
public void exclu()
{
    for(int i:exclude)
{
    for(int j=0;j<size;j++)
    {
     m[i][j]=0;
     m[j][i]=0;
    }
}
}
public List<Integer> Alternates(int sp)
{
    List<Integer> alt=new ArrayList<Integer>();
    for(int j=0;j<size;j++)
        if(m[sp][j]==1)
        alt.add(j);
    return alt;
}
public void Output()
{

    try{
for(int i=0;i<paths.size();i++)
{List<Integer> path=new ArrayList<Integer>();
path=paths.get(i);
double dist,total=0;
System.out.println("------------------------------------------");
for(int j=0;j<path.size()-1;j++){
    String z1=s2.get(path.get(j));
    String z2=s2.get(path.get(j+1));
    dist=distance(path.get(j),path.get(j+1));
    total=total+dist;
    System.out.println(z1+" to "+z2+" Length "+dist);
}
System.out.println("Total path length "+total);
System.out.println("-----------------------------------------");
}
    }catch(Exception e){e.printStackTrace();}
}
public double distance(int a,int b)
{
    if(heu==2)
        return 1;
    double x1,x2,y1,y2,z1,z2,e,f;
    x1=s1.get(a).get(0);
    y1=s1.get(a).get(1);
    x2=s1.get(b).get(0);
    y2=s1.get(b).get(1);
    e=x2-x1;
    f=y2-y1;
    z1=Math.pow(e,2);
    z2=Math.pow(f,2);
    return Math.sqrt((z1+z2));
}
public List<Integer> findpath(int [] prev,int current)
{
    List<Integer> path=new ArrayList<Integer>();
    path.add(current);
    while(prev[current]!=-1)
    {
        current=prev[current];
        path.add(current);
    }
    Collections.reverse(path);
    return (path);
}
public void Astar()
{
   List<Integer> open=new ArrayList<Integer>();
     List<Integer> close=new ArrayList<Integer>();
     open.add(spi);
     int [] prev=new int[size];
     for(int i=0;i<size;i++)
        prev[i]=-1;
     double[] disttravel=new double[size];
     double[] estimated=new double[size];
     for(int i=0;i<size;i++)
     {
         disttravel[i]=Double.MAX_VALUE;
         estimated[i]=Double.MAX_VALUE;
     }
     disttravel[spi]=0;
     estimated[spi]=distance(spi,epi);
     while(open.size()>0)
     {
         double min=estimated[open.get(0)];
         int mini=0;
         for(int i=0;i<open.size();i++)
         {

             if(estimated[open.get(i)]<min)
             {
                 min=estimated[open.get(i)];
                 mini=i;
             }

         }

         int current=open.get(mini);
         List<Integer> alt=new ArrayList<Integer>();
         alt=Alternates(current);
         if(opt==2)
         System.out.println("City selected:    "+s2.get(current));
         if(current==epi)
            {paths.add(findpath(prev,current));
            if(opt==2){
                    open.remove(mini);
         System.out.print("Possible cities to where to travel:    ");
          for(int i=0;i<alt.size();i++)
         System.out.print(s2.get(alt.get(i))+"  ");
         System.out.println();
         System.out.print("Cities at the end of possible paths:    ");
         for(int i=0;i<open.size();i++)
         {System.out.print(s2.get(open.get(i))+"("+estimated[open.get(i)]+")   ");
         if(i==open.size()-1)
            System.out.println();}
            }
            break;}
         open.remove(mini);
         close.add(current);

         for(int i=0;i<alt.size();i++)
         {
             if(exclude.indexOf(alt.get(i))>-1)
                continue;
             if(close.indexOf(alt.get(i))>-1)
                continue;
             double tdisttravel=disttravel[current]+distance(current,alt.get(i));
             if(open.indexOf(alt.get(i))==-1)
             {
                 open.add(alt.get(i));

             }
             else if(tdisttravel>=disttravel[alt.get(i)])
                continue;
             prev[alt.get(i)]=current;
             disttravel[alt.get(i)]=tdisttravel;
             estimated[alt.get(i)]=disttravel[alt.get(i)]+distance(alt.get(i),epi);
         }
         if(opt==2){
         System.out.print("Possible cities to where to travel:    ");
          for(int i=0;i<alt.size();i++)
         System.out.print(s2.get(alt.get(i))+"  ");
         System.out.println();
         System.out.print("Cities at the end of possible paths:    ");
         for(int i=0;i<open.size();i++)
         {System.out.print(s2.get(open.get(i))+"("+estimated[open.get(i)]+")   ");
         if(i==open.size()-1)
            System.out.println();}
}

if(opt==2)
System.out.println("--------------------------------------------------------------------------------");
     }

}
public static void main(String args[])
{
    Scanner sc=new Scanner(System.in);
    PathFinder a=new PathFinder();
a.Input();
    System.out.println("enter starting point");
sp=sc.next();
spi=s2.indexOf(sp);
System.out.println("enter ending point");
ep=sc.next();
epi=s2.indexOf(ep);
System.out.println("do you want to exclude some nodes press y or n");
ex=sc.next();
while(ex.compareTo("n")!=0)
{
    System.out.println("enter the node");
    String x=sc.next();
    if(x.compareTo(sp)==0||x.compareTo(ep)==0)
    {
        System.out.println("enter other than end nodes");
        x=sc.next();
    }
    a.exclude.add(s2.indexOf(x));
    System.out.println("do you want to exclude some nodes press y or n");
    ex=sc.next();
}
//a.exclu();
System.out.println("for final output press 1");
System.out.println("for step by step output press 2");
opt=Integer.parseInt(sc.next());
System.out.println("for straight line heuristic press 1");
System.out.println("for fewest cities heuristic press 2");
heu=Integer.parseInt(sc.next());
//System.out.println(spi);
//System.out.println(epi);
System.out.println("The paths are \n");

    List<Integer> visited= new ArrayList<Integer>();
    visited.add(spi);
    a.Astar();
    a.Output();
}
}
