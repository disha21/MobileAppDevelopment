package edu.neu.madcourse.dishasoni;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class AboutMe extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_me);
        this.setTitle("About Me");
        // TelephonyManager telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        //   telephonyManager.getDeviceId();
        String  myAndroidDeviceId = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        TextView deviceId = (TextView)findViewById(R.id.imeiText);
        deviceId.setText("Device Id: " + myAndroidDeviceId);
//        String androidVersion = Build.VERSION.RELEASE;
//        String androidVersionName =  Build.VERSION_CODES.class.getFields()[android.os.Build.VERSION.SDK_INT].getName();
        try {
            PackageInfo pInfo = this.getPackageManager().getPackageInfo(getPackageName(), 0);
            String buildVersionName = pInfo.versionName;
            int buildVersionCode = pInfo.versionCode;
            TextView verisonNum = (TextView)findViewById(R.id.VersionNum);
            verisonNum.setText("Version Number: " + buildVersionCode);
            TextView versionName = (TextView)findViewById(R.id.VersionName);
            versionName.setText("Version Name: " + buildVersionName);

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }
}
