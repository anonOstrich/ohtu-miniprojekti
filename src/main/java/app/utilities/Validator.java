/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.utilities;

import java.net.MalformedURLException;
import java.net.URL;

/**
 *
 * @author jussiste
 */
public class Validator {

    /**
     * Check if the given name is valid. Only empty strings are counted as invalid.
     * @param name
     * @return
     */
    public static boolean validName(String name){
        return !name.isEmpty();
    }
    
    /**
     * Check if the given url is valid. The url must start with "www." or "http://" and must end either with domain name or filename. 
     * @param url
     * @return
     */
    public static boolean validUrl(String url) {
        if(!url.matches("^(http://)?www\\.[a-z0-9]+\\.[a-z]{2,6}(\\/[a-z0-9]*)?$")){
            return false;
        }
        return true;
        
    }

    /**
     * Check if the given ISBN is valid. The ISBN must contain only numbers and lines
     * @param ISBN
     * @return
     */
    public static boolean validISBN(String ISBN){
        ISBN=ISBN.replaceAll("-", "");
        try{
            Long.parseLong(ISBN);
        }catch(NumberFormatException e){
            return false;
        }
        return true;
    }
}
