package com.keepsaver.xposed;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class StatusActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);
        TextView status = findViewById(R.id.status);
        status.setText(getString(R.string.status_heading, BuildConfig.VERSION_NAME) + "\n\n"
                + getString(R.string.status_body));
    }
}
