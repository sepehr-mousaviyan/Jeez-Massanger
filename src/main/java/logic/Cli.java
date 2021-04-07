package logic;

import java.util.Scanner;

public class Cli {
    Scanner scanner;

    Cli() { scanner = new Scanner(System.in); }

    public String inputString() {
        return scanner.nextLine();
    }

    public void print(String content) {
        System.out.println(content);
    }

}
