package in.bingshop.mobileapp.linker;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import in.bingshop.exception.MyObjNotFoundException;

public class ProcessServerData {

    JSONObject serverDataJObj;
    JSONObject header,metaData,data;

    public ProcessServerData(String serverData)throws MyObjNotFoundException{
        Object obj= JSONValue.parse(serverData);
        serverDataJObj = (JSONObject) obj;
        processHeader();                                                                                // mandatory data. Exception will thrown if not found
        processMetaData();
        processData();
    }

    public void setServerData(String serverData) throws MyObjNotFoundException{
        Object obj= JSONValue.parse(serverData);
        serverDataJObj = (JSONObject) obj;
        processHeader();                                                                                // mandatory data. Exception will thrown if not found
        processMetaData();
        processData();
    }

    private void processHeader()throws MyObjNotFoundException{
        try {
            header = (JSONObject) JSONValue.parse(serverDataJObj.get("header").toString());
        }catch (RuntimeException e){
            e.printStackTrace();
            throw new MyObjNotFoundException("Header not found",1);
        }
    }

    private void processMetaData(){
        try {
            metaData = (JSONObject) JSONValue.parse(serverDataJObj.get("metadata").toString());
        }catch (RuntimeException e){
            e.printStackTrace();
        }
    }

    private void processData(){
        try {
            data = (JSONObject) JSONValue.parse(serverDataJObj.get("data").toString());
        }catch (RuntimeException e){
            e.printStackTrace();
        }
    }

    public int getRC() throws MyObjNotFoundException {
        String rc;
        try {
            rc = header.get("rc").toString();
        }catch (RuntimeException e){
            e.printStackTrace();
            throw new MyObjNotFoundException("RC not found in header",101);
        }
        return Integer.parseInt(rc);
    }

    public String getMessage() throws MyObjNotFoundException{
        String message;
        try {
            message = header.get("message").toString();
        }catch (RuntimeException e){
            e.printStackTrace();
            throw new MyObjNotFoundException("Message not found in header",102);
        }
        return message;
    }

    public int getIsMoreData() throws MyObjNotFoundException{
        String message;
        try {
            message = metaData.get("more").toString();
        }catch (RuntimeException e){
            e.printStackTrace();
            throw new MyObjNotFoundException("Any more not found in meta data",201);
        }
        return Integer.parseInt(message);
    }

    public String getMoreType() throws MyObjNotFoundException{
        String message;
        try {
            message = metaData.get("moretype").toString();
        }catch (RuntimeException e){
            e.printStackTrace();
            throw new MyObjNotFoundException("Any more type not found in meta data",202);
        }
        return message;
    }

    public int getRowCount() throws MyObjNotFoundException{
        String message;
        try {
            message = metaData.get("row").toString();
        }catch (RuntimeException e){
            e.printStackTrace();
            throw new MyObjNotFoundException("Row count not found in meta data",203);
        }
        return Integer.parseInt(message);
    }

    public int getColumnCount() throws MyObjNotFoundException{
        String message;
        try {
            message = metaData.get("column").toString();
        }catch (RuntimeException e){
            e.printStackTrace();
            throw new MyObjNotFoundException("Column count not found in meta data",204);
        }
        return Integer.parseInt(message);
    }

    public int getSearchCount() throws MyObjNotFoundException{
        String message;
        try {
            message = metaData.get("search").toString();
        }catch (RuntimeException e){
            e.printStackTrace();
            throw new MyObjNotFoundException("Search count not found in meta data",205);
        }
        return Integer.parseInt(message);
    }

    public int getSearchAllCount() throws MyObjNotFoundException{
        String message;
        try {
            message = metaData.get("searchall").toString();
        }catch (RuntimeException e){
            e.printStackTrace();
            throw new MyObjNotFoundException("Search All count not found in meta data",206);
        }
        return Integer.parseInt(message);
    }

    public int getOrderRowCount() throws MyObjNotFoundException{
        String message;
        try {
            message = metaData.get("orderrow").toString();
        }catch (RuntimeException e){
            e.printStackTrace();
            throw new MyObjNotFoundException("Order row count not found in meta data",207);
        }
        return Integer.parseInt(message);
    }

    public int getOrderColumnCount() throws MyObjNotFoundException{
        String message;
        try {
            message = metaData.get("ordercolumn").toString();
        }catch (RuntimeException e){
            e.printStackTrace();
            throw new MyObjNotFoundException("Order column count not found in meta data",208);
        }
        return Integer.parseInt(message);
    }

    public int getItemRowCount() throws MyObjNotFoundException{
        String message;
        try {
            message = metaData.get("itemrow").toString();
        }catch (RuntimeException e){
            e.printStackTrace();
            throw new MyObjNotFoundException("Item row count not found in meta data",209);
        }
        return Integer.parseInt(message);
    }

    public int getItemColumnCount() throws MyObjNotFoundException{
        String message;
        try {
            message = metaData.get("itemcolumn").toString();
        }catch (RuntimeException e){
            e.printStackTrace();
            throw new MyObjNotFoundException("Item column count not found in meta data",210);
        }
        return Integer.parseInt(message);
    }

    public String[][] getTableData() throws MyObjNotFoundException,Exception{
        String message[][] = new String[999][99];
        try {
            JSONArray dataArray = (JSONArray) JSONValue.parse(data.get("tabledata").toString());
            JSONObject columnData;
            for (int i = 0; i < getRowCount(); i++){
                columnData = (JSONObject) JSONValue.parse(dataArray.get(i).toString());
                for (int j = 0; j < getColumnCount(); j++){
                    message[i][j] = (String) columnData.get("Col " + j);
                }
            }

        }catch (RuntimeException e){
            e.printStackTrace();
            throw new MyObjNotFoundException("Table data not found in data",301);
        }catch (Exception e){
            e.printStackTrace();
            throw new Exception();
        }
        return message;
    }

