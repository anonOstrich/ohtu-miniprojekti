/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bookmarks;

import java.util.ArrayList;

/**
 *
 * @author jussiste
 */
public class BlogBookmark implements BookmarkInterface{
    private String author;
    private String title;
    private String url;
    private ArrayList<String> releatedCourses;
    private ArrayList<String> tags;

    public BlogBookmark(String author, String title, String url, ArrayList<String> tags, ArrayList<String> releatedCourses) {
        this.author=author;
        this.title = title;
        this.tags=tags;
        this.url = url;
        this.releatedCourses = releatedCourses;
    }
    public String releatedCourses(){
        String str="";
        for(String s: this.releatedCourses){
            str+=s + ", ";
        }
        return str;
    }
    public String tags(){
        String str="";
        for(String s: this.tags){
            str+=s + ", ";
        }
        return str;
    }
    @Override
    public String toString() {
        return "Tekijä: "+ author +"\n Otsikko: " + title+ "\n Url: "+url+ "\n Tyyppi: Blogpost"+"\n Tagit: " +tags()+"\n Samankaltaisia kursseja: "+ releatedCourses();
    }
    

}
