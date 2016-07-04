//package pl.hypeapp.vrstream;
//
//import android.app.Activity;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.net.wifi.WifiManager;
//import android.preference.PreferenceManager;
//import android.support.annotation.IntegerRes;
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.SurfaceHolder;
//import android.view.View;
//import android.view.Window;
//import android.view.WindowManager;
//import android.widget.ProgressBar;
//import android.widget.TextView;
//
//import net.majorkernelpanic.streaming.Session;
//import net.majorkernelpanic.streaming.SessionBuilder;
//import net.majorkernelpanic.streaming.audio.AudioQuality;
//import net.majorkernelpanic.streaming.gl.SurfaceView;
//import net.majorkernelpanic.streaming.rtsp.RtspClient;
//import net.majorkernelpanic.streaming.rtsp.RtspServer;
//import net.majorkernelpanic.streaming.video.VideoQuality;
//
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//
//public class ViewStreamActivity extends Activity
//        implements
//        RtspClient.Callback,
//        Session.Callback,
//        SurfaceHolder.Callback, RtspServer.CallbackListener
//{
//
//    String TAG = "SzynaGada";
//
//
//
//
//    private SurfaceView surfaceView;
//    private TextView textBitrate;
//    //    private EditText mEditTextURI;
////    private EditText mEditTextPassword;
////    private EditText mEditTextUsername;
//    private Session session;
//    private RtspClient client;
//    private RtspServer rtspServer;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_view_stream);
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
////        requestWindowFeature(Window.FEATURE_NO_TITLE);
//
//        WifiManager wifiManager = (WifiManager) getSystemService(WIFI_SERVICE);
//        int ipAddress = wifiManager.getConnectionInfo().getIpAddress();
//        Log.i(TAG, "ip address " + String.format("%d.%d.%d.%d", (ipAddress & 0xff), (ipAddress >> 8 & 0xff),
//                (ipAddress >> 16 & 0xff), (ipAddress >> 24 & 0xff)));
//
//        surfaceView = (SurfaceView) findViewById(R.id.surfacePlay);
//        textBitrate = (TextView) findViewById(R.id.bitrate);
//
//
//        session = SessionBuilder.getInstance()
//                .setContext(getApplicationContext())
//                .setAudioEncoder(SessionBuilder.AUDIO_AAC)
//                .setAudioQuality(new AudioQuality(8000,16000))
//                .setVideoEncoder(SessionBuilder.VIDEO_H264)
//                .setVideoQuality(new VideoQuality(320,240,20,500000))
//                .setSurfaceView(surfaceView)
//                .setPreviewOrientation(90)
//                .setCallback(this)
//                .build();
//
//        client = new RtspClient();
//        client.setSession(session);
//        client.setCallback(this);
//
//        surfaceView.getHolder().addCallback(this);
//
//
//
////        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(this).edit();
////        editor.putString(RtspServer.KEY_PORT, String.valueOf(8080));
////        editor.commit();
//
//        rtspServer = new RtspServer();
////        rtspServer.setPort(8080);
//        rtspServer.start();
////        rtspServer.setAuthorization("p", "123");
//
////        this.startService(new Intent(this,RtspServer.class));
//
//
//        toggleStream();
//
//    }
//
//    public void toggleStream() {
//        if (!client.isStreaming()) {
//            String ip,port,path;
//
//            // We save the content user inputs in Shared Preferences
////            SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(ViewStreamActivity.this);
////            SharedPreferences.Editor editor = mPrefs.edit();
//////            editor.putString("uri", mEditTextURI.getText().toString());
////            editor.putString("password", "123");
////            editor.putString("username", "p");
////            editor.commit();
//
//            // We parse the URI written in the Editext
////            Pattern uri = Pattern.compile("rtsp://(.+):(\\d*)/(.+)");
////            Matcher m = uri.matcher(mEditTextURI.getText()); m.find();
////            ip = m.group(1);
////            port = m.group(2);
////            path = m.group(3);
//
////            client.setCredentials("p", "123");
//            client.setServerAddress("192.168.1.3", Integer.parseInt("8086"));
//            client.setSession(session);
//            client.setStreamPath("/");
//            client.startStream();
//
//        } else {
//            // Stops the stream and disconnects from the RTSP server
//            client.stopStream();
//        }
//    }
//
//    @Override
//    public void onDestroy(){
//        super.onDestroy();
//        client.release();
//        session.release();
//        surfaceView.getHolder().removeCallback(this);
//        this.stopService(new Intent(this,RtspServer.class));
//    }
//
//    @Override
//    public void onBitrateUpdate(long bitrate) {
//        textBitrate.setText(""+bitrate/1000+" kbps");
//    }
//
//    @Override
//    public void onSessionError(int reason, int streamType, Exception e) {
//        Log.e(TAG, "An error occured " + e.getMessage());
//    }
//
//    @Override
//    public void onPreviewStarted() {
//        Log.e(TAG, "Preview Started");
//    }
//
//    @Override
//    public void onSessionConfigured() {
//        Log.e(TAG, "Session Configured");
//    }
//
//    @Override
//    public void onSessionStarted() {
//        Log.e(TAG, "Session Stared");
//    }
//
//    @Override
//    public void onSessionStopped() {
//        Log.e(TAG, "Session Stopped");
//    }
//
//    @Override
//    public void surfaceCreated(SurfaceHolder holder) {
//        session.startPreview();
//    }
//
//    @Override
//    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
//
//    }
//
//    @Override
//    public void surfaceDestroyed(SurfaceHolder holder) {
//        client.stopStream();
//    }
//
//    @Override
//    public void onRtspUpdate(int message, Exception exception) {
//        Log.e(TAG, " RTSPupdate " + exception.getMessage());
//    }
//
//    @Override
//    public void onError(RtspServer server, Exception e, int error) {
//        Log.e(TAG, "RTSP_ERROR " + e.getMessage());
//    }
//
//    @Override
//    public void onMessage(RtspServer server, int message) {
//        Log.e(TAG, " RTSP_ONMESSEAGE " + Integer.toString(message));
//    }
//}
