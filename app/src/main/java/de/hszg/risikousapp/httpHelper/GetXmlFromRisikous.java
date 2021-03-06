package de.hszg.risikousapp.httpHelper;



import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * Class to get the xml data from the risikous server.
 */
public class GetXmlFromRisikous extends AsyncTask<String, Void, String> {

    /**
     * Start http get as async task.
     * @param actions
     * @return xml response from server as String
     */
    @Override
    protected String doInBackground(String... actions) {
        try {
            return getXmlAsString(actions[0]);
        } catch (IOException e) {
            Log.e("http-error", "error while downloading", e);
            return "error";
        }
    }

    /**
     * Starts the get method and convert response entity into a String.
     * @param action
     * @return response entity
     * @throws IOException
     */
    private String getXmlAsString(String action) throws IOException {
        HttpResponse response = getXmlFromRisikous(action);
        HttpEntity entity = response.getEntity();

        String xmlData = "";
        xmlData = EntityUtils.toString(entity, "UTF-8");

        return xmlData;
    }

    /**
     * Get http response from risikous server.
     * @param action selects which restful service is executed
     * @return complete http response
     * @throws IOException
     */
    private HttpResponse getXmlFromRisikous(String action) throws IOException {
        String PROTOCOL = "http";
        String HOST = "94.101.38.155";
        String PATH = "/RisikousRESTful/rest/";
        String url = PROTOCOL + "://" + HOST + PATH;
        HttpParams httpParams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpParams, 3000);


        HttpClient client = new DefaultHttpClient(httpParams);
        HttpGet request = new HttpGet(url + action);
        request.addHeader("Accept", "application/xml");
        HttpResponse response = client.execute(request);
        Log.i("Response Status-Code", "Status-Code: " + response.getStatusLine().getStatusCode());
        Log.i("Response Status", "Status" + response.getStatusLine().getReasonPhrase());
        return response;
    }
}
