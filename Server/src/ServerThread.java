import java.net.*;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ServerThread extends Thread
{
    static String DatePattern = "dd-M-yyyy HH:mm:ss";
    protected Socket socket;
    public ServerThread(Socket clientSocket)
    {
        this.socket = clientSocket;
    }
    public Date ParseStringToDate(String line, String pattern) throws ParseException
    {
        SimpleDateFormat dateformat = new SimpleDateFormat(pattern);
        Date newdate = dateformat.parse(line);
        return newdate;
    }

    public void run()
    {
        System.out.println("New client accepted ID:"+this.getId());
        DataInputStream in     =  null;
        BufferedReader brin    =  null;
        DataOutputStream out   =  null;
        try
        {
            in = new DataInputStream(
                    new DataInputStream(socket.getInputStream()));
            out = new DataOutputStream(
                    new DataOutputStream(socket.getOutputStream()));
            String linedate,linetxt;
            linetxt = in.readUTF();
            linedate = in.readUTF();
            Date date = ParseStringToDate(linedate, DatePattern);
            System.out.println(linetxt);
            System.out.println("Started waiting ID: "+this.getId());
            Date now = new Date(System.currentTimeMillis());
            while(now.before(date))
            {
                now = new Date(System.currentTimeMillis());
            }
            out.writeUTF(linetxt);
            socket.close();
            return;
        }
         catch (IOException | ParseException e)
        {
            System.out.println(e.toString() + e.getStackTrace());
            return;
        }
    }
}