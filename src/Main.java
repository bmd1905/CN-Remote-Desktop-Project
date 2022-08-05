import java.lang.Object;
import java.io.Reader;
import java.io.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Main {

    // Question
    // 1------------------------------------------------------------------------------------------------
    static void quest1() throws IOException {
        // Print all process
        try {
            String line;
            Process p = Runtime.getRuntime().exec(System.getenv("windir") + "\\system32\\" + "tasklist.exe");
            BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
            while ((line = input.readLine()) != null) {
                System.out.println(line); // <-- Parse data here.
            }
            input.close();
        } catch (Exception err) {
            err.printStackTrace();
        }

        // Delete Process
        Scanner myObj = new Scanner(System.in);
        System.out.print("Enter our pip: ");
        String pid = myObj.nextLine();

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

        // Start a process
        myObj = new Scanner(System.in);

        // System name:
        /*
         * File Explorer - explorer
         * Calculator - calc
         * Notepad - notepad
         * Character Map - charmap
         * Paint - mspaint
         * Command Prompt (new window) - cmd
         * Windows Media Player - wmplayer
         * Task Manager - taskmgr
         */
        System.out.print("Enter app you want to start: ");
        String appName = myObj.nextLine();

        String commandLineStart = String.format("start %s", appName);

        builder = new ProcessBuilder(
                "cmd.exe", "/c", commandLineStart);
        builder.redirectErrorStream(true);
        p = builder.start();
        r = new BufferedReader(new InputStreamReader(p.getInputStream()));
    }

    // Question
    // 2------------------------------------------------------------------------------------------------
    static void quest2() throws IOException {
        // Print App in running
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

            System.out.println(line);
        }

        // Delete App
        Scanner myObj = new Scanner(System.in);
        System.out.print("Enter Id you want to delete: ");
        String pid = myObj.nextLine();

        commandLine = String.format("taskkill /F /PID %s", pid);

        builder = new ProcessBuilder(
                "cmd.exe", "/c", commandLine);
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

        // Start a process
        myObj = new Scanner(System.in);

        // System name:
        /*
         * File Explorer - explorer
         * Calculator - calc
         * Notepad - notepad
         * Character Map - charmap
         * Paint - mspaint
         * Command Prompt (new window) - cmd
         * Windows Media Player - wmplayer
         * Task Manager - taskmgr
         */
        System.out.print("Enter app you want to start: ");
        String appName = myObj.nextLine();

        String commandLineStart = String.format("start %s", appName);

        builder = new ProcessBuilder(
                "cmd.exe", "/c", commandLineStart);
        builder.redirectErrorStream(true);
        p = builder.start();
        r = new BufferedReader(new InputStreamReader(p.getInputStream()));
    }

    // Question
    // 3------------------------------------------------------------------------------------------------
    static void quest3() throws IOException {
        Scanner myObj = new Scanner(System.in);
        System.out.print("Enter your image name (*.png): ");
        String imageName = myObj.nextLine();

        String commandLine = String.format("cd tool\\ && nircmd.exe savescreenshot %s.png && cd ..", imageName);

        ProcessBuilder builder = new ProcessBuilder(
                "cmd.exe", "/c", commandLine);
        builder.redirectErrorStream(true);
        Process p = builder.start();
    }

    // Question
    // 4------------------------------------------------------------------------------------------------
    static void quest4() {
        Scanner scanner = new Scanner(System.in);
        String readString = scanner.nextLine();
        while (readString != null) {
            System.out.println(readString);

            if (readString.isEmpty()) {
                System.out.println("Read Enter Key.");
            }

            if (scanner.hasNextLine()) {
                readString = scanner.nextLine();
            } else {
                readString = null;
            }
        }
    }

    // Question
    // 5------------------------------------------------------------------------------------------------
    static void quest5() throws IOException {
        String commandLine = "shutdown /s";

        ProcessBuilder builder = new ProcessBuilder(
                "cmd.exe", "/c", commandLine);
        builder.redirectErrorStream(true);
        Process p = builder.start();
    }

    public static void main(String[] args) throws Exception {

        quest1();

    }

    public static void quest1(DataInputStream in, DataOutputStream out) {
    }
}