package pl.com.kotlewski.kalendarzbiegacza.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TableLayout;

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

import java.util.Calendar;

import pl.com.kotlewski.kalendarzbiegacza.R;
import pl.com.kotlewski.kalendarzbiegacza.activities.components.RaceTableRow;
import pl.com.kotlewski.kalendarzbiegacza.domain.Race;

public class ShowRacesActivity extends AppCompatActivity {
    TableLayout racesTable;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_races);
        racesTable = (TableLayout) findViewById(R.id.table);
        getData();
    }

    private void getData(){

        RequestQueue mRequestQueue = createRequestQueue();
        mRequestQueue.start();

        String url = getUrl(getIntent());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject j = null;
                        try {
                            j = new JSONObject(response);
                            JSONArray result = j.getJSONArray("result");
                            populateRaceTable(result);
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

    @NonNull
    public RequestQueue createRequestQueue() {
        RequestQueue mRequestQueue;
        Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap
        Network network = new BasicNetwork(new HurlStack());
        mRequestQueue = new RequestQueue(cache, network);
        return mRequestQueue;
    }

    private void populateRaceTable(JSONArray raceJSONArray){
        for(int i = 0; i < raceJSONArray.length(); i++)
        {
            try {
                JSONObject raceJSONObject = raceJSONArray.getJSONObject(i);

                Race race = new Race();
                race.setId(raceJSONObject.getInt("id"));
                race.setName(raceJSONObject.getString("name"));
                race.setCity(raceJSONObject.getString("city"));
                race.setDistance(raceJSONObject.getString("distance"));
                race.setFileUrl(raceJSONObject.getString("fileUrl"));
                String date = shortenDateFormat(raceJSONObject.getString("date"));
                String [] parsedDate = date.split("-");
                Calendar raceCalendar = Calendar.getInstance();
                raceCalendar.set(Integer.parseInt(parsedDate[0]),Integer.parseInt(parsedDate[1])-1, Integer.parseInt(parsedDate[2]),0,0,0);
                race.setDate(raceCalendar);

                RaceTableRow raceTableRow = new RaceTableRow(this, race);
                racesTable.addView(raceTableRow);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    private String getUrl(Intent intent){
        String year = (String) intent.getSerializableExtra("YEAR");
        String dayFrom = (String) intent.getSerializableExtra("DAY_FROM");
        String dayTo = (String) intent.getSerializableExtra("DAY_TO");
        String month = String.valueOf((int) intent.getSerializableExtra("MONTH"));
        String city = (String) intent.getSerializableExtra("CITY");

        String url = "http://kalendarzbiegacza.com.pl:8080/findRacesByParams?year=" + year + "&month=" + month +
        "&dayFrom=" + dayFrom + "&dayTo=" + dayTo;

        if(!city.isEmpty())
            url = url+ "&city=" + city;
        return url;

    }

    @NonNull
    private String shortenDateFormat(String extendedDate){
        return extendedDate.substring(0, 10);
    }
}
