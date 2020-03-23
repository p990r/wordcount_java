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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

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
        string = removeScriptTags(string);
        string = string.replaceAll("<[^>]*>", " ");
        return string;
    }

    public static String textToWords(String string){
        string = removeTags(string);
        string = string.replaceAll("\t", " ");
        return string;
    }

    public void makeWordsList() throws IOException {
        getHtmlCode();
        String text = textToWords(this.html_code);
        text = text.toLowerCase();
        String[] stringList = text.split("[^a-z]");
        this.wordsList.addAll(Arrays.asList(stringList));
    }

    public void makeSet(){
        this.wordsSet.addAll(this.wordsList);
    }

    public Set<String> getWordsSet(){
        return this.wordsSet;
    }
}
