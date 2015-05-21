package kei.mobilehero.activities.character;

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
import kei.mobilehero.classes.general.*;
import kei.mobilehero.classes.general.Character;
import kei.mobilehero.custom.widgets.MyCustomEditText;

public class NewCharacterActivity extends ActionBarActivity {
    private Game game;
    private Round round;
    private Character character;

    private EditText nameText;
    private EditText genderText;
    private EditText alignmentText;
    private EditText raceText;
    private EditText classNameText;
    private EditText levelText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_character);

        if((game = (Game) getIntent().getExtras().get("game")) == null ||
                (round = (Round) getIntent().getExtras().get("round")) == null){
            Log.v("NewCharacter onCreate()", "Couldn't get the extras.");
            finish();
        }

        // Instanciate the views
        nameText = (EditText) findViewById(R.id.editText_characterName_new_character);
        genderText = (EditText) findViewById(R.id.editText_characterGender_new_character);
        alignmentText = (EditText) findViewById(R.id.editText_characterAlignment_new_character);
        raceText = (EditText) findViewById(R.id.editText_characterRace_new_character);
        classNameText = (EditText) findViewById(R.id.editText_characterClassName_new_character);
        levelText = (MyCustomEditText) findViewById(R.id.editText_characterLevel_new_character);

        if ((character = (Character) getIntent().getExtras().get("character")) != null) init();
    }

    /**
     * Charge les donnees du personnage
     */
    public void init(){
        nameText.setText(character.getName());
        genderText.setText(character.getGender());
        alignmentText.setText(character.getAlignment());
        raceText.setText(character.getRace());
        classNameText.setText(character.getClassName());
        levelText.setText(String.valueOf(character.getLevel()));
    }

    public void buttonOnClick(View v) {
        switch(v.getId()){
            case R.id.button_saveCharacter_new_character:
                String characterName;
                if (!(characterName = nameText.getText().toString()).isEmpty()){
                    if (character == null){
                        character = new Character(characterName);
                    }
                    else{
                        character.setName(characterName);
                        character.setGender(genderText.getText().toString());
                        character.setAlignment(alignmentText.getText().toString());
                        character.setRace(raceText.getText().toString());
                        character.setClassName(classNameText.getText().toString());
                        character.setLevel(Integer.parseInt(levelText.getText().toString()));
                    }
                    if (character.save(getApplicationContext(), game.getName(), round.getName())) finish();
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
        getMenuInflater().inflate(R.menu.menu_new_character, menu);
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
