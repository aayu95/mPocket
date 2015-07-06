package mpocket.project.com.mpocket;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

/**
 * Created by abhishek on 6/7/15.
 */
public class SplashScreen extends Activity {

    int SCREEN_TIMEOUT = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashscreen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent proceedToMain = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(proceedToMain);
            }
        }, SCREEN_TIMEOUT);
    }
}
