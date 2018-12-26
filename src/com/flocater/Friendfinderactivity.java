package com.flocater;

import com.flocator.R;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;



public class Friendfinderactivity extends Activity {
	/** Called when the activity is first created. */
	String deviceid = "";
	
	String device_id = "";
	String user_id = "";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		DBHelper dbhelper = new DBHelper(this);
		SQLiteDatabase db = dbhelper.getWritableDatabase();

		TelephonyManager tm = (TelephonyManager) this
				.getSystemService(Context.TELEPHONY_SERVICE);

		deviceid = tm.getDeviceId();

		Cursor cursor = db.rawQuery("select * from "
				+ Database.SUBSCRIBE_TABLE_NAME, null);

		int rows = cursor.getCount();

		if (rows > 0) {
			cursor.moveToFirst();
			user_id = cursor.getString(0);
			device_id = cursor.getString(3);
		}
		db.close();
		if (device_id.equals(deviceid)) {
			setContentView(R.layout.subscriber);
		} else
			setContentView(R.layout.subscribe);
	}

	public void subscribeUser(View v) {
		// get access to views
		EditText editName = (EditText) this.findViewById(R.id.editName);
		EditText editPhone = (EditText) this.findViewById(R.id.editPhone);
		EditText editArea = (EditText) this.findViewById(R.id.editArea);
		EditText editCity = (EditText) this.findViewById(R.id.editCity);

		boolean done = Database.addUser(this, editName.getText().toString(),
				editPhone.getText().toString(), deviceid, editArea.getText()
						.toString(), editCity.getText().toString());
		
		if(done) {
			Toast.makeText(this, "Subscribed Successfully!", Toast.LENGTH_LONG).show();
			Intent intent = new Intent(this,Friendfinderactivity.class);
			startActivity(intent);
		}
		else
			Toast.makeText(this, "Sorry! Could not subscribe!", Toast.LENGTH_LONG).show();
	}
	
	public void addFriends(View v) {
		Intent intent = new Intent(this,AddFriends.class);
		startActivity(intent);
	}
	
	public void viewFriends(View v) {
		Intent intent = new Intent(this,SeeFriends.class);
		startActivity(intent);
	}
	
	public void seeLocation(View v) {
		Intent intent = new Intent(this,SeeFriendsLocation.class);
		startActivity(intent);
	}
	
	public void updateDetails(View v) {
		Intent intent = new Intent(this,UpdateUserDetails.class);
		intent.putExtra("userid", user_id);
		startActivity(intent);
	}
}