import java.io.*;
public class Win
{
public static void main(String[] Miller) throws IOException
{
 Process process =
Runtime.getRuntime().exec("c:\\\Windows\\notepad.exe");
         process.waitFor();
}
}