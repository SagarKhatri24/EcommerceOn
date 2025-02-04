package ecommerce.on;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class UserListActivity extends AppCompatActivity {

    ListView listView;
    SQLiteDatabase db;
    ArrayList<CustomList> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        db = openOrCreateDatabase("EcomApp.db", MODE_PRIVATE, null);
        String tableQuery = "CREATE TABLE IF NOT EXISTS USERS(USERID INTEGER PRIMARY KEY AUTOINCREMENT,FIRSTNAME VARCHAR(50),LASTNAME VARCHAR(50),EMAIL VARCHAR(100),CONTACT INT(10),PASSWORD VARCHAR(20),GENDER VARCHAR(10))";
        db.execSQL(tableQuery);

        listView = findViewById(R.id.user_listview);

        String loginQuery = "SELECT * FROM USERS ORDER BY USERID DESC";
        Cursor cursor = db.rawQuery(loginQuery,null);
        if(cursor.getCount()>0) {
            arrayList = new ArrayList<>();
            while (cursor.moveToNext()){
                CustomList list = new CustomList();
                list.setUserId(cursor.getString(0));
                list.setFirstName(cursor.getString(1));
                list.setLastName(cursor.getString(2));
                list.setEmail(cursor.getString(3));
                list.setContact(cursor.getString(4));
                list.setGender(cursor.getString(6));
                arrayList.add(list);
            }
            UserListAdapter adapter = new UserListAdapter(UserListActivity.this,arrayList);
            listView.setAdapter(adapter);
        }
        else{
            Toast.makeText(UserListActivity.this, "User Not Found", Toast.LENGTH_SHORT).show();
        }

    }
}