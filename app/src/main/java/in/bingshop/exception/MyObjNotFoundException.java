package in.bingshop.exception;

/**
 * Created by Prabakaran on 31-01-2015.
 */
public class MyObjNotFoundException extends Exception {

    public int code;

    public MyObjNotFoundException(){
        super();
    }

    public MyObjNotFoundException(String message){
        super(message);
        code = 0;
    }

    public MyObjNotFoundException(String message, int code){
        super(message);
        this.code = code;
    }

}
