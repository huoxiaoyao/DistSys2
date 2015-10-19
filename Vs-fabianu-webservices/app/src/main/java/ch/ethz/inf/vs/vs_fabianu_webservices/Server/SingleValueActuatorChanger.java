package ch.ethz.inf.vs.vs_fabianu_webservices.Server;

import android.content.Context;

import ch.HtmlChanger;

/**
 * Created by Fabian_admin on 19.10.2015.
 */
public class SingleValueActuatorChanger extends HtmlChanger{
    String name;
    String label;
    String url;
    public SingleValueActuatorChanger(String name, String buttonLabel, String url, String pathToHtml, Context context) {
        super(pathToHtml, context);
        this.name = name;
        label = buttonLabel;
        this.url = url;
    }

    @Override
    public String generateChangedContent() {
        return String.format(htmlContent, name, name, url, label);
    }
}
