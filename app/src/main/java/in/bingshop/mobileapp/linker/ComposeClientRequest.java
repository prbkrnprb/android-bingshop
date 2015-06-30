package in.bingshop.mobileapp.linker;

/**
 * Created by Prabakaran on 02-02-2015.
 */

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;


public class ComposeClientRequest {

    JSONObject jHeader, jMetaData, jData;

    private String user;
    private final String APP_START = "appstart" ;
    private final String LOGIN = "login";
    private final String TABLE_DATA = "gettable";
    private final String TABLE_SEARCH = "search" ;
    private final String CHECKOUT_PROCESS = "checkoutprocess";
    private final String VALIDATE_EMAIL_PHONE = "validateemailphone";
    private final String CHECK_EMAIL_PHONE = "checkemailphone";
    private final String NEW_CUSTOMER = "newcustomer";
    private final String RETRIEVE_ORDERS = "retrieveorders";
    private final String PICTURE_REQUEST = "getpicture";


    public ComposeClientRequest(){
    }

    public ComposeClientRequest(String user){
        this.user = user;
    }

    private String setCartData(ArrayList<String> itemSizeKey, ArrayList <Integer> quantity){
        JSONArray jTableData = new JSONArray();
        JSONObject jTableColumn;
        int rowCount = itemSizeKey.size();
        for(int i = 0; i < rowCount; i++){
            jTableColumn = new JSONObject();
            jTableColumn.put("Key",itemSizeKey.get(i));
            jTableColumn.put("Quantity",quantity.get(i));
            jTableData.add(jTableColumn.toString());
        }
        return jTableData.toString();
    }

    public String forAppStart(String ip, String clientType){
        JSONObject jComplete = new JSONObject();

        jHeader = new JSONObject();
        jHeader.put("ip", ip);
        jHeader.put("clienttype",clientType);
        jComplete.put("header", jHeader.toString());

        jMetaData = new JSONObject();
        jMetaData.put("request", APP_START);
        jComplete.put("metadata", jMetaData.toString());

        return jComplete.toString();
    }

    public String forLogin(String user, String pwd,String socialId){
        JSONObject jComplete = new JSONObject();

        jMetaData = new JSONObject();
        jMetaData.put("request", LOGIN);
        jMetaData.put("user", user);
        jMetaData.put("pwd", pwd);
        jMetaData.put("socialid", socialId);
        jComplete.put("metadata", jMetaData.toString());

        return jComplete.toString();
    }

    public String forTableData(String tableName, String tableKey){
        JSONObject jComplete = new JSONObject();

        jMetaData = new JSONObject();
        jMetaData.put("request", TABLE_DATA);
        jMetaData.put("user", user);
        jComplete.put("metadata", jMetaData.toString());

        jData = new JSONObject();
        jData.put("tablename", tableName);
        jData.put("tablekey", tableKey);
        jComplete.put("data", jData.toString());

        return jComplete.toString();
    }

    public String forTableSearch(String tableName, String searchKey){
        JSONObject jComplete = new JSONObject();

        jMetaData = new JSONObject();
        jMetaData.put("request", TABLE_SEARCH);
        jMetaData.put("user", user);
        jComplete.put("metadata", jMetaData.toString());

        jData = new JSONObject();
        jData.put("tablename", tableName);
        jData.put("searchkey", searchKey);
        jComplete.put("data", jData.toString());

        return jComplete.toString();
    }

    public String forPictureRequest(String tableKey) {
        JSONObject jComplete = new JSONObject();

        jMetaData = new JSONObject();
        jMetaData.put("request", PICTURE_REQUEST);
        jMetaData.put("user", user);
        jComplete.put("metadata", jMetaData.toString());

        jData = new JSONObject();
        jData.put("tablekey", tableKey);
        jComplete.put("data", jData.toString());

        return jComplete.toString();
    }

    public String forCheckoutProcess(ArrayList<String> itemSizeKey, ArrayList <Integer> quantity) {
        String cartData = setCartData(itemSizeKey, quantity);
        JSONObject jComplete = new JSONObject();

        jMetaData = new JSONObject();
        jMetaData.put("request", CHECKOUT_PROCESS);
        jMetaData.put("user", user);
        jComplete.put("metadata", jMetaData.toString());

        jData = new JSONObject();
        jData.put("cartdata", cartData);
        jComplete.put("data", jData.toString());

        return jComplete.toString();
    }

    public String forValidateDetails(String email, String phone) {
        JSONObject jComplete = new JSONObject();

        jMetaData = new JSONObject();
        jMetaData.put("request", VALIDATE_EMAIL_PHONE);
        jMetaData.put("user", user);
        jComplete.put("metadata", jMetaData.toString());

        jData = new JSONObject();
        jData.put("email", email);
        jData.put("phonenumber", phone);
        jComplete.put("data", jData.toString());

        return jComplete.toString();
    }

    public String forCheckDetails(String email, String phone) {
        JSONObject jComplete = new JSONObject();

        jMetaData = new JSONObject();
        jMetaData.put("request", CHECK_EMAIL_PHONE);
        jMetaData.put("user", user);
        jComplete.put("metadata", jMetaData.toString());

        jData = new JSONObject();
        jData.put("email", email);
        jData.put("phonenumber", phone);
        jComplete.put("data", jData.toString());

        return jComplete.toString();
    }

    public String forNewCustomer(String customerInfo) {
        JSONObject jComplete = new JSONObject();

        jMetaData = new JSONObject();
        jMetaData.put("request", NEW_CUSTOMER);
        jMetaData.put("user", user);
        jComplete.put("metadata", jMetaData.toString());

        jData = new JSONObject();
        jData.put("customerinfo", customerInfo);
        jComplete.put("data", jData.toString());

        return jComplete.toString();
    }

    public String forRetrieveOrders(){
        JSONObject jComplete = new JSONObject();

        jMetaData = new JSONObject();
        jMetaData.put("request", RETRIEVE_ORDERS);
        jMetaData.put("user", user);
        jComplete.put("metadata", jMetaData.toString());

        return jComplete.toString();
    }
}

