package tech.linjiang.android.pandora.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import tech.linjiang.android.pandora.db.entity.Drink;

@Dao
public interface TestDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long[] insert(Drink... drinks);

    @Query("DELETE FROM Drink")
    void delete();
}
