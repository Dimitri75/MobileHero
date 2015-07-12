package kei.mobilehero.activities.game;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import kei.mobilehero.R;
import kei.mobilehero.activities.dice.DicesActivity;
import kei.mobilehero.classes.general.Game;

public class GameFormActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_form);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    public void buttonOnClick(View v) {
        switch(v.getId()){
            case R.id.button_saveGame_new_game:
                EditText view_gameName = (EditText) findViewById(R.id.editText_gameName_new_game);
                String gameName = view_gameName.getText().toString();

                if (gameName.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Le formulaire n'est pas correctement rempli.", Toast.LENGTH_SHORT).show();
                }
                else {
                    Game game = new Game(gameName);
                    if (game.save(getApplicationContext())) finish();
                }
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
        getMenuInflater().inflate(R.menu.menu_new_game, menu);
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
