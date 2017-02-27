package pl.hypeapp.endoscope.presenter;

import net.grandcentrix.thirtyinch.TiPresenter;
import net.grandcentrix.thirtyinch.rx.RxTiPresenterSubscriptionHandler;
import net.grandcentrix.thirtyinch.rx.RxTiPresenterUtils;

import java.util.concurrent.TimeUnit;

import pl.hypeapp.endoscope.view.SplashScreenView;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class SplashScreenPresenter extends TiPresenter<SplashScreenView> {
    private final boolean isFirstRun;
    private static final long ANIMATION_DELAY = 3000;
    private static final long RUN_ACTIVITY_DELAY = 2000;
    private final RxTiPresenterSubscriptionHandler rxHelper = new RxTiPresenterSubscriptionHandler(this);

    public SplashScreenPresenter(boolean isFirstRun) {
        this.isFirstRun = isFirstRun;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        delaySplashAnimation(ANIMATION_DELAY);
    }

    public void delayRunActivity() {
        rxHelper.manageSubscription(Observable.timer(RUN_ACTIVITY_DELAY, TimeUnit.MILLISECONDS)
                .compose(RxTiPresenterUtils.<Long>deliverLatestToView(this))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        intentToNextActivity();
                    }
                })
        );
    }

    private void intentToNextActivity() {
        if (isFirstRun) {
            getView().intentToHowToUse();
        } else {
            getView().intentToMainMenu();
        }
    }

    private void delaySplashAnimation(long delay) {
        rxHelper.manageSubscription(Observable.timer(delay, TimeUnit.MILLISECONDS)
                .compose(RxTiPresenterUtils.<Long>deliverLatestToView(this))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        getView().startSplashAnimation();
                    }
                })
        );
    }
}
