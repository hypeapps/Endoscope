package pl.hypeapp.vrstream;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;


import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;


public class WiFiStateChangeReceiver extends BroadcastReceiver {

    AlertDialog alertDialog;
    @Override
    public void onReceive(Context context, Intent intent) {
        int extraWifiState = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE ,
                WifiManager.WIFI_STATE_UNKNOWN);
        Log.i("WIFI_STATE", "" + extraWifiState);

        switch(extraWifiState){
            case WifiManager.WIFI_STATE_ENABLED:
                if((alertDialog != null) && (alertDialog.isShowing())){
                    alertDialog.dismiss();
                    if(context.getClass() == StartStreamActivity.class){
                        Log.i("HALO", "HANS");

                        ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                        NetworkInfo wifi = connManager.getActiveNetworkInfo();
                        if (wifi.isConnected()) {
                            Intent startStreamRestart = new Intent();
                            startStreamRestart.setClassName("pl.hypeapp.vrstream", "pl.hypeapp.vrstream.StartStreamActivity");
                            startStreamRestart.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            context.startActivity(startStreamRestart);
                        }
                    }
                }
                break;
            case WifiManager.WIFI_STATE_DISABLED:
                alertDialog = newWiFiAlertDialog(context);
                alertDialog.show();
                break;
        }

    }

    private AlertDialog newWiFiAlertDialog(final Context context){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(context.getString(R.string.wi_fi_disabled));
        builder.setIcon(R.drawable.ic_signal_wifi_off_black_24dp);
        builder.setPositiveButton("BACK TO MAIN MENU", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                intentToMainMenu(context);
            }
        });
        builder.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
                if (i == KeyEvent.KEYCODE_BACK) {
                    intentToMainMenu(context);
                }
                return false;
            }
        });
        AlertDialog alertDialog = builder.create();
        return alertDialog;
    }

    private void intentToMainMenu(Context context){
        Intent intent = new Intent();
        intent.setClassName("pl.hypeapp.vrstream", "pl.hypeapp.vrstream.MainMenuActivity");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }

}
