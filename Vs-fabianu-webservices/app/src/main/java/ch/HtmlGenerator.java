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
        String rUrl = dispatchLastSlash(url);
        if(urlToHtmlChanger.containsKey(rUrl)) {
            HtmlChanger changer = urlToHtmlChanger.get(rUrl);
            return changer.generateChangedContent();
        } else {
            return null;
        }
    }

    public void registerSite(String url, HtmlChanger changer) {
        urlToHtmlChanger.put(dispatchLastSlash(url), changer);
    }

    public List<String> urlList() {
        return new LinkedList<>(urlToHtmlChanger.keySet());
    }

    private String dispatchLastSlash(String in) {
        String s = in;
        if(s.length() > 1 && s.charAt(in.length() - 1) == '/') {
            //register without last /
            s = s.substring(0, s.length() - 2);
        }
        return s;
    }
}
