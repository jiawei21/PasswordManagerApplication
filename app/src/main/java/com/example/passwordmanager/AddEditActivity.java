
package com.example.passwordmanager;

import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

public class AddEditActivity extends AppCompatActivity {

    EditText site, username, password;
    Button saveBtn;

    AppDatabase db;
    int id = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit);

        site = findViewById(R.id.site);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        saveBtn = findViewById(R.id.saveBtn);

        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "password-db")
                .allowMainThreadQueries()
                .build();

        if (getIntent().hasExtra("id")) {
            id = getIntent().getIntExtra("id", -1);
            site.setText(getIntent().getStringExtra("site"));
            username.setText(getIntent().getStringExtra("user"));
            password.setText(
                EncryptionUtil.decrypt(getIntent().getStringExtra("pass"))
            );
        }

        saveBtn.setOnClickListener(v -> {
            String encryptedPass = EncryptionUtil.encrypt(password.getText().toString());

            if (id == -1) {
                db.passwordDao().insert(new PasswordEntity(
                        site.getText().toString(),
                        username.getText().toString(),
                        encryptedPass
                ));
            } else {
                PasswordEntity p = new PasswordEntity(
                        site.getText().toString(),
                        username.getText().toString(),
                        encryptedPass
                );
                p.id = id;
                db.passwordDao().update(p);
            }
            finish();
        });
    }
}
