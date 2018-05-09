package pl.com.kotlewski.kalendarzbiegacza.activities.components;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import pl.com.kotlewski.kalendarzbiegacza.activities.RaceDetailsActivity;
import pl.com.kotlewski.kalendarzbiegacza.domain.Race;
import pl.com.kotlewski.kalendarzbiegacza.domain.database.AppDatabase;

/**
 * Created by Łukasz on 2018-05-07.
 */

public class CalendarRaceTextView extends android.support.v7.widget.AppCompatTextView {
    Race race;
    Context context;
    public CalendarRaceTextView(final Context context, final Race race) {
        super(context);
        this.context = context;
        this.race = race;
        this.setText(race.toString());

        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(AnimationUtils.loadAnimation(context, android.R.anim.fade_in));
                Intent intent = new Intent(getContext(), RaceDetailsActivity.class);
                intent.putExtra("RACE", race);
                AppDatabase db = Room.databaseBuilder(context, AppDatabase.class, "appDatabase").allowMainThreadQueries().build();
                intent.putExtra("WEBSITES", db.raceLinkedWebsiteDao().getWebsitesLinkedToRace(race.getId()));
                context.startActivity(intent);
            }
        });
    }


}
