package in.bingshop.mobileapp.linker;

import in.bingshop.exception.MyObjNotFoundException;
import in.bingshop.mobileapp.connection.BaseConnection;

/**
 * Created by Prabakaran on 06-30-15.
 */
public class CheckDetailsLinker extends BaseLinker{

    public CheckDetailsLinker(String email,String phone) {
        super();
        request = cClient.forCheckDetails(email,phone);
        restart();
    }

    public void restart(){
        new BaseConnection(this,request);
        postComplete(0,"");
    }

    public Boolean getEmailExist(){
        try {
            return pServer.getEmailExist();
        } catch (MyObjNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Boolean getPhoneExist(){
        try {
            return pServer.getPhoneExist();
        } catch (MyObjNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }
}
