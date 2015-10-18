package ch.ethz.inf.vs.vs_fabianu_webservices.Server;

import android.content.Context;

import ch.HtmlChanger;

/**
 * Created by Fabian_admin on 18.10.2015.
 */
public class ChangeNothing extends HtmlChanger {

    public ChangeNothing(String pathToHtml, Context context) {
        super(pathToHtml, context);
    }

    @Override
    public String generateChangedContent() {
        return htmlContent;
    }
}
