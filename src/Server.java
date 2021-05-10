import java.net.*;
import java.io.*;
public class Server {
    public static void main(String[] args) {
        try{
            System.out.println("Server has been started");
            ServerSocket serverSocket = new ServerSocket(3458);
            Socket socket1 = serverSocket.accept();
            Socket socket2 = serverSocket.accept();
            new Thread(() -> {
                try{
                    BufferedReader listener = new BufferedReader(new InputStreamReader(socket1.getInputStream()));
                    PrintWriter sender = new PrintWriter(socket2.getOutputStream(), true);
                    String client1Name = listener.readLine();
                    System.out.println(client1Name + " logged in");
                    sender.println(client1Name);
                    while (true){
                        String message = listener.readLine();
                        if (message.equals("q")){
                            System.out.println(client1Name + " logged out");
                            sender.println("q");
                            break;
                        }
                        else {
                            System.out.println(client1Name + ": " + message);
                        }
                        sender.println(message);
                    }
                }catch (IOException e){
                    System.err.println(e.toString());
                }
            }).start();
            new Thread(() -> {
                try{
                    BufferedReader listener = new BufferedReader(new InputStreamReader(socket2.getInputStream()));
                    PrintWriter sender = new PrintWriter(socket1.getOutputStream(),true);
                    String client2Name = listener.readLine();
                    System.out.println(client2Name + " logged in");
                    sender.println(client2Name);
                    while (true){
                        String message = listener.readLine();
                        if (message.equals("q")){
                            System.out.println(client2Name + " logged out");
                            sender.println("q");
                            break;
                        }
                        else {
                            System.out.println(client2Name + ": " + message);
                        }
                        sender.println(message);
                    }

                }catch (IOException e){
                    System.err.println(e.toString());
                }
            }).start();
        }
        catch (IOException e){
            System.err.println("an error has occurred on the server");
        }
    }
}
