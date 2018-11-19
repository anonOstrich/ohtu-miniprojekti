
import bookmarks.BookBookmark;
import java.util.ArrayList;

public class Main {

  public static void main(String[] args){
      ArrayList<String> tags=new ArrayList<>();
      tags.add("julma");
      tags.add( "sydän");
     
      BookBookmark bm=new BookBookmark("Seppo", "Juna", "123", new ArrayList<String>() , new ArrayList<String>(), tags);
      System.out.println(bm);
    System.out.println("Hello tigers of the world!");
  }
}
