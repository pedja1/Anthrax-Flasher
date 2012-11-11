package rs.pedjaapps.anthrax.flasher;

import android.app.*;
import android.content.*;
import android.content.res.*;
import android.os.*;
import android.preference.*;
import android.support.v4.app.*;
import android.util.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;
import java.io.*;
import java.util.*;
import java.util.regex.*;
import java.util.zip.*;

import java.lang.Process;

public class AnthraxFlasherHome extends Activity {

	private static final int PICKFILE_RESULT_CODE = 1;
	private static final int SETUPKERNEL_RESULT_CODE = 2;
	private TextView kernelFile;
	private String path;
	private Button pickKernel;
	private Button setupKernel;
	private Button flashKernel;

	private String cpuMin;
	private String cpuMax;
	private String cpuScrOff;
	private String cpuGov;
	private String scheduler;
	private String gpu2d;
	private String gpu3d;
	private int s2w;
	private String s2wStart;
	private String s2wEnd;
	private boolean vsync;
	private boolean mpdec;
	private boolean dalvik;

	SharedPreferences preferences;
	SharedPreferences.Editor editor;
	ProgressDialog pd;

	private String cmdline;
	String device = android.os.Build.DEVICE;
	String partition;
	
	boolean RESULT = true;
	String error;
	
	
	
	private class ExtractKernel extends AsyncTask<String, Void, Object> {

		@Override
		protected Object doInBackground(String... args) {
			try

			{

				File fSourceZip = new File(path);

				String zipPath = "/data/data/rs.pedjaapps.anthrax.flasher/files/temp";

				File temp = new File(zipPath);

				temp.mkdir();
				
				Runtime.getRuntime().exec("chmod 777 " + temp);

				System.out.println(zipPath + " created");

				ZipFile zipFile = new ZipFile(fSourceZip);

				Enumeration e = zipFile.entries();

				while (e.hasMoreElements())

				{

					ZipEntry entry = (ZipEntry) e.nextElement();

					File destinationFilePath = new File(zipPath, entry.getName());

					destinationFilePath.getParentFile().mkdirs();

				
					if (entry.isDirectory())

					{
						continue;
					}

					else

					{

						System.out.println("Extracting " + destinationFilePath);

						BufferedInputStream bis = new BufferedInputStream(zipFile

						.getInputStream(entry));

						int b;

						byte buffer[] = new byte[1024];

						FileOutputStream fos = new FileOutputStream(
								destinationFilePath);

						BufferedOutputStream bos = new BufferedOutputStream(fos,

						1024);

						while ((b = bis.read(buffer, 0, 1024)) != -1) {

							bos.write(buffer, 0, b);

						}

						bos.flush();

						bos.close();

						bis.close();

					}

				}

			}

			catch (IOException ioe)

			{

				System.out.println("IOError :" + ioe);

			}
			return "";
		}

		@Override
		protected void onPostExecute(Object result) {
			pd.dismiss();
			Toast.makeText(AnthraxFlasherHome.this,
					"kerne and modules extracted sucesfully",
					Toast.LENGTH_SHORT).show();
			if(new File("/data/data/rs.pedjaapps.anthrax.flasher/files/temp/kernel/"+android.os.Build.DEVICE+"/zImage").exists() && new File("/data/data/rs.pedjaapps.anthrax.flasher/files/temp/modules/"+android.os.Build.DEVICE).exists()){
				setupKernel.setEnabled(true);
				System.out.println(path);
				kernelFile.setText(path);
				
			}
			else{
				Toast.makeText(
						getApplicationContext(),
						"Archive doesnt contain kernel and modules",
						Toast.LENGTH_LONG).show();
				new ClearTemp().execute();
			}
		}
		@Override
		protected void onPreExecute(){
			pd = new ProgressDialog(AnthraxFlasherHome.this);
			pd.setMessage("Extracting kernel and modules to temp dir");
			pd.setCancelable(false);
			pd.setCanceledOnTouchOutside(false);
			pd.show();
		}

	}
	
	private class Reboot extends AsyncTask<Void, Integer, Void> {

		@Override
		protected Void doInBackground(Void... args) {

			Process localProcess;
			try
			{
				localProcess = Runtime.getRuntime().exec("su");

				DataOutputStream localDataOutputStream = new DataOutputStream(localProcess.getOutputStream());
			
				
				localDataOutputStream.writeBytes("/data/data/rs.pedjaapps.anthrax.flasher/files/reboot\n");

			

				localDataOutputStream.writeBytes("exit\n");
				localDataOutputStream.flush();
				localDataOutputStream.close();
				localProcess.waitFor();
				localProcess.destroy();
			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch (InterruptedException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}



			return null;
		}

	

	}
	
