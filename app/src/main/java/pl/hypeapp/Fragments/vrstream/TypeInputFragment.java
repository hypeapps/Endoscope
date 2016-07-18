package pl.hypeapp.Fragments.vrstream;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import pl.hypeapp.vrstream.ConnectToStreamActivity;
import pl.hypeapp.vrstream.R;


public class TypeInputFragment extends Fragment implements View.OnClickListener{


    EditText editTextIpAddress;
    Button buttonConnect;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.type_input_fragment, container, false);

        editTextIpAddress = (EditText) v.findViewById(R.id.ip_edit_text);
        int maxLength = 15;
        editTextIpAddress.setFilters(new InputFilter[] {new InputFilter.LengthFilter(maxLength)});
        buttonConnect = (Button) v.findViewById(R.id.connect_button);
        buttonConnect.setOnClickListener(this);

        return v;
    }


    @Override
    public void onClick(View view) {
        String ipAddress =  editTextIpAddress.getText().toString();
        ConnectToStreamActivity connectToStreamActivity = (ConnectToStreamActivity) getActivity();
        connectToStreamActivity.connectStream(ipAddress);
    }
}
