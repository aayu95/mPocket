package mpocket.project.com.mpocket;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;

/**
 * Created by abhishek on 6/7/15.
 */
public class MyWallet extends ActionBarActivity {

    TextView balance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_wallet);

        getSupportActionBar().setTitle("myWallet");

        balance = (TextView) findViewById(R.id.balance);

        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/digital-7 (mono).ttf");
        balance.setTypeface(tf);

    }
}
