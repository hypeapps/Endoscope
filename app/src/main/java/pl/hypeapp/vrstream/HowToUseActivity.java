package pl.hypeapp.vrstream;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import pl.hypeapp.HowToUseFragments.vrstream.HowToUsePagerAdapter;


public class HowToUseActivity extends AppCompatActivity {

    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_how_to_use);

        viewPager = (ViewPager)findViewById(R.id.how_to_use_pager);
        HowToUsePagerAdapter pagerAdapter = new HowToUsePagerAdapter(getSupportFragmentManager());

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                dotStatusManage(position);
                viewDoneButton(position);
                viewSkipButton(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewPager.setAdapter(pagerAdapter);
    }

    void dotStatusManage(int position){
        ImageView dotOne = (ImageView) findViewById(R.id.circle_page1);
        ImageView dotTwo = (ImageView) findViewById(R.id.circle_page2);
        ImageView dotThird = (ImageView) findViewById(R.id.circle_page3);
        switch(position){
            case 0:
                setActiveDot(dotOne);
                setInactiveDot(dotTwo);
                setInactiveDot(dotThird);
                break;
            case 1:
                setInactiveDot(dotOne);
                setActiveDot(dotTwo);
                setInactiveDot(dotThird);
                break;
            case 2:
                setInactiveDot(dotOne);
                setInactiveDot(dotTwo);
                setActiveDot(dotThird);
                break;
        }
    }

    void setActiveDot(ImageView dot){
        if(dot != null) dot.setImageResource(R.drawable.circle_shape_active);
    }

    void setInactiveDot(ImageView dot){
        if(dot != null) dot.setImageResource(R.drawable.circle_shape_inactive);
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

    void viewSkipButton(int postion){
        TextView skipButton = (TextView) findViewById(R.id.skip_button);
        if(skipButton != null){
            if(postion > 0){
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

}
