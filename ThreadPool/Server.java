import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    public final ExecutorService threadPool;
    public Server (int poolSize)
    {
        this.threadPool = Executors.newFixedThreadPool(poolSize);
    }

    public void handleClient(Socket clientSocket)
    {
        try {
            PrintWriter toSocket = new PrintWriter(clientSocket.getOutputStream(), true);
            toSocket.println("Hlle from server "+ clientSocket.getInetAddress());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args)
    {
        int port=8010;
        int poolSize =10;
        Server server = new Server(poolSize);

        try{
            ServerSocket serverSocket = new ServerSocket(port);
            serverSocket.setSoTimeout(10000);
            System.out.println("SERVER IS LISTENING ON PORT "+ port);

            while(true)
            {
                Socket cleintSocket = serverSocket.accept();
                server.threadPool.execute(()-> server.handleClient(cleintSocket));
            }

        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        } finally{
            server.threadPool.shutdown();
        }

    }
}