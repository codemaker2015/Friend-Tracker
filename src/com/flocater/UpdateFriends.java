package com.flocater;

import com.flocator.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateFriends extends Activity {

	private String friendId;
	private EditText editFName, editFPhone, editFArea, editFCity;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.updatefriends);
		editFName = (EditText) this.findViewById(R.id.editFName);
		editFPhone = (EditText) this.findViewById(R.id.editFPhone);
		editFArea = (EditText) this.findViewById(R.id.editFArea);
		editFCity = (EditText) this.findViewById(R.id.editFCity);

	}

	@Override
	public void onStart() {
		super.onStart();
		friendId = this.getIntent().getStringExtra("frndid");
		Log.d("Subscriber", "Friend Id : " + friendId);
		DBHelper dbhelper = new DBHelper(this);
		SQLiteDatabase db = dbhelper.getReadableDatabase();
		Cursor account = db.query(Database.FRIENDS_TABLE_NAME, null,
				" _id = ?", new String[] { friendId }, null, null, null);
		// startManagingCursor(accounts);
		if (account.moveToFirst()) {
			// update view
			editFName.setText(account.getString(account
					.getColumnIndex(Database.FRIENDS_NAME)));
			editFPhone.setText(account.getString(account
					.getColumnIndex(Database.FRIENDS_PHONE)));
			editFArea.setText(account.getString(account
					.getColumnIndex(Database.FRIENDS_AREA)));
			editFCity.setText(account.getString(account
					.getColumnIndex(Database.FRIENDS_CITY)));
		}
		account.close();
		db.close();
		dbhelper.close();

	}

	public void updateFriend(View v) {
		try {
			DBHelper dbhelper = new DBHelper(this);
			SQLiteDatabase db = dbhelper.getWritableDatabase();
			// execute insert command
			ContentValues values = new ContentValues();
			values.put(Database.FRIENDS_NAME, editFName.getText().toString());
			values.put(Database.FRIENDS_PHONE, editFPhone.getText().toString());
			values.put(Database.FRIENDS_AREA, editFArea.getText().toString());
			values.put(Database.FRIENDS_CITY, editFCity.getText().toString());

			long rows = db.update(Database.FRIENDS_TABLE_NAME, values,
					"_id = ?", new String[] { friendId });

			db.close();
			if (rows > 0) {
				Toast.makeText(this, "Updated Successfully!", Toast.LENGTH_LONG)
						.show();
				Intent intent = new Intent(this,Friendfinderactivity.class);
				startActivity(intent);
			}
			else
				Toast.makeText(this, "Sorry! Could not update!",
						Toast.LENGTH_LONG).show();
		} catch (Exception ex) {
			Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG).show();
		}
	}

	public void deleteFriend(View v) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("Are you sure you want to delete?")
				.setCancelable(true)
				.setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								deleteCurrentFriend();
							}
						})
				.setNegativeButton("No", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
					}
				});
		AlertDialog alert = builder.create();
		alert.show();
	}

	public void deleteCurrentFriend() {
		try {
			DBHelper dbhelper = new DBHelper(this);
			SQLiteDatabase db = dbhelper.getWritableDatabase();
			int rows = db.delete(Database.FRIENDS_TABLE_NAME, "_id=?",
					new String[] { friendId });
			dbhelper.close();
			if (rows == 1) {
				Toast.makeText(this, "Friend Deleted Successfully!",
						Toast.LENGTH_LONG).show();
				this.finish();   
				Intent intent = new Intent(this,Friendfinderactivity.class);
				startActivity(intent);
			} else
				Toast.makeText(this, "Could not delete!",
						Toast.LENGTH_LONG).show();

		} catch (Exception ex) {
			Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG).show();
		}

	}

}
