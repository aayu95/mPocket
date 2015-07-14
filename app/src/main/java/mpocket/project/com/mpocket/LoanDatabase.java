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
 * Created by abhishek on 14/7/15.
 */
public class LoanDatabase extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 4;

    // Database Name
    private static final String DATABASE_NAME = "mPocket";

    //Table name
    private static final String TABLE_NAME = "loans";

    //Table columns
    private static final String KEY_ID = "id";
    private static final String KEY_AMOUNT = "amount";
    private static final String KEY_LEND_FROM= "borrowed_from";
    private static final String KEY_LEND_ON = "borrow_date";
    private static final String KEY_RETURN_BY = "returning_date";

    public LoanDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "create table " + TABLE_NAME + "(" +
                KEY_ID + " integer primary key autoincrement, " +
                KEY_AMOUNT + " real, " +
                KEY_LEND_FROM + " text, " +
                KEY_LEND_ON + " text, " +
                KEY_RETURN_BY + " text);";

        db.execSQL(query);

        Log.d("Create Table", "Creating Table");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_NAME);
        onCreate(db);
    }

    public void addNewLoan(LoanData data) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_AMOUNT, data.get_amount());
        values.put(KEY_LEND_FROM, data.get_lend_from());
        values.put(KEY_LEND_ON, data.get_lend_on());
        values.put(KEY_RETURN_BY, data.get_return_by());

        db.insert(TABLE_NAME, null, values);
    }

    public void deleteLoan(LoanData data) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, KEY_ID + " = ?",
                new String[]{String.valueOf(data.get_id())});
        db.close();
    }

    public void editDebt(LoanData data) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_AMOUNT, data.get_amount());
        values.put(KEY_LEND_FROM, data.get_lend_from());
        values.put(KEY_LEND_ON, data.get_lend_on());
        values.put(KEY_RETURN_BY, data.get_return_by());

        // updating row
        db.update(TABLE_NAME, values, KEY_ID + " = ?",
                new String[]{String.valueOf(data.get_id())});

        db.close();
    }

    public List<LoanData> showAllLoans() {
        List<LoanData> loanList = new ArrayList<LoanData>();

        String selectQuery = "SELECT  * FROM " + TABLE_NAME + " order by id desc";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                LoanData data = new LoanData();
                data.set_id(Integer.parseInt(cursor.getString(0)));
                data.set_amount(Float.parseFloat(cursor.getString(1)));
                data.set_lend_from(cursor.getString(2));
                data.set_lend_on(cursor.getString(3));
                data.set_return_by(cursor.getString(4));
                // Adding contact to list
                loanList.add(data);
            } while (cursor.moveToNext());
        }
        db.close();
        return loanList;
    }

    public boolean isTableEmpty() {
        SQLiteDatabase db = this.getReadableDatabase();
        String count = "SELECT count(*) FROM " + TABLE_NAME;
        Cursor mcursor = db.rawQuery(count, null);
        mcursor.moveToFirst();
        int icount = mcursor.getInt(0);

        if (icount > 0) {
            db.close();
            return false;
        } else {
            db.close();
            return true;
        }
    }
}
