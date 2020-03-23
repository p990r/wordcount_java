/*
    Github: p990r
 */

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.MalformedURLException;

public class Tests {
    String[] urls;
    String path; // path to url text file
    String first_url;
    String bad_url;

    @Before
    public void setUp() throws Exception {
        try {
            path = "wordcount-urls.txt";
            first_url = "https://en.wikipedia.org/wiki/%C3%89cole_Polytechnique_F%C3%A9d%C3%A9rale_de_Lausanne";
            bad_url = "https://en.wikipedia.org/wiki/fkghjdswswfkghywferkgywrfkgyxbhkjxvbhk";
            String list = WordCount.fileToString(path);
            urls = list.split("\\R", 2);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testFileToString(){
        assertEquals(first_url, urls[0]);
        assertNotEquals(first_url,urls[1]);
    }

    @Test
    public void testRemoveScriptTags(){
        String text = "<script>Text in script</script>";
        String result = UrlObj.removeScriptTags(text);
        assertEquals("", result);
    }

    @Test
    public void testRemoveTags(){
        String text = "a<abc>b<efg>c<hij>d";
        String result = UrlObj.removeTags(text);
        assertEquals("a b c d", result);
    }
    @Test
    public void testTextToWords(){
        String text = "\t\t<a>";
        String result = UrlObj.textToWords(text);
        assertEquals("   ", result);
    }
}