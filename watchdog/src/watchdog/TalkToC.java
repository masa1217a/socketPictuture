package watchdog;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

public class TalkToC
{
    public static void main(String[] argv) 
        throws Exception
    {
        Socket socket = new Socket("172.16.107.3", 10007);

        BufferedReader reader = new BufferedReader
            (new InputStreamReader(socket.getInputStream(), "US-ASCII"));
  	        OutputStream output = socket.getOutputStream();
	                
	        output.write("HELO\r\n".getBytes("US-ASCII"));
	        System.out.println(reader.readLine());
	                 
    }
}