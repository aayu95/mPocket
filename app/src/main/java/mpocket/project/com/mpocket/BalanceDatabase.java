package mpocket.project.com.mpocket;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by abhishek on 15/7/15.
 */
public class BalanceDatabase extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 4;

    // Database Name
    private static final String DATABASE_NAME = "mPocket";

    //Table name
    private static final String TABLE_NAME = "amount";

    //Table columns
    private static final String KEY_ID = "id";
    private static final String KEY_AMOUNT = "balance";
    private static final String[] COLUMNS = { KEY_ID, KEY_AMOUNT };

    public BalanceDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "create table " + TABLE_NAME + "(" +
                KEY_ID + " integer primary key autoincrement, " +
                KEY_AMOUNT + " real);";

        db.execSQL(query);
        Log.d("Create Table", "Creating Table");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_NAME);
        onCreate(db);
    }

    public void add(float amount) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_AMOUNT, amount);


        db.insert(TABLE_NAME, null, values);
        db.close();
    }


}
