package mpocket.project.com.mpocket;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;


/**
 * Created by abhishek on 8/7/15.
 */
public class PersonalExpenses extends ActionBarActivity {
    Calendar calender = Calendar.getInstance();
    int Year = calender.get(Calendar.YEAR),
            Month = calender.get(Calendar.MONTH),
            Day = calender.get(Calendar.DAY_OF_MONTH);

    static final int DIALOG_ID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_expenses);
        getSupportActionBar().setTitle("myExpenses");
        String message = Day + "/" + (Month+1) + "/" + Year;
        TextView setDateText = (TextView) findViewById(R.id.dateText);
        setDateText.setText(message);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.personal_expenses, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_calender) {
            startCalenderDialog();
            return true;
        } else if (id == R.id.action_add_new_event) {
            addNewEvent();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void addNewEvent() {
        Context context = this;
        LayoutInflater inflater = LayoutInflater.from(context);
        String message = Day + "/" + (Month+1) + "/" + Year;

        View dialogView = inflater.inflate(R.layout.add_new_event, null);

        final AlertDialog.Builder customEventDialog = new AlertDialog.Builder(context);



        customEventDialog.setView(dialogView);
        customEventDialog.setTitle("Date: " + message);
        customEventDialog.setCancelable(true);
        customEventDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "Expenditure Added", Toast.LENGTH_SHORT).show();
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });


        customEventDialog.create().show();
    }

    private void startCalenderDialog() {

        showDialog(DIALOG_ID);

    }

    @Override
    public Dialog onCreateDialog(int id) {
        if (id == DIALOG_ID) {
            return new DatePickerDialog(this, dPickerListener, Year, Month, Day);
        }
        return null;
    }


    private DatePickerDialog.OnDateSetListener dPickerListener = new DatePickerDialog.OnDateSetListener() {


        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            Year = year; Month = monthOfYear; Day = dayOfMonth;
            String message = dayOfMonth + "/" + (monthOfYear+1) + "/" + year;
            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            TextView dateText = (TextView) findViewById(R.id.dateText);
            dateText.setText(message);

        }
    };


}
