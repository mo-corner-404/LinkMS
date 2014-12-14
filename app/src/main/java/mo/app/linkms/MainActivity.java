package mo.app.linkms;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.widget.Toast;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String intentURIString = Uri.decode(getIntent().getDataString());
        // Turning the intent DATA into a single line string
        String rawURIString = intentURIString.replaceAll("[\r\n]+", " ");
        // Decide the type of intent received
        int indexMS = rawURIString.indexOf("?モンストでマルチしない？");
        int indexGenericLine = rawURIString.indexOf("http://line.naver.jp");
        if (indexGenericLine!=-1)
        {
        	
        	if (indexMS != -1) 
        	{
        	
	        	Log.i("LinkMS","It is a SHARE INTENT");
	        	
	        	int IndexStart = rawURIString.indexOf("「");
	        	int IndexEnd = rawURIString.indexOf("↑") - 1;
	        	
	        	String FinalURIString = rawURIString.substring(IndexStart, IndexEnd);
	        	
	        	showToast(FinalURIString, Toast.LENGTH_LONG);
	        	        	
	        	// Create the text message with a string
	        	Intent sendIntent = new Intent();
	        	sendIntent.setAction(Intent.ACTION_SEND);
	        	sendIntent.putExtra(Intent.EXTRA_TEXT, FinalURIString);
	        	sendIntent.setType("text/plain");
	
	        	// Verify that the intent will resolve to an activity
	        	if (sendIntent.resolveActivity(getPackageManager()) != null) 
	        	{
	        	    startActivity(Intent.createChooser(sendIntent, "Send invite with..." ));
	        	}
        	}
        	else
        	{
        		//TODO: build intent to pass back to line

        	}
		}
        else 
        {
        	vibrate(100);
        	Log.i("LinkMS","It is a link CLICKED");
        	
        	showToast("Loading game...", Toast.LENGTH_SHORT);
        	
            Uri playuri = trimUrl(intentURIString);
            
            final Intent playIntent = new Intent(Intent.ACTION_VIEW);
    		playIntent.setData(playuri);
    		startActivity(playIntent);
        };
        
		finish();
        
    }
    
    public void vibrate(int duration)
    {
       Vibrator vibs = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
       vibs.vibrate(duration);    
    }
    
    public void showToast(String msg,int duration)
    {
    	Context context = getApplicationContext();

    	Toast toast = Toast.makeText(context, msg, duration);
    	toast.show();
    }
    
	protected Uri trimUrl(String in) {
		String prefix = "monsterstrike-app://joingame/?join=";
		int index = in.indexOf("=") + 1;
		in = in.substring(index);
		Uri returnuri = Uri.parse(prefix + in);
		return returnuri;
	}
}
