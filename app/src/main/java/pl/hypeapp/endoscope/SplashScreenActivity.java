package pl.hypeapp.endoscope;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.widget.ImageView;

import xyz.hanks.library.SmallBang;
import xyz.hanks.library.SmallBangListener;


public class SplashScreenActivity extends Activity {

    private SmallBang smallBang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setContentView(R.layout.activity_splash_screen);
        smallBang = SmallBang.attach2Window(this);
        splashAnimation();
    }

    private void splashAnimation(){
        final ImageView textLogoSwapper = (ImageView) findViewById(R.id.logo_text);
        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                smallBang.bang(textLogoSwapper, 180, new SmallBangListener() {
                    @Override
                    public void onAnimationStart() {
                        ImageView mImageViewFilling = (ImageView) findViewById(R.id.logo_aparat_icon);
                        ((AnimationDrawable) mImageViewFilling.getBackground()).start();

                    }

                    @Override
                    public void onAnimationEnd() {
                        intentNextActivityAfterSeconds(2000);
                    }
                });
                textLogoSwapper.setImageResource(R.drawable.hypeap_logo_text);
            }
        };

        handler.postDelayed(runnable, 3000);
    }

    private void intentNextActivityAfterSeconds(long seconds){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isFirstRun = sharedPreferences.getBoolean("is_first_run", true);
        Intent intentTo;
        if(isFirstRun){
            SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(this).edit();
            editor.putBoolean("is_first_run", false);
            editor.commit();
            intentTo = new Intent(SplashScreenActivity.this, HowToUseActivity.class);
        }else{
            intentTo = new Intent(SplashScreenActivity.this, MainMenuActivity.class);
        }

        final Intent intent = intentTo;
        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                startActivity(intent);
                onDestroy();
            }
        };
        handler.postDelayed(runnable, seconds);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }
}
