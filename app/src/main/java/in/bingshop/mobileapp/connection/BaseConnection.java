package in.bingshop.mobileapp.connection;

import android.os.AsyncTask;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import in.bingshop.mobileapp.linker.ServerConnInterface;
import in.bingshop.util.Constants;
import in.bingshop.util.Utilities;

/**
 * Created by Prabakaran on 06-22-15.
 */
public class BaseConnection extends AsyncTask{
    private ServerConnInterface parent;
    String message;

    public BaseConnection(ServerConnInterface parent, String message){
        this.parent = parent;
        this.message = message;
        this.execute();
    }

    @Override
    protected Object doInBackground(Object[] params) {
        try{
        parent.postComplete(1,connect());
        } catch (Exception e) {
          e.printStackTrace();
        }
        return null;
    }

    public String connect() {
        String url;
        url = Constants.SERVER_URL;
        HttpClient httpclient = new DefaultHttpClient();

        String result = "";

        HttpPost httppost = new HttpPost(url);

        List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(2);
        nameValuePair.add(new BasicNameValuePair("clientValues", message));
        nameValuePair.add(new BasicNameValuePair("password", "123456789"));

        HttpResponse response;
        try {
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePair));
            response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                InputStream instream = entity.getContent();
                result = Utilities.convertStreamToString(instream);
                instream.close();
            }

        } catch (Exception e) {
            result = e.toString();
        }

        return result;
    }

}
