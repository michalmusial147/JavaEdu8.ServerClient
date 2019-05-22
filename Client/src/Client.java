// A Java program for a Client
import java.net.*;
import java.io.*;
import java.text.*;
import java.util.*;
public class Client
{
    static String DatePattern = "dd-M-yyyy HH:mm:ss";
    private Socket socket            = null;
    private DataInputStream  terminalinput   = null;
    private DataOutputStream socketout = null;
    private DataInputStream socketin = null;
    public Client(String address, int port)
    {
        // establish a connection
        try
        {
            socket = new Socket(address, port);
            System.out.println("Connected");
            terminalinput  = new DataInputStream(System.in);
            socketout = new DataOutputStream(socket.getOutputStream());
            socketin = new DataInputStream(socket.getInputStream());
        }
        catch(UnknownHostException u)
        {
            System.out.println(u);
        }
        catch(IOException i)
        {
            System.out.println(i);
        }
        String line = "";
        Date date = new Date();
        // send data and wait for return from server
        try
        {
            System.out.println("Please give text of notification : ");
            line = terminalinput.readLine();
            socketout.writeUTF(line);
            System.out.println("Please give time \"dd-M-yyyy HH:mm:ss\": ");
            line = terminalinput.readLine();
            Date newdate = parse(line, DatePattern);
            socketout.writeUTF(line);
            System.out.println(socketin.readUTF());
            return;
        }
        catch(IOException | ParseException i)
        {
            System.out.println(i);
        }
        //close streams
        try
        {
            terminalinput.close();
            socketout.close();
            socket.close();
        }
        catch(IOException i)
        {
            System.out.println(i);
        }
    }
    public Date parse(String line,String pattern) throws ParseException
    {
        SimpleDateFormat dateformat = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        Date newdate = dateformat.parse(line);
        return newdate;
    }
    public static void main(String args[])
    {
        Client client = new Client("192.168.56.1", 5000);
    }
}