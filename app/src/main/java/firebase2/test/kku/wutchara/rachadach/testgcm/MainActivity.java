package firebase2.test.kku.wutchara.rachadach.testgcm;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.RemoteMessage;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final String SENDER_ID = "rachadach.wutchara.kku.test.firebase2";
    private static final String SERVER_ID = "AAAAplZ9MYI:APA91bH2x3qscZnUVgaVrBDr5S9c86qFOjDIq89JWsaBky6I-OXoQSiSlwOTrAvzWhZCEbIWTaDjh3jHr4OT9BQtbfr62_Ia3hOcnSYi7ZKeTAR3Ax6K6Yb4896qmQVqnmZbg2jwPNRmMmD821Gy4pM3TPKCaSa4IQ";
    private static final String SEND_TO_ID = "714415616386";
    private static final String SENDER_TOKEN = "f_Fqgfljos0:APA91bEMWaMZLTA1awP7JK37y9aeZRwIJwGEdMBSV9xH4GvurVmni0YOCdyZyDdYEo-Nr-K20MX2f1feql_nteRACyJFFICmLUTYOpdeJCP-cDYyz4GGykxym3LVXUeU47X5sb3jBifm";
    private int msgId = 1;

    public static final String FCM_PROJECT_SENDER_ID = "714415616386";
    public static final String FCM_SERVER_CONNECTION = "@gcm.googleapis.com";
    public static final String BACKEND_ACTION_MESSAGE = "MESSAGE";
    public static final String BACKEND_ACTION_ECHO = "rachadach.wutchara.kku.test.firebase2";
    public static final Random RANDOM = new Random();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // If a notification message is tapped, any data accompanying the notification
        // message is available in the intent extras. In this sample the launcher
        // intent is fired when the notification is tapped, so any accompanying data would
        // be handled here. If you want a different intent fired, set the click_action
        // field of the notification message to the desired intent. The launcher intent
        // is used when no click_action is specified.
        //
        // Handle possible data accompanying notification message.
        // [START handle_data_extras]
        if (getIntent().getExtras() != null) {
            for (String key : getIntent().getExtras().keySet()) {
                Object value = getIntent().getExtras().get(key);
                Log.d(TAG, "Key: " + key + " Value: " + value);
            }
        }
        // [END handle_data_extras]

        Button subscribeButton = (Button) findViewById(R.id.subscribeButton);
        subscribeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // [START subscribe_topics]
                FirebaseMessaging.getInstance().subscribeToTopic("news");
                // [END subscribe_topics]

                // Log and toast
                String msg = getString(R.string.msg_subscribed);
                Log.d(TAG, msg);
                Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
        });

        Button logTokenButton = (Button) findViewById(R.id.logTokenButton);
        logTokenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get token
                String token = FirebaseInstanceId.getInstance().getToken();

                // Log and toast
                String msg = getString(R.string.msg_token_fmt, token);
                Log.d(TAG, msg);
                Toast.makeText(MainActivity.this, msg + "\n", Toast.LENGTH_SHORT).show();
            }
        });

        Button sendButton = (Button) findViewById(R.id.sendButton);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Echo Upstream message logic");
                String message = "HAM TEST";
                Log.d(TAG, "Message: " + message + ", recipient: " + SENDER_TOKEN);
                FirebaseMessaging.getInstance().send(new RemoteMessage.Builder(FCM_PROJECT_SENDER_ID + FCM_SERVER_CONNECTION)
                        .setMessageId(Integer.toString(RANDOM.nextInt()))
                        .addData("message", message)
                        .addData("action", BACKEND_ACTION_ECHO)
                        .build());
            }
        });
    }
}
