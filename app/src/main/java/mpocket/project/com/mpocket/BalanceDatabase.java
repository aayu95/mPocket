package mpocket.project.com.mpocket;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

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

    public void addBalance(BalanceData data) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_AMOUNT, data.get_amount());


        db.insert(TABLE_NAME, null, values);
        Log.d("Error", "Added Balance");
        db.close();
    }



    public List<BalanceData> showAll() {
        List<BalanceData> contactList = new ArrayList<BalanceData>();

        String selectQuery = "SELECT  * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                BalanceData contact = new BalanceData();
                contact.set_id(Integer.parseInt(cursor.getString(0)));
                contact.set_amount(Float.parseFloat(cursor.getString(1)));
                // Adding contact to list
                contactList.add(contact);
            } while (cursor.moveToNext());
        }

        return contactList;
    }

    public void resetData() {
        BalanceData data = returnOldAmount();
        data.set_amount(00);
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_AMOUNT, data.get_amount());
        db.update(TABLE_NAME, values, KEY_ID + " = ?",
                new String[]{String.valueOf(data.get_id())});

        db.close();
    }

    public BalanceData returnOldAmount() {

        String selectQuery = "SELECT  * FROM " + TABLE_NAME + " where id = 1";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        cursor.moveToFirst();
                BalanceData contact = new BalanceData();
                contact.set_id(Integer.parseInt(cursor.getString(0)));
                contact.set_amount(Float.parseFloat(cursor.getString(1)));


        return contact;
    }

    public void addAmount(float amount) {
        BalanceData data = returnOldAmount();
        amount = data.get_amount() + amount;
        data.set_amount(amount);
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_AMOUNT, data.get_amount());
        db.update(TABLE_NAME, values, KEY_ID + " = ?",
                new String[]{String.valueOf(data.get_id())});

        db.close();
    }

    public void subtractAmount(float amount) {
        BalanceData data = returnOldAmount();
        amount = data.get_amount() - amount;
        data.set_amount(amount);
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_AMOUNT, data.get_amount());
        db.update(TABLE_NAME, values, KEY_ID + " = ?",
                new String[]{String.valueOf(data.get_id())});

        db.close();
    }

    public void deleteLoan(LoanData data) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, KEY_ID + " = ?",
                new String[]{String.valueOf(data.get_id())});
        db.close();
    }

    public int ifTableIsEmpty() {
        SQLiteDatabase db = this.getReadableDatabase();
        String count = "SELECT count(*) FROM " + TABLE_NAME;
        Cursor mcursor = db.rawQuery(count, null);
        mcursor.moveToFirst();
        int icount = mcursor.getInt(0);

        if (icount > 0) {
            db.close();
            return icount;
        } else {
            db.close();
            return icount;
        }
    }

}
