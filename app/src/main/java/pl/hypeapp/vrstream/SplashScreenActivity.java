package pl.hypeapp.vrstream;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;

import xyz.hanks.library.SmallBang;
import xyz.hanks.library.SmallBangListener;


public class SplashScreenActivity extends Activity {

    private SmallBang smallBang;
    private ImageView aparatIconAnim;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash_screen);
        aparatIconAnim = (ImageView)findViewById(R.id.logo_aparat_icon);
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
//                        aparatIconAnim.setBackgroundResource(R.drawable.aparat_animation);
//                        AnimationDrawable frameAnimation = (AnimationDrawable) aparatIconAnim.getBackground();
//                        frameAnimation.start();
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
        final Intent intentToMainActivity = new Intent(SplashScreenActivity.this, MainMenuActivity.class);
        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                startActivity(intentToMainActivity);
                onDestroy();
            }
        };
        handler.postDelayed(runnable, seconds);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("onDest", "onDeste");
    }
}
