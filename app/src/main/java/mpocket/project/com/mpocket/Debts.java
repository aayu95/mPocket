package mpocket.project.com.mpocket;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBarActivity;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by abhishek on 13/7/15.
 */
public class Debts extends ActionBarActivity{

    private static final int SEND_CODE = 1;
    // emptylist linear layout
    View emptyListLinearLayout;

    //List of debts
    ListView debtList;

    EditText person;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.debts);
        emptyListLinearLayout = (View) findViewById(R.id.emptyList);
        debtList = (ListView) findViewById(R.id.debtList);
        registerForContextMenu(debtList);
        printDebts();

    }

    public void newDebt(View view) {
        Intent intent = new Intent(this, NewDebt.class);
        startActivityForResult(intent, SEND_CODE);
    }



    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {

        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.debt_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        DebtDatabase db = new DebtDatabase (this);
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        DebtData data = (DebtData) debtList.getItemAtPosition(info.position);

        switch (item.getItemId()) {
            case R.id.action_delete:
                db.deleteDebt(data);
                printDebts();
                Toast.makeText(getApplicationContext(), "Deleted", Toast.LENGTH_SHORT).show();
                db.close();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        DebtDatabase db = new DebtDatabase(this);

        // new Debt Details
        if (requestCode == SEND_CODE) {
            if (resultCode == RESULT_OK) {

                String userAmount = data.getStringExtra("User_Amount");
                Float amount = Float.parseFloat(userAmount);
                String name = data.getStringExtra("User_Name");
                String borrowDate = data.getStringExtra("Borrowing_Date");
                String returnDate = data.getStringExtra("Returning_Date");
                DebtData debtData = new DebtData(amount, name, borrowDate, returnDate);
                db.addNewDebt(debtData);
                db.close();
                Toast.makeText(this, "Details Added", Toast.LENGTH_SHORT).show();
                printDebts();
            }
        }
    }



    private void printDebts() {
        DebtDatabase db = new DebtDatabase(this);
        if (db.isTableEmpty()) {
            emptyListLinearLayout.setVisibility(View.VISIBLE);
            debtList.setVisibility(View.GONE);
            TextView emptyText = (TextView) findViewById(R.id.emptyListText);
            Typeface tf= Typeface.createFromAsset(getAssets(), "fonts/empty_list.ttf");
            emptyText.setTypeface(tf);
        } else {
            emptyListLinearLayout.setVisibility(View.GONE);
            debtList.setVisibility(View.VISIBLE);

            List<DebtData> contacts = db.showAllDebts();

            DebtListCustomAdapter adapter = new DebtListCustomAdapter(this, contacts);
            debtList.setAdapter(adapter);
        }
        db.close();
    }


    public void lendingDate(View view) {
    }

    public void returningDate(View view) {
    }
}
