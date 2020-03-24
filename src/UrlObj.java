/*
    Github: p990r
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

public class UrlObj {
    private URL url;
    private String html_code;
    private ArrayList<String> wordsList;
    private Set<String> wordsSet;

    public UrlObj(String url) throws MalformedURLException {
        this.url = new URL(url);
        this.html_code = "";
        this.wordsList = new ArrayList<>();
        this.wordsSet = new HashSet<String>();
    }

    public String getUrl(){
        return this.url.toString();
    }

    private void getHtmlCode() throws IOException {
        URLConnection con = url.openConnection();
        InputStream is = con.getInputStream();

        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String line;
        while ((line = br.readLine()) != null) {
            this.html_code += line;
        }
    }

    public static String removeScriptTags(String string) {
        String scriptStart = "<script";
        String scriptEnd = "</script>";
        int start = string.indexOf(scriptStart);
        int end = string.indexOf(scriptEnd);
        while (start != -1 && end != -1) {
            string = string.substring(0, start) + string.substring(end + 9);
            start = string.indexOf(scriptStart);
            end = string.indexOf(scriptEnd);
        }
        return string;
    }

    public static String removeTags(String string) {
        return string.replaceAll("<[^>]*>", " ");
    }

    public static String removeTabs(String string) {
        return string.replaceAll("\t", " ");
    }

    public static String textToWords(String string) {
        string = removeScriptTags(string);
        string = removeTags(string);
        string = removeTabs(string);
        return string;
    }

    public static List<String> stringToList(String string) {
        string = string.toLowerCase();
        String[] stringList = string.split("[^a-z]");
        return Arrays.asList(stringList);
    }

    public void makeWordsList() throws IOException {
        getHtmlCode();
        String text = textToWords(this.html_code);
        this.wordsList.addAll(stringToList(text));
    }

    public void makeSet(){
        this.wordsSet.addAll(this.wordsList);
    }

    public Set<String> getWordsSet(){
        return this.wordsSet;
    }
}
