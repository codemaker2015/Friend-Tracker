package com.flocater;
import com.flocator.R;
import android.app.Activity;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddFriends extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addfriends);
	}

	public void addFriend(View v) {
		// get access to views
		EditText editName = (EditText) this.findViewById(R.id.editName1);
		EditText editPhone = (EditText) this.findViewById(R.id.editPhone1);
		EditText editArea = (EditText) this.findViewById(R.id.editArea1);
		EditText editCity = (EditText) this.findViewById(R.id.editCity1);

		try {
			DBHelper dbhelper = new DBHelper(this);
			SQLiteDatabase db = dbhelper.getWritableDatabase();
			Log.d("Account", "Got Writable database");
			// execute insert command

			ContentValues values = new ContentValues();
			values.put(Database.FRIENDS_NAME, editName.getText().toString());
			values.put(Database.FRIENDS_PHONE, editPhone.getText().toString());
			values.put(Database.FRIENDS_AREA, editArea.getText().toString());
			values.put(Database.FRIENDS_CITY, editCity.getText().toString());

			long rows = db.insert(Database.FRIENDS_TABLE_NAME, null, values);
			db.close();
			if (rows > 0) {
				Toast.makeText(this, "Added Friend Successfully!",
						Toast.LENGTH_LONG).show();
				this.finish();
			} else
				Toast.makeText(this, "Sorry! Could not add Friend!",
						Toast.LENGTH_LONG).show();

		} catch (Exception ex) {
			Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG).show();
		}

	}
}
