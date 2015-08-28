package in.bingshop.mobileapp.external;

import android.content.Context;
import android.graphics.drawable.Drawable;

import in.bingshop.bingshop.R;

/**
 * Created by Prabakaran on 12-01-2015.
 */
public class GetImage {
    Context context;
    AccessPictureDatabase pDB;
    public GetImage(Context context){
        this.context = context;
        pDB = new AccessPictureDatabase(context,"picture");
        //ExecutorService executorService = Executors.newFixedThreadPool(1);
        //executorService.submit(new Thread(this, "DownloadImage"));
    }/*

    @Override
    public void run() {
        String key = BingUtil.imageViewSQueue.poll();
        ImageView imageView = BingUtil.imageViewQueue.poll();
        try {
            Drawable temp = pDB.getImage(key);
            if (pDB.valid != 0)
                imageView.setImageDrawable(temp);
            else {
                MyPictureLoadLinker linker = new MyPictureLoadLinker(key);
                linker.start();
                imageView.setImageBitmap(linker.bitmap);
                pDB.putImage(linker.key, new BitmapDrawable(activity.getResources(),linker.bitmap));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

    public void downloadImage(String key){
        try {
            String temp[] = {"B100000", "B100000x", "B100001", "B100001x", "CT100000", "CT100000x", "CT100001", "CT100001x", "I100000", "I100000x", "S1100000", "S1100000x", "S1100001", "S1100001x", "S2100000", "S2100000x", "S2100001", "S2100001x"};
            String temp1[] = {"http://i59.tinypic.com/72eumx.jpg", "http://i57.tinypic.com/28i124o.jpg", "http://i60.tinypic.com/dcaebp.jpg","http://i59.tinypic.com/2m4uflx.jpg","http://i58.tinypic.com/33x92kn.jpg","http://i57.tinypic.com/2r38prt.jpg","http://i58.tinypic.com/sqtawg.jpg","http://i60.tinypic.com/1jr9qh.jpg","http://i57.tinypic.com/4udkiq.jpg","http://i61.tinypic.com/xo07yx.jpg","http://i58.tinypic.com/28su3jb.jpg","http://i60.tinypic.com/6jhi89.jpg","http://i60.tinypic.com/11hvzah.jpg","http://i57.tinypic.com/96cp6g.jpg","http://i58.tinypic.com/16ktd3b.jpg","http://i58.tinypic.com/zv7iir.jpg","http://i61.tinypic.com/246te7a.jpg","http://i58.tinypic.com/2u8fk02.jpg"};
            int i;
            String url="http://i59.tinypic.com/72eumx.jpg";
            for (i = 1; i<18; i++){
                if (temp[i].equals(key)){
                    url = temp1[i];
                }
            }

            //PicServerConn tempP = new PicServerConn(url);
            //pDB.putImage(key, new BitmapDrawable(context.getResources(),tempP.bm));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
/* commented due to servlet migration
    public void downloadImage(String key){
        try {
            pDB.getImage(key);
            if (pDB.valid == 0) {
                MyPictureLoadLinker linker = new MyPictureLoadLinker(key);
                linker.start();
                if(linker.receivedString == "success")
                    pDB.putImage(linker.key, new BitmapDrawable(activity.getResources(),linker.bitmap));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
*/
    public Drawable get(String key){
        try {
            Drawable d = pDB.getImage(key);
            if (pDB.valid == 1) {
                return d;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return context.getResources().getDrawable(R.drawable.bingshop);
    }
}
/*
class DownloadImage implements Runnable{
    ImageView imageView;
    String key;
    AccessPictureDatabase pDB;
    Context activity;
    public DownloadImage(String key,ImageView imageView,AccessPictureDatabase pDB,Context activity){
        this.imageView = imageView;
        this.key = key;
        this.pDB = pDB;
        this.activity = activity;
        ExecutorService executorService = Executors.newFixedThreadPool(1);

        executorService.submit(new Thread(this, "DownloadImage"));
    }
    @Override
    public void run() {
        try {
            Drawable temp = pDB.getImage(key);
            if (pDB.valid != 0)
                BingUtil.imageViewQueue.poll().setImageDrawable(temp);
            else {
                MyPictureLoadLinker linker = new MyPictureLoadLinker(key);
                linker.start();
                BingUtil.imageViewQueue.poll().setImageBitmap(linker.bitmap);
                pDB.putImage(linker.key, new BitmapDrawable(activity.getResources(),linker.bitmap));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}*/

