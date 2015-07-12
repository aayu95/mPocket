package mpocket.project.com.mpocket;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void generateMessage(View view) {
        Toast.makeText(this, "Button Selected", Toast.LENGTH_SHORT).show();
    }

    public void MyWalletActivity(View view) {
        Intent go = new Intent(this, MyWallet.class);
        startActivity(go);
    }

    public void PersonalExpensesActivity(View view) {
        Intent go = new Intent(this, PersonalExpenses.class);
        startActivity(go);
    }

}
