package serverTest;

import java.io.*;
import java.net.*;
import java.util.ArrayList;


public class NetServerThread {

    public static void main(String[] args) {
        try {
            ServerSocket serv = new ServerSocket(8071);
            System.out.println("initialized");



            while (true) {
                //ожидание клиента
                Socket sock = serv.accept();
                System.out.println(sock.getInetAddress().getHostName() + " connected");

                /*создание отдельного потока для обмена данными с соединившимся клиентом*/
                ServerThread server = new ServerThread(sock);
                server.start();//запуск потока
            }
        } catch (IOException e) {
            System.err.println(e);
        }
    }
}

class ServerThread extends Thread {

    private PrintStream os;//передача
    private BufferedReader is;//чтение
    private InetAddress addr;//адрес клиента

    public ServerThread(Socket s) throws IOException {
        os = new PrintStream(s.getOutputStream());
        is = new BufferedReader(new InputStreamReader(s.getInputStream()));
        addr = s.getInetAddress();
    }

    public void run() {
        String str;
        ArrayList<String> recipe;
        try {
            FromDB db = new FromDB();
            str = is.readLine();
            System.out.println(str);
            recipe = db.GetFromDB(str);

            os.println(recipe);


            String[] recipes = recipe.toString().split(";");
            for (int i = 0; i < recipes.length; i++) {
                System.out.println("*************************************");
                System.out.println(recipes[i]);
            }

        } catch (IOException e) {
            //если клиент не отвечает, соединение с ним разрывается
            System.out.println("Disconnect");
        } finally {
            disconnect();//уничтожение потока
        }
    }

    public void disconnect() {
        try {
            System.out.println(addr.getHostName() + " disconnected");
            os.close();
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            this.interrupt();
        }
    }
}