package bookmarks;

import app.domain.Tag;
import app.io.IO;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import org.fusesource.jansi.Ansi;

/**
 * Class that is used to store bookmarks of books.
 *
 * @author jussiste
 */

@Entity
@Table(name="BookBookmark")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class BookBookmark extends Bookmark {
    private String author; 
    private String ISBN;


    public BookBookmark(){
        super(); 
    }

    public BookBookmark(String ISBN, String author, String title,  List<Tag> tags, String description) {
        super(title, tags, description);
        this.author = author; 
        this.ISBN = ISBN;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
    
        
    public String getISBN(){
        return ISBN;
    }
    public void setISBN(String ISBN){
        this.ISBN=ISBN;
    }
   
    @Override
    public String type(){
        return "Book";
    }

    @Override
    public Ansi.Color colorOfType() {
        return Ansi.Color.GREEN;
    }
    

    @Override
    public void printInfo(IO io){
        printID(io);

        printFieldName("Type", io);
        printType(io);
        io.println("");

        printFieldName("ISBN", io);
        io.print(ISBN);
        io.println("");

        printFieldName("Author", io);
        io.print(author);
        io.println("");
        super.printInfo(io);
    }

    @Override
    public void printShortInfo(IO io) {
        super.printShortInfo(io);
        io.boldPrint("Author: ");
        io.println(author);
    }

    @Override
    public String toString() {
        String result = "ID: " + id + "\n Type: " + type() + "\n ISBN: " + this.ISBN + "\n Author: " + author;
        return result + "\n" + super.toString();
    }
    
    @Override
    public String shortPrint(){
        return super.shortPrint() + " Author: " + author; 
    }


}
