package kei.mobilehero.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;

import kei.mobilehero.R;
import kei.mobilehero.activities.game.GamesActivity;

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void buttonOnClick(View v) {
        switch(v.getId()){
            case R.id.main_activity:
                Intent i = new Intent(getApplicationContext(), GamesActivity.class);
                startActivity(i);
                finish();
                break;
        }
    }

}
