package com.hackMIT.peblock;

import java.util.UUID;

import com.getpebble.android.kit.PebbleKit;
import com.getpebble.android.kit.util.PebbleDictionary;

import android.os.Bundle;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	public final static UUID PEBLOCK_UUID = UUID.fromString("3836DC88-4121-4C3C-AD1D-8190F0920973");
	String unlocked = new String("UNLOCKED");
	String locked = new String("LOCKED");
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		final TextView Status = (TextView) findViewById(R.id.STATUStext);
		final TextView carStat = (TextView) findViewById(R.id.carStat);
		
		boolean connected = PebbleKit.isWatchConnected(getApplicationContext());
		Log.e("pebblelock", "hey!");
		Log.i("pebblelock", "Pebble is " + (connected ? "connected" : "disconnected"));
		
		PebbleKit.registerPebbleConnectedReceiver(getApplicationContext(), new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				Status.setText("PEBBLE CONNECTED!");
				carStat.setText("CAR UNLOCKED!");
				Log.i("pebblelock", "pebble connected");
				PebbleKit.startAppOnPebble(getApplicationContext(), PEBLOCK_UUID);
				PebbleDictionary TEXT = new PebbleDictionary();
				TEXT.addString(0, unlocked);
				PebbleKit.sendDataToPebble(getApplicationContext(), PEBLOCK_UUID, TEXT);
				Log.i("pebblelock", "sent text");
				
			}
		});

		PebbleKit.registerPebbleDisconnectedReceiver(getApplicationContext(), new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				Status.setText("PEBBLE DISCONNECTED!");
				carStat.setText("CAR LOCKED!");
				Log.e("pebblelock", "pebble disconnected");
				PebbleKit.startAppOnPebble(getApplicationContext(), PEBLOCK_UUID);
				PebbleDictionary TEXT = new PebbleDictionary();
				TEXT.addString(0, locked);
				
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
