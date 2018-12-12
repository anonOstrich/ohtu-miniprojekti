package app.io;

import org.fusesource.jansi.Ansi;

public interface IO {

    String nextLine();

    void println(String toPrint);
    
    void print(String toPrint);
    
    void colorPrint(String toPrint, Ansi.Color color);

    void colorPrint(String toPrint, Ansi.Color color, Ansi.Color bgcolor);

    void boldPrint(String toPrint);

    void boldColorPrint(String toPrint, Ansi.Color color);

    void boldColorPrint(String toPrint, Ansi.Color color, Ansi.Color bgcolor);


    default public void redPrint(String toPrint) {
        colorPrint(toPrint, Ansi.Color.RED);
    }

    default public void boldRedPrint(String toPrint) {
        boldColorPrint(toPrint, Ansi.Color.RED);
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
