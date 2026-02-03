import com.sun.source.tree.Scope;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {

    public Runnable getRunnable(){
        return new Runnable() {
            @Override
            public void run() {
                int port = 8010;
                try
                {
                    InetAddress address = InetAddress.getByName("localhost");
                    Socket socket = new Socket(address,port);
                    try{
                        PrintWriter toSocket = new PrintWriter(socket.getOutputStream(), true);
                        BufferedReader fromScoket = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        toSocket.println("Hello from clinet"+socket.getLocalSocketAddress());
                        String line = fromScoket.readLine();
                        System.out.println("Response from server" + line);
                    } catch (UnknownHostException e) {
                        e.printStackTrace();
                    }

                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        };

    }
    public static void main(String[] args)
    {
        Client client = new Client();
        for (int i=0; i<100;i++)
        {
            try{
                Thread thread = new Thread(client.getRunnable());
                thread.start();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
}
