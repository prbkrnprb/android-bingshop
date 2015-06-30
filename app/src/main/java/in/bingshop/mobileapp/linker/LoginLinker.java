package in.bingshop.mobileapp.linker;

import in.bingshop.exception.MyObjNotFoundException;
import in.bingshop.mobileapp.ServerConnInterface;
import in.bingshop.mobileapp.connection.BaseConnection;
import in.bingshop.util.StaticVariables;

/**
 * Created by Prabakaran on 06-25-15.
 */
public class LoginLinker extends BaseLinker implements ServerConnInterface {

    public LoginLinker(String user, String pwd) {
        super();
        request = cClient.forLogin(user, pwd,"");
        restart();
    }

    public LoginLinker(String user, String pwd,String socialId) {
        super();
        request = cClient.forLogin(user, pwd,socialId);
        restart();
    }

    public void restart(){
        new BaseConnection(this,request);
        postComplete(0,"");
    }

    public int getLoginRC(){
        try {
            return pServer.getLoginRC();
        } catch (MyObjNotFoundException e) {
            e.printStackTrace();
            return 9;
        }
    }

    public String getCustomerID(){
        try {
            StaticVariables.user = pServer.getCustomerID();
            return pServer.getCustomerID();
        } catch (MyObjNotFoundException e) {
            e.printStackTrace();
            return "Error : " + receivedString;
        }
    }

    public String getLoginMessage() {
        try {
            return pServer.getLoginMessage();
        } catch (MyObjNotFoundException e) {
            e.printStackTrace();
            return "Error : " + receivedString;
        }
    }
}
