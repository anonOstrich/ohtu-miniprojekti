package bookmarks;

import app.domain.Tag;
import app.io.IO;
import app.utilities.Utilities;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.UpdateTimestamp;

import org.fusesource.jansi.Ansi;
//import java.sql.Timestamp;

/**
 * An abstract class that is used to create bookmarks, each kind of bookmark is
 * created by a separate class that inherits this class.
 *
 * @author jussiste
 */
@Entity
public abstract class Bookmark {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "title")
    String title;

    String description;

    // columnDefinintion is the only way (that I found) to set default values
    // in database. This allows for inserting data outside the Java program, 
    // and not have the fields be null!
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @CreationTimestamp
    Date created;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @UpdateTimestamp
    Date updated;

    // toistaiseksi eager, sillä jos Session ei auki ja esim kutsutaan bookmarkin
    // toString => virhe
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "bookmark_tags",
            joinColumns = {
                @JoinColumn(name = "bookmark_id")},
            inverseJoinColumns = {
                @JoinColumn(name = "tag_id")})
    @Fetch(value = FetchMode.SUBSELECT)
    List<Tag> tags;

    // Hibernate requires a constructor with no parameters
    public Bookmark() {
        tags = new ArrayList();
    }

    public Bookmark(String title, List<Tag> tags, String description) {
        this.title = title;
        this.tags = tags;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public Date getCreated() {
        return created;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setEdited(Date edited) {
        this.updated = edited;
    }

    public Long getId() {
        return id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public String tagsStr() {
        return Utilities.formStringSeparatedByCommas(tags);
    }

    public void setUrl(String url) {
    }

    @Override
    public String toString() {
        String result
                = " Title: " + title + "\n"
                + " Tags: " + tagsStr() + "\n"
                + " Description: " + description + "\n"
                + " Created: " + created + "\n"
                + " Last edited: " + updated;
        return result;
    }

    public String type() {
        return "<BUGI>";
    }

    public Ansi.Color colorOfType() {
        return Ansi.Color.WHITE;
    }
    
    public void printType(IO io) {
        io.boldColorPrint(type(), colorOfType());
    }


    /**
     * Prints tags
     *
     * <p>Tags are printed with different background-colors based on hashes
     * </p>
     *
     * @param io IO object used for printing
     */
    private void printTags(IO io){
        for (Tag tag : tags){
            tag.print(io);
            io.print(" ");
        }
    }

    protected void printID(IO io){
        io.boldPrint("ID: ");
        io.boldRedPrint(id.toString());
        io.println("");
    }
    /**
     * Prints all the name of a field
     *
     * <p></p>
     *
     * @param fieldName Name of the field
     * @param io IO object used for printing
     */
    protected void printFieldName(String fieldName, IO io){
        fieldName=" "+fieldName+":";
        while (fieldName.length() < 15){
            fieldName+=" ";
        }
        io.boldPrint(fieldName);
    }

    /**
     * Prints all the fields of the bookmark
     *
     * <p>
     * If the IO class is a stub for testing, the toString return value is
     * printed as such. Otherwise the value of the title field is printed in
     * cyan, and everything else in the default color</p>
     *
     * @param io IO object used for printing
     */
    public void printInfo(IO io){
        printFieldName("Title", io);
        io.cyanPrint(title);
        io.println("");

        printFieldName("Tags", io);
        printTags(io);
        io.println("");

        printFieldName("Desctiption", io);
        io.print(description);
        io.println("");

        printFieldName("Created", io);
        io.print(created.toString());
        io.println("");

        printFieldName("Last edited", io);
        io.print(updated.toString());
        io.println("");
    }

    /**
     * Prints the essential fields and their values of this bookmark
     *
     * <p>
     * Prints the summary of the bookmark. Everything will be printed with the
     * basic print method of the IO object.</p>
     *
     * @param io IO object used for printing
     */
    public void printShortInfo(IO io) {
        io.boldPrint("ID: ");
        io.boldRedPrint(id.toString());
        io.print("  ");
        io.boldPrint("Type: ");
        printType(io);
        io.print("  ");
        io.boldPrint("Title: ");
        io.cyanPrint(title+"  ");
    }

    public String shortPrint() {
        return "ID: " + this.id + "  Type: " + type() + "  Title: " + this.title;
    }

    /**
     * Changes the value of a field if such a field exists
     *
     * <p>
     * Bookmark does not have author, so a normal setter for author cannot be
     * called on Bookmark instances. However, this method can be called, and the
     * value will be changed if there is a method setAuthor.</p>
     *
     * <p>
     * If the actual class of this is not Book, the method does nothing. Of
     * course this can be used in similar fashion with urls etc.</p>
     *
     * @param attributeName Name of the class variable to be changed
     * @param newValue New value for the class variable
     */
    public void updateAttribute(String attributeName, String newValue) {

        try {
            String modifiedName = Character.toUpperCase(attributeName.charAt(0))
                    + attributeName.substring(1);
            Method setter = this.getClass().getDeclaredMethod("set" + modifiedName, "".getClass());
            setter.invoke(this, newValue);
        } catch (Exception e) {
        }
    }
;

}
