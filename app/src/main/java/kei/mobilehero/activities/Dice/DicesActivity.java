package kei.mobilehero.activities.dice;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

import kei.mobilehero.R;
import kei.mobilehero.classes.general.*;

public class DicesActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dices);
        findViewById(R.id.listView_dices_result).setVisibility(View.INVISIBLE);
    }

    public void buttonOnClick(View v) {
        switch (v.getId()) {
            case R.id.button_roll_dices:
                final View view = v;
                EditText view_nbDices = (EditText) findViewById(R.id.editText_nbDices_dices);
                EditText view_nbSides = (EditText) findViewById(R.id.editText_nbSides_dices);

                if(!view_nbDices.getText().toString().isEmpty() && !view_nbSides.getText().toString().isEmpty()) {
                    int nbDices = Integer.parseInt(view_nbDices.getText().toString());
                    int nbSides = Integer.parseInt(view_nbSides.getText().toString());

                    final Dice dice = new Dice(nbDices, nbSides);

                    Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.animation_roll_dices);
                    if (animation == null) return;

                    animation.reset();

                    if (view != null) {
                        view.startAnimation(animation);

                        animation.setAnimationListener(new Animation.AnimationListener(){
                            public void onAnimationStart(Animation a){
                                ((Button)view).setText("");
                            }
                            public void onAnimationRepeat(Animation a){}
                            public void onAnimationEnd(Animation a){
                                ArrayList<Integer> listResult = dice.roll();

                                // Set sum result on the dice
                                String result = String.valueOf(dice.getSumOfRolls(listResult));
                                ((Button)view).setText(result);

                                ArrayList<String> listString = dice.listResultToListString(listResult);

                                // Fil the listView with all the results
                                ArrayAdapter<String> myAdapter = new ArrayAdapter<>(getApplicationContext(),
                                        android.R.layout.simple_list_item_1,
                                        android.R.id.text1,
                                        listString);

                                ListView listView = (ListView) findViewById(R.id.listView_dices_result);
                                listView.setAdapter(myAdapter);
                                listView.setVisibility(View.VISIBLE);
                            }
                        });

                    }
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_dices, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        //int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*if (id == R.id.action_dices) {
            Intent i = new Intent(getApplicationContext(), DicesActivity.class);
            startActivity(i);
        }*/

        return super.onOptionsItemSelected(item);
    }
}
