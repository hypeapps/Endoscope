package pl.hypeapp.endoscope.receiver;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.view.KeyEvent;

import pl.hypeapp.endoscope.R;
import pl.hypeapp.endoscope.ui.activity.MainMenuActivity;
import pl.hypeapp.endoscope.ui.activity.StartStreamActivity;


public class WiFiStateChangeReceiver extends BroadcastReceiver {
    private AlertDialog alertDialog;

    @Override
    public void onReceive(Context context, Intent intent) {
        NetworkInfo wifiInfo = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
        int extraWifiState = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE,
                WifiManager.WIFI_STATE_UNKNOWN);

        if (isWifiDisabled(extraWifiState)) {
            alertDialog = newWiFiAlertDialog(context);
            alertDialog.show();
        }

        if (wifiInfo != null) {
            if ((isWifiConnected(wifiInfo)) && (isAlertDialogShowing())) {
                destroyAlertDialog();
                if (isStartStreamActivityClass(context)) {
                    intentToMainMenu(context);
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

    //Restart activity is not possible because old activity keep rtsp port.
    @Deprecated
    private void restartStartStreamActivity(Context context) {
        Intent startStreamRestart = new Intent(context, StartStreamActivity.class);
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

    private AlertDialog newWiFiAlertDialog(final Context context) {
        return new AlertDialog.Builder(context)
                .setTitle(context.getString(R.string.wi_fi_disabled))
                .setIcon(R.drawable.ic_signal_wifi_off_black_24dp)
                .setCancelable(false)
                .setPositiveButton(context.getString(R.string.receiver_back_to_menu), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        intentToMainMenu(context);
                    }
                })
                .setOnKeyListener(new DialogInterface.OnKeyListener() {
                    @Override
                    public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
                        if (i == KeyEvent.KEYCODE_BACK) {
                            dialogInterface.dismiss();
                            intentToMainMenu(context);
                        }
                        return false;
                    }
                }).create();
    }

    private void intentToMainMenu(Context context) {
        Intent intent = new Intent(context, MainMenuActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }
}
