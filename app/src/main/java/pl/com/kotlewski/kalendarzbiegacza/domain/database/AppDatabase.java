package pl.com.kotlewski.kalendarzbiegacza.domain.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import pl.com.kotlewski.kalendarzbiegacza.domain.Race;
import pl.com.kotlewski.kalendarzbiegacza.domain.RaceLinkedWebsite;

/**
 * Created by Łukasz on 2018-05-05.
 */

@Database(version = 1, entities = {Race.class, RaceLinkedWebsite.class})
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {

    abstract public RaceDao raceDao();
    abstract public RaceLinkedWebsiteDao raceLinkedWebsiteDao();
}
