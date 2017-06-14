package com.bbpos.bbdevice.example;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alodiga.security.encryption.S3cur1ty3Cryt3r;

/**
 * Created by usuario on 13/06/17.
 */

public class MailCIFragment extends Fragment{

    View rootView =null;
    String cid = null;
    String email = null;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        super.onCreateView(inflater, container, savedInstanceState);
        rootView = inflater.inflate(R.layout.fragment_mailci,container,false);
        final Button confirmButton = (Button) rootView.findViewById(R.id.confirmButton);
        final Button cancelButton = (Button) rootView.findViewById(R.id.cancelButton);
        final String key = getArguments().getString("key");
        final String deviceId = getArguments().getString("deviceId");

        EditText checkEmail = (EditText) rootView.findViewById(R.id.Cid);

        final BaseActivity activity = (BaseActivity) getActivity();


/*		final String NAMESPACE = "http://services.ws.acquiring.alodiga.com/";
		final String METHOD_NAME = "getMailByDocumentno";
		final String URL = "http://ec2-35-167-158-127.us-west-2.compute.amazonaws.com:8080/UtilmailsWS/UtilEmailsWS";
		final String SOAP_ACTION ="";*/



        checkEmail.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) { }
            @Override public void afterTextChanged(Editable s) {
                if(s.length()>=8){
                    cid=((EditText) rootView.findViewById(R.id.Cid)).getText().toString();

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try
                            {

                                try {
                                    final String  deEvice = Utils.sha256(deviceId);
                                    final String number1 = S3cur1ty3Cryt3r.aloEncrypter((cid),key,null);
                                    final String response = WebService.invokeGetAutoConfigString(number1,deEvice,"getMailByDocumentno","http://ec2-35-167-158-127.us-west-2.compute.amazonaws.com:8080/UtilmailsWS/UtilEmailsWS");



                                    getActivity().runOnUiThread(new Runnable() {
                                        public void run() {
                                            String[] auth = response.split(",");

                                            if (Integer.valueOf(auth[0].toString().trim()).equals(0)) {
                                                email = auth[2].toString();
                                                ((EditText) rootView.findViewById(R.id.emailEditText)).setText(email, TextView.BufferType.EDITABLE);

                                            }
                                        }
                                    });



                                    //	String response = S3cur1ty3Cryt3r.aloDesencrypter(value, key, null);

                                }catch (Exception e){

                                    e.printStackTrace();
                                    e.getMessage();
                                }



                            }



                            catch (Exception e) {

                                e.printStackTrace();
                                e.getMessage();
                            }
                        }}).start();
                }
            }
        });

        rootView.findViewById(R.id.confirmButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = ((EditText) rootView.findViewById(R.id.emailEditText)).getText().toString();
                cid= ((EditText) rootView.findViewById(R.id.Cid)).getText().toString();
                if((email.equals("")||cid.equals(""))){
                    Toast.makeText(getActivity(), getString(R.string.Field_empty), Toast.LENGTH_SHORT).show();
                    return;
                }
                activity.promptForStartEmv(cid, email);

            }
        });
        rootView.findViewById(R.id.cancelButton).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                activity.statusEditText.setText("");
                activity.stopConnection();
            }
        });


    return rootView;
    }
}
