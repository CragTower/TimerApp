package com.cragtowercreations.timerapp;

import static android.icu.text.ListFormatter.Type.OR;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    // Declare variables
    TextView timerView;
    Button startStopButton;
    boolean timerStarted = false;
    Timer timer;
    TimerTask timerTask;
    Double time = 0.0;
    Spinner spinnerSeconds;
    Spinner spinnerMinutes;
    Spinner spinnerHours;
    Integer[] secondsMinutesHours;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize variables
        timerView = (TextView) findViewById(R.id.timerView);
        startStopButton = (Button) findViewById(R.id.startStopButton);
        timer = new Timer();
        secondsMinutesHours = new Integer[60];
        spinnerSeconds = (Spinner) findViewById(R.id.spinnerSeconds);
        spinnerMinutes = (Spinner) findViewById(R.id.spinnerMinutes);
        spinnerHours = (Spinner) findViewById(R.id.spinnerHours);

        // Fill secondsMinutesHours Array with times
        for (int i = 0; i < 60; i++)
        {
            secondsMinutesHours[i] = i;
        }

        // Create ArrayAdapter
        ArrayAdapter<Integer> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, secondsMinutesHours);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Set the ArrayAdapter to the spinners
        spinnerSeconds.setAdapter(adapter);
        spinnerMinutes.setAdapter(adapter);
        spinnerHours.setAdapter(adapter);
    }

    public void resetPressed(View view)
    {
        if (timerTask != null)
        {
            timerTask.cancel();
            time = 0.0;
            timerStarted = false;
            timerView.setText("00 : 00 : 00");
            startStopButton.setText("START");
        }
    }

    public void startStopPressed(View view)
    {
        // Checks if timer is on or off
        if (timerStarted == false)
        {
            timerStarted = true;
            startStopButton.setText("STOP");

            startTimer();
        }
        else
        {
            timerStarted = false;
            startStopButton.setText("START");

            timerTask.cancel();
        }
    }

    private void startTimer()
    {
        timerTask = new TimerTask()
        {
            @Override
            public void run()
            {
                runOnUiThread(new Runnable()
                {
                    @Override
                    public void run() {
                        timerView.setText(getTimerText());
                        time++;
                    }
                });
            }
        };
        timer.scheduleAtFixedRate(timerTask, 0, 1000);
    }

    private String getTimerText()
    {
        int rounded = (int) Math.round(time);

        int seconds = ((rounded % 86400) % 3600) % 60;
        int minutes = ((rounded % 86400) % 3600) / 60;
        int hours = ((rounded % 86400) / 3600);

        checkDuration(seconds);

        return formatTime(seconds, minutes, hours);
    }

    private String formatTime(int seconds, int minutes, int hours)
    {
        return String.format("%02d", hours) + " : " + String.format("%02d", minutes) + " : " + String.format("%02d", seconds);
    }
    
    private void checkDuration(int seconds)
    {
        if (seconds == 0)
        {
            Toast.makeText(this, "This actually worked?!", Toast.LENGTH_SHORT).show();
        }
    }
}