// A Java program for a Server 
import java.net.*;
import java.io.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.TimeUnit;
public class Server
{
    //initialize socket and input stream
    private Socket           socket   = null;
    private ServerSocket     server   = null;
    private DataInputStream  in       =  null;
    private DataOutputStream out      =  null;
    // constructor with port 
    public Server(int port)
    {
        ServerSocket serverSocket = null;
        Socket socket = null;
        try
        {
            serverSocket = new ServerSocket(port);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        while (true)
        {
            try
            {
                socket = serverSocket.accept();
            }
            catch (IOException e)
            {
                System.out.println("I/O error: " + e);
            }
            // new thread for a client
            new ServerThread(socket).start();
        }
    }
    public static void main(String args[])
    {
        Server server = new Server(5000);
    }
} 