package mpocket.project.com.mpocket;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

/**
 * Created by abhishek on 14/7/15.
 */
public class NewLoan extends ActionBarActivity {

    Calendar calender = Calendar.getInstance();

    int Year = calender.get(Calendar.YEAR),
            Month = calender.get(Calendar.MONTH),
            Day = calender.get(Calendar.DAY_OF_MONTH);

    int Borrow_Date = 0, Return_Date = 1, DISPLAY_CODE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_new_loan);
        getSupportActionBar().setTitle("Add new Loan");
    }

    public void AddLoan(View view) {
        EditText debtAmount = (EditText) findViewById(R.id.loanAmount);
        EditText debtPerson = (EditText) findViewById(R.id.loanPerson);
        EditText borrowDate = (EditText) findViewById(R.id.borrowDate);
        EditText returnDate = (EditText) findViewById(R.id.returnDate);

        if (debtAmount.getText().toString().equals("")
                || debtPerson.getText().toString().equals("")
                || borrowDate.getText().toString().equals("")
                || returnDate.getText().toString().equals("")) {
            String message = "Details were not added because did not fill the form correctly";
            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        } else {
            Intent sendName = new Intent(this, Debts.class);
            sendName.putExtra("User_Amount", debtAmount.getText().toString());
            sendName.putExtra("User_Name", debtPerson.getText().toString());
            sendName.putExtra("Borrowing_Date", borrowDate.getText().toString());
            sendName.putExtra("Returning_Date", returnDate.getText().toString());
            setResult(RESULT_OK, sendName);
            finish();
        }
    }

    public void borrowDate(View view) {
        showDialog(Borrow_Date);
    }

    public void returningDate(View view) {
        showDialog(Return_Date);
    }

    @Override
    public Dialog onCreateDialog(int id) {

        if (id == Borrow_Date) {
            return new DatePickerDialog(this, borrowDatePickerListener, Year, Month, Day);
        } else if (id == Return_Date) {
            return new DatePickerDialog(this, returnDatePickerListener, Year, Month, Day);
        }

        return null;
    }

    private DatePickerDialog.OnDateSetListener borrowDatePickerListener = new DatePickerDialog.OnDateSetListener() {


        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {


            String message = makeDate(dayOfMonth, (monthOfYear+1), year);

            EditText borrowDate = (EditText) findViewById(R.id.borrowDate);
            borrowDate.setText(message);

        }
    };

    private DatePickerDialog.OnDateSetListener returnDatePickerListener = new DatePickerDialog.OnDateSetListener() {


        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

            String message = makeDate(dayOfMonth, (monthOfYear+1), year);
            EditText returnDate = (EditText) findViewById(R.id.returnDate);
            returnDate.setText(message);
        }
    };

    String makeDate(int day_of_month, int month, int year) {
        String date = day_of_month + " " + makeMonth(month) + " " + year;
        return date;
    }

    private String makeMonth(int month) {
        String mMonth = "";
        if (month == 1) {
            mMonth = "January";
        } else if (month == 2) {
            mMonth = "February";
        } else if (month == 3) {
            mMonth = "March";
        } else if (month == 4){
            mMonth = "April";
        } else if (month == 5) {
            mMonth = "May";
        } else if (month == 6) {
            mMonth = "June";
        } else if (month == 7) {
            mMonth = "July";
        } else if (month == 8) {
            mMonth = "August";
        } else if (month == 9) {
            mMonth = "September";
        } else if (month == 10) {
            mMonth = "October";
        } else if (month == 11) {
            mMonth = "November";
        } else if (month == 12) {
            mMonth = "December";
        }

        return mMonth;
    }


    public void launchContact(View view) {
        Intent i = new Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
        startActivityForResult(i, DISPLAY_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == DISPLAY_CODE && resultCode == RESULT_OK) {
            Uri contactUri = data.getData();
            Cursor cursor = getContentResolver().query(contactUri, null, null, null, null);
            cursor.moveToFirst();
            //int column = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
            int name = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);

            EditText loanPerson = (EditText) findViewById(R.id.loanPerson);
            loanPerson.setText(cursor.getString(name));

            //Log.d("phone number", cursor.getString(column));
        }
    }
}
