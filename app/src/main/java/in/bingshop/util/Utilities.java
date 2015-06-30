package in.bingshop.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import in.bingshop.exception.MyObjNotFoundException;

/**
 * Created by Prabakaran on 06-22-15.
 */
public class Utilities {

    public static String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    public static boolean validateMessage(String serverMessage) {
        ProcessServerData pD = null;
        try {
            pD = new ProcessServerData(serverMessage);
            if(pD.getRC() == 0){
                return true;
            }
        } catch (MyObjNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

}
