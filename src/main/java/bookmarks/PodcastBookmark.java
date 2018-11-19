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
public class PodcastBookmark implements BookmarkInterface{
    private String author;
    private String title;
    // tulee ehkä tehdä erillinen tag luokka
    private String description;
    private ArrayList<String> tags;
    private ArrayList<String> releatedCourses;

    public PodcastBookmark(String author, String title, String description, ArrayList<String> tags, ArrayList<String> releatedCourses) {
        this.author = author;
        this.title = title;
        this.description = description;
        this.tags = tags;
        this.releatedCourses = releatedCourses;
    }
    
    public String tags(){
        String str="";
        for(String s: this.tags){
            str+=s + ", ";
        }
        return str;
    }
    public String releatedCourses(){
        String str="";
        for(String s: this.releatedCourses){
            str+=s + ", ";
        }
        return str;
    }
    @Override
    public String toString() {
        return "Kirjoittaja: "+ author +"\n Otsikko: " + title+ "\n Kuvaus: "+description+  "\n Tyyppi: Kirja"+"\n Tagit: " +tags()+"\n Samankaltaisia kursseja: "+ releatedCourses();
    }
    
    
    
}
