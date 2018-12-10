/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.utilities;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author jussiste
 */
public class ValidatorTest {
    @Test
    public void canDetectValidTitles(){
        assertFalse(Validator.validName(""));
        assertTrue(Validator.validName("name"));
    }
    @Test
    public void canDetectValidURLs(){
        assertFalse(Validator.validUrl("url"));
        assertTrue(Validator.validUrl("www.url.com"));
    }
    @Test
    public void canDetectValidISBN(){
        assertFalse(Validator.validISBN("isbn121"));
        assertTrue(Validator.validISBN("123-1351-12234"));
    }
}
