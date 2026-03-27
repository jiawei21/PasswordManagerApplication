
package com.example.passwordmanager;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "passwords")
public class PasswordEntity {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public String siteName;
    public String username;
    public String password;

    public PasswordEntity(String siteName, String username, String password) {
        this.siteName = siteName;
        this.username = username;
        this.password = password;
    }
}
