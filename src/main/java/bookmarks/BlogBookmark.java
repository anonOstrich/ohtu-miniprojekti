package bookmarks;

import app.domain.Tag;
import app.io.IO;
import java.util.List;
import javax.persistence.*;
import org.fusesource.jansi.Ansi;

/**
 * Class that is used to store blogbookmarks
 *
 * @author jussiste
 */
@Entity
@Table(name = "BlogBookmark")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)

public class BlogBookmark extends Bookmark {

    private String url;

    public BlogBookmark() {
        super();
    }

    public BlogBookmark(String title, String url, List<Tag> tags,
            String description) {

        super(title, tags, description);
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
    
    @Override
    public void setUrl(String url) {
        this.url = url;
    }


    @Override
    public String type(){
        return "Blogpost";
    }

    @Override
    public Ansi.Color colorOfType() {
        return Ansi.Color.YELLOW;
    }


    @Override
    public void printInfo(IO io){
        printID(io);

        printFieldName("Type", io);
        printType(io);
        io.println("");

        printFieldName("Url", io);
        io.print(url);
        io.println("");

        super.printInfo(io);
    }

    @Override
    public void printShortInfo(IO io) {
        super.printShortInfo(io);
        io.boldPrint("Url: ");
        io.println(url);
    }

    @Override
    public String toString() {
        return "ID: " + id + "\n Type: "+type()+"\n Url: " + url + "\n" + super.toString();
    }
    
    @Override
    public String shortPrint(){
        return super.shortPrint() + " Url: " + url; 
    }
}
