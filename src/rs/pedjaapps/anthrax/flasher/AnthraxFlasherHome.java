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
import java.util.zip.*;

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
	private boolean reboot;
	
	SharedPreferences preferences;
	SharedPreferences.Editor editor;
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		checkPhone();
		setContentView(R.layout.anthrax_home);
		// getActionBar().setDisplayHomeAsUpEnabled(true);
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
		
		setupKernel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
		        intent.setClass(AnthraxFlasherHome.this,AnthraxFlasherSettings.class);
		        startActivityForResult(intent,SETUPKERNEL_RESULT_CODE);

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
				String ext = path.substring((path.lastIndexOf(".") + 1), path.length());
			if(ext.equals("zip")){
				setupKernel.setEnabled(true);
				pickKernel.setEnabled(false);
				System.out.println(path);
				kernelFile.setText(path);
			readZip();
			}
			else{
				Toast.makeText(getApplicationContext(), "Not zip archive. Please select zip file containing kernel and modules", Toast.LENGTH_LONG).show();
			}
			
			}
			break;
		case SETUPKERNEL_RESULT_CODE:
			if (resultCode == RESULT_OK) {
				setupKernel.setEnabled(false);
				flashKernel.setEnabled(true);
				cpuMin = data.getStringExtra("cpuMin");
				cpuMax = data.getStringExtra("cpuMax");
				cpuScrOff = data.getStringExtra("cpuScrOff");
				cpuGov = data.getStringExtra("cpuGov");
				scheduler  = data.getStringExtra("scheduler");
				gpu2d = data.getStringExtra("gpu2d");
				gpu3d = data.getStringExtra("gpu3d");
				s2w = data.getIntExtra("s2w", 0);
				s2wStart = data.getStringExtra("s2wStart");
				s2wEnd = data.getStringExtra("s2wEnd");
				vsync = data.getBooleanExtra("vsync", true);
				mpdec = data.getBooleanExtra("mpdec", true);
				reboot = data.getBooleanExtra("reboot", true);
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
	
	public void checkPhone(){
		String device = android.os.Build.DEVICE;
		if(device.equals("shooteru") || device.equals("shooter") || device.equals("pyramid")){
			AlertDialog.Builder builder = new AlertDialog.Builder(
		            AnthraxFlasherHome.this);

				
				builder.setMessage("Device detected\n"+android.os.Build.MODEL+"\nDont proceed if incorect!");
				
				builder.setPositiveButton("Proceed", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which)
						{
							boolean first = preferences.getBoolean("first_launch", false);
							if(first==false){
								CopyAssets();
								Toast.makeText(getApplicationContext(), "Extracting Tools", Toast.LENGTH_LONG).show();
							}
						}
					});
				builder.setNegativeButton("Exit", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which)
						{
							AnthraxFlasherHome.this.finish();
						}
					});
				;
				AlertDialog alert = builder.create();
				

				alert.show();
		}
		else{
			AlertDialog.Builder builder = new AlertDialog.Builder(
		            AnthraxFlasherHome.this);

				
				builder.setMessage("Device detected\n"+android.os.Build.MODEL+"\nThis device isnt supported!");
				
				
				builder.setNegativeButton("Exit", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which)
						{
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
				for(String filename : files) {
					InputStream in = null;
					OutputStream out = null;
					try {
						in = assetManager.open(filename);
						file =	new File(this.getFilesDir ().getAbsolutePath()+"/"+ filename);
						out = new FileOutputStream(file); 
						copyFile(in, out); in.close(); 
						Runtime.getRuntime().exec("chmod 755 " + file);
						file.setExecutable(false);
						in = null; 
						out.flush(); 
						out.close(); 
						out = null; 
						} 
						catch(Exception e) {
							Log.e("tag", e.getMessage()); 
							}
					} 
					
					editor.putBoolean("first_launch", true);
					editor.commit();
				} 
							
						
				private void copyFile(InputStream in, OutputStream out) throws IOException { 
				byte[] buffer = new byte[1024];
				int read; while((read = in.read(buffer)) != -1){
					out.write(buffer, 0, read);
					} 
				}
				
		public void readZip(){
		/*	List<String> folders = new ArrayList<String>();
			boolean kernel;
			boolean modules;
			try {
				ZipFile zipFile = new ZipFile(path);

				Enumeration zipEntries = zipFile.entries();

				while (zipEntries.hasMoreElements()) {

					//Process the name, here we just print it out
					System.out.println(((ZipEntry)zipEntries.nextElement()).getName());

					if(((ZipEntry)zipEntries.nextElement()).isDirectory()){
						if( ((ZipEntry)zipEntries.nextElement()).getName().equals("kernel")){
							
						}
					}
				}

			} catch (IOException ex) {
				ex.printStackTrace();
			}
			try
			{
				OutputStream out = new FileOutputStream("your.file");
		
			FileInputStream fin = new FileInputStream(path);
			BufferedInputStream bin = new BufferedInputStream(fin);
			ZipInputStream zin = new ZipInputStream(bin);
			ZipEntry ze = null;
			while ((ze = zin.getNextEntry()) != null) {
				if (ze.getName().equals("kernel")) {
					byte[] buffer = new byte[8192];
					int len;
					while ((len = zin.read(buffer)) != -1) {
						out.write(buffer, 0, len);
					}
					out.close();
					break;
				}
			}
			}
			catch (FileNotFoundException e)
			{}
			catch(IOException i){}*/
			try

			{

				/*

				 * STEP 1 : Create directory with the name of the zip file

				 * 

				 * For e.g. if we are going to extract c:/demo.zip create c:/demo 

				 * directory where we can extract all the zip entries

				 * 

				 */

				File fSourceZip = new File(path);

				String zipPath = "/data/data/rs.pedjaapps.anthrax.flasher/files/temp";

				File temp = new File(zipPath);

				temp.mkdir();

				System.out.println(zipPath + " created");



				/*

				 * STEP 2 : Extract entries while creating required

				 * sub-directories

				 * 

				 */

				ZipFile zipFile = new ZipFile(fSourceZip);

				Enumeration e = zipFile.entries();



				while(e.hasMoreElements())

				{

					ZipEntry entry = (ZipEntry)e.nextElement();

					File destinationFilePath = new File(zipPath,entry.getName());



					//create directories if required.

					destinationFilePath.getParentFile().mkdirs();



					//if the entry is directory, leave it. Otherwise extract it.

					if(entry.isDirectory())

					{

						continue;

					}

					else

					{

						System.out.println("Extracting " + destinationFilePath);



						/*

						 * Get the InputStream for current entry

						 * of the zip file using

						 * 

						 * InputStream getInputStream(Entry entry) method.

						 */

						BufferedInputStream bis = new BufferedInputStream(zipFile

																		  .getInputStream(entry));



						int b;

						byte buffer[] = new byte[1024];



						/*

						 * read the current entry from the zip file, extract it

						 * and write the extracted file.

						 */

						FileOutputStream fos = new FileOutputStream(destinationFilePath);

						BufferedOutputStream bos = new BufferedOutputStream(fos,

																			1024);



						while ((b = bis.read(buffer, 0, 1024)) != -1) {

							bos.write(buffer, 0, b);

						}



						//flush the output stream and close it.

						bos.flush();

						bos.close();



						//close the input stream.

						bis.close();

					}

				}

			}

			catch(IOException ioe)

			{

				System.out.println("IOError :" + ioe);

			}



        
		}
	
}
