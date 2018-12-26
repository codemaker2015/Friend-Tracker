package com.flocater;

import com.flocator.R;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class SeeFriends extends Activity {
	TextView friend_id;
	ListView listFriends;
	@Override
	public void onCreate(Bundle savedInstanceState) {
	  super.onCreate(savedInstanceState);
	  setContentView(R.layout.list);
	  try {
		  DBHelper dbhelper = new DBHelper(this);
		  SQLiteDatabase db = dbhelper.getReadableDatabase();
		  Cursor frnds = db.query(Database.FRIENDS_TABLE_NAME, null, null, null, null, null, null);
		  String from[] = {Database.FRIENDS_ID,Database.FRIENDS_NAME};
		  int to[] = {R.id.textFriendId,R.id.textFrnds};
		  
		  SimpleCursorAdapter ca = new SimpleCursorAdapter (this, R.layout.list_friends,frnds,from,to);
		  
		  listFriends = (ListView) this.findViewById(R.id.listFriends);
		  listFriends.setAdapter(ca);
		  db.close();
	  } catch(Exception e) {}
	  
	  listFriends.setOnItemClickListener( new OnItemClickListener() {
			
			public void onItemClick(AdapterView<?> parent, View selectedView, int arg2,long arg3) {
				friend_id = (TextView) selectedView.findViewById(R.id.textFriendId);
				Log.d("Friends", "Selected Friend Id : " + friend_id.getText().toString());
				Intent intent = new Intent(SeeFriends.this, UpdateFriends.class);
				intent.putExtra("frndid", friend_id.getText().toString());
				startActivity(intent);
			}
		});
	}
}
