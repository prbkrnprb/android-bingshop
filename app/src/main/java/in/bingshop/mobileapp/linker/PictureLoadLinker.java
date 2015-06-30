package in.bingshop.mobileapp.linker;

import android.graphics.Bitmap;

import in.bingshop.mobileapp.connection.BaseConnection;

/**
 * Created by Prabakaran on 06-30-15.
 */
public class PictureLoadLinker extends BaseLinker{

    public PictureLoadLinker(String key) {
        super();
        request = cClient.forPictureRequest(key);
        restart();
    }

    public void restart(){
        new BaseConnection(this,request);
        postComplete(0,"");
    }

    public Bitmap getBitmap(){
        return bitmap;
    }

}
