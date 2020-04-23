package com.example.practicas_pdm;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;

import com.example.practicas_pdm.ui.login.LoginActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.ViewCompat;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class ScrollingActivity extends AppCompatActivity {

    ListView simpleList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        String message = intent.getStringExtra(LoginActivity.RESPONSE);
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();

        String a = " India China Australia Portugal America NewZealand Inglaterra Rusia Alemania Polonia Meixico";

        String newList = " https://youtu.be/vTsbYbN8VVI http://coldattic.info/post/88/ https://developer.android.com/training/basics/firstapp/starting-activity";
        newList = newList + " https://disco.ethz.ch/courses/hs17/distsys/exercises/solution11.pdf https://kconrad.math.uconn.edu/blurbs/ugradnumthy/millerrabin.pdf";
        newList = newList + " https://stackoverflow.com/questions/57987134/whats-the-most-efficient-way-to-find-wilson-prime-number-using-python https://developer.android.com/reference/android/content/Intent#ACTION_MAIN";
        newList = newList + " https://developer.android.com/training/basics/intents/sending";
        newList = newList + " geo:37.7749,-122.4194?q=restaurants google.streetview:cbll=29.9774614,31.1329645&cbp=0,30,0,0,-15";

        message = message.substring(5, message.length()) + newList + a + a + a + a + a;

        final String[] result = message.split(" ");


        simpleList = (ListView)findViewById(R.id.simpleListView);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.activity_listview, R.id.textView, result);
        simpleList.setAdapter(arrayAdapter);

        ViewCompat.setNestedScrollingEnabled(simpleList, true);

        simpleList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                if (position > 20) {
                    Toast.makeText(getApplicationContext(), result[position], Toast.LENGTH_LONG).show();
                }else{
                    // Build the intent
                    String l = result[position];

                    Uri link = Uri.parse(l);
                    Intent linkIntent = new Intent(Intent.ACTION_VIEW, link);

                    if (l.contains("google") || l.contains("geo")){
                        linkIntent.setPackage("com.google.android.apps.maps");
                    }

                    if (l.contains("youtube")){
                        if (isAppInstalled("com.google.android.youtube")){
                            linkIntent.setPackage("com.google.android.youtube");
                        }
                    }

                    // Verify it resolves
                    PackageManager packageManager = getPackageManager();
                    List<ResolveInfo> activities = packageManager.queryIntentActivities(linkIntent, 0);
                    boolean isIntentSafe = ((List) activities).size() > 0;

                    // Start an activity if it's safe
                    if (isIntentSafe) {
                        startActivity(linkIntent);
                    }

                }
            }
        });

        // Capture the layout's TextView and set the string as its text
        //TextView textView = findViewById(R.id.textView);
        //textView.setText(message);

    }

    private boolean isAppInstalled(String packageName) {
        PackageManager pm = getPackageManager();
        boolean installed = false;
        try {
            pm.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
            installed = true;
        } catch (PackageManager.NameNotFoundException e) {
            installed = false;
        }
        return installed;
    }
}
