package de.hszg.risikousapp.httpcommandhelper;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
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
            return sendPost(actions[0]);
        } catch (IOException e) {
            return appContext.getResources().getString(R.string.connection_error);
        }
    }

    private HttpResponse postXmlToRisikous(String action) throws IOException {
        String PROTOCOL = "http";
        String HOST = "94.101.38.155";
        String PATH = "/RisikousRESTful/rest/";
        String url = PROTOCOL + "://" + HOST + PATH;

        HttpClient client = new DefaultHttpClient();
        HttpPost request = new HttpPost(url + URLEncoder.encode(action, "UTF-8"));
        request.addHeader("Accept", "application/xml");
        HttpResponse response = client.execute(request);
        Log.i("Response Status-Code", "Status-Code: " + response.getStatusLine().getStatusCode());

        return response;
    }

    private String sendPost(String action) throws IOException {
        HttpResponse response = postXmlToRisikous(action);
        String statusCode = "" + response.getStatusLine().getStatusCode();

        return statusCode;
    }

    private void test(){

    }
}

