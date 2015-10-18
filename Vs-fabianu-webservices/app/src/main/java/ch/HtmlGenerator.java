package ch;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Fabian_admin on 18.10.2015.
 */
public class HtmlGenerator {
    private HashMap<String, HtmlChanger> urlToHtmlChanger;

    public HtmlGenerator() {
        urlToHtmlChanger = new HashMap<>();
    }

    public String generate(String url) {
        //for a big project, create datastructures like hashmaps for url -> websites and changes
        if(urlToHtmlChanger.containsKey(url)) {
            HtmlChanger changer = urlToHtmlChanger.get(url);
            return changer.generateChangedContent();
        } else {
            return null;
        }
    }

    public void registerSite(String url, HtmlChanger changer) {
        urlToHtmlChanger.put(url, changer);
    }

    public List<String> urlList() {
        return new LinkedList<>(urlToHtmlChanger.keySet());
    }
}
