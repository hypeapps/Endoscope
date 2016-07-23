package pl.hypeapp.endoscope;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.view.KeyEvent;


public class WiFiStateChangeReceiver extends BroadcastReceiver {

    AlertDialog alertDialog;
    final String PACKAGE_NAME = "pl.hypeapp.endoscope";
    final String START_STREAM_ACTIVITY_CLASS_NAME = "pl.hypeapp.endoscope.StartStreamActivity";

    @Override
    public void onReceive(Context context, Intent intent) {

        NetworkInfo wifiInfo = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);

        int extraWifiState = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE ,
                WifiManager.WIFI_STATE_UNKNOWN);

        if(isWifiDisabled(extraWifiState)){
            alertDialog = newWiFiAlertDialog(context);
            alertDialog.show();
        }

        if (wifiInfo != null) {
            if ((isWifiConnected(wifiInfo)) && (isAlertDialogShowing())) {
                destroyAlertDialog();
                    if (isStartStreamActivityClass(context)) {
                        restartStartStreamActivity(context);
                    }
            }
        }

    }

    private boolean isWifiDisabled(int extraWifiState) {
        return extraWifiState == WifiManager.WIFI_STATE_DISABLED;
    }

    private boolean isAlertDialogShowing() {
        return (alertDialog != null) && (alertDialog.isShowing());
    }

    private boolean isWifiConnected(NetworkInfo wifiInfo) {
        return (wifiInfo.isConnected()) && (wifiInfo.getType() == ConnectivityManager.TYPE_WIFI);
    }


    private void restartStartStreamActivity(Context context) {
        Intent startStreamRestart = new Intent();
        startStreamRestart.setClassName(PACKAGE_NAME , START_STREAM_ACTIVITY_CLASS_NAME);
        startStreamRestart.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(startStreamRestart);
    }

    private boolean isStartStreamActivityClass(Context context) {
        return context.getClass() == StartStreamActivity.class;
    }

    private void destroyAlertDialog() {
        alertDialog.dismiss();
        alertDialog = null;
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
        intent.setClassName("pl.hypeapp.endoscope", "pl.hypeapp.endoscope.MainMenuActivity");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }

}
