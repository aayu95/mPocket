package mpocket.project.com.mpocket;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.zip.Inflater;

/**
 * Created by abhishek on 6/7/15.
 */
public class MyWallet extends ActionBarActivity {

    Context context = this;
    DatabaseHandler balanceDb = new DatabaseHandler(this);
    TextView balance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_wallet);

        getSupportActionBar().setTitle("myWallet");

        balance = (TextView) findViewById(R.id.balance);
        printAmount();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.wallet_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        if (id == R.id.action_reset) {
            resetAccount();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void resetAccount() {
        DatabaseHandler db = new DatabaseHandler(this);
        db.resetData();
        db.close();
        printAmount();
    }

    /*
    *  Starts Dialog Activity
    * */
    public void showDialog(View view) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.amount_dialog, null);

        final AlertDialog.Builder customDialog = new AlertDialog.Builder(context);

        customDialog.setView(dialogView);

        final EditText userInput = (EditText) dialogView
                .findViewById(R.id.amountTextview);

        customDialog.setCancelable(true);
        customDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DatabaseHandler db = new DatabaseHandler(getApplicationContext());
                float amount;
                if (userInput.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Amount not added because you left the field blank", Toast.LENGTH_LONG).show();
                } else {
                    amount = Float.parseFloat(userInput.getText().toString());
                    db.addAmount(amount);
                    db.close();
                    Toast.makeText(getApplicationContext(), "Amount Added Succesfully", Toast.LENGTH_SHORT).show();
                    printAmount();
                }
            }
        });
        customDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        customDialog.create().show();
    }

    public void printAmount() {
        DatabaseHandler db = new DatabaseHandler(this);
        BalanceData data = db.returnOldAmount();
        balance = (TextView) findViewById(R.id.balance);
        balance.setText(""+data.get_amount());
        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/digital-7 (mono).ttf");
        balance.setTypeface(tf);
        db.close();
    }
}
