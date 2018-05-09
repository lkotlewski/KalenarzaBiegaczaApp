package pl.com.kotlewski.kalendarzbiegacza.domain;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;

@Entity
public class Race implements Serializable {
    @PrimaryKey
    private int id;

    private String name;


    private Calendar date;

    private String distance;

    private String city;

    private String fileUrl;

    public Race() {
    }

    public Race(String name, Calendar date, String distance, String city, String fileUrl) {
        this.name = name;
        this.date = date;
        this.distance = distance;
        this.city = city;
        this.fileUrl = fileUrl;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getId() {

        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    @Override
    public String toString(){

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String formattedDate = dateFormat.format(date.getTime());
        return name + " " + formattedDate + " " + city + " " + distance;
    }
}
