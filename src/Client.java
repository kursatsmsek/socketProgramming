import java.io.*;
import java.net.*;
public class Client {
    public static void main(String[] args) {
        try{
            Socket socket2 = new Socket("localhost",3458);
            BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
            PrintWriter sender = new PrintWriter(socket2.getOutputStream(), true);
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket2.getInputStream()));
            System.out.println("Welcome");
            System.out.print("Name: ");
            String client2Name = input.readLine();
            sender.println(client2Name);
            System.out.println("Server connection has been accomplishment");
            String otherPerson = reader.readLine();
            Thread messageInput = new Thread(() -> {
                try{
                    while (true){
                        String message = input.readLine();
                        if (message.equals("q")){
                            sender.println(message);
                            System.exit(0);
                        }
                        else {
                            sender.println(message);
                        }
                    }
                }catch (IOException e){
                    System.err.println(e.toString());
                }
            });
            Thread getMessage= new Thread(() -> {
                try {
                    while (true){
                        String message = reader.readLine();
                        if (message.equals("q")){
                            System.out.println(otherPerson + " logged out.");
                            break;
                        }
                        else if(messageInput.isAlive()){
                            System.out.println(otherPerson + ": " + message);
                        }
                    }
                }catch (IOException e){
                    System.err.println(e.toString());
                }
            });
            getMessage.start();
            messageInput.start();
        }
        catch (IOException e){
            System.err.println("an error has occurred on the client side");
        }
    }
}
