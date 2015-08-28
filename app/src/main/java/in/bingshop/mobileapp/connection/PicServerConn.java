package in.bingshop.mobileapp.connection;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import in.bingshop.mobileapp.linker.ServerConnInterface;

/**
 * Created by Prabakaran on 08-28-15.
 */
class PicServerConn implements Runnable{
    private ServerConnInterface parent;
    private Socket s;
    OutputStream outputStream;
    PrintWriter pwrite;
    String message;
    BingUtil bingUtil;
    public Bitmap bm;

    public PicServerConn(ServerConnInterface parent,String message){
        this.parent = parent;
        this.message = message;
        bingUtil = new BingUtil();
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        executorService.submit(new Thread(this,"Picture Server Connection"));
    }

    public PicServerConn(String message){
        this.message = message;
        bingUtil = new BingUtil();
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        executorService.submit(new Thread(this,"Picture Server Connection"));
    }

    private void createSocket(){
        try{
            s = new Socket(BingUtil.connPicIP, BingUtil.connPicPort);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {

            String temp[] = new String[20];
            String temp1[] = new String [20];



            URL url = new URL(message);
            Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            bm = bmp;
            parent.postComplete(1, "success", bmp);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}