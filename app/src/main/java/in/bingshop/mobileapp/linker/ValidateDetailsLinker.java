package in.bingshop.mobileapp.linker;

import in.bingshop.exception.MyObjNotFoundException;
import in.bingshop.mobileapp.connection.BaseConnection;

/**
 * Created by Prabakaran on 06-30-15.
 */
public class ValidateDetailsLinker extends BaseLinker{

    public ValidateDetailsLinker(String email,String phone) {
        super();
        request = cClient.forValidateDetails(email,phone);
        restart();
    }

    public void restart(){
        new BaseConnection(this,request);
        postComplete(0,"");
    }

    public String getEmailOTP(){
        try {
            return pServer.getEmailOTP();
        } catch (MyObjNotFoundException e) {
            e.printStackTrace();
            return "0";
        }
    }

    public String getPhoneOTP(){
        try {
            return pServer.getPhoneOTP();
        } catch (MyObjNotFoundException e) {
            e.printStackTrace();
            return "0";
        }
    }
}
