package pl.hypeapp.endoscope.ui.activity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.widget.ImageView;

import net.grandcentrix.thirtyinch.TiActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.hypeapp.endoscope.R;
import pl.hypeapp.endoscope.presenter.SplashScreenPresenter;
import pl.hypeapp.endoscope.ui.animation.SmallBang;
import pl.hypeapp.endoscope.ui.animation.SmallBangListener;
import pl.hypeapp.endoscope.util.SettingsPreferencesUtil;
import pl.hypeapp.endoscope.view.SplashScreenView;


public class SplashScreenActivity extends TiActivity<SplashScreenPresenter, SplashScreenView> implements SplashScreenView {
    private static final int SMALLBANG_RADIUS = 180;
    private SplashScreenPresenter splashScreenPresenter;
    private SmallBang smallBang;
    @BindView(R.id.logo_text) ImageView logoTextSwap;
    @BindView(R.id.logo_aparat_icon) ImageView logoIcon;

    @NonNull
    @Override
    public SplashScreenPresenter providePresenter() {
        final SettingsPreferencesUtil settingsPreferencesUtil = new SettingsPreferencesUtil(PreferenceManager.getDefaultSharedPreferences(this));
        splashScreenPresenter = new SplashScreenPresenter(settingsPreferencesUtil);
        return splashScreenPresenter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        ButterKnife.bind(this);
        smallBang = SmallBang.attach2Window(this);
    }

    @Override
    public void startSplashAnimation() {
        smallBang.bang(logoTextSwap, SMALLBANG_RADIUS, new SmallBangListener() {
            @Override
            public void onAnimationStart() {
                ((AnimationDrawable) logoIcon.getBackground()).start();
                logoTextSwap.setImageResource(R.drawable.hypeap_logo_text);
            }

            @Override
            public void onAnimationEnd() {
                splashScreenPresenter.delayRunActivity();
            }
        });
    }

    @Override
    public void intentToMainMenu() {
        Intent intent = new Intent(SplashScreenActivity.this, MainMenuActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public void intentToHowToUse() {
        Intent intent = new Intent(SplashScreenActivity.this, HowToUseActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

}
