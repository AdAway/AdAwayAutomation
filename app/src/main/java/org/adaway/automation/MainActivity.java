package org.adaway.automation;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "AutomationActivity";
    private static final String PERMISSION = "org.adaway.permission.SEND_COMMAND";
    private final ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    sendCommand(this, this.command);
                } else {
                    Log.w(TAG, "Permission not granted");
                }
            });
    private String command = "unknown";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button enableButton = findViewById(R.id.enableAdBlock);
        enableButton.setOnClickListener(this::enableAdBlock);
        Button disableButton = findViewById(R.id.disableAdBlock);
        disableButton.setOnClickListener(this::disableAdBlock);
    }

    private void enableAdBlock(View view) {
        this.command = "START";
        checkPermissionThenSendCommand();
    }

    private void disableAdBlock(View view) {
        this.command = "STOP";
        checkPermissionThenSendCommand();
    }

    private void checkPermissionThenSendCommand() {
        if (ContextCompat.checkSelfPermission(this, PERMISSION) == PERMISSION_GRANTED) {
            sendCommand(this, this.command);
        } else {
            this.requestPermissionLauncher.launch(PERMISSION);
        }
    }

    static void sendCommand(Context context, String command) {
        Log.i(TAG, "sendCommand: " + command);
        Intent intent = new Intent("org.adaway.action.SEND_COMMAND");
        intent.setPackage("org.adaway");
        intent.putExtra("COMMAND", command);
        context.sendBroadcast(intent);
    }
}