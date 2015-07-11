package kei.mobilehero.activities.round;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import kei.mobilehero.R;
import kei.mobilehero.activities.dice.DicesActivity;
import kei.mobilehero.classes.general.Game;
import kei.mobilehero.classes.general.Round;

public class NewRoundActivity extends ActionBarActivity {
    Game game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_round_form);

        if((game = (Game) getIntent().getExtras().get("game")) == null){
            Log.v("NewRound onCreate()","Couldn't get the game.");
            finish();
        }
    }

    public void buttonOnClick(View v) {
        switch(v.getId()){
            case R.id.button_saveRound_new_round:
                EditText view_roundName = (EditText) findViewById(R.id.editText_roundName_new_round);
                String roundName = view_roundName.getText().toString();

                Round round = new Round(roundName);
                if(round.save(getApplicationContext(), game)) finish();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    /**
     * Dispatch onPause() to fragments.
     */
    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_round, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_dices) {
            Intent i = new Intent(getApplicationContext(), DicesActivity.class);
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }
}
