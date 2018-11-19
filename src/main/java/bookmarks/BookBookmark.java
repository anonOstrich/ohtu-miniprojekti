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
public class BookBookmark implements BookmarkInterface{
    private String author;
    private String title;
    private String ISBN;
    // tulee ehkä tehdä erillinen tag luokka
    private ArrayList<String> tags;
    private ArrayList<String> prerequisiteCourses;
    private ArrayList<String> releatedCourses;

    public BookBookmark(String author, String title, String ISBN, ArrayList<String> tags, ArrayList<String> prerequisiteCourses, ArrayList<String> releatedCourses) {
        this.author = author;
        this.title = title;
        this.ISBN = ISBN;
        this.tags = tags;
        this.prerequisiteCourses = prerequisiteCourses;
        this.releatedCourses = releatedCourses;
    }
    public String tags(){
        String str="";
        for(String s: this.tags){
            str+=s + ", ";
        }
        return str;
    }
    public String preqCourses(){
        String str="";
        for(String s: this.prerequisiteCourses){
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
        return "Kirjoittaja: "+ author +"\n Otsikko: " + title+ "\n Tyyppi: Kirja"+"\n ISBN: "+ISBN+"\n Tagit: " +tags()+"\n Esitietokurssit: "+preqCourses()+"\n Samankaltaisia kursseja: "+ releatedCourses();
    }
    
    
    
    
}
