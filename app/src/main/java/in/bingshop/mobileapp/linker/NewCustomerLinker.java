package in.bingshop.mobileapp.linker;

import in.bingshop.exception.MyObjNotFoundException;
import in.bingshop.mobileapp.connection.BaseConnection;

/**
 * Created by Prabakaran on 06-30-15.
 */
public class NewCustomerLinker extends BaseLinker{

    public NewCustomerLinker(String customerInfo) {
        super();
        request = cClient.forNewCustomer(customerInfo);
        restart();
    }

    public void restart(){
        new BaseConnection(this,request);
        postComplete(0,"");
    }

    public String getCustomerId(){
        if (getRC() == 0){
            try {
                return pServer.getCustomerId();
            } catch (MyObjNotFoundException e) {
                e.printStackTrace();
                return "0";
            }
        }
        return "0";
    }
}
