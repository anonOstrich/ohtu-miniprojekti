package app.io;

import java.util.Scanner;
import org.fusesource.jansi.Ansi;
import static org.fusesource.jansi.Ansi.*;
import org.fusesource.jansi.AnsiConsole;

public class ConsoleIO implements IO {

    private final Scanner scanner;

    public ConsoleIO() {
        this(new Scanner(System.in));
    }

    public ConsoleIO(Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public void colorPrint(String toPrint, Ansi.Color COLOR) {
        AnsiConsole.out.print(ansi().fg(COLOR).a(toPrint).reset());
    }

    @Override
    public void println(String toPrint) {
        System.out.println(toPrint);
    }

    @Override
    public String nextLine() {
        return scanner.nextLine();
    }

    @Override
    public int nextInt() {
        return scanner.nextInt();
    }

    @Override
    public void print(String toPrint) {
        System.out.print(toPrint);
    }


}
