package ch;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Fabian_admin on 18.10.2015.
 */
public abstract class HtmlChanger {
    protected String htmlContent = "";

    public HtmlChanger(String pathToHtml, Context context) {
        AssetManager am = context.getAssets();
        InputStream stream = null;

        try {
            stream = am.open(pathToHtml);
        } catch (IOException e) {
            Log.e("FileError", "HTML-File not found in Assetmanager: " + pathToHtml);
        }

        if(stream != null) {
            BufferedReader buf = new BufferedReader(new InputStreamReader(stream));
            String lastLine = null;
            try {
                while ((lastLine = buf.readLine()) != null) {
                    htmlContent += lastLine;
                }
            } catch (IOException e) {
                Log.w("FileError", "file closed while reading");
            }
        }
    }

    public abstract String generateChangedContent();
}
