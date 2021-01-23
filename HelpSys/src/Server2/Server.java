package Server2;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Server {
    private static ServerSocket server;

    public static void main(String[] args) throws IOException {
         start();
         handle();
         end();
    }

    private static void handle(){
      while(true){
          try {
              Socket client = server.accept();
              new ClientHandler(client);
          } catch (SocketException ex) {
              return;
          } catch (IOException e){
              e.printStackTrace();
          }
          try{
              Thread.sleep(10);
          } catch (InterruptedException e) {
              e.printStackTrace();
          }
      }
    }

    private static void start() throws IOException {
       server = new ServerSocket(8000);


    }

    private static void end() throws IOException {
       server.close();
    }
}
