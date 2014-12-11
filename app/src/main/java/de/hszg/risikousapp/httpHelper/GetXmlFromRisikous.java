package de.hszg.risikousapp.httpHelper;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URLEncoder;

import de.hszg.risikousapp.R;

/**
 * Created by Julian on 03.12.2014.
 */
public class GetXmlFromRisikous extends AsyncTask<String, Void, String> {

    private Context appContext;

    public GetXmlFromRisikous(Context context){
        appContext = context;
    }

    @Override
    protected String doInBackground(String... actions) {
        try {
            return getQuestionnaireXml(actions[0]);
        } catch (IOException e) {
            return appContext.getResources().getString(R.string.connection_error);
        }
    }

    private HttpResponse getXmlFromRisikous(String action) throws IOException {
        String PROTOCOL = "http";
        String HOST = "94.101.38.155";
        String PATH = "/RisikousRESTful/rest/";
        String url = PROTOCOL + "://" + HOST + PATH;

        HttpClient client = new DefaultHttpClient();
        HttpGet request = new HttpGet(url + URLEncoder.encode(action, "UTF-8"));
        request.addHeader("Accept", "application/xml");
        HttpResponse response = client.execute(request);
        Log.i("Response Status-Code", "Status-Code: " + response.getStatusLine().getStatusCode());

        return response;
    }

    private String getQuestionnaireXml(String action) throws IOException {
        HttpResponse response = getXmlFromRisikous(action);
        HttpEntity entity = response.getEntity();

        String xmlData = EntityUtils.toString(entity);

        return xmlData;
    }
}
