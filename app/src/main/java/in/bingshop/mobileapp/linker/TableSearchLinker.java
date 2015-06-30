package in.bingshop.mobileapp.linker;

import in.bingshop.exception.MyObjNotFoundException;
import in.bingshop.mobileapp.connection.BaseConnection;

/**
 * Created by Prabakaran on 06-30-15.
 */
public class TableSearchLinker  extends BaseLinker {

    public TableSearchLinker(String tableName, String searchKey) {
        super();
        request = cClient.forTableSearch(tableName, searchKey);
        restart();
    }

    public void restart(){
        new BaseConnection(this,request);
        postComplete(0,"");
    }

    public int getSearchCount(){
        try {
            return pServer.getSearchCount();
        } catch (MyObjNotFoundException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int getSearchAllCount(){
        try {
            return pServer.getSearchAllCount();
        } catch (MyObjNotFoundException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public String[] getTableSearchData(){
        try {
            return pServer.getTableSearch();
        } catch (Exception e) {
            e.printStackTrace();
            return new String[999];
        }
    }

    public String[] getTableSearchAllData(){
        try {
            return pServer.getTableSearchAll();
        } catch (Exception e) {
            e.printStackTrace();
            return new String[999];
        }
    }
}
