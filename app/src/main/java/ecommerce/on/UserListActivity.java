package ecommerce.on;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserListActivity extends AppCompatActivity {

    ListView listView;
    SQLiteDatabase db;
    ArrayList<CustomList> arrayList;
    ApiInterface apiInterface;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        db = openOrCreateDatabase("EcomApp.db", MODE_PRIVATE, null);
        String tableQuery = "CREATE TABLE IF NOT EXISTS USERS(USERID INTEGER PRIMARY KEY AUTOINCREMENT,FIRSTNAME VARCHAR(50),LASTNAME VARCHAR(50),EMAIL VARCHAR(100),CONTACT INT(10),PASSWORD VARCHAR(20),GENDER VARCHAR(10))";
        db.execSQL(tableQuery);

        listView = findViewById(R.id.user_listview);

        //doSqliteData();
        if(new ConnectionDetector(UserListActivity.this).networkConnected()){
            //new getData().execute();
            pd = new ProgressDialog(UserListActivity.this);
            pd.setMessage("Please Wait...");
            pd.setCancelable(false);
            pd.show();
            getDataRetrofit();
        }
        else{
            new ConnectionDetector(UserListActivity.this).networkDisconnected();
        }

    }

    private void getDataRetrofit() {
        Call<GetUserData> call = apiInterface.getUserData();
        call.enqueue(new Callback<GetUserData>() {
            @Override
            public void onResponse(Call<GetUserData> call, Response<GetUserData> response) {
                pd.dismiss();
                if(response.code()==200){
                    if(response.body().status){
                        arrayList = new ArrayList<>();
                        for(int i=0;i<response.body().userData.size();i++){
                            CustomList list = new CustomList();
                            list.setUserId(response.body().userData.get(i).userid);
                            list.setFirstName(response.body().userData.get(i).firstName);
                            list.setLastName(response.body().userData.get(i).lastName);
                            list.setEmail(response.body().userData.get(i).email);
                            list.setContact(response.body().userData.get(i).contact);
                            list.setGender(response.body().userData.get(i).gender);
                            arrayList.add(list);
                        }
                        UserListAdapter adapter = new UserListAdapter(UserListActivity.this,arrayList);
                        listView.setAdapter(adapter);
                    }
                    else{
                        Toast.makeText(UserListActivity.this, response.body().message, Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(UserListActivity.this, ConstantSp.SERVER_ERROR+response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetUserData> call, Throwable t) {
                pd.dismiss();
                Toast.makeText(UserListActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void doSqliteData() {
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

    private class getData extends AsyncTask<String,String,String> {

        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(UserListActivity.this);
            pd.setMessage("Please Wait...");
            pd.setCancelable(false);
            pd.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            return new MakeServiceCall().MakeServiceCall(ConstantSp.GET_USER_URL,MakeServiceCall.POST,new HashMap<>());
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pd.dismiss();
            try {
                JSONObject object = new JSONObject(s);
                if(object.getBoolean("status")){
                    arrayList = new ArrayList<>();
                    JSONArray array = object.getJSONArray("UserData");
                    for(int i=0;i<array.length();i++){
                        JSONObject jsonObject = array.getJSONObject(i);
                        CustomList list = new CustomList();
                        list.setUserId(jsonObject.getString("userid"));
                        list.setFirstName(jsonObject.getString("first_name"));
                        list.setLastName(jsonObject.getString("last_name"));
                        list.setEmail(jsonObject.getString("email"));
                        list.setContact(jsonObject.getString("contact"));
                        list.setGender(jsonObject.getString("gender"));
                        arrayList.add(list);
                    }
                    UserListAdapter adapter = new UserListAdapter(UserListActivity.this,arrayList);
                    listView.setAdapter(adapter);
                }
                else{
                    Toast.makeText(UserListActivity.this, object.getString("message"), Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
    }
}