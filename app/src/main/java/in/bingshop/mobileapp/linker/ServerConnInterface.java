package in.bingshop.mobileapp.linker;

import android.graphics.Bitmap;

/**
 * Created by Prabakaran on 31-01-2015.
 */
public interface ServerConnInterface {
    public void postComplete(int ind, String receivedString);
    public void postComplete(int ind, String receivedString, Bitmap bitmap);
}
