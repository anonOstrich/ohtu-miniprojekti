package app.io;

import java.util.ArrayList;
import java.util.List;
import org.fusesource.jansi.Ansi;

/**
 * A simple implementation of IO for testing purposes
 * 
 * <p>Since there is no easy way to test for color output, the color-related
 * methods are implemented in the same way as colorless ones.</p>
 * 
 */
public class StubIO implements IO {
    private List<String> lines;
    private int i;
    private ArrayList<String> prints;

    public StubIO(List<String> lines) {
        this.lines = lines;
        i=0;
        prints = new ArrayList<>();
    }

    public StubIO(String input) {
        lines = new ArrayList<>();
        String[] ilines = input.split("\n");
        for (int i = 0; i < ilines.length; i++) {
            lines.add(ilines[i]);
        }
        prints = new ArrayList<>();
    }

    @Override
    public void println(String toPrint) {
        // System.out.println(">"+toPrint);
        prints.add(toPrint);
    }

    public ArrayList<String> getPrints() {
        return prints;
    }

    @Override
    public String nextLine() {
        if (i < lines.size()) {
            // System.out.println("<"+lines.get(i));
            return lines.get(i++);
        }
        // System.out.println("<");
        return "";
    }

    @Override
    public void colorPrint(String toPrint, Ansi.Color color) {
        print(toPrint);
    }

    @Override
    public void colorPrint(String toPrint, Ansi.Color color, Ansi.Color bgcolor) {
        print(toPrint);
    }

    @Override
    public void boldPrint(String toPrint) {
        print(toPrint);
    }

    @Override
    public void boldColorPrint(String toPrint, Ansi.Color color) {
        print(toPrint);
    }

    @Override
    public void boldColorPrint(String toPrint, Ansi.Color color, Ansi.Color bgcolor) {
        print(toPrint);
    }

    @Override
    public void print(String toPrint) {
        println(toPrint);
    }

}
