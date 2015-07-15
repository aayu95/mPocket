package mpocket.project.com.mpocket;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.List;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void MyWalletActivity(View view) {
        Intent go = new Intent(this, MyWallet.class);
        startActivity(go);
    }

    public void PersonalExpensesActivity(View view) {
        Intent go = new Intent(this, PersonalExpenses.class);
        startActivity(go);
    }

    public void LoanActivity(View view) {
        Intent go = new Intent(this, Loans.class);
        startActivity(go);
    }

    public void DebtActivity(View view) {
        Intent go = new Intent(this, Debts.class);
        startActivity(go);
    }

}
