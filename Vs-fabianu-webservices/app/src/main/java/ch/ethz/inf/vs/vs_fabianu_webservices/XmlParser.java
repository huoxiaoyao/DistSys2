package ch.ethz.inf.vs.vs_fabianu_webservices;

import android.util.Log;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import skeleton.sensor.ResponseParser;

/**
 * Created by Fabian_admin on 19.10.2015.
 */
public class XmlParser implements ResponseParser {

    //We are interested in the string between the temperature-tags
    private static final String XML_CODE = "<temperature>(\\S+)</temperature>";

    @Override
    public double parseResponse(String response) {

        if (response != null) {
            Pattern pattern = Pattern.compile(XML_CODE);
            Matcher matcher = pattern.matcher(response);
            if (matcher.find()) {

                String res = matcher.group(1);
                return Double.parseDouble(res);
            }
        }

        return Log.d("debug", "Response is not in the expected format!");
    }
}
