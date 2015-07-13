package kei.mobilehero.activities;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.TextView;

import kei.mobilehero.R;
import kei.mobilehero.activities.game.GamesActivity;

public class MainActivity extends ActionBarActivity {
    private static MediaPlayer mp = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (mp == null)
            playThemeSong();

        TextView myText = (TextView) findViewById(R.id.textView_main );

        Animation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(1000);
        anim.setStartOffset(20);
        anim.setRepeatMode(Animation.REVERSE);
        anim.setRepeatCount(Animation.INFINITE);
        myText.startAnimation(anim);
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
