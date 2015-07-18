package mpocket.project.com.mpocket;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import java.util.List;

/**
 * Created by abhishek on 6/7/15.
 */
public class SplashScreen extends Activity {

    int SCREEN_TIMEOUT = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashscreen);

        if (isFirstTime()) {
            createTablesAndDatabases();
            startSplashscreen();
        } else {
            startSplashscreen();
        }

    }

    private void createTablesAndDatabases() {
        DatabaseHandler db = new DatabaseHandler(this);


        BalanceData d1 = new BalanceData(0);
        db.addBalance(d1);

        PersonalExpensesData d2 = new PersonalExpensesData(0, "dd", "17 June 2015");
        db.addNewExpense(d2);


        LoanData d3 = new LoanData(0, "Dummy", "17 June 2015", "17 June 2015");
        db.addNewLoan(d3);


        DebtData d4 = new DebtData(0, "Dummy", "17 June 2015", "17 June 2015");
        db.addNewDebt(d4);


        db.deleteFirstDebt();
        db.deleteFirstLoan();
        db.deleteFirstExpense();


        db.close();

    }

    private boolean isFirstTime() {
        boolean isFirstRun = getSharedPreferences("PREFERENCE", MODE_PRIVATE)
                .getBoolean("isfirstrun", true);
        if (isFirstRun) {
            getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit().
                    putBoolean("isfirstrun", false).commit();
            return true;

        } else {
            return false;
        }
    }

    private void startSplashscreen() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent proceedToMain = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(proceedToMain);
                finish();
            }
        }, SCREEN_TIMEOUT);
    }

}
