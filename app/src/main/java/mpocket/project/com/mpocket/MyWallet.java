package mpocket.project.com.mpocket;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

/**
 * Created by abhishek on 6/7/15.
 */
public class MyWallet extends ActionBarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_wallet);

        getSupportActionBar().setTitle("myWallet");

    }
}