	private class FlashKernel extends AsyncTask<Void, Integer, Void> {

		@Override
		protected Void doInBackground(Void... args) {
			
			Process localProcess;
			try
			{
				localProcess = Runtime.getRuntime().exec("su");

				DataOutputStream localDataOutputStream = new DataOutputStream(localProcess.getOutputStream());
				//backup and remove thermald
				publishProgress(0);
				localDataOutputStream.writeBytes("mv /system/bin/thermald /system/bin/thermald.backup\n");
				localDataOutputStream.writeBytes("exit\n");
				localDataOutputStream.flush();
				localDataOutputStream.close();
				localProcess.waitFor();
				localProcess.destroy();
			}
			catch (IOException io)
			{
				// TODO Auto-generated catch block

				error = io.getMessage();
				RESULT=false;
			}
			catch (InterruptedException ie)
			{
				// TODO Auto-generated catch block
				error = ie.getMessage();
				RESULT=false;
			}
			try
			{
				localProcess = Runtime.getRuntime().exec("su");

				DataOutputStream localDataOutputStream = new DataOutputStream(localProcess.getOutputStream());
				
				//if selected backup remove mpdecision
				
				if(mpdec==true){
					publishProgress(1);
					localDataOutputStream.writeBytes("mv /system/bin/mpdecision /system/bin/mpdecision.backup\n");	
				}
				localDataOutputStream.writeBytes("exit\n");
				localDataOutputStream.flush();
				localDataOutputStream.close();
				localProcess.waitFor();
				localProcess.destroy();
			}
			catch (IOException io)
			{
				// TODO Auto-generated catch block

				error = io.getMessage();
				RESULT=false;
			}
			catch (InterruptedException ie)
			{
				// TODO Auto-generated catch block
				error = ie.getMessage();
				RESULT=false;
			}
			try
			{
				localProcess = Runtime.getRuntime().exec("su");

				DataOutputStream localDataOutputStream = new DataOutputStream(localProcess.getOutputStream());
				
				//pull boot.img
				publishProgress(2);
				localDataOutputStream.writeBytes("/data/data/rs.pedjaapps.anthrax.flasher/files/dd if="+partition+" of=/data/data/rs.pedjaapps.anthrax.flasher/files/temp/boot.img\n");
				localDataOutputStream.writeBytes("exit\n");
				localDataOutputStream.flush();
				localDataOutputStream.close();
				localProcess.waitFor();
				localProcess.destroy();

			}
			catch (IOException io)
			{
				// TODO Auto-generated catch block

				error = io.getMessage();
				RESULT=false;
			}
			catch (InterruptedException ie)
			{
				// TODO Auto-generated catch block
				error = ie.getMessage();
				RESULT=false;
			}
			try
			{
				localProcess = Runtime.getRuntime().exec("su");

				DataOutputStream localDataOutputStream = new DataOutputStream(localProcess.getOutputStream());
				
				//unpack boot.img
				publishProgress(3);
				localDataOutputStream.writeBytes("/data/data/rs.pedjaapps.anthrax.flasher/files/unpackbootimg /data/data/rs.pedjaapps.anthrax.flasher/files/temp/boot.img /data/data/rs.pedjaapps.anthrax.flasher/files/temp/\n");
				localDataOutputStream.writeBytes("exit\n");
				localDataOutputStream.flush();
				localDataOutputStream.close();
				localProcess.waitFor();
				localProcess.destroy();

			}
			catch (IOException io)
			{
				// TODO Auto-generated catch block

				error = io.getMessage();
				RESULT=false;
			}
			catch (InterruptedException ie)
			{
				// TODO Auto-generated catch block
				error = ie.getMessage();
				RESULT=false;
			}
			try
			{
				localProcess = Runtime.getRuntime().exec("su");

				DataOutputStream localDataOutputStream = new DataOutputStream(localProcess.getOutputStream());
				
				//set cmdline
				publishProgress(4);
				localDataOutputStream.writeBytes("sh /data/data/rs.pedjaapps.anthrax.flasher/files/cmdline.sh " + cpuMin + " " + cpuMax + " " + cpuScrOff + " " + gpu3d + " " + gpu2d + " " + cpuGov+"\n");
				localDataOutputStream.writeBytes("exit\n");
				localDataOutputStream.flush();
				localDataOutputStream.close();
				localProcess.waitFor();
				localProcess.destroy();

			}
			catch (IOException io)
			{
				// TODO Auto-generated catch block

				error = io.getMessage();
				RESULT=false;
			}
			catch (InterruptedException ie)
			{
				// TODO Auto-generated catch block
				error = ie.getMessage();
				RESULT=false;
			}
			try
			{
				localProcess = Runtime.getRuntime().exec("su");

				DataOutputStream localDataOutputStream = new DataOutputStream(localProcess.getOutputStream());
				
				//set s2w
				publishProgress(5);
				localDataOutputStream.writeBytes("sh /data/data/rs.pedjaapps.anthrax.flasher/files/sweep2wake.sh " + s2w + " " + s2wStart + " " + s2wEnd +"\n");
				localDataOutputStream.writeBytes("exit\n");
				localDataOutputStream.flush();
				localDataOutputStream.close();
				localProcess.waitFor();
				localProcess.destroy();

			}
			catch (IOException io)
			{
				// TODO Auto-generated catch block

				error = io.getMessage();
				RESULT=false;
			}
			catch (InterruptedException ie)
			{
				// TODO Auto-generated catch block
				error = ie.getMessage();
				RESULT=false;
			}
			try
			{
				localProcess = Runtime.getRuntime().exec("su");

				DataOutputStream localDataOutputStream = new DataOutputStream(localProcess.getOutputStream());
				
		    	//set scheduler
				publishProgress(6);
				localDataOutputStream.writeBytes("sh /data/data/rs.pedjaapps.anthrax.flasher/files/scheduler.sh " + scheduler+"\n");
				localDataOutputStream.writeBytes("exit\n");
				localDataOutputStream.flush();
				localDataOutputStream.close();
				localProcess.waitFor();
				localProcess.destroy();

			}
			catch (IOException io)
			{
				// TODO Auto-generated catch block

				error = io.getMessage();
				RESULT=false;
			}
			catch (InterruptedException ie)
			{
				// TODO Auto-generated catch block
				error = ie.getMessage();
				RESULT=false;
			}
			try
			{
				localProcess = Runtime.getRuntime().exec("su");

				DataOutputStream localDataOutputStream = new DataOutputStream(localProcess.getOutputStream());
				
				//set init.d
				publishProgress(7);
				int vs;
				int mp;
				if(vsync==true)
				{
					vs=1;
				}
				else{
					vs=0;
				}
				if(mpdec==true)
				{
					mp=1;
				}
				else{
					mp=0;
				}
				localDataOutputStream.writeBytes("sh /data/data/rs.pedjaapps.anthrax.flasher/files/features.sh " + vs + " " + mp + "\n");
				localDataOutputStream.writeBytes("exit\n");
				localDataOutputStream.flush();
				localDataOutputStream.close();
				localProcess.waitFor();
				localProcess.destroy();

			}
			catch (IOException io)
			{
				// TODO Auto-generated catch block

				error = io.getMessage();
				RESULT=false;
			}
			catch (InterruptedException ie)
			{
				// TODO Auto-generated catch block
				error = ie.getMessage();
				RESULT=false;
			}
			try
			{
				localProcess = Runtime.getRuntime().exec("su");

				DataOutputStream localDataOutputStream = new DataOutputStream(localProcess.getOutputStream());
				
			// make new boot.img
				publishProgress(8);
				localDataOutputStream.writeBytes("sh /data/data/rs.pedjaapps.anthrax.flasher/files/mkbootimg.sh " +device +"\n");
				localDataOutputStream.writeBytes("exit\n");
				localDataOutputStream.flush();
				localDataOutputStream.close();
				localProcess.waitFor();
				localProcess.destroy();
				
			}
			catch (IOException io)
			{
				// TODO Auto-generated catch block

				error = io.getMessage();
				RESULT=false;
				
			}
			catch (InterruptedException ie)
			{
				// TODO Auto-generated catch block
				error = ie.getMessage();
				RESULT=false;
				
			}
			try
			{
				localProcess = Runtime.getRuntime().exec("su");

				DataOutputStream localDataOutputStream = new DataOutputStream(localProcess.getOutputStream());
				
			//flash boot.img
				publishProgress(9);
				localDataOutputStream.writeBytes("/data/data/rs.pedjaapps.anthrax.flasher/files/flash_image " + partition + " " + "/data/data/rs.pedjaapps.anthrax.flasher/files/temp/newboot.img" + "\n");
				localDataOutputStream.writeBytes("exit\n");
				localDataOutputStream.flush();
				localDataOutputStream.close();
				localProcess.waitFor();
				localProcess.destroy();

			}
			catch (IOException io)
			{
				// TODO Auto-generated catch block

				error = io.getMessage();
				RESULT=false;
			}
			catch (InterruptedException ie)
			{
				// TODO Auto-generated catch block
				error = ie.getMessage();
				RESULT=false;
			}
			try
			{
				localProcess = Runtime.getRuntime().exec("su");

				DataOutputStream localDataOutputStream = new DataOutputStream(localProcess.getOutputStream());
				
			//delete old modules
				publishProgress(10);
				localDataOutputStream.writeBytes("rm /system/lib/modules/*\n");
				localDataOutputStream.writeBytes("exit\n");
				localDataOutputStream.flush();
				localDataOutputStream.close();
				localProcess.waitFor();
				localProcess.destroy();

			}
			catch (IOException io)
			{
				// TODO Auto-generated catch block

				error = io.getMessage();
				RESULT=false;
			}
			catch (InterruptedException ie)
			{
				// TODO Auto-generated catch block
				error = ie.getMessage();
				RESULT=false;
			}
			try
			{
				localProcess = Runtime.getRuntime().exec("su");

				DataOutputStream localDataOutputStream = new DataOutputStream(localProcess.getOutputStream());
				
			//push new modules
				publishProgress(11);
				localDataOutputStream.writeBytes("cp /data/data/rs.pedjaapps.anthrax.flasher/files/temp/modules/" +device+"/* /system/lib/modules/\n");
				localDataOutputStream.writeBytes("cp /data/data/rs.pedjaapps.anthrax.flasher/files/temp/modules/common/* /system/lib/modules/\n");
				localDataOutputStream.writeBytes("chmod 644 /system/lib/modules/*\n");
				localDataOutputStream.writeBytes("exit\n");
				localDataOutputStream.flush();
				localDataOutputStream.close();
				localProcess.waitFor();
				localProcess.destroy();

			}
			catch (IOException io)
			{
				// TODO Auto-generated catch block

				error = io.getMessage();
				RESULT=false;
			}
			catch (InterruptedException ie)
			{
				// TODO Auto-generated catch block
				error = ie.getMessage();
				RESULT=false;
			}
			try
			{
				localProcess = Runtime.getRuntime().exec("su");

				DataOutputStream localDataOutputStream = new DataOutputStream(localProcess.getOutputStream());
				
			//wipe dalvik cache if enabled
				if(dalvik==true){
				publishProgress(12);
				localDataOutputStream.writeBytes("rm /data/dalvik-cache/*\n");
				}
				
				localDataOutputStream.writeBytes("exit\n");
				localDataOutputStream.flush();
				localDataOutputStream.close();
				localProcess.waitFor();
				localProcess.destroy();
				
			}
			catch (IOException io)
			{
				// TODO Auto-generated catch block
				
				error = io.getMessage();
				RESULT=false;
			}
			catch (InterruptedException ie)
			{
				// TODO Auto-generated catch block
				error = ie.getMessage();
				RESULT=false;
			}
			try
			{
				localProcess = Runtime.getRuntime().exec("su");

				DataOutputStream localDataOutputStream = new DataOutputStream(localProcess.getOutputStream());

				//cleen temp dir
				
					publishProgress(13);
						localDataOutputStream.writeBytes("rm -r /data/data/rs.pedjaapps.anthrax.flasher/files/temp\n");
				

				localDataOutputStream.writeBytes("exit\n");
				localDataOutputStream.flush();
				localDataOutputStream.close();
				localProcess.waitFor();
				localProcess.destroy();

			}
			catch (IOException io)
			{
				// TODO Auto-generated catch block

				error = io.getMessage();
				RESULT=false;
			}
			catch (InterruptedException ie)
			{
				// TODO Auto-generated catch block
				error = ie.getMessage();
				RESULT=false;
			}
		//	RESULT=true;
			
			return null;
		}
		
