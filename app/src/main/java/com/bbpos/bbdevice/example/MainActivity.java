package com.bbpos.bbdevice.example;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import java.util.Hashtable;
import java.util.Locale;
import java.util.Set;

import com.bbpos.bbdevice.BBDeviceController;
import com.bbpos.bbdevice.BBDeviceController.CheckCardMode;
import com.bbpos.bbdevice.BBDeviceController.EmvOption;


import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.content.pm.ActivityInfo;

public class MainActivity extends BaseActivity {

	protected static String webAutoConfigString = "";
	protected static boolean isLoadedLocalSettingFile = false;
	protected static boolean isLoadedWebServiceAutoConfig = false;




	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.activity_main);


		//((TextView) findViewById(R.id.modelTextView)).setText(Build.MANUFACTURER.toUpperCase(Locale.ENGLISH) + " - " + Build.MODEL + " (Android " + Build.VERSION.RELEASE + ")");

		fidSpinner = (Spinner) findViewById(R.id.fidSpinner);
		//startButton = (Button) findViewById(R.id.startButton);
		amountEditText = (TextView) findViewById(R.id.amountEditText);
		statusEditText = (TextView) findViewById(R.id.statusEditText);



		String[] fids = new String[] { "FID22", "FID36", "FID46", "FID54", "FID55", "FID60", "FID61", "FID64", "FID65", };
		fidSpinner.setAdapter(new ArrayAdapter<String>(this, R.layout.my_spinner_item, fids));
		fidSpinner.setSelection(5);
		fidSpinner.setVisibility(View.INVISIBLE);

		currentActivity = this;
		
		try {
			String filename = "settings.txt";
			String inputDirectory = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Android/data/com.bbpos.bbdevice.ui/";
			
			FileInputStream fis = new FileInputStream(inputDirectory + filename);
			byte[] temp = new byte[fis.available()];
			fis.read(temp);
			fis.close();
			
			isLoadedLocalSettingFile = true;
			bbDeviceController.setAudioAutoConfig(new String(temp));
			
			new Handler().post(new Runnable() {
				@Override
				public void run() {
					Toast.makeText(currentActivity, getString(R.string.setting_config), Toast.LENGTH_LONG).show();
				}
			});
		} catch(Exception e) {
		}
		
		//Create instance for AsyncCallWS
        AsyncCallWS task = new AsyncCallWS();
        //Call execute 
        task.execute();
	}

/*	class MyOnClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			statusEditText.setText("");

			if (v == startButton) {
				isPinCanceled = false;
				amountEditText.setText("");

				statusEditText.setText(R.string.starting);
				BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
				Set<BluetoothDevice> pairedDevice = bluetoothAdapter.getBondedDevices();
				if(pairedDevice.size()>0) {
					for (BluetoothDevice device : pairedDevice) {

						if (device.getAddress().toString().trim().equals("2F:11:2F:CF:0F:DC")) {

							bbDeviceController.connectBT(device);
						}

						try {
							promptForEmail();
						} catch (Exception e) {
							e.printStackTrace();
						}
						//  promptForStartEmv();
						//promptForCheckCard();
					}
				}
			}
		}
	}*/
	
	private class AsyncCallWS extends AsyncTask<String, Void, Void> {
		@Override
		protected Void doInBackground(String... params) {
			if (isLoadedWebServiceAutoConfig == false) {
			//	webAutoConfigString = WebService.invokeGetAutoConfigString(Build.MANUFACTURER.toUpperCase(Locale.US), Build.MODEL.toUpperCase(Locale.US), BBDeviceController.getApiVersion(), "getAutoConfigString");
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			if (isLoadedWebServiceAutoConfig == false) {
				isLoadedWebServiceAutoConfig = true;
				if (isLoadedLocalSettingFile == false) {
					if (!webAutoConfigString.equalsIgnoreCase("Error occured") && !webAutoConfigString.equalsIgnoreCase("")) {
						bbDeviceController.setAudioAutoConfig(webAutoConfigString);
						
						try {
							String filename = "settings.txt";
							String outputDirectory = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Android/data/com.bbpos.emvswipe.ui/";

							File directory = new File(outputDirectory);
							if (!directory.isDirectory()) {
								directory.mkdirs();
							}
							FileOutputStream fos = new FileOutputStream(outputDirectory + filename, true);
							fos.write(webAutoConfigString.getBytes());
							fos.flush();
							fos.close();
						} catch (Exception e) {
						}
						
						new Handler().post(new Runnable() {
							@Override
							public void run() {
								Toast.makeText(currentActivity, getString(R.string.setting_config_from_web_service), Toast.LENGTH_LONG).show();
							}
						});
					}
				}
			}
		}

		@Override
		protected void onPreExecute() {
		}

		@Override
		protected void onProgressUpdate(Void... values) {
		}

	}
}
