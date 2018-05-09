package pl.com.kotlewski.kalendarzbiegacza.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import org.w3c.dom.Text;

import java.util.Calendar;

import pl.com.kotlewski.kalendarzbiegacza.R;

public class InputSearchFiltersActivity extends AppCompatActivity {
    String [] months = {"Styczeń", "Luty", "Marzec", "Kwiecień", "Maj", "Czerwiec", "Lipiec",
            "Sierpień", "Wrzesień", "Październik", "Listopad", "Grudzień"} ;
    String [] days;
    String [] years;
    Spinner monthSpinner;
    Spinner dayFromSpinner;
    Spinner dayToSpinner;
    Spinner yearSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Calendar calendar = Calendar.getInstance();
        setContentView(R.layout.activity_input_search_filters);

        monthSpinner = findViewById(R.id.month_spinner);
        ArrayAdapter<String> monthArrayAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item, months);
        monthSpinner.setAdapter(monthArrayAdapter);
        monthSpinner.setSelection(calendar.get(Calendar.MONTH));

        days = new String[31];
        for (int i = 0; i < 31; i++)
            days[i] = String.valueOf(i + 1);

        dayFromSpinner = findViewById(R.id.day_from_spinner);
        ArrayAdapter<String> dayArrayAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item, days);
        dayFromSpinner.setAdapter(dayArrayAdapter);
        dayFromSpinner.setSelection(0);

        dayToSpinner = findViewById(R.id.day_to_spinner);
        ArrayAdapter<String> dayArrayAdapter2 = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item, days);
        dayToSpinner.setAdapter(dayArrayAdapter2);
        dayToSpinner.setSelection(30);

        years = new String[2];
        int year = calendar.get(Calendar.YEAR);
        years[0] = String.valueOf(year);
        years[1] = String.valueOf(year + 1);

        yearSpinner = findViewById(R.id.year_spinner);
        ArrayAdapter<String> yearArrayAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item, years);
        yearSpinner.setAdapter(yearArrayAdapter);
        yearSpinner.setSelection(0);

    }

    public void onSearchButtonClicked(View view){
        Intent intent = new Intent(this, ShowRacesActivity.class);
        intent.putExtra("YEAR", (String) yearSpinner.getSelectedItem());
        intent.putExtra("DAY_FROM", (String) dayFromSpinner.getSelectedItem());
        intent.putExtra("DAY_TO", (String) dayToSpinner.getSelectedItem());
        intent.putExtra("MONTH", monthSpinner.getSelectedItemPosition());
        intent.putExtra("CITY", ((EditText)findViewById(R.id.city_text_edit)).getText().toString());
        this.startActivity(intent);
    }
}
