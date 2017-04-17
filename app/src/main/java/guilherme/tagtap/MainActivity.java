package guilherme.tagtap;

import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    private static final String TAG = "NFCWriteTag";
    private NfcAdapter mNfcAdapter;
    private IntentFilter[] mWriteTagFilters;
    private PendingIntent mNfcPendingIntent;
    private boolean silent=false;
    private boolean writeProtect = false;
    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //Inflating a Menu.xml
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    /*
    Checking the NFC adapter, and if it's not enabled, will put a dialog box to take us to our settings to enable it.  
    Otherwise it fails. 
     */
    @Override
    protected void onResume() {
        super.onResume();
        if(mNfcAdapter != null) {
            if (!mNfcAdapter.isEnabled()){
                LayoutInflater inflater = getLayoutInflater();
                View dialoglayout = inflater.inflate(R.layout.nfc_settings_layout,(ViewGroup) findViewById(R.id.nfc_settings_layout));
                new AlertDialog.Builder(this).setView(dialoglayout)
                        .setPositiveButton("Update Settings", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface arg0, int arg1) {
                                Intent setnfc = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
                                startActivity(setnfc);
                            }
                        })
                        .setOnCancelListener(new DialogInterface.OnCancelListener() {
                            public void onCancel(DialogInterface dialog) {
                                finish(); // exit application if user cancels
                            }
                        }).create().show();
            }
            mNfcAdapter.enableForegroundDispatch(this, mNfcPendingIntent, mWriteTagFilters, null);
        } else {
            Toast.makeText(context, "Sorry, No NFC Adapter found.", Toast.LENGTH_SHORT).show();
        }
    }

















}
