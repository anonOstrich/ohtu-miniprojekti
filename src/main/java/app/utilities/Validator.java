package app.utilities;


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
        return url.matches("^(http://)?www\\.[a-z0-9-]+\\.[a-z]{2,6}(\\/[a-z0-9.-_\\?!\\+=&]*)?$");
        
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
