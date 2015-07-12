package kei.mobilehero.activities;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;

import kei.mobilehero.R;
import kei.mobilehero.activities.game.GamesActivity;

public class MainActivity extends ActionBarActivity {
    private MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playThemeSong();
    }

    public void buttonOnClick(View v) {
        switch (v.getId()) {
            case R.id.main_activity:
                Intent i = new Intent(getApplicationContext(), GamesActivity.class);
                startActivity(i);
                mp.stop();
                finish();
                break;
        }
    }

    public void playThemeSong(){
        mp = MediaPlayer.create(getApplicationContext(), R.raw.theme);
        mp.setLooping(true);
        mp.start();
    }
}
