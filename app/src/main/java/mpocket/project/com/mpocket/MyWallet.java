package mpocket.project.com.mpocket;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
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
    BalanceDatabase balanceDb = new BalanceDatabase(this);
    TextView balance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_wallet);

        getSupportActionBar().setTitle("myWallet");

        balance = (TextView) findViewById(R.id.balance);
        BalanceDatabase balanceDb = new BalanceDatabase(this);

        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/digital-7 (mono).ttf");
        balance.setTypeface(tf);
        balanceDb.close();
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
                BalanceDatabase db = new BalanceDatabase(getApplicationContext());
                float amount;
                if (userInput.getText().toString().equals("")) {
                    amount = 0;
                } else {
                    amount = Float.parseFloat(userInput.getText().toString());
                }
                Toast.makeText(getApplicationContext(), "Amount Added Succesfully", Toast.LENGTH_SHORT).show();
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
}
