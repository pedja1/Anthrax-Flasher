package rs.pedjaapps.anthrax.flasher;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.List;


import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v4.app.NavUtils;

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
	
	
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		checkPhone();
		setContentView(R.layout.anthrax_home);
		// getActionBar().setDisplayHomeAsUpEnabled(true);
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
				setupKernel.setEnabled(true);

				pickKernel.setEnabled(false);
				path = data.getData().getPath();
				System.out.println(path);
				kernelFile.setText(path);
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
				;
				AlertDialog alert = builder.create();
				alert.setCanceledOnTouchOutside(false);
				alert.setCancelable(false);
				alert.show();
		}
	}

}
