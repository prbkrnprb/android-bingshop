package in.bingshop.mobileapp.linker;

import in.bingshop.exception.MyObjNotFoundException;
import in.bingshop.mobileapp.connection.BaseConnection;

/**
 * Created by Prabakaran on 06-30-15.
 */
public class RetrieveOrdersLinker extends BaseLinker{

    public RetrieveOrdersLinker() {
        super();
        request = cClient.forRetrieveOrders();
        restart();
    }

    public void restart(){
        new BaseConnection(this,request);
        postComplete(0,"");
    }

    public int getOrderRowCount(){
        try {
            return pServer.getOrderRowCount();
        } catch (MyObjNotFoundException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int getOrderColumnCount(){
        try {
            return pServer.getOrderColumnCount();
        } catch (MyObjNotFoundException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int getItemRowCount(){
        try {
            return pServer.getItemRowCount();
        } catch (MyObjNotFoundException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int getItemColumnCount(){
        try {
            return pServer.getItemColumnCount();
        } catch (MyObjNotFoundException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public String[][] getOrderData(){
        try {
            return pServer.getOrderData();
        } catch (Exception e) {
            e.printStackTrace();
            return new String[999][99];
        }
    }

    public String[][] getItemData(){
        try {
            return pServer.getItemData();
        } catch (Exception e) {
            e.printStackTrace();
            return new String[999][99];
        }
    }
}
