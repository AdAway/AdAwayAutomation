package org.adaway.automation;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class InsecureCommandReceiver extends BroadcastReceiver {
    private static final String TAG = "AutomationReceiver";
    private static final String SEND_COMMAND_ACTION = "org.adaway.action.SEND_COMMAND_INSECURE";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (SEND_COMMAND_ACTION.equals(intent.getAction())) {
            if (intent.hasExtra("COMMAND")) {
                String command = intent.getStringExtra("COMMAND");
                Log.i(TAG, "InsecureCommandReceiver invoked with command "+command);
                MainActivity.sendCommand(context, command);
            } else {
                Log.i(TAG, "InsecureCommandReceiver invoked without command");
            }
        }
    }
}
