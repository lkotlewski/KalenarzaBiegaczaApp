package pl.com.kotlewski.kalendarzbiegacza.activities.components;


import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.InputType;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.TableRow;
import android.widget.TextView;

import java.text.SimpleDateFormat;

import pl.com.kotlewski.kalendarzbiegacza.R;
import pl.com.kotlewski.kalendarzbiegacza.activities.RaceDetailsActivity;
import pl.com.kotlewski.kalendarzbiegacza.domain.Race;

import static pl.com.kotlewski.kalendarzbiegacza.R.color.colorAccent;


public class RaceTableRow extends TableRow {
    Race race;
    Context context;
    private ProgressDialog dialog;
    TableRow tableRow;

    public RaceTableRow(final Context context, final Race race) {
        super(context);
        this.context = context;
        this.race = race;
        this.tableRow = this;
        this.setPadding(0, 20, 0, 20);
        this.setFocusable(true);
        fillRowWithRaceData();

        this.setOnClickListener(new OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {
                view.startAnimation(AnimationUtils.loadAnimation(context, android.R.anim.fade_in));
                Intent intent = new Intent(getContext(), RaceDetailsActivity.class);
                intent.putExtra("RACE", race);
                context.startActivity(intent);
            }
        });
    }

    public Race getRace() {
        return race;
    }

    public void setRace(Race race) {
        this.race = race;
    }

    private void fillRowWithRaceData() {

        TextView nameTextView = new TextView(context);
        nameTextView.setText(race.getName());
        nameTextView.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        nameTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        nameTextView.setSingleLine(false);
        nameTextView.setWidth(0);

        TextView dateTextView = new TextView(context);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String formattedDate = dateFormat.format(race.getDate().getTime());
        dateTextView.setText(formattedDate);
        dateTextView.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        dateTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        dateTextView.setWidth(0);
        dateTextView.setPadding(50,0,0, 0);


        TextView cityTextView = new TextView(context);
        cityTextView.setText(race.getCity());
        cityTextView.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        cityTextView.setSingleLine(false);
        cityTextView.setWidth(0);
        cityTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);

        TextView distanceTextView = new TextView(context);
        distanceTextView.setText(race.getDistance());
        distanceTextView.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        distanceTextView.setSingleLine(false);
        distanceTextView.setWidth(0);
        distanceTextView.setPadding(50,0,0, 0);
        distanceTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);

        this.addView(nameTextView);
        this.addView(dateTextView);
        this.addView(cityTextView);
        this.addView(distanceTextView);


    }
}
