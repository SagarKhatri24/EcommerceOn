package ecommerce.on;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class HomeActivity extends AppCompatActivity {

    TextView username,password;

    //RadioButton male,female;
    RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        username = findViewById(R.id.home_username);
        password = findViewById(R.id.home_password);

        /*username.setText(MainActivity.username.getText().toString());
        password.setText(MainActivity.password.getText().toString());*/

        Bundle bundle = getIntent().getExtras();
        username.setText(bundle.getString("USERNAME"));
        password.setText(bundle.getString("PASSWORD"));

        /*male = findViewById(R.id.home_male);
        female = findViewById(R.id.home_female);

        male.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(HomeActivity.this, male.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });

        female.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(HomeActivity.this, female.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });*/

        radioGroup = findViewById(R.id.home_gender);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton radioButton = findViewById(i);
                Toast.makeText(HomeActivity.this, radioButton.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}