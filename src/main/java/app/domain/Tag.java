package app.domain;

import bookmarks.Bookmark;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import app.io.IO;
import org.fusesource.jansi.Ansi;

@Entity
public class Tag {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    

    
    @ManyToMany(mappedBy = "tags", fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    private Set<Bookmark> bookmarks;

    @Column(name = "name", unique = true)
    private String name;

    public Tag(){}
    
    public Tag(String name) {
        this.name = name;
    }

    public Set<Bookmark> getBookmarks() {
        return bookmarks;
    }

    public void setBookmarks(Set<Bookmark> bookmarks) {
        this.bookmarks = bookmarks;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    

    /**
     * Calculates and returns the Ansi Color background-color for a tag
     *
     * <p>Tags are printed with different background-colors based on hashes
     * </p>
     *
     * @param tag Tag for which the color is calculated
     */
    public static Ansi.Color getTagBgColor(String tag){
        int md = tag.hashCode()%7;
        switch (md){
            case 0: return Ansi.Color.CYAN;
            case 1: return Ansi.Color.MAGENTA;
            case 2: return Ansi.Color.YELLOW;
            case 3: return Ansi.Color.WHITE;
            case 4: return Ansi.Color.RED;
            case 5: return Ansi.Color.GREEN;
            case 6: return Ansi.Color.BLUE;
        }
        return Ansi.Color.WHITE;
    }
    
    public void print(IO io){
        io.colorPrint(toString(), Ansi.Color.BLACK, getTagBgColor(toString()));
    }
    
    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }

        Tag t = (Tag) o;

        return this.name.equals(t.getName());
    }

}