		@Override
		protected void onProgressUpdate(Integer... values)
		{
			switch(values[0]){
			case 0:
				pd.setMessage("Backing up and removing thermald");
				pd.setProgress(0);
				break;
			case 1:
				pd.setMessage("Backing up and removing mpdecision");
				pd.setProgress(1);
				break;
			case 2:
				pd.setMessage("Pulling boot.img");
				pd.setProgress(2);
				break;
			case 3:
				pd.setMessage("Unpacking boot.img");
				pd.setProgress(3);
				break;
			case 4:
				pd.setMessage("Setting cmdline options");
				pd.setProgress(4);
				break;
			case 5:
				pd.setMessage("Setting s2w in cmdline");
				pd.setProgress(5);
				break;
			case 6:
				pd.setMessage("Setting scheduler in cmdline");
				pd.setProgress(6);
				break;
			case 7:
				pd.setMessage("Creating init.d script and setting options");
				pd.setProgress(7);
				break;
			case 8:
				pd.setMessage("Creating new boot.img");
				pd.setProgress(8);
				break;
			case 9:
				pd.setMessage("Flashing new boot.img");
				pd.setProgress(9);
				break;
			case 10:
				pd.setMessage("Deleting Old modules");
				pd.setProgress(10);
				break;
			case 11:
				pd.setMessage("Writing new modules");
				pd.setProgress(11);
				break;
			case 12:
				pd.setMessage("Wiping dalvik-cache");
				pd.setProgress(12);
				break;
			case 13:
				pd.setMessage("Cleaning up");
				pd.setProgress(13);
				break;
			}
			super.onProgressUpdate();
		}

