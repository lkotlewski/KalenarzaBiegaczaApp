package pl.com.kotlewski.kalendarzbiegacza.activities;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import pl.com.kotlewski.kalendarzbiegacza.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }

    public void onSearchForRunsClicked(View view){
        Intent searchRunsIntent = new Intent(this, InputSearchFiltersActivity.class);
        startActivity(searchRunsIntent);
    }

    public void onMyRacesCalendarClicked(View view){
        Intent showRacesCalendar = new Intent(this, ViewMyRacesCalendarActivity.class);
        startActivity(showRacesCalendar);
    }
}
