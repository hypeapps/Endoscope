package pl.hypeapp.endoscope;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.InputFilter;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import net.majorkernelpanic.streaming.rtsp.RtspServer;


public class SettingsActivity extends Activity {

    AlertDialog dialog;
    SharedPreferences sharedPreferences;
    boolean isAudioStream;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String port = sharedPreferences.getString(RtspServer.KEY_PORT, "8086");
        TextView portValueText = (TextView) findViewById(R.id.port_option);
        portValueText.setText(port);

        int videoEncoder = sharedPreferences.getInt("video_encoder", 0);
        TextView videoEncoderTextView = (TextView) findViewById(R.id.vide_encoder_option);
        videoEncoderTextView.setText(getResources().getTextArray(R.array.video_encoders)[videoEncoder]);

        int resolution = sharedPreferences.getInt("resolution", 2);
        TextView resolutionTextView = (TextView) findViewById(R.id.resolution_option);
        resolutionTextView.setText(getResources().getTextArray(R.array.resolutions)[resolution]);

        isAudioStream = sharedPreferences.getBoolean("is_audio_stream", false);
        setAudioStreamOption(isAudioStream);
    }

    public void setAudioStreamOption(boolean isAudioStream){
        TextView audioStreamTextView = (TextView) findViewById(R.id.stream_sound_option);
        if(isAudioStream){
            audioStreamTextView.setText(R.string.enabled);
        }else{
            audioStreamTextView.setText(R.string.off);
        }
    }

    public void setResolution(View v){
        showDialogSelectItem(getResources().getStringArray(R.array.resolutions), getString(R.string.resolution_dialog_title),
                sharedPreferences.getInt("resolution", 0), "resolution");
    }

    public void setAudioStream(View v){
        isAudioStream = !isAudioStream;
        setAudioStreamOption(isAudioStream);
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(this).edit();
        editor.putBoolean("is_audio_stream", isAudioStream);
        editor.commit();
    }

    public void setVideoEncoder(View v){
        showDialogSelectItem(getResources().getStringArray(R.array.video_encoders), getString(R.string.set_video_encoder_title),
                sharedPreferences.getInt("video_encoder", 0), "video_encoder");
    }

    public void setPort(View v){
        showDialogEditText(getString(R.string.set_port));
    }

    private void showDialogSelectItem(CharSequence[] items, String title, int selectedItem, final String option){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setSingleChoiceItems(items, selectedItem, null)
                .setPositiveButton(R.string.button_text, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.dismiss();
                        int selectedPosition = ((AlertDialog)dialog).getListView().getCheckedItemPosition();
                        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(SettingsActivity.this).edit();
                        if(option.equals("video_encoder")) {
                            TextView videoEncoderOption = (TextView) findViewById(R.id.vide_encoder_option);
                            videoEncoderOption.setText(getResources().getTextArray(R.array.video_encoders)[selectedPosition]);
                        }else if(option.equals("resolution")){
                            TextView resolutionOption = (TextView) findViewById(R.id.resolution_option);
                            resolutionOption.setText(getResources().getTextArray(R.array.resolutions)[selectedPosition]);
                        }
                        editor.putInt(option, selectedPosition);
                        editor.commit();
                    }
                });

        dialog = builder.create();
        dialog.show();

    }

    private void showDialogEditText(String title){
        final EditText editTextPort = new EditText(this);
        editTextPort.setInputType(InputType.TYPE_CLASS_NUMBER);
        int maxLength = 4;
        editTextPort.setFilters(new InputFilter[] {new InputFilter.LengthFilter(maxLength)});

        new AlertDialog.Builder(this)
                .setTitle(title)
                .setView(editTextPort)
                .setPositiveButton(title, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String port = editTextPort.getText().toString();
                        changePort(Integer.valueOf(port));
                        TextView portValueText = (TextView) findViewById(R.id.port_option);
                        portValueText.setText(port);

                        Toast.makeText(SettingsActivity.this , R.string.port_changed, Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton(R.string.cancel , new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    }
                })
                .show();
    }

    private void changePort(int port){
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(this).edit();
        editor.putString(RtspServer.KEY_PORT, String.valueOf(port));
        editor.commit();
    }
}
