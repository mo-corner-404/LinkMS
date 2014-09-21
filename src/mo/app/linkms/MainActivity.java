package mo.app.linkms;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
          
        String originuri = getIntent().getDataString();
        Uri playuri = trimUrl(originuri);
        
		final Intent playIntent = new Intent(Intent.ACTION_VIEW);
		playIntent.setData(playuri);
		startActivity(playIntent);
		finish();
        
    }
    
	protected Uri trimUrl(String in) {
		
		in = in.substring(49);
		
		Uri returnuri = Uri.parse("monsterstrike-app://joingame/?join="+ in);
		return returnuri;
	}
}
