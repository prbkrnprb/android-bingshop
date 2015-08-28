package in.bingshop.mobileapp.linker;

import android.graphics.Bitmap;

import in.bingshop.exception.MyObjNotFoundException;
import in.bingshop.util.StaticVariables;

/**
 * Created by Prabakaran on 06-24-15.
 */
public class BaseLinker implements ServerConnInterface {

    protected ComposeClientRequest cClient;
    protected ProcessServerData pServer;
    protected String receivedString = "";
    protected Bitmap bitmap;
    protected String request = "";

    protected BaseLinker(){
        cClient = new ComposeClientRequest(StaticVariables.user);
    }

    @Override
    public synchronized void postComplete(int ind, String receivedString) {
        if(ind == 0){
            try{
                wait();
                try {
                    pServer = new ProcessServerData(this.receivedString);
                } catch (MyObjNotFoundException e) {
                    e.printStackTrace();
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        else{
            this.receivedString = receivedString;
            notify();
        }
    }

    @Override
    public synchronized void postComplete(int ind, String receivedString, Bitmap bitmap) {
        if(ind == 0){
            try {
                pServer = new ProcessServerData(this.receivedString);
            } catch (MyObjNotFoundException e) {
                e.printStackTrace();
            }
        }
        else{
            this.receivedString = receivedString;
            this.bitmap = bitmap;
            notify();
        }
    }

    public int getRC(){
        try {
            return pServer.getRC();
        } catch (MyObjNotFoundException e) {
            e.printStackTrace();
            return 1;
        }
    }
}
