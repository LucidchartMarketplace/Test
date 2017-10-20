package studyelephant.com.studyelephant.asynctask;

import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Zhihan on 10/16/2017.
 */

public class CollegeScoreCardTask extends AsyncTask<Void, Void, String> {

    protected final String host = "https://api.data.gov/ed/collegescorecard/v1/schools";
    protected final String key = "api_key=6KVhOQ5Ay1MyNwcgTtLkTKfUUMUOpwf17ie5qz0t";
    protected String fields;
    protected String queries;

    public CollegeScoreCardTask(String fields, String queries) {
        this.fields = fields;
        this.queries = queries;
    }

    protected String readString(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        InputStreamReader sr = new InputStreamReader(is);
        char[] buf = new char[1024];
        int len;
        while ((len = sr.read(buf)) > 0) {
            sb.append(buf, 0, len);
        }
        return sb.toString();
    }

    private String toAPIString() {
        String url = host + "?" + key;
        if (!fields.equals("")) {
            url += "?fields=" + fields;
        }
        if (!queries.equals("")) {
            url += "?" + queries;
        }
        return url;
    }

    @Override
    protected String doInBackground(Void... params) {
        android.os.Debug.waitForDebugger();
        try {
            URL url = new URL(toAPIString());
            HttpURLConnection http = (HttpURLConnection) url.openConnection();

            http.setConnectTimeout(5000);
            http.setDoInput(true);
            http.addRequestProperty("Content-Type", "application/json");
            http.connect();


            if (http.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream respBody = http.getInputStream();
                String respData = readString(respBody);

                return respData;
            }
            else {
                String errorInfo = "error: " + http.getResponseMessage();
                InputStream respBody = http.getInputStream();
                String respData = readString(respBody);
                errorInfo += "\n" + respData;
                return errorInfo;
            }
        }
        catch (Exception e) {
            return "error: " + e.getMessage();
        }
    }
}
