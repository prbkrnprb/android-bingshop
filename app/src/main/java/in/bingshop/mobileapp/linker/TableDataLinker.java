package in.bingshop.mobileapp.linker;

import in.bingshop.exception.MyObjNotFoundException;
import in.bingshop.mobileapp.connection.BaseConnection;

/**
 * Created by Prabakaran on 06-25-15.
 */
public class TableDataLinker extends BaseLinker{

    public TableDataLinker(String tableName, String tableKey) {
        super();
        request = cClient.forTableData(tableName, tableKey);
        restart();
    }

    public void restart(){
        new BaseConnection(this,request);
        postComplete(0,"");
    }

    public int getRowCount(){
        try {
            return pServer.getRowCount();
        } catch (MyObjNotFoundException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int getColumnCount(){
        try {
            return pServer.getColumnCount();
        } catch (MyObjNotFoundException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public String[][] getTableData(){
        try {
            return pServer.getTableData();
        } catch (Exception e) {
            e.printStackTrace();
            return new String[999][99];
        }
    }
}
