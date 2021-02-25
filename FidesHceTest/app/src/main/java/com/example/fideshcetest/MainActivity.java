package com.example.fideshcetest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public class HelloMessageReceiver extends BroadcastReceiver {

        private TextView textView;

        public HelloMessageReceiver(TextView textView) {
            this.textView = textView;
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction() == Constants.NFC_HELLO_MESSAGE) {
                textView.setText(intent.getStringExtra(Constants.NAME_STRING));
            }
        }
    }

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        IntentFilter filter = new IntentFilter(Constants.NFC_HELLO_MESSAGE);

        HelloMessageReceiver receiver = new HelloMessageReceiver(findViewById(R.id.hello_text_view));
        registerReceiver(receiver, filter);
    }
}