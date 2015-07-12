package mpocket.project.com.mpocket;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Calendar;
import java.util.List;


/**
 * Created by abhishek on 8/7/15.
 */
public class PersonalExpenses extends ActionBarActivity {

    Calendar calender = Calendar.getInstance();

    ListView expenseList;

    boolean ALL_EXPENSES_VISIBLE = false;

    int Year = calender.get(Calendar.YEAR),
            Month = calender.get(Calendar.MONTH),
            Day = calender.get(Calendar.DAY_OF_MONTH);

    static final int DIALOG_ID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ALL_EXPENSES_VISIBLE = false;
        setContentView(R.layout.personal_expenses);
        getSupportActionBar().setTitle("myExpenses");

        expenseList = (ListView) findViewById(R.id.list_of_expenses);
        registerForContextMenu(expenseList);
        String message = makeDate(Day, (Month+1), Year);

        TextView setDateText = (TextView) findViewById(R.id.dateText);
        setDateText.setText(message);
        printExpenses();
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
            addNewExpense();
            return true;
        } else if (id == R.id.action_go_to_today) {
            ALL_EXPENSES_VISIBLE = false;
            showCurrentDayExpenses();
            return true;
        } else if (id == R.id.show_all_expenses) {
            ALL_EXPENSES_VISIBLE = true;
            showAllExpensesTillDate();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {

        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.personal_expenses_context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        PersonalExpensesDatabase db = new PersonalExpensesDatabase(this);
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        PersonalExpensesData data = (PersonalExpensesData) expenseList.getItemAtPosition(info.position);

        switch (item.getItemId()) {
            case R.id.action_edit:
                startEditDialog(data);

                if (ALL_EXPENSES_VISIBLE) {
                    showAllExpensesTillDate();
                } else {
                    printExpenses();
                }

                return true;
            case R.id.action_delete:
                db.deleteContact(data);
                if (ALL_EXPENSES_VISIBLE) {
                    showAllExpensesTillDate();
                } else {
                    printExpenses();
                }
                Toast.makeText(getApplicationContext(), "Deleted", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    private void startEditDialog(PersonalExpensesData data) {
        final Context context = this;
        LayoutInflater inflater = LayoutInflater.from(context);


        View dialogView = inflater.inflate(R.layout.edit_personal_expense, null);

        final AlertDialog.Builder customEventDialog = new AlertDialog.Builder(context);

        final EditText purposeText = (EditText) dialogView.findViewById(R.id.purpose);
        final EditText amountText = (EditText) dialogView.findViewById(R.id.amount);
        final EditText dateText = (EditText) dialogView.findViewById(R.id.dateEdit);

        purposeText.setText(data.get_purpose());
        amountText.setText(""+data.get_amount());
        dateText.setText(data.get_date());

        final PersonalExpensesData newData = data;
        customEventDialog.setView(dialogView);
        customEventDialog.setTitle("Edit Details");
        customEventDialog.setCancelable(true);
        customEventDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                float amount = Float.parseFloat(String.valueOf(amountText.getText()));
                String date = String.valueOf(dateText.getText());
                String purpose = String.valueOf(purposeText.getText());

                newData.set_amount(amount);
                newData.set_purpose(purpose);
                newData.set_date(date);
                PersonalExpensesDatabase db = new PersonalExpensesDatabase(context);
                db.editContact(newData);
                if (ALL_EXPENSES_VISIBLE) {
                    showAllExpensesTillDate();
                } else {
                    printExpenses();
                }
                Toast.makeText(getApplicationContext(), "Edit Successful", Toast.LENGTH_SHORT).show();
                db.close();
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });


        customEventDialog.create().show();
    }


    private void showCurrentDayExpenses() {
        ALL_EXPENSES_VISIBLE = false;
        int Year = calender.get(Calendar.YEAR),
                Month = calender.get(Calendar.MONTH),
                Day = calender.get(Calendar.DAY_OF_MONTH);

        String date =  makeDate(Day, (Month + 1), Year);
        TextView setDateText = (TextView) findViewById(R.id.dateText);
        setDateText.setText(date);
        printExpenses();
    }

    private void addNewExpense() {
        final Context context = this;
        LayoutInflater inflater = LayoutInflater.from(context);

        int Year = calender.get(Calendar.YEAR),
                Month = calender.get(Calendar.MONTH),
                Day = calender.get(Calendar.DAY_OF_MONTH);


        String date =  makeDate(Day, (Month + 1), Year);

        View dialogView = inflater.inflate(R.layout.add_new_expenditure, null);

        final AlertDialog.Builder customEventDialog = new AlertDialog.Builder(context);

        final EditText currentDate = (EditText) dialogView.findViewById(R.id.currentDate);
        currentDate.setText("Date: " + date);
        final EditText purposeText = (EditText) dialogView.findViewById(R.id.eventName);
        final EditText amountText = (EditText) dialogView.findViewById(R.id.amount);

        customEventDialog.setView(dialogView);
        customEventDialog.setTitle("Today's new Expense");
        customEventDialog.setCancelable(true);
        customEventDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                float amount = Float.parseFloat(String.valueOf(amountText.getText()));
                int Year = calender.get(Calendar.YEAR),
                        Month = calender.get(Calendar.MONTH),
                        Day = calender.get(Calendar.DAY_OF_MONTH);


                String date =  makeDate(Day, (Month + 1), Year);
                String purpose = String.valueOf(purposeText.getText());

                PersonalExpensesData newData = new PersonalExpensesData(amount, purpose, date);
                PersonalExpensesDatabase db = new PersonalExpensesDatabase(context);
                db.addNewExpense(newData);
                showCurrentDayExpenses();
                Toast.makeText(getApplicationContext(), "Expenditure Added", Toast.LENGTH_SHORT).show();
                db.close();
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
        ALL_EXPENSES_VISIBLE =false;
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
            Year = year;
            Month = monthOfYear;
            Day = dayOfMonth;

            String message = makeDate(dayOfMonth, (monthOfYear+1), year);
            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();

            TextView dateText = (TextView) findViewById(R.id.dateText);
            dateText.setText(message);
            printExpenses();

        }
    };

    private void printExpenses() {


        PersonalExpensesDatabase db = new PersonalExpensesDatabase(this);

        //Gets date from this activity

        TextView setDateText = (TextView) findViewById(R.id.dateText);
        String date = setDateText.getText().toString();
        setDateText.setVisibility(View.VISIBLE);

        View emptyLinearLayout = findViewById(R.id.frame);

        int count = db.isTableEmpty(date);

        if (count == 0) {
            expenseList.setVisibility(View.GONE);
            emptyLinearLayout.setVisibility(View.VISIBLE);
            TextView emptyText = (TextView) findViewById(R.id.emptyListText);
            Typeface
                    tf= Typeface.createFromAsset(getAssets(), "fonts/empty_list.ttf");
            emptyText.setTypeface(tf);

        } else {
            emptyLinearLayout.setVisibility(View.GONE);
            expenseList.setVisibility(View.VISIBLE);
            List<PersonalExpensesData> contacts = db.showAllExpensesByDate(date);

            PersonalExpensesCustomListAdapter adapter = new PersonalExpensesCustomListAdapter(this, contacts);
            expenseList.setAdapter(adapter);
        }

        db.close();
    }

    private void showAllExpensesTillDate() {

        ALL_EXPENSES_VISIBLE = true;

        int Year = calender.get(Calendar.YEAR),
                Month = calender.get(Calendar.MONTH),
                Day = calender.get(Calendar.DAY_OF_MONTH);
        String date = makeDate(Day, (Month+1), Year);
        TextView setDateText = (TextView) findViewById(R.id.dateText);
        setDateText.setText("All Expenses");

        PersonalExpensesDatabase db = new PersonalExpensesDatabase(this);

        View emptyLinearLayout = findViewById(R.id.frame);

        int count = db.isTableEmpty();

        if (count == 0) {
            expenseList.setVisibility(View.GONE);
            emptyLinearLayout.setVisibility(View.VISIBLE);
            TextView emptyText = (TextView) findViewById(R.id.emptyListText);
            Typeface tf= Typeface.createFromAsset(getAssets(), "fonts/empty_list.ttf");
            emptyText.setTypeface(tf);

        } else {
            emptyLinearLayout.setVisibility(View.GONE);
            expenseList.setVisibility(View.VISIBLE);
            List<PersonalExpensesData> contacts = db.showAllExpenses();

            PersonalExpensesCustomListAdapter adapter = new PersonalExpensesCustomListAdapter(this, contacts);
            expenseList.setAdapter(adapter);
        }

        db.close();

    }


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




}
