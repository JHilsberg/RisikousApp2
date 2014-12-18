package de.hszg.risikousapp.httpcommandhelper;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.net.URLEncoder;

import de.hszg.risikousapp.R;

/**
 * Created by Julian on 11.12.2014.
 */
public class PostXmlToRisikousServer extends AsyncTask<String, Void, String> {

    private Context appContext;

    public PostXmlToRisikousServer(Context context){
        appContext = context;
    }

    @Override
    protected String doInBackground(String... actions) {
        try {
            return sendPost(actions[0], actions[1]);
        } catch (IOException e) {
            Log.e("Post-Error", "Fehler beimn senden der Daten");
        }
        return "";
    }

    private HttpResponse postXmlToRisikous(String action, String payload) throws IOException {
        String PROTOCOL = "http";
        String HOST = "94.101.38.155";
        String PATH = "/RisikousRESTful/rest/";
        String url = PROTOCOL + "://" + HOST + PATH;

        StringEntity stringEntity = new StringEntity(payload);
        stringEntity.setContentEncoding("UTF-8");
        stringEntity.setContentType("application/xml");

        HttpClient client = new DefaultHttpClient();
        HttpPost request = new HttpPost(url + action);
        request.setEntity(stringEntity);
        HttpResponse response = client.execute(request);

        return response;
    }

    private String sendPost(String action, String payload) throws IOException {
        HttpResponse response = postXmlToRisikous(action, payload);
        String statusCode = String.valueOf(response.getStatusLine().getStatusCode());

        return statusCode;
    }
}

