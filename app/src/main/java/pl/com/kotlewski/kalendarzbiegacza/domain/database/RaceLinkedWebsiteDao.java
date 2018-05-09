package pl.com.kotlewski.kalendarzbiegacza.domain.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import pl.com.kotlewski.kalendarzbiegacza.domain.Race;
import pl.com.kotlewski.kalendarzbiegacza.domain.RaceLinkedWebsite;

/**
 * Created by Łukasz on 2018-05-05.
 */

@Dao
public interface RaceLinkedWebsiteDao {

    @Insert
    public void addRaceLinkedWebsites(RaceLinkedWebsite ... raceLinkedWebsites);

    @Delete
    public void deleteRaceLinkedWebsites(RaceLinkedWebsite ... raceLinkedWebsite);

    @Query("Select * FROM RaceLinkedWebsite WHERE raceId = :raceId")
    public RaceLinkedWebsite[] getWebsitesLinkedToRace(int raceId);
}
