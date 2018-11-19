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
public class VideoBookmark implements BookmarkInterface{
    private String title;
    private String url;
    private ArrayList<String> releatedCourses;
    private ArrayList<String> tags;
    private String comment;

    public VideoBookmark(String title, String url, ArrayList<String> releatedCourses, ArrayList<String> tags, String comment) {
        this.title = title;
        this.url = url;
        this.releatedCourses = releatedCourses;
        this.tags = tags;
        this.comment = comment;
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
        return "Otsikko: " + title+ "\n Url: "+url+ "\n Tyyppi: Video"+"\n Tagit: " +tags()+"\n Samankaltaisia kursseja: "+ releatedCourses();
    }
    
}
