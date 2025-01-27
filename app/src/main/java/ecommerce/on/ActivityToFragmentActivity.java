package ecommerce.on;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

public class ActivityToFragmentActivity extends AppCompatActivity {

    Button openFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_fragment);

        openFragment = findViewById(R.id.open_fragment);
        openFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager manager = getSupportFragmentManager();
                manager.beginTransaction().replace(R.id.activity_layout,new DemoFragment()).addToBackStack("").commit();
            }
        });

    }
}