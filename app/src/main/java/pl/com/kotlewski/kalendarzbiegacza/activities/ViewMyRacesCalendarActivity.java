package pl.com.kotlewski.kalendarzbiegacza.activities;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import pl.com.kotlewski.kalendarzbiegacza.R;
import pl.com.kotlewski.kalendarzbiegacza.activities.components.CalendarCustomAdapter;
import pl.com.kotlewski.kalendarzbiegacza.domain.Race;
import pl.com.kotlewski.kalendarzbiegacza.domain.database.AppDatabase;

public class ViewMyRacesCalendarActivity extends AppCompatActivity {
    AppDatabase db;
    ArrayList<Race> races;
    ListView dayRaceInfoListView;
    Context context;
    CompactCalendarView compactCalendar;
    Date currentClickedDate = new Date(System.currentTimeMillis());
    private SimpleDateFormat dateFormatMonth = new SimpleDateFormat("MMM yyyy", Locale.getDefault());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_my_races);
        context = this;
        compactCalendar = (CompactCalendarView) findViewById(R.id.races_calendar);
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setTitle(dateFormatMonth.format(Calendar.getInstance().getTime()));

        dayRaceInfoListView = findViewById(R.id.raceInfoTextView);
        db = Room.databaseBuilder(this, AppDatabase.class, "appDatabase").allowMainThreadQueries().build();

        compactCalendar.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                currentClickedDate = dateClicked;
                List<Event> eventsOnSelectedDay = compactCalendar.getEvents(dateClicked);
                List<Race> dayRaces = new ArrayList<>();

                for (Event event : eventsOnSelectedDay){
                    dayRaces.add((Race)event.getData());
                }
                final CalendarCustomAdapter calendarCustomAdapter = new CalendarCustomAdapter(context, dayRaces);
                dayRaceInfoListView.setAdapter(calendarCustomAdapter);

            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                actionBar.setTitle(dateFormatMonth.format(firstDayOfNewMonth));
                dayRaceInfoListView.setAdapter(null);
            }

        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        compactCalendar.removeAllEvents();
        races = new ArrayList<Race>(Arrays.asList(db.raceDao().getAllRaces()));
        for(Race race : races) {
            Event event = new Event(Color.WHITE, race.getDate().getTimeInMillis(), race);
            compactCalendar.addEvent(event);
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentClickedDate);
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND,0);
        List<Race> dayRaces = new ArrayList<>(Arrays.asList(db.raceDao().getRaceByDate(calendar.getTimeInMillis())));
        final CalendarCustomAdapter calendarCustomAdapter = new CalendarCustomAdapter(context, dayRaces);
        dayRaceInfoListView.setAdapter(calendarCustomAdapter);


    }
}
