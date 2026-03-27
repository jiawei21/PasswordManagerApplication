
package com.example.passwordmanager;

import android.content.Intent;
import android.os.Bundle;
import android.text.*;
import android.widget.EditText;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.*;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.room.Room;
import java.util.*;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FloatingActionButton addBtn;
    EditText searchBar;

    AppDatabase db;
    PasswordAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        addBtn = findViewById(R.id.addBtn);
        searchBar = findViewById(R.id.searchBar);

        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "password-db")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        addBtn.setOnClickListener(v ->
                startActivity(new Intent(this, AddEditActivity.class))
        );

        searchBar.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filter(s.toString());
            }
            public void beforeTextChanged(CharSequence s,int a,int b,int c){}
            public void afterTextChanged(Editable s){}
        });

        loadData();
    }

    private void loadData() {
        List<PasswordEntity> list = db.passwordDao().getAll();
        if (adapter == null) {
            adapter = new PasswordAdapter(list, new PasswordAdapter.OnItemAction() {
                @Override
                public void onDelete(PasswordEntity entity) {
                    showDeleteConfirmation(entity);
                }

                @Override
                public void onEdit(PasswordEntity entity) {
                    Intent i = new Intent(MainActivity.this, AddEditActivity.class);
                    i.putExtra("id", entity.id);
                    i.putExtra("site", entity.siteName);
                    i.putExtra("user", entity.username);
                    i.putExtra("pass", entity.password);
                    startActivity(i);
                }
            });
            recyclerView.setAdapter(adapter);
        } else {
            adapter.updateList(list);
        }
    }

    private void showDeleteConfirmation(PasswordEntity entity) {
        new AlertDialog.Builder(this)
                .setTitle("Delete Password")
                .setMessage("Are you sure you want to delete the password for " + entity.siteName + "?")
                .setPositiveButton("Delete", (dialog, which) -> {
                    db.passwordDao().delete(entity);
                    loadData();
                })
                .setNegativeButton("Cancel", null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    private void filter(String text) {
        List<PasswordEntity> all = db.passwordDao().getAll();
        if (text.isEmpty()) {
            adapter.updateList(all);
            return;
        }
        
        List<PasswordEntity> filtered = new ArrayList<>();
        for (PasswordEntity p : all) {
            if (p.siteName.toLowerCase().contains(text.toLowerCase()) ||
                p.username.toLowerCase().contains(text.toLowerCase())) {
                filtered.add(p);
            }
        }
        adapter.updateList(filtered);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }
}
