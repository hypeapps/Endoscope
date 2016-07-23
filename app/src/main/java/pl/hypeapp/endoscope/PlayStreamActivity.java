package pl.hypeapp.endoscope;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;

import net.majorkernelpanic.streaming.rtsp.RtspServer;

import java.io.IOException;


public class PlayStreamActivity extends Activity implements SurfaceHolder.Callback, MediaPlayer.OnPreparedListener {

    private MediaPlayer mediaPlayer;
    private SurfaceView surfaceView;
    private SurfaceHolder surfaceHolder;
    private String ipAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_stream);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        surfaceView = (SurfaceView) findViewById(R.id.surface_play);
        surfaceHolder = surfaceView.getHolder();
        surfaceHolder.addCallback(this);

        Intent i = getIntent();
        ipAddress = i.getStringExtra("ip_connect");
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        play(ipAddress);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {}

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        surfaceHolder.removeCallback(this);
        mediaPlayer.release();
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mp.start();
    }

    private void play(String ipAddress) {
        if(mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();
        }
        setErrorListener();
        mediaPlayer.setDisplay(surfaceHolder);
        mediaPlayer.setOnPreparedListener(this);
        try {
            String videoUri = "rtsp://" + ipAddress + ":" + getPort() + "/";
            mediaPlayer.setDataSource(videoUri);
            mediaPlayer.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getPort(){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        return sharedPreferences.getString(RtspServer.KEY_PORT, "8086");
    }

    private void setErrorListener() {
        mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                if(extra == MediaPlayer.MEDIA_ERROR_IO) {
                    logError("MEDIA ERROR");
                }else if(extra == MediaPlayer.MEDIA_ERROR_SERVER_DIED) {
                    logError("SERVER DIED ERROR");
                }else if(extra == MediaPlayer.MEDIA_ERROR_UNSUPPORTED) {
                    logError("MEDIA UNSUPPORTED");
                }else if(extra == MediaPlayer.MEDIA_ERROR_UNKNOWN) {
                    logError("MEDIA ERROR UNKNOWN");
                }else if(extra == MediaPlayer.MEDIA_ERROR_NOT_VALID_FOR_PROGRESSIVE_PLAYBACK) {
                    logError("NOT VALID PROGRESSIVE PLAYBACK");
                }else if(extra == MediaPlayer.MEDIA_ERROR_TIMED_OUT){
                    logError("MEDIA ERROR TIMED OUT");
                }else {
                    logError("ERROR UNKNOWN (" + what + ")" + "(" + extra + ")");
                }
                return false;
            }
        });
    }

    private void logError(final String msg) {
        final String error = (msg == null) ? "Error unknown" : msg;
        AlertDialog.Builder builder = new AlertDialog.Builder(PlayStreamActivity.this);
        builder.setTitle("Error");
        builder.setMessage(error).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Intent intent = new Intent(PlayStreamActivity.this, MainMenuActivity.class);
                startActivity(intent);
            }
        });
        builder.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
                if (i == KeyEvent.KEYCODE_BACK) {
                    Intent intent = new Intent(PlayStreamActivity.this, MainMenuActivity.class);
                    startActivity(intent);
                }
                return false;
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

}
