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
 * Created by abhishek on 12/7/15.
 */
public class PersonalExpensesDatabase extends SQLiteOpenHelper {


    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "mPocket";

    //Table name
    private static final String TABLE_NAME = "personal_expenses";

    //Table columns
    private static final String KEY_ID = "id";
    private static final String KEY_AMOUNT = "amount";
    private static final String KEY_PURPOSE = "purpose";
    private static final String KEY_DATE = "date";


    public PersonalExpensesDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "create table " + TABLE_NAME + "(" +
                KEY_ID + " integer primary key autoincrement, " +
                KEY_AMOUNT + " real, " +
                KEY_PURPOSE + " text, " +
                KEY_DATE + " text);";

        db.execSQL(query);

        Log.d("Create Table", "Creating Table");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_NAME);
        onCreate(db);
        db.close();
    }

    public void addNewExpense(PersonalExpensesData newExpense) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_AMOUNT, newExpense.get_amount());
        values.put(KEY_PURPOSE, newExpense.get_purpose());
        values.put(KEY_DATE, newExpense.get_date());

        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public void deleteContact(PersonalExpensesData contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, KEY_ID + " = ?",
                new String[]{String.valueOf(contact.get_id())});
        db.close();
    }

    public void editContact(PersonalExpensesData contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_AMOUNT, contact.get_amount());
        values.put(KEY_PURPOSE, contact.get_purpose());
        values.put(KEY_DATE, contact.get_date());

        // updating row
        db.update(TABLE_NAME, values, KEY_ID + " = ?",
                new String[]{String.valueOf(contact.get_id())});

        db.close();
    }

    public List<PersonalExpensesData> showAllExpenses() {
        List<PersonalExpensesData> expenseList = new ArrayList<PersonalExpensesData>();

        String selectQuery = "SELECT  * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                PersonalExpensesData expensesData = new PersonalExpensesData();
                expensesData.set_id(Integer.parseInt(cursor.getString(0)));
                expensesData.set_amount(Float.parseFloat(cursor.getString(1)));
                expensesData.set_purpose(cursor.getString(2));
                expensesData.set_date(cursor.getString(3));
                // Adding contact to list
                expenseList.add(expensesData);
            } while (cursor.moveToNext());
        }
        db.close();
        return expenseList;
    }

    public List<PersonalExpensesData> showAllExpensesByDate(String date) {
        List<PersonalExpensesData> expenseList = new ArrayList<PersonalExpensesData>();

        String selectQuery = "SELECT  * FROM " + TABLE_NAME + " where " + KEY_DATE + " = \"" + date + "\"";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                PersonalExpensesData expensesData = new PersonalExpensesData();
                expensesData.set_id(Integer.parseInt(cursor.getString(0)));
                expensesData.set_amount(Float.parseFloat(cursor.getString(1)));
                expensesData.set_purpose(cursor.getString(2));
                expensesData.set_date(cursor.getString(3));
                // Adding contact to list
                expenseList.add(expensesData);
            } while (cursor.moveToNext());
        }
        db.close();
        return expenseList;
    }

    public int isTableEmpty(String date) {
        SQLiteDatabase db = this.getReadableDatabase();
        String count = "SELECT count(*) FROM " + TABLE_NAME + " where " + KEY_DATE + " = \"" + date + "\"";
        Cursor mcursor = db.rawQuery(count, null);
        mcursor.moveToFirst();
        int icount = mcursor.getInt(0);

        if (icount > 0) {
            db.close();
            return 1;
        } else {
            db.close();
            return 0;
        }

    }

    public int isTableEmpty() {
        SQLiteDatabase db = this.getReadableDatabase();
        String count = "SELECT count(*) FROM " + TABLE_NAME;
        Cursor mcursor = db.rawQuery(count, null);
        mcursor.moveToFirst();
        int icount = mcursor.getInt(0);

        if (icount > 0) {
            db.close();
            return 1;
        } else {
            db.close();
            return 0;
        }
    }



}
