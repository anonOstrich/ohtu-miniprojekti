package app.io;

import java.util.ArrayList;
import java.util.List;
import org.fusesource.jansi.Ansi;

public class StubIO implements IO {

    private List<String> lines;
    private int i;
    private int j;
    private ArrayList<String> prints;

    public StubIO(List<String> lines) {
        this.lines = lines;
        prints = new ArrayList<>();
    }

    public StubIO(String input) {
        lines = new ArrayList<>();
        String[] ilines = input.split("\n");
        for (int i = 0; i < ilines.length; i++) {
            lines.add(ilines[i]);
        }
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

    // is the nextInt() actually used somewhere?
    @Override
    public int nextInt() {
        String line = nextLine();
        int no;
        try {
            no = Integer.parseInt(line);
        } catch (NumberFormatException e) {
            // System.out.println("<0");
            // is it good to return 0 in case of an exception 
            // or would it be better to throw an exception like ConsoleIO would do?
            return 0;
        }
        // System.out.println("<"+no);
        return no;
    }

    @Override
    public void colorPrint(String toPrint, Ansi.Color color) {
        // if we don't test colors, there is no need to print them differently 
        print(toPrint);
    }

    @Override
    public void print(String toPrint) {
        String[] inputLines = toPrint.split("\n", -1);
        
        if (!inputLines[0].isEmpty()) {
            int size = lines.size();
            if (size == 0) {
                lines.add(toPrint);
            } else {
                String latestLine = lines.get(size - 1);
                lines.remove(size - 1);
                lines.add(size - 1, latestLine + toPrint);
            }
        }
        
        for(int idx = 1; idx < inputLines.length; idx++){
            lines.add(inputLines[idx]);
        }
    }

}
