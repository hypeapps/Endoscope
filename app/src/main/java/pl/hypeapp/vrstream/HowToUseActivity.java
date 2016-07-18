package pl.hypeapp.vrstream;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


public class HowToUseActivity extends AppCompatActivity {

    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_how_to_use);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        viewPager = (ViewPager)findViewById(R.id.how_to_use_pager);
        HowToUsePagerAdapter pagerAdapter = new HowToUsePagerAdapter(getSupportFragmentManager());

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                PagerCirclesManager.dotStatusManage(position, getActivity());
                viewDoneButton(position);
                viewSkipButton(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewPager.setAdapter(pagerAdapter);
    }





    void viewDoneButton(int position) {
        ImageView nextButton = (ImageView) findViewById(R.id.next_page_button);
        TextView doneButton = (TextView) findViewById(R.id.done_button);
        if ((nextButton != null) && (doneButton != null)) {
            if (position < 2) {
                nextButton.setVisibility(View.VISIBLE);
                doneButton.setVisibility(View.GONE);

            }else {
                nextButton.setVisibility(View.GONE);
                doneButton.setVisibility(View.VISIBLE);
            }
        }
    }

    void viewSkipButton(int position){
        TextView skipButton = (TextView) findViewById(R.id.skip_button);
        if(skipButton != null){
            if(position > 0){
                skipButton.setVisibility(View.GONE);
            }else{
                skipButton.setVisibility(View.VISIBLE);
            }
        }
    }

    public void nextPage(View v){
        int position = viewPager.getCurrentItem();
        viewPager.setCurrentItem(position + 1);
    }

    public void skipPages(View v){
        viewPager.setCurrentItem(2);
    }

    public void donePages(View v){
        Intent i = new Intent(HowToUseActivity.this, MainMenuActivity.class);
        startActivity(i);
    }

    public Activity getActivity(){
        return this;
    }

}
