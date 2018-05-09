package pl.com.kotlewski.kalendarzbiegacza.domain.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.Calendar;

import pl.com.kotlewski.kalendarzbiegacza.domain.Race;

/**
 * Created by Łukasz on 2018-05-05.
 */

@Dao
public interface RaceDao{

    @Insert
    public void addRace(Race race);

    @Delete
    public void deleteRace(Race race);

    @Query("SELECT * FROM race")
    public Race[] getAllRaces();

    @Query("SELECT * FROM race WHERE id = :id")
    public Race getRaceById(int id);

    @Query("SELECT * FROM race WHERE date = :date")
    public Race[] getRaceByDate(long date);
}
