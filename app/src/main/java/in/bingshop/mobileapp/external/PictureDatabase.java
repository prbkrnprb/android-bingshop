package in.bingshop.mobileapp.external;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.drawable.Drawable;
import android.util.Log;

import in.bingshop.util.Utilities;

/**
 * Created by Prabakaran on 25-01-2015.
 */
public class PictureDatabase extends SQLiteOpenHelper {

    protected static final String TABLE_NAME = "picture";
    protected static final String COLUMN_PICTURE_ID = "_id";
    protected static final String COLUMN_PICTURE = "picture";

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_CREATE = "CREATE TABLE IF NOT EXISTS "
            + TABLE_NAME + "(" + COLUMN_PICTURE_ID
            + " VARCHAR PRIMARY KEY, " + COLUMN_PICTURE
            + " TEXT NOT NULL);";

    public PictureDatabase(Context context, String database) {
        super(context, database+".db",null, DATABASE_VERSION);
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
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}

class AccessPictureDatabase extends PictureDatabase{

    public int valid;

    private SQLiteDatabase database;

    public AccessPictureDatabase(Context context, String databaseName) {
        super(context, databaseName);
        valid = 0;
    }

    public void putImage(String key, Drawable drawable){
        openDB(1);
        ContentValues values = new ContentValues();
        values.put(COLUMN_PICTURE_ID, key);
        values.put(COLUMN_PICTURE, Utilities.drawableToByteArray(drawable));
        database.insert(TABLE_NAME, null, values);
    }

    public Drawable getImage(String key){
        openDB(0);
        byte[] value = new byte[0];
        try {
            Cursor cursor = database.rawQuery("select " + COLUMN_PICTURE + " from " + TABLE_NAME + " where _id = ?", new String[]{key});

            if (cursor.getCount() > 0){
                valid = 1;
                cursor.moveToFirst();
                value = cursor.getBlob(0);
            }
            else{
                valid = 0;
            }

        }
        catch (CursorIndexOutOfBoundsException e){
            e.printStackTrace();
            valid = 0;
        }

        return Utilities.byteToDrawable(value);
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
