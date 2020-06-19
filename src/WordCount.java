/*
    Github: p990r
 */

import java.io.*;
import java.net.MalformedURLException;
import java.util.*;

public class WordCount {


    public static String fileToString(String path) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(path));
        String everything = "";
        try {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            everything = sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            br.close();
        }

        return everything;
    }

    public static Set urlToSet(String url) throws IOException {
        Set wordsSet = new HashSet();
        try {
            UrlObj urlObj = new UrlObj(url);
            urlObj.makeWordsList();
            urlObj.makeSet();
            wordsSet = urlObj.getWordsSet();
        } catch (MalformedURLException e) {
            // URL unreachable
            e.printStackTrace();
        }
        return wordsSet;
    }

    public static Set makeUnionSet(List<String> urls) throws IOException {
        Set unionSet = new HashSet();
        urls.parallelStream().forEach(url->{
            try {
                Set temp = urlToSet(url);

                // Synchronized block
                synchronized (unionSet) {
                    unionSet.addAll(temp);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        return unionSet;
    }

    public static void main(String[] args) throws IOException {
        long startTime = System.currentTimeMillis();

        String path = "wordcount-urls.txt"; // path to url text file
        String list = fileToString(path);
        String[] urls = list.split("\\R");
        List<String> urlsList = Arrays.asList(urls);
        Set unionSet = makeUnionSet(urlsList);
        System.out.println("Number of tokens: " + unionSet.size());

        long duration = (new Date()).getTime() - startTime;
        double totalTime = (double)duration / 1000; // milliseconds to seconds conversion
        System.out.println("Total time to finish task: " + totalTime + " seconds");
    }
}
