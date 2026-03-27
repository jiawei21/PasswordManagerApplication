
package com.example.passwordmanager;

import androidx.room.*;
import java.util.List;

@Dao
public interface PasswordDao {

    @Insert
    void insert(PasswordEntity p);

    @Update
    void update(PasswordEntity p);

    @Delete
    void delete(PasswordEntity p);

    @Query("SELECT * FROM passwords")
    List<PasswordEntity> getAll();
}
