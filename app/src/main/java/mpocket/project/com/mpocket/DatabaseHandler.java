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
 * Created by abhishek on 16/7/15.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "mPocket";

    //Table name
    private static final String WALLET_TABLE_NAME = "amount";
    private static final String EXPENSES_TABLE_NAME = "personal_expenses";
    private static final String DEBT_TABLE_NAME = "debts";
    private static final String LOAN_TABLE_NAME = "loans";

    //Wallet Table columns
    private static final String KEY_ID = "id";
    private static final String KEY_AMOUNT = "balance";

    //Personal Expenses Table Columns
    private static final String EXPENSES_AMOUNT = "amount";
    private static final String KEY_PURPOSE = "purpose";
    private static final String KEY_DATE = "date";

    //Loans Table Columns
    private static final String LOAN_AMOUNT = "amount";
    private static final String KEY_LEND_FROM= "borrowed_from";
    private static final String LOAN_LEND_ON = "borrow_date";
    private static final String LOAN_RETURN_BY = "returning_date";

    //Debts Table Columns
    private static final String DEBT_AMOUNT = "amount";
    private static final String KEY_LEND_TO= "lend_to";
    private static final String DEBT_LEND_ON = "lend_on";
    private static final String DEBT_RETURN_BY = "returning_date";


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        // Wallet Table
        String query = "create table if not exists " + WALLET_TABLE_NAME + "(" +
                KEY_ID + " integer primary key autoincrement, " +
                KEY_AMOUNT + " real);";

        db.execSQL(query);

        //Personal Expenses Table
        query = "create table if not exists " + EXPENSES_TABLE_NAME + "(" +
                KEY_ID + " integer primary key autoincrement, " +
                EXPENSES_AMOUNT + " real, " +
                KEY_PURPOSE + " text, " +
                KEY_DATE + " text);";

        db.execSQL(query);

        // Loans Table
        query = "create table if not exists " + LOAN_TABLE_NAME + "(" +
                KEY_ID + " integer primary key autoincrement, " +
                LOAN_AMOUNT + " real, " +
                KEY_LEND_FROM + " text, " +
                LOAN_LEND_ON + " text, " +
                LOAN_RETURN_BY + " text);";

        db.execSQL(query);

        // Debts Table
        query = "create table if not exists " + DEBT_TABLE_NAME + "(" +
                KEY_ID + " integer primary key autoincrement, " +
                DEBT_AMOUNT + " real, " +
                KEY_LEND_TO + " text, " +
                DEBT_LEND_ON + " text, " +
                DEBT_RETURN_BY + " text);";

        db.execSQL(query);

        Log.d("Error", "Creating Table");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + EXPENSES_TABLE_NAME);
        db.execSQL("drop table if exists " + WALLET_TABLE_NAME);
        db.execSQL("drop table if exists " + LOAN_TABLE_NAME);
        db.execSQL("drop table if exists " + DEBT_TABLE_NAME);
        onCreate(db);
    }

    public void addBalance(BalanceData data) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_AMOUNT, data.get_amount());


        db.insert(WALLET_TABLE_NAME, null, values);
        Log.d("Error", "Added Balance");
        db.close();
    }

    public List<BalanceData> showAll() {
        List<BalanceData> contactList = new ArrayList<BalanceData>();

        String selectQuery = "SELECT  * FROM " + WALLET_TABLE_NAME;

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
        db.update(WALLET_TABLE_NAME, values, KEY_ID + " = ?",
                new String[]{String.valueOf(data.get_id())});

        db.close();
    }

    public BalanceData returnOldAmount() {

        String selectQuery = "SELECT  * FROM " + WALLET_TABLE_NAME + " where id = 1";

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
        db.update(WALLET_TABLE_NAME, values, KEY_ID + " = ?",
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
        db.update(WALLET_TABLE_NAME, values, KEY_ID + " = ?",
                new String[]{String.valueOf(data.get_id())});

        db.close();
    }


    //  *****************************      Personal Expenses   **********************************************

    public void addNewExpense(PersonalExpensesData newExpense) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(EXPENSES_AMOUNT, newExpense.get_amount());
        values.put(KEY_PURPOSE, newExpense.get_purpose());
        values.put(KEY_DATE, newExpense.get_date());

        db.insert(EXPENSES_TABLE_NAME, null, values);
        Log.d("Error", "Added Expense");
        db.close();
    }

    public void deleteContact(PersonalExpensesData contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(EXPENSES_TABLE_NAME, KEY_ID + " = ?",
                new String[]{String.valueOf(contact.get_id())});
        Log.d("Error", "Deleted Expense");
        db.close();
    }

    public void editContact(PersonalExpensesData contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(EXPENSES_AMOUNT, contact.get_amount());
        values.put(KEY_PURPOSE, contact.get_purpose());
        values.put(KEY_DATE, contact.get_date());

        // updating row
        db.update(EXPENSES_TABLE_NAME, values, KEY_ID + " = ?",
                new String[]{String.valueOf(contact.get_id())});

        db.close();
    }

    public List<PersonalExpensesData> showAllExpenses() {
        List<PersonalExpensesData> expenseList = new ArrayList<PersonalExpensesData>();

        String selectQuery = "SELECT  * FROM " + EXPENSES_TABLE_NAME + " order by id desc";

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

        String selectQuery = "SELECT  * FROM " + EXPENSES_TABLE_NAME + " where " + KEY_DATE + " = \"" + date +
                "\"" + " order by id desc";

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

    public int isExpensesEmpty(String date) {
        SQLiteDatabase db = this.getReadableDatabase();
        String count = "SELECT count(*) FROM " + EXPENSES_TABLE_NAME + " where " + KEY_DATE + " = \"" + date + "\"";
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

    public int isExpensesEmpty() {
        SQLiteDatabase db = this.getReadableDatabase();
        String count = "SELECT count(*) FROM " + EXPENSES_TABLE_NAME;
        Cursor mcursor = db.rawQuery(count, null);
        mcursor.moveToFirst();
        int icount = mcursor.getInt(0);

        if (icount > 0) {
            db.close();
            Log.d("Error", "Expenses is not Empty");
            return 1;
        } else {
            db.close();
            Log.d("Error", "Expenses is Empty");
            return 0;
        }
    }

    //  *****************************      Loans   **********************************************

    public void addNewLoan(LoanData data) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(LOAN_AMOUNT, data.get_amount());
        values.put(KEY_LEND_FROM, data.get_lend_from());
        values.put(LOAN_LEND_ON, data.get_lend_on());
        values.put(LOAN_RETURN_BY, data.get_return_by());

        db.insert(LOAN_TABLE_NAME, null, values);
        Log.d("Error", "Added Loan");
    }

    public void deleteLoan(LoanData data) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(LOAN_TABLE_NAME, KEY_ID + " = ?",
                new String[]{String.valueOf(data.get_id())});
        Log.d("Error", "Deleted Loan");
        db.close();
    }

    public List<LoanData> showAllLoans() {
        List<LoanData> loanList = new ArrayList<LoanData>();

        String selectQuery = "SELECT  * FROM " + LOAN_TABLE_NAME + " order by id desc";

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

    public boolean isLoanEmpty() {
        SQLiteDatabase db = this.getReadableDatabase();
        String count = "SELECT count(*) FROM " + LOAN_TABLE_NAME;
        Cursor mcursor = db.rawQuery(count, null);
        mcursor.moveToFirst();
        int icount = mcursor.getInt(0);

        if (icount > 0) {
            db.close();
            Log.d("Error", "Loan is not Empty");
            return false;
        } else {
            db.close();
            Log.d("Error", "Loan is Empty");
            return true;
        }
    }

    //  *****************************      DEBT   **********************************************

    public void addNewDebt(DebtData data) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(DEBT_AMOUNT, data.get_amount());
        values.put(KEY_LEND_TO, data.get_lend_to());
        values.put(DEBT_LEND_ON, data.get_lend_on());
        values.put(DEBT_RETURN_BY, data.get_return_by());

        db.insert(DEBT_TABLE_NAME, null, values);
        Log.d("Error", "Added Debt");
    }

    public void deleteDebt(DebtData data) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DEBT_TABLE_NAME, KEY_ID + " = ?",
                new String[]{String.valueOf(data.get_id())});
        Log.d("Error", "Deleted Debt");
        db.close();
    }


    public List<DebtData> showAllDebts() {
        List<DebtData> debtList = new ArrayList<DebtData>();

        String selectQuery = "SELECT  * FROM " + DEBT_TABLE_NAME + " order by id desc";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                DebtData data = new DebtData();
                data.set_id(Integer.parseInt(cursor.getString(0)));
                data.set_amount(Float.parseFloat(cursor.getString(1)));
                data.set_lend_to(cursor.getString(2));
                data.set_lend_on(cursor.getString(3));
                data.set_return_by(cursor.getString(4));
                // Adding contact to list
                debtList.add(data);
            } while (cursor.moveToNext());
        }
        db.close();
        return debtList;
    }

    public boolean isDebtEmpty() {
        SQLiteDatabase db = this.getReadableDatabase();
        String count = "SELECT count(*) FROM " + DEBT_TABLE_NAME;
        Cursor mcursor = db.rawQuery(count, null);
        mcursor.moveToFirst();
        int icount = mcursor.getInt(0);

        if (icount > 0) {
            db.close();
            Log.d("Error", "Debt is not Empty");
            return false;
        } else {
            db.close();
            Log.d("Error", "Debt is Empty");
            return true;
        }
    }

    // *********************************************************************************************************************

    public void deleteFirstDebt() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DEBT_TABLE_NAME, KEY_ID + " = ?",
                new String[]{String.valueOf(1)});
        Log.d("Error", "Deleted Debt");
        db.close();
    }

    public void deleteFirstLoan() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(LOAN_TABLE_NAME, KEY_ID + " = ?",
                new String[]{String.valueOf(1)});
        Log.d("Error", "Deleted Loan");
        db.close();
    }

    public void deleteFirstExpense() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(EXPENSES_TABLE_NAME, KEY_ID + " = ?",
                new String[]{String.valueOf(1)});
        Log.d("Error", "Deleted Expense");
        db.close();
    }


}
