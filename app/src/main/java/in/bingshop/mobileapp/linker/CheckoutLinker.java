package in.bingshop.mobileapp.linker;

import java.util.ArrayList;

import in.bingshop.exception.MyObjNotFoundException;
import in.bingshop.mobileapp.connection.BaseConnection;

/**
 * Created by Prabakaran on 06-30-15.
 */
public class CheckoutLinker extends BaseLinker{

    public CheckoutLinker(ArrayList<String> itemSizeKey, ArrayList <Integer> quantity) {
        super();
        request = cClient.forCheckoutProcess(itemSizeKey,quantity);
        restart();
    }

    public void restart(){
        new BaseConnection(this,request);
        postComplete(0,"");
    }

    public String getOrderID(){
        try {
            return pServer.getOrderID();
        } catch (MyObjNotFoundException e) {
            e.printStackTrace();
            return "0";
        }
    }
}
