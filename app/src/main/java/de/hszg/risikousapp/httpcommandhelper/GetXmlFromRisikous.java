package de.hszg.risikousapp.httpcommandhelper;


import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

import de.hszg.risikousapp.R;

/**
 * Created by Julian on 03.12.2014.
 */
public class GetXmlFromRisikous extends AsyncTask<String, Void, String> {

    @Override
    protected String doInBackground(String... actions) {
        try {
            return getXmlAsString(actions[0]);
        } catch (IOException e) {
            return "Fehler beim Daten Download";
        }
    }

    private HttpResponse getXmlFromRisikous(String action) throws IOException {
        String PROTOCOL = "http";
        String HOST = "94.101.38.155";
        String PATH = "/RisikousRESTful/rest/";
        String url = PROTOCOL + "://" + HOST + PATH;

        HttpClient client = new DefaultHttpClient();
        HttpGet request = new HttpGet(url + action);
        request.addHeader("Accept", "application/xml");
        HttpResponse response = client.execute(request);
        Log.i("Response Status-Code", "Status-Code: " + response.getStatusLine().getStatusCode());
        Log.i("Response Status", "Status" + response.getStatusLine().getReasonPhrase());
        return response;
    }

    private String getXmlAsString(String action) throws IOException {
        HttpResponse response = getXmlFromRisikous(action);
        HttpEntity entity = response.getEntity();

        String xmlData = EntityUtils.toString(entity, "UTF-8");

        return xmlData;
    }

}
