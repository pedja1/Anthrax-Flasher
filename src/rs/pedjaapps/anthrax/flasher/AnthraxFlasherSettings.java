package rs.pedjaapps.anthrax.flasher;


import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Switch;
import android.support.v4.app.NavUtils;

public class AnthraxFlasherSettings extends Activity {
	
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
	
	private Switch reboot;
	private Switch vsync;
	private Switch mpdec;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anthrax_flasher);
        Spinner cpuMinSpinner = (Spinner)findViewById(R.id.spinner1);
		Spinner cpuMaxSpinner = (Spinner)findViewById(R.id.spinner2);
		Spinner cpuScrOffSpinner = (Spinner)findViewById(R.id.spinner3);
		Spinner cpuGovSpinner = (Spinner)findViewById(R.id.spinner4);
		Spinner schedulerSpinner= (Spinner)findViewById(R.id.spinner5);
		Spinner gpu3dSpinner = (Spinner)findViewById(R.id.spinner6);
		Spinner gpu2dSpinner = (Spinner)findViewById(R.id.spinner7);
		Spinner s2wSpinner = (Spinner)findViewById(R.id.spinner8);
		Spinner s2wStartSpinner = (Spinner)findViewById(R.id.spinner9);
		Spinner s2wEndSpinner = (Spinner)findViewById(R.id.spinner10);
		
		vsync = (Switch)findViewById(R.id.switch1);
		mpdec = (Switch)findViewById(R.id.switch2);
		reboot = (Switch)findViewById(R.id.switch3);
		
		String[] cpuMinFreqs = {"192 MHz - HTC Default on OLD kernels", "384 MHz - Anthrax Recommended, HTC Default on NEW kernels", "432 MHz"};
		String[] cpuMaxFreqs = {"1188 MHz - HTC STOCK", 
				"1242 MHz",
				"1296 MHz", 
				"1350 MHz",
				"1404 MHz",
				"1458 MHz",
				"1512 MHz - QUALCOMM STOCK",
				"1536 MHz",
				"1566 MHz - AnthraX Recommended",
				"1620 MHz - MAY CAUSE NO-BOOT/BOOT LOOP",
				"1674 MHz - MAY CAUSE NO-BOOT/BOOT LOOP",
				"1728 MHz - MAY CAUSE NO-BOOT/BOOT LOOP",};
		String[] cpuScrOffFreqs = {"192 MHz - will cause random reboots",
				  "384 MHz - may cause random reboots",
				  "432 MHz - may be unstable",
				  "486 MHz - AnthraX Recommended",
				  "540 MHz",
				  "594 MHz",
				  "648 MHz",
				  "702 MHz",
				  "756 MHz",};
		
		String[] cpuGovFreqs = {"ondemand",
				  "interactive",
				  "conservative",
				  "userspace",
				  "powersave",
				  "performance",
				  "xondemand",
				  "lagfree",};
		String[] schedulers = {"deadline - Venom Recommended",
				  "noop - AnthraX Recommended",
				  "cfq",
				  "vr",
				  "sio",};
		String[] gpu3dFreqs = {"266 MHz - HTC Default",
				  "300 MHz - Qualcomm Default",
				  "320 MHz - Overclocked"};
		String[] gpu2dFreqs = {"200 MHz - Underclocked",
				  "228 MHz - HTC Default",
				  "266 MHz - Qualcomm Default",};
		String[] s2wOptions = {"Disabled",
				  "Enabled Without Captive Backlight",
				  "Enabled With Captive Backlight"};
		String[] s2wStartKeys = {"HOME",
				  "MENU",
				  "BACK",};
		String[] s2wEndKeys  = {"SEARCH",
				  "BACK",
				  "MENU",};
		
		ArrayAdapter<String> cpuMinArrayAdapter = new ArrayAdapter<String>(this,   android.R.layout.simple_spinner_item, cpuMinFreqs);
		cpuMinArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down vieww
		cpuMinSpinner.setAdapter(cpuMinArrayAdapter);

		
			int cpuMinPosition = cpuMinArrayAdapter.getPosition(cpuMinFreqs[1]);
			cpuMinSpinner.setSelection(cpuMinPosition);
		
		cpuMinSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
        		@Override 
        	    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id)
				{
        			switch (pos){
        			case 0:
        			cpuMin = "192000";
        				break;
        			case 1:
            			cpuMin = "384000";
            			break;
        			case 2:
            			cpuMin = "432000";
            			break;
        			}
        	    	System.out.println(cpuMin);

        	    }

        		@Override
        	    public void onNothingSelected(AdapterView<?> parent)
				{
        	        //do nothing
        	    }



        	});
		
		ArrayAdapter<String> cpuMaxArrayAdapter = new ArrayAdapter<String>(this,   android.R.layout.simple_spinner_item, cpuMaxFreqs);
		cpuMaxArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down vieww
		cpuMaxSpinner.setAdapter(cpuMaxArrayAdapter);

		
			int cpuMaxPosition = cpuMaxArrayAdapter.getPosition(cpuMaxFreqs[6]);
			cpuMaxSpinner.setSelection(cpuMaxPosition);
		
		cpuMaxSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
        		@Override 
        	    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id)
				{
        			
        			switch (pos){
        			case 0:
        			cpuMax = "1188000";
        				break;
        			case 1:
            			cpuMax = "1242000";
            			break;
        			case 2:
            			cpuMax = "1296000";
            			break;
        			case 3:
            			cpuMax = "1350000";
            			break;
        			case 4:
            			cpuMax = "1404000";
            			break;
        			case 5:
            			cpuMax = "1458000";
            			break;
        			case 6:
            			cpuMax = "1512000";
            			break;
        			case 7:
            			cpuMax = "1566000";
            			break;
            			
        			case 8:
            			cpuMax = "1620000";
            			break;
        			case 9:
            			cpuMax = "1674000";
            			break;
        			case 10:
            			cpuMax = "1728000";
            			break;
        			}
        	    	
        	    	
        	    	System.out.println(cpuMax);

        	    }

        		@Override
        	    public void onNothingSelected(AdapterView<?> parent)
				{
        	        //do nothing
        	    }



        	});
		
		ArrayAdapter<String> cpuScrOffArrayAdapter = new ArrayAdapter<String>(this,   android.R.layout.simple_spinner_item, cpuScrOffFreqs);
		cpuScrOffArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down vieww
		cpuScrOffSpinner.setAdapter(cpuScrOffArrayAdapter);

		
			int cpuScrOffPosition = cpuScrOffArrayAdapter.getPosition(cpuScrOffFreqs[3]);
			cpuScrOffSpinner.setSelection(cpuScrOffPosition);
		
		cpuScrOffSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
        		@Override 
        	    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id)
				{
        			
        			switch (pos){
        			case 0:
        			cpuScrOff = "192000";
        				break;
        			case 1:
            			cpuScrOff = "384000";
            			break;
        			case 2:
            			cpuScrOff = "432000";
            			break;
        			case 3:
            			cpuScrOff = "486000";
            			break;
        			case 4:
            			cpuScrOff = "540000";
            			break;
        			case 5:
            			cpuScrOff = "594000";
            			break;
        			case 6:
            			cpuScrOff = "648000";
            			break;
        			case 7:
            			cpuScrOff = "702000";
            			break;
            			
        			case 8:
            			cpuScrOff = "756000";
            			break;
        			
        			}
        	    	
        	    	
        	    	System.out.println(cpuScrOff);

        	    }

        		@Override
        	    public void onNothingSelected(AdapterView<?> parent)
				{
        	        //do nothing
        	    }



        	});
		
		ArrayAdapter<String> cpuGovArrayAdapter = new ArrayAdapter<String>(this,   android.R.layout.simple_spinner_item, cpuGovFreqs);
		cpuGovArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down vieww
		cpuGovSpinner.setAdapter(cpuGovArrayAdapter);

		
			int cpuGovPosition = cpuGovArrayAdapter.getPosition(cpuGovFreqs[7]);
			cpuGovSpinner.setSelection(cpuGovPosition);
		
		cpuGovSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
        		@Override 
        	    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id)
				{
        			
        			cpuGov = parent.getItemAtPosition(pos).toString();
        	    	
        	    	
        	    	System.out.println(cpuGov);

        	    }

        		@Override
        	    public void onNothingSelected(AdapterView<?> parent)
				{
        	        //do nothing
        	    }



        	});
		
		ArrayAdapter<String> schedulerArrayAdapter = new ArrayAdapter<String>(this,   android.R.layout.simple_spinner_item, schedulers);
		schedulerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down vieww
		schedulerSpinner.setAdapter(schedulerArrayAdapter);

		
			int schedulerPosition = schedulerArrayAdapter.getPosition(schedulers[1]);
			schedulerSpinner.setSelection(schedulerPosition);
		
			schedulerSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
        		@Override 
        	    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id)
				{
        			
        			switch (pos){
        			case 0:
        				scheduler = "deadline";
        				break;
        			case 1:
        				scheduler = "noop";
            			break;
        			case 2:
        				scheduler = "cfg";
            			break;
        			case 3:
        				scheduler = "vr";
            			break;
        			case 4:
        				scheduler = "sio";
            			break;
        			
        			
        			}
        	    	
        	    	
        	    	System.out.println(scheduler);

        	    }

        		@Override
        	    public void onNothingSelected(AdapterView<?> parent)
				{
        	        //do nothing
        	    }



        	});
			
			ArrayAdapter<String> gpu3dArrayAdapter = new ArrayAdapter<String>(this,   android.R.layout.simple_spinner_item, gpu3dFreqs);
			gpu3dArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down vieww
			gpu3dSpinner.setAdapter(gpu3dArrayAdapter);

			
				int gpu3dPosition = gpu3dArrayAdapter.getPosition(gpu3dFreqs[2]);
				gpu3dSpinner.setSelection(gpu3dPosition);
			
				gpu3dSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
	        		@Override 
	        	    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id)
					{
	        			
	        			switch (pos){
	        			case 0:
	        				gpu3d = "266667000";
	        				break;
	        			case 1:
	        				gpu3d = "300000000";
	            			break;
	        			case 2:
	        				gpu3d = "320000000";
	            			break;
	        			
	        			
	        			
	        			}
	        	    	
	        	    	
	        	    	System.out.println(gpu3d);

	        	    }

	        		@Override
	        	    public void onNothingSelected(AdapterView<?> parent)
					{
	        	        //do nothing
	        	    }



	        	});
				
				ArrayAdapter<String> gpu2dArrayAdapter = new ArrayAdapter<String>(this,   android.R.layout.simple_spinner_item, gpu2dFreqs);
				gpu2dArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down vieww
				gpu2dSpinner.setAdapter(gpu2dArrayAdapter);

				
					int gpu2dPosition = gpu2dArrayAdapter.getPosition(gpu2dFreqs[2]);
					gpu2dSpinner.setSelection(gpu2dPosition);
				
					gpu2dSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
		        		@Override 
		        	    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id)
						{
		        			
		        			switch (pos){
		        			case 0:
		        				gpu2d = "200000000";
		        				break;
		        			case 1:
		        				gpu2d = "228571000";
		            			break;
		        			case 2:
		        				gpu2d = "266667000";
		            			break;
		        			
		        			
		        			
		        			}
		        	    	
		        	    	
		        	    	System.out.println(gpu2d);

		        	    }

		        		@Override
		        	    public void onNothingSelected(AdapterView<?> parent)
						{
		        	        //do nothing
		        	    }



		        	});
					
					ArrayAdapter<String> s2wArrayAdapter = new ArrayAdapter<String>(this,   android.R.layout.simple_spinner_item, s2wOptions);
					s2wArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down vieww
					s2wSpinner.setAdapter(s2wArrayAdapter);

					
						int s2wPosition = s2wArrayAdapter.getPosition(s2wOptions[2]);
						s2wSpinner.setSelection(s2wPosition);
					
						s2wSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			        		@Override 
			        	    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id)
							{
			        			
			        			switch (pos){
			        			case 0:
			        				s2w = 0;
			        				break;
			        			case 1:
			        				s2w = 1;
			            			break;
			        			case 2:
			        				s2w = 2;
			            			break;
			        			
			        			
			        			
			        			}
			        	    	
			        	    	
			        	    	System.out.println(s2w);

			        	    }

			        		@Override
			        	    public void onNothingSelected(AdapterView<?> parent)
							{
			        	        //do nothing
			        	    }



			        	});
						
						ArrayAdapter<String> s2wStartArrayAdapter = new ArrayAdapter<String>(this,   android.R.layout.simple_spinner_item, s2wStartKeys);
						s2wStartArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down vieww
						s2wStartSpinner.setAdapter(s2wStartArrayAdapter);

						
							int s2wStartPosition = s2wStartArrayAdapter.getPosition(s2wStartKeys[0]);
							s2wStartSpinner.setSelection(s2wStartPosition);
						
							s2wStartSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
				        		@Override 
				        	    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id)
								{
				        			
				        			s2wStart = parent.getItemAtPosition(pos).toString();

				        	    	System.out.println(s2wStart);

				        	    }

				        		@Override
				        	    public void onNothingSelected(AdapterView<?> parent)
								{
				        	        //do nothing
				        	    }



				        	});
							
							ArrayAdapter<String> s2wEndArrayAdapter = new ArrayAdapter<String>(this,   android.R.layout.simple_spinner_item, s2wEndKeys);
							s2wEndArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down vieww
							s2wEndSpinner.setAdapter(s2wEndArrayAdapter);

							
								int s2wEndPosition = s2wEndArrayAdapter.getPosition(s2wEndKeys[0]);
								s2wEndSpinner.setSelection(s2wEndPosition);
							
								s2wEndSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
					        		@Override 
					        	    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id)
									{
					        			
					        			s2wEnd = parent.getItemAtPosition(pos).toString();

					        	    	System.out.println(s2wEnd);

					        	    }

					        		@Override
					        	    public void onNothingSelected(AdapterView<?> parent)
									{
					        	        //do nothing
					        	    }



					        	});
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_anthrax_flasher_settings, menu);
        return true;
    }

    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save:
            	boolean vs = true;
            	boolean mp = true;
            	boolean rb = true;
            	if(vsync.isChecked()){
            		vs = true;
            	}
            	else{
            		vs = false;
            	}
            	if(mpdec.isChecked()){
            		mp = true;
            	}
            	else{
            		mp = false;
            	}
            	if(reboot.isChecked()){
            		rb = true;
            	}
            	else{
            		rb = false;
            	}
            	
            	Intent intent = new Intent();
            	intent.putExtra("cpuMin",cpuMin);
            	intent.putExtra("cpuMax",cpuMax);
            	intent.putExtra("cpuScrOff",cpuScrOff);
            	intent.putExtra("cpuGov",cpuGov);
            	intent.putExtra("scheduler",scheduler);
            	intent.putExtra("gpu2d",gpu2d);
            	intent.putExtra("gpu3d",gpu3d);
            	intent.putExtra("s2w",s2w);
            	intent.putExtra("s2wStart",s2wStart);
            	intent.putExtra("s2wEnd",s2wEnd);
            	intent.putExtra("vsync",vs);
            	intent.putExtra("mpdec",mp);
            	intent.putExtra("reboot",rb);
            	setResult(RESULT_OK, intent);
            	
            	finish();
                return true;
            case R.id.cancel:
            	setResult(RESULT_CANCELED);
            	finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
