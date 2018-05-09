package pl.com.kotlewski.kalendarzbiegacza.activities.components;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

import pl.com.kotlewski.kalendarzbiegacza.domain.Race;

/**
 * Created by Łukasz on 2018-05-08.
 */

public class CalendarCustomAdapter extends BaseAdapter {
    List<Race> races;
    Context context;

    public CalendarCustomAdapter(Context context, List<Race> races){
        this.context = context;
        this.races = races;
    }
    @Override
    public int getCount() {
        return races.size();
    }

    @Override
    public Object getItem(int i) {
        return races.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Race race = races.get(i);
        CalendarRaceTextView calendarRaceTextView = new CalendarRaceTextView(context, race);
        return calendarRaceTextView;
    }
}
