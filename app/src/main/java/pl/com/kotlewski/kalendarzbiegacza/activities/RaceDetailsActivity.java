package pl.com.kotlewski.kalendarzbiegacza.activities;

import android.app.AlertDialog;
import android.app.LauncherActivity;
import android.app.ProgressDialog;
import android.arch.persistence.room.Room;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import pl.com.kotlewski.kalendarzbiegacza.R;
import pl.com.kotlewski.kalendarzbiegacza.domain.Race;
import pl.com.kotlewski.kalendarzbiegacza.domain.RaceLinkedWebsite;
import pl.com.kotlewski.kalendarzbiegacza.domain.database.AppDatabase;

public class RaceDetailsActivity extends AppCompatActivity {

    Race race;
    RaceLinkedWebsite[] websites;
    private ProgressDialog dialog;
    AppDatabase db;
    List<String> urls = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_race_details);
        race = (Race) getIntent().getSerializableExtra("RACE");

        if(getIntent().getSerializableExtra("WEBSITES") == null)
            getRaceLinkedWebsites(race.getId());
        else {
            Object[] websites_objects = (Object [])getIntent().getSerializableExtra("WEBSITES");
            websites = new RaceLinkedWebsite[websites_objects.length];
            for(int i = 0; i<websites_objects.length; i++){
                websites[i] = (RaceLinkedWebsite) websites_objects[i];
                urls.add(websites[i].getUrl());
            }
            setWebsitesOnView(urls);
        }

        ((TextView)findViewById(R.id.cityEdit)).setText(race.getCity());
        ((TextView)findViewById(R.id.nameEdit)).setText(race.getName());
        ((TextView)findViewById(R.id.distanceEdit)).setText(race.getDistance());
        ((TextView)findViewById(R.id.regimenEdit)).setText(race.getFileUrl());

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String formattedDate = dateFormat.format(race.getDate().getTime());
        ((TextView)findViewById(R.id.dataEdit)).setText(formattedDate);

        db = Room.databaseBuilder(this, AppDatabase.class, "appDatabase").allowMainThreadQueries().build();
        if(db.raceDao().getRaceById(race.getId()) != null) {
            findViewById(R.id.addRaceButton).setEnabled(false);
            findViewById(R.id.deleteRaceButton).setEnabled(true);
        }


    }

    private void getRaceLinkedWebsites(Integer id){

        RequestQueue mRequestQueue = createRequestQueue();
        mRequestQueue.start();

        StringRequest stringRequest = new StringRequest(Request.Method.GET,"http://kalendarzbiegacza.com.pl:8080/findWebsitesByRaceId?raceId=" + id.toString(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject j = null;
                        try {
                            j = new JSONObject(response);
                            JSONArray result = j.getJSONArray("result");
                            addLinkedWebsites(result);
                            dialog.dismiss();
                            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("ERROR", "Error occurred ", error);
                        dialog.dismiss();
                    }
                });
        dialog = new ProgressDialog(this);
        dialog.setMessage("Wczytywanie danych");
        dialog.show();
        mRequestQueue.add(stringRequest);
    }

    private void addLinkedWebsites(JSONArray linkedWebsites) {

        websites = new RaceLinkedWebsite[linkedWebsites.length()];
            try {
                for(int i = 0; i < linkedWebsites.length(); i++) {
                    JSONObject linkedWebsiteJSON = linkedWebsites.getJSONObject(i);
                    RaceLinkedWebsite raceLinkedWebsite = new RaceLinkedWebsite();
                    raceLinkedWebsite.setId(linkedWebsiteJSON.getInt("id"));
                    raceLinkedWebsite.setRaceId(linkedWebsiteJSON.getInt("raceId"));
                    raceLinkedWebsite.setUrl(linkedWebsiteJSON.getString("url"));
                    websites[i] = raceLinkedWebsite;

                    urls.add(raceLinkedWebsite.getUrl());
                }
                setWebsitesOnView(urls);

            }

            catch (JSONException e) {
                e.printStackTrace();
            }
        }

    private void setWebsitesOnView(List<String> urls) {
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>
                (this, R.layout.link_text_view, urls);


        ListView lv = findViewById(R.id.websiteslistView);
        lv.setAdapter(arrayAdapter);
    }

    @NonNull
    public RequestQueue createRequestQueue() {
        RequestQueue mRequestQueue;
        Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap
        Network network = new BasicNetwork(new HurlStack());
        mRequestQueue = new RequestQueue(cache, network);
        return mRequestQueue;
    }

    public void onAddRaceToCalendarClicked(View view){

        db.raceDao().addRace(race);
        db.raceLinkedWebsiteDao().addRaceLinkedWebsites(websites);
        findViewById(R.id.addRaceButton).setEnabled(false);
        findViewById(R.id.deleteRaceButton).setEnabled(true);

        showDialog("Dodano do kalendarza.");

    }

    public void onDeleteRaceFromCalendarClicked(View view){

        db.raceDao().deleteRace(race);
        db.raceLinkedWebsiteDao().deleteRaceLinkedWebsites(websites);
        findViewById(R.id.addRaceButton).setEnabled(true);
        findViewById(R.id.deleteRaceButton).setEnabled(false);

        showDialog("Usunięto z kalendarza.");

    }

    private void showDialog(String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setMessage(message);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

}
