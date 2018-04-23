package pl.hypeapp.endoscope.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pl.hypeapp.endoscope.R;
import pl.hypeapp.endoscope.adapter.HowToUsePagerAdapter;
import pl.hypeapp.endoscope.ui.listener.OnDotPageChangeListener;

public class HowToUseActivity extends AppCompatActivity {
    public static final int LAST_PAGE = 3;
    private ViewPager viewPager;
    @BindView(R.id.next_page_button) ImageView nextPageButton;
    @BindView(R.id.skip_button) TextView skipButton;
    @BindView(R.id.done_button) TextView doneButton;
    @BindViews({R.id.circle_page1, R.id.circle_page2, R.id.circle_page3, R.id.circle_page4}) List<ImageView> dots;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_how_to_use);
        ButterKnife.bind(this);
        initViewPager();
    }

    private void initViewPager() {
        viewPager = (ViewPager) findViewById(R.id.how_to_use_pager);
        HowToUsePagerAdapter pagerAdapter = new HowToUsePagerAdapter(getSupportFragmentManager());
        viewPager.addOnPageChangeListener(new OnDotPageChangeListener(dots, nextPageButton, skipButton, doneButton));
        viewPager.setAdapter(pagerAdapter);
    }

    @OnClick(R.id.next_page_button)
    public void nextPage() {
        int position = viewPager.getCurrentItem();
        viewPager.setCurrentItem(position + 1);
    }

    @OnClick(R.id.skip_button)
    public void skipPages() {
        viewPager.setCurrentItem(LAST_PAGE);
    }

    @OnClick(R.id.done_button)
    public void done() {
        Intent i = new Intent(HowToUseActivity.this, MainMenuActivity.class);
        startActivity(i);
    }
}
