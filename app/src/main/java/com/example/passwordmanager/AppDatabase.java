
package com.example.passwordmanager;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {PasswordEntity.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract PasswordDao passwordDao();
}
