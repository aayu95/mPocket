package mpocket.project.com.mpocket;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by abhishek on 13/7/15.
 */
public class Loans extends ActionBarActivity {

    // emptylist linear layout
    View emptyListLinearLayout;

    //List of Loans
    ListView loanList;

    static final int SEND_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loans);
        emptyListLinearLayout = (View) findViewById(R.id.emptyList);
        loanList = (ListView) findViewById(R.id.loanList);
        registerForContextMenu(loanList);
        printLoans();
    }

    public void newLoan(View view) {
        Intent intent = new Intent(this, NewLoan.class);
        startActivityForResult(intent, SEND_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SEND_CODE) {
            if (resultCode == RESULT_OK) {
                LoanDatabase db = new LoanDatabase(this);

                String userAmount = data.getStringExtra("User_Amount");
                Float amount = Float.parseFloat(userAmount);
                String name = data.getStringExtra("User_Name");
                String borrowDate = data.getStringExtra("Borrowing_Date");
                String returnDate = data.getStringExtra("Returning_Date");
                LoanData loanData = new LoanData(amount, name, borrowDate, returnDate);
                db.addNewLoan(loanData);
                db.close();
                printLoans();
            }
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {

        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.loan_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        LoanDatabase db = new LoanDatabase (this);

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        LoanData data = (LoanData) loanList.getItemAtPosition(info.position);

        switch (item.getItemId()) {
            case R.id.action_delete:
                db.deleteLoan(data);
                printLoans();
                Toast.makeText(getApplicationContext(), "Deleted", Toast.LENGTH_SHORT).show();
                db.close();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
    private void printLoans() {
        LoanDatabase db = new LoanDatabase(this);
        if (db.isTableEmpty()) {
            emptyListLinearLayout.setVisibility(View.VISIBLE);
            loanList.setVisibility(View.GONE);
            TextView emptyText = (TextView) findViewById(R.id.emptyListText);
            Typeface tf= Typeface.createFromAsset(getAssets(), "fonts/empty_list.ttf");
            emptyText.setTypeface(tf);
        } else {
            emptyListLinearLayout.setVisibility(View.GONE);
            loanList.setVisibility(View.VISIBLE);

            List<LoanData> loanData = db.showAllLoans();

            LoanListCustomAdapter adapter = new LoanListCustomAdapter(this, loanData);
            loanList.setAdapter(adapter);
        }
        db.close();
    }
}
