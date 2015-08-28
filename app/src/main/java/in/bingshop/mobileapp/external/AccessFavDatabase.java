package in.bingshop.mobileapp.external;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Prabakaran on 25-01-2015.
 */
class FavDatabase extends SQLiteOpenHelper {

    protected static final String COLUMN_ITEM_ID = "_id";
    protected static final String COLUMN_IND = "ind";

    private static final int DATABASE_VERSION = 1;

    protected String DATABASE_CREATE;

    protected String tableName;

    public FavDatabase(Context context, String database,String tableName) {
        super(context, database+".db",null, DATABASE_VERSION);

        DATABASE_CREATE = "CREATE TABLE IF NOT EXISTS "
                + tableName + "(" + COLUMN_ITEM_ID
                + " VARCHAR PRIMARY KEY, " + COLUMN_IND
                + " INTEGER NOT NULL);";
        this.tableName = tableName;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(PictureDatabase.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + tableName);
        onCreate(db);
    }
}

public class AccessFavDatabase extends FavDatabase{

    private SQLiteDatabase database;

    public AccessFavDatabase(Context context, String databaseName,String tableName) {
        super(context, databaseName, tableName);

        openDB(1);
        database.execSQL(DATABASE_CREATE);
    }

    public void putInd(String key, int ind){
        openDB(1);
        ContentValues values = new ContentValues();
        values.put(COLUMN_ITEM_ID, key);
        values.put(COLUMN_IND, ind);
        database.insert(tableName, null, values);
    }

    public void updateInd(String key, int ind){
        openDB(1);
        ContentValues values = new ContentValues();
        values.put(COLUMN_IND, ind);
        database.update(tableName, values,COLUMN_ITEM_ID + "='" + key + "'", null);
    }

    public int getInd(String key){
        openDB(0);
        int ind = 0;
        try {
            Cursor cursor = database.rawQuery("select " + COLUMN_IND + " from " + tableName + " where _id = ?", new String[]{key});

            if (cursor.getCount() > 0){
                cursor.moveToFirst();
                ind = cursor.getInt(0);
            }
            else{
                putInd(key,0);
            }

        }
        catch (CursorIndexOutOfBoundsException e){
            e.printStackTrace();
        }

        return ind;
    }

    private void openDB(int mode) {
        if (mode == 0)
            database = super.getReadableDatabase();
        else if(mode == 1)
            database = super.getWritableDatabase();
    }

    private void closeDB() {
        super.close();
    }
}
