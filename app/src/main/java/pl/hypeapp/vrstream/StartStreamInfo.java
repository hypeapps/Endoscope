package pl.hypeapp.vrstream;


import android.app.Activity;
import android.content.Context;
import android.view.View;

public class StartStreamInfo {

    private Context context;
    private View startStreamActivity;

    public StartStreamInfo(Context context, View activity){
        this.context = context;
        this.startStreamActivity = activity;
    }

    public void CoverAboutConnectionLayout(){
        View aboutConnectionLayout =  startStreamActivity.findViewById(R.id.about_connection);
        aboutConnectionLayout.setVisibility(View.GONE);
    }

    public void ShowAboutConnectionLayout(){
        View aboutConnectionLayout =  startStreamActivity.findViewById(R.id.about_connection);
        aboutConnectionLayout.setVisibility(View.VISIBLE);
    }
}
