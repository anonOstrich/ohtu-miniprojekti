package app.io;

import org.fusesource.jansi.Ansi;

public interface IO {

    void println(String toPrint);
    
    void print(String toPrint);

    String nextLine();

    int nextInt();
    
    void colorPrint(String toPrint, Ansi.Color color);

    default public void redPrint(String toPrint) {
        colorPrint(toPrint, Ansi.Color.RED);
    }

    default public void cyanPrint(String toPrint) {
        colorPrint(toPrint, Ansi.Color.CYAN);
    }

    default public void yellowPrint(String toPrint) {
        colorPrint(toPrint, Ansi.Color.YELLOW);
    }
    
    default public void magentaPrint(String toPrint){
        colorPrint(toPrint, Ansi.Color.MAGENTA);
    }

}
