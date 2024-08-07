package com.example.saheli;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.ClipData;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity3 extends AppCompatActivity {
    CardView settings,safety,siren;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        getSupportActionBar().hide();

        Intent backgroundService = new Intent( getApplicationContext(), ScreenOnOffBackgroundService.class );
        this.startService( backgroundService );
        Log.d( ScreenOnOffReceiver.SCREEN_TOGGLE_TAG, "Activity onCreate" );
        int permissionCheck = ContextCompat.checkSelfPermission (MainActivity3.this, Manifest.permission.SEND_SMS);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission (MainActivity3.this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission (MainActivity3.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

            final AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity3.this);
            View mView = getLayoutInflater().inflate(R.layout.custom_dialog_mainactivity, null);

            Button btn_okay = (Button) mView.findViewById(R.id.btn_okay);
            TextView heading = mView.findViewById(R.id.heading);
            heading.setText("Saheli needs access to");
            TextView sms = mView.findViewById(R.id.sms);
            sms.setText("Sending SMS:-");
            TextView textView = mView.findViewById(R.id.textFormodal);
            textView.setText("Emergency messaging needs SEND SMS permission");
            TextView location = mView.findViewById(R.id.location);
            location.setText("Location:-");
            TextView locationText = mView.findViewById(R.id.textLocation);
            locationText.setText("Messaging embedded with live location needs Location permission");
            TextView call = mView.findViewById(R.id.call);
            call.setText("Phone Call:-");
            TextView callText = mView.findViewById(R.id.textCall);
            callText.setText("Emergency Calling needs CALL PHONE permission");
            TextView declaration = mView.findViewById(R.id.declaration);
            declaration.setText("Declaration");
            TextView declaratioText = mView.findViewById(R.id.textDeclaration);
            declaratioText.setText("The app is solely developed by INDIAN Developers and all data related to this app is stored locally in your phone.");
            CheckBox checkbox = (CheckBox) mView.findViewById(R.id.checkBox);
            TextView checkBoxtext = (TextView) mView.findViewById(R.id.checkBoxText);
            checkbox.setVisibility(View.VISIBLE);
            checkBoxtext.setVisibility(View.VISIBLE);
            checkbox.setEnabled(true);
            checkBoxtext.setEnabled(true);

            checkbox.setText("");
            checkBoxtext.setText(Html.fromHtml("I accept the " +
                    "<a href='https://www.websitepolicies.com/policies/view/IaK4RjyB'>PRIVACY POLICY</a>" + " of the app"));
            checkBoxtext.setClickable(true);
            checkBoxtext.setMovementMethod(LinkMovementMethod.getInstance());
            alert.setView(mView);
            final AlertDialog alertDialog = alert.create();
            alertDialog.setCanceledOnTouchOutside(false);
            btn_okay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (checkbox.isChecked()) {

                        alertDialog.dismiss();
                        ActivityCompat.requestPermissions(MainActivity3.this, new String[]{Manifest.permission.SEND_SMS, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.CALL_PHONE}, 0);
                    } else {
                        Toast.makeText(MainActivity3.this, "Please accept privacy policy", Toast.LENGTH_LONG).show();

                    }
                }
            });
            alertDialog.show();

        }

        settings = findViewById( R.id.settings );
        safety = findViewById( R.id.safety );
        siren = findViewById(R.id.siren);
        settings.setOnClickListener( v -> startActivity( new Intent( getApplicationContext(), MainActivity2.class ) ) );



        safety.setOnClickListener( v -> startActivity(new Intent(getApplicationContext(),SosInsructionsActivity.class ) ) );



        siren.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent( getApplicationContext(), Flashing.class ) );
            }
        } );





    }
}