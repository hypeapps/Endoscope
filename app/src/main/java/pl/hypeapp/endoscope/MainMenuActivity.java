package pl.hypeapp.endoscope;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;

public class MainMenuActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }
    public void intentStreamVideo(View view) {
        Intent i =  new Intent(MainMenuActivity.this, StartStreamActivity.class);
        startActivity(i);
    }
    public void intentViewStream(View view) {
        Intent i =  new Intent(MainMenuActivity.this, ConnectToStreamActivity.class);
        startActivity(i);
    }
    public void intentSettings(View View) {
        Intent i = new Intent(MainMenuActivity.this, SettingsActivity.class);
        startActivity(i);
    }
    public void intentHowToUse(View view) {
        Intent i =  new Intent(MainMenuActivity.this, HowToUseActivity.class);
        startActivity(i);
    }
}