		@Override
		protected void onPostExecute(Void res) {
			pd.dismiss();
			if(RESULT){
			AlertDialog.Builder builder = new AlertDialog.Builder(
				AnthraxFlasherHome.this);

				if(dalvik==true){
			builder.setMessage("Device will reboot now!");
					builder.setPositiveButton("OK",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								new Reboot().execute();
							}
						});
			}
			else{
				builder.setMessage("Reboot now!");
				builder.setPositiveButton("Reboot",
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							new Reboot().execute();
						}
					});
					
				builder.setNegativeButton("Dont Reboot",
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
						
						}
					});
			}
			
		
			
			AlertDialog alert = builder.create();
			alert.setCanceledOnTouchOutside(false);
			alert.setCancelable(false);

			alert.show();
			}
			else{
				AlertDialog.Builder builder = new AlertDialog.Builder(
					AnthraxFlasherHome.this);

					builder.setTitle("Flashing failed");
					builder.setMessage(error);
					builder.setPositiveButton("OK",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								finish();
							}
						});
				
				



				AlertDialog alert = builder.create();
				alert.setCanceledOnTouchOutside(false);
				alert.setCancelable(false);

				alert.show();
			}
		}
		@Override
		protected void onPreExecute(){
			pd = new ProgressDialog(AnthraxFlasherHome.this);
			pd.setMax(13);
			pd.setIndeterminate(false);
			pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			pd.setMessage("Preparing for flashing kernel");
			pd.setCancelable(false);
			pd.setCanceledOnTouchOutside(false);
			pd.show();
		}

	}
	private class ClearTemp extends AsyncTask<String, Void, Object> {

		@Override
		protected Object doInBackground(String... args) {
			try {
				Runtime.getRuntime().exec("rm -r /data/data/rs.pedjaapps.anthrax.flasher/files/temp");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return "";
		}

		@Override
		protected void onPostExecute(Object result) {
			
		}
		@Override
		protected void onPreExecute(){
			
		}

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
		checkPhone();
		setContentView(R.layout.anthrax_home);
		// getActionBar().setDisplayHomeAsUpEnabled(true);
		if(device.equals("shooteru") || device.equals("pyramid")){
			partition = "/dev/block/mmcblk0p20";
		}
		if(device.equals("shooter")){
			partition = "/dev/block/mmcblk0p21";
		}
		preferences = PreferenceManager.getDefaultSharedPreferences(this);
		editor = preferences.edit();
		kernelFile = (TextView) findViewById(R.id.textView2);
		pickKernel = (Button) findViewById(R.id.button1);
		setupKernel = (Button) findViewById(R.id.button2);
		flashKernel = (Button) findViewById(R.id.button3);
		setupKernel.setEnabled(false);
		flashKernel.setEnabled(false);

		pickKernel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent chooseFile;
				Intent intent;
				chooseFile = new Intent(Intent.ACTION_GET_CONTENT);
				chooseFile.setType("file/*");
				intent = Intent.createChooser(chooseFile, "Choose a file");
				startActivityForResult(intent, PICKFILE_RESULT_CODE);

			}

		});
		
		flashKernel.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
				new FlashKernel().execute();

				}

			});

		setupKernel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(AnthraxFlasherHome.this,
						AnthraxFlasherSettings.class);
				startActivityForResult(intent, SETUPKERNEL_RESULT_CODE);

			}

		});
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		switch (requestCode) {
		case PICKFILE_RESULT_CODE:
			if (resultCode == RESULT_OK) {

				path = data.getData().getPath();
				String ext = path.substring((path.lastIndexOf(".") + 1),
						path.length());
				if (ext.equals("zip")) {
					new ExtractKernel().execute();
					
				} else {
					Toast.makeText(
							getApplicationContext(),
							"Not zip archive. Please select zip file containing kernel and modules",
							Toast.LENGTH_LONG).show();
				}

			}
			break;
		case SETUPKERNEL_RESULT_CODE:
			if (resultCode == RESULT_OK) {
				//setupKernel.setEnabled(false);
				flashKernel.setEnabled(true);
				cpuMin = data.getStringExtra("cpuMin");
				cpuMax = data.getStringExtra("cpuMax");
				cpuScrOff = data.getStringExtra("cpuScrOff");
				cpuGov = data.getStringExtra("cpuGov");
				scheduler = data.getStringExtra("scheduler");
				gpu2d = data.getStringExtra("gpu2d");
				gpu3d = data.getStringExtra("gpu3d");
				s2w = data.getIntExtra("s2w", 2);
				s2wStart = data.getStringExtra("s2wStart");
				s2wEnd = data.getStringExtra("s2wEnd");
				vsync = data.getBooleanExtra("vsync", true);
				mpdec = data.getBooleanExtra("mpdec", true);
				dalvik = data.getBooleanExtra("dalvik", true);
			}
			break;

		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// getMenuInflater().inflate(R.menu.activity_anthrax_flasher, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}


	
	
	public void checkPhone() {
		
		String device = android.os.Build.DEVICE;
		if (device.equals("shooteru") || device.equals("shooter")
				|| device.equals("pyramid")) {
			AlertDialog.Builder builder = new AlertDialog.Builder(
					AnthraxFlasherHome.this);

			builder.setMessage("Device detected\n" + android.os.Build.MODEL
					+ "\nDont proceed if incorect!");

			builder.setPositiveButton("Proceed",
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							boolean first = preferences.getBoolean(
									"first_launch", false);
							if (first == false) {
								CopyAssets();
								Toast.makeText(getApplicationContext(),
										"Extracting Tools", Toast.LENGTH_LONG)
										.show();
							}
						}
					});
			builder.setNegativeButton("Exit",
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							AnthraxFlasherHome.this.finish();
						}
					});
			;
			AlertDialog alert = builder.create();
			alert.setCanceledOnTouchOutside(false);
			alert.setCancelable(false);

			alert.show();
		} else {
			AlertDialog.Builder builder = new AlertDialog.Builder(
					AnthraxFlasherHome.this);

			builder.setMessage("Device detected\n" + android.os.Build.MODEL
					+ "\nThis device isnt supported!");

			builder.setNegativeButton("Exit",
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							AnthraxFlasherHome.this.finish();
						}
					});

			AlertDialog alert = builder.create();
			alert.setCanceledOnTouchOutside(false);
			alert.setCancelable(false);
			alert.show();
		}
	}

	private void CopyAssets() {
		AssetManager assetManager = getAssets();
		String[] files = null;
		File file;
		try {
			files = assetManager.list("");
		} catch (IOException e) {
			Log.e("tag", e.getMessage());
		}
		for (String filename : files) {
			InputStream in = null;
			OutputStream out = null;
			try {
				in = assetManager.open(filename);
				file = new File(this.getFilesDir().getAbsolutePath() + "/"
						+ filename);
				out = new FileOutputStream(file);
				copyFile(in, out);
				in.close();
				Runtime.getRuntime().exec("chmod 755 " + file);
				file.setExecutable(false);
				in = null;
				out.flush();
				out.close();
				out = null;
			} catch (Exception e) {
				Log.e("tag", e.getMessage());
			}
		}

		editor.putBoolean("first_launch", true);
		editor.commit();
	}

	private void copyFile(InputStream in, OutputStream out) throws IOException {
		byte[] buffer = new byte[1024];
		int read;
		while ((read = in.read(buffer)) != -1) {
			out.write(buffer, 0, read);
		}
	}



}
