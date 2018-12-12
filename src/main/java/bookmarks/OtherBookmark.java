package bookmarks;

import app.domain.Tag;
import app.io.IO;
import java.util.List;
import javax.persistence.Entity;
import org.fusesource.jansi.Ansi;

@Entity
public class OtherBookmark extends Bookmark {
    private String url; 

    
    public OtherBookmark(){
        super(); 
    }

    public OtherBookmark(String title, String url, List<Tag> tags,  String description) {
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
        return "Other";
    }

    @Override
    public Ansi.Color colorOfType() {
        return Ansi.Color.BLUE;
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
    public String toString() {
        return "ID: " + id + "\n Type: " + type() + "\n Url: " + url
                + "\n" + super.toString(); 
    }

    public void printShortInfo(IO io) {
        super.printShortInfo(io);
        io.boldPrint("Url: ");
        io.println(url);
    }

    @Override
    public String shortPrint() {
        return super.shortPrint() + " Url: " + url; 
    }


}
