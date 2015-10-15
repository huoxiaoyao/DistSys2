package ch.ethz.inf.vs.vs_fabianu_webservices;

import android.util.Log;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Caroline on 10/14/15.
 */
public class HtmlParser {

    double temp = 0;

    public double parseResponse(String response) {

        final String regExp = "<span class=\"getterValue\">(\\S+)</span>";

        if (response != null) {
            Pattern pattern = Pattern.compile(regExp);
            Matcher matcher = pattern.matcher(response);
            if (matcher.find()) {

                String findings = matcher.group(1);
                temp = Double.parseDouble(findings);
            }

        }
        return temp;

    }

}
