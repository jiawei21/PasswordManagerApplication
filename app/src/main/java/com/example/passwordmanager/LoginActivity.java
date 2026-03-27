
package com.example.passwordmanager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText pass = findViewById(R.id.passwordInput);
        Button btn = findViewById(R.id.loginBtn);

        btn.setOnClickListener(v -> {
            if(pass.getText().toString().equals("1234")){
                startActivity(new Intent(this, MainActivity.class));
            } else {
                Toast.makeText(this,"Wrong Password",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
