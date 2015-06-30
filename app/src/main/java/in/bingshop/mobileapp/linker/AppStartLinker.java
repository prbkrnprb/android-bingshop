package in.bingshop.mobileapp.linker;

import in.bingshop.mobileapp.connection.BaseConnection;

/**
 * Created by Prabakaran on 06-25-15.
 */
public class AppStartLinker extends BaseLinker {

    public AppStartLinker(String ip, String clientType) {
        super();
        request = cClient.forAppStart(ip, clientType);
        restart();
    }

    public void restart(){
        new BaseConnection(this,request);
        postComplete(0,"");
    }
}