    public int getLoginRC() throws MyObjNotFoundException{
        String message;
        try {
            message = data.get("loginrc").toString();
        }catch (RuntimeException e){
            e.printStackTrace();
            throw new MyObjNotFoundException("Login RC not found in data",302);
        }
        return Integer.parseInt(message);
    }

   public String getLoginMessage() throws MyObjNotFoundException{
        String message;
        try {
            message = data.get("loginmessage").toString();
        }catch (RuntimeException e){
            e.printStackTrace();
            throw new MyObjNotFoundException("Login Message not found in data",303);
        }
        return message;
    }

    public String getCustomerID() throws MyObjNotFoundException{
        String message;
        try {
            message = data.get("customerid").toString();
        }catch (RuntimeException e){
            e.printStackTrace();
            throw new MyObjNotFoundException("Customer ID not found in data",304);
        }
        return message;
    }

    public String getOrderID() throws MyObjNotFoundException{
        String message;
        try {
            message = data.get("orderid").toString();
        }catch (RuntimeException e){
            e.printStackTrace();
            throw new MyObjNotFoundException("Order ID not found in data",305);
        }
        return message;
    }

    public String getEmailOTP() throws MyObjNotFoundException{
        String message;
        try {
            message = data.get("emailotp").toString();
        }catch (RuntimeException e){
            e.printStackTrace();
            throw new MyObjNotFoundException("Email OTP not found in data",306);
        }
        return message;
    }

    public String getPhoneOTP() throws MyObjNotFoundException{
        String message;
        try {
            message = data.get("phoneotp").toString();
        }catch (RuntimeException e){
            e.printStackTrace();
            throw new MyObjNotFoundException("Phone OTP not found in data",307);
        }
        return message;
    }

    public String getCustomerId() throws MyObjNotFoundException{
        String message;
        try {
            message = data.get("customerid").toString();
        }catch (RuntimeException e){
            e.printStackTrace();
            throw new MyObjNotFoundException("Customer Id not found in data",308);
        }
        return message;
    }

    public String[] getTableSearchAll() throws MyObjNotFoundException,Exception{
        String message[] = new String[999];
        try {
            JSONArray dataArray = (JSONArray) JSONValue.parse(data.get("searchalldata").toString());
            for (int i = 0; i < getSearchAllCount(); i++){
                message[i] = dataArray.get(i).toString();
            }

        }catch (RuntimeException e){
            e.printStackTrace();
            throw new MyObjNotFoundException("Search All data not found in data",309);
        }catch (Exception e){
            e.printStackTrace();
            throw new Exception();
        }
        return message;
    }

    public String[] getTableSearch() throws MyObjNotFoundException,Exception{
        String message[] = new String[999];
        try {
            JSONArray dataArray = (JSONArray) JSONValue.parse(data.get("searchdata").toString());
            for (int i = 0; i < getSearchCount(); i++){
                message[i] = dataArray.get(i).toString();
            }
        }catch (RuntimeException e){
            e.printStackTrace();
            throw new MyObjNotFoundException("Search data not found in data",310);
        }catch (Exception e){
            e.printStackTrace();
            throw new Exception();
        }
        return message;
    }

    public String[][] getOrderData() throws MyObjNotFoundException,Exception{
        String message[][] = new String[999][99];
        try {
            JSONArray dataArray = (JSONArray) JSONValue.parse(data.get("orderdata").toString());
            JSONObject columnData;
            for (int i = 0; i < getOrderRowCount(); i++){
                columnData = (JSONObject) JSONValue.parse(dataArray.get(i).toString());
                for (int j = 0; j < getOrderColumnCount(); j++){
                    message[i][j] = (String) columnData.get("Col " + j);
                }
            }

        }catch (RuntimeException e){
            e.printStackTrace();
            throw new MyObjNotFoundException("Order data not found in data",311);
        }catch (Exception e){
            e.printStackTrace();
            throw new Exception();
        }
        return message;
    }

    public String[][] getItemData() throws MyObjNotFoundException,Exception{
        String message[][] = new String[999][99];
        try {
            JSONArray dataArray = (JSONArray) JSONValue.parse(data.get("itemdata").toString());
            JSONObject columnData;
            for (int i = 0; i < getItemRowCount(); i++){
                columnData = (JSONObject) JSONValue.parse(dataArray.get(i).toString());
                for (int j = 0; j < getItemColumnCount(); j++){
                    message[i][j] = (String) columnData.get("Col " + j);
                }
            }

        }catch (RuntimeException e){
            e.printStackTrace();
            throw new MyObjNotFoundException("Item data not found in data",312);
        }catch (Exception e){
            e.printStackTrace();
            throw new Exception();
        }
        return message;
    }

    public Boolean getEmailExist() throws MyObjNotFoundException{
        Boolean message = false;
        try {
            if(Integer.parseInt(data.get("emailexist").toString()) == 1)
                message = true;
        }catch (RuntimeException e){
            e.printStackTrace();
            throw new MyObjNotFoundException("Email Exist not found in data",313);
        }
        return message;
    }

    public Boolean getPhoneExist() throws MyObjNotFoundException{
        Boolean message = false;
        try {
            if(Integer.parseInt(data.get("phoneexist").toString()) == 1)
                message = true;
        }catch (RuntimeException e){
            e.printStackTrace();
            throw new MyObjNotFoundException("Phone Exist not found in data",314);
        }
        return message;
    }
}
