package kei.mobilehero.activities.round;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;

import kei.mobilehero.R;
import kei.mobilehero.activities.character.CharactersActivity;
import kei.mobilehero.activities.dice.DicesActivity;
import kei.mobilehero.classes.general.Game;
import kei.mobilehero.classes.general.Round;
import kei.mobilehero.classes.utils.persistence.Loader;
import kei.mobilehero.classes.utils.swipe.SwipeDismissListViewTouchListener;

public class RoundsActivity extends ActionBarActivity {
    private Game game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rounds);

        if((game = (Game) getIntent().getExtras().get("game")) == null){
            Log.v("NewRound onCreate()", "Couldn't get the game.");
            finish();
        }

        init();
    }

    public void init() {
        game = Loader.getInstance().loadRounds(getApplicationContext(), game);

        if (game.getRounds().isEmpty()) return;

        // This is the array adapter, it takes the context of the activity as a
        // first parameter, the type of list view as a second parameter and your
        // array as a third parameter.
        final ArrayAdapter<Round> myAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                game.getRounds());

        ListView listView = (ListView) findViewById(R.id.listView_rounds);
        listView.setAdapter(myAdapter);

        // Create a ListView-specific touch listener. ListViews are given special treatment because
        // by default they handle touches for their list items... i.e. they're in charge of drawing
        // the pressed state (the list selector), handling list item clicks, etc.
        SwipeDismissListViewTouchListener swipeTouchListener =
                new SwipeDismissListViewTouchListener(
                        listView,
                        new SwipeDismissListViewTouchListener.DismissCallbacks() {

                            @Override
                            public boolean canDismiss(int position) {
                                return true;
                            }

                            @Override
                            public void onDismiss(ListView listView, int[] reverseSortedPositions) {
                                for (int position : reverseSortedPositions) {
                                    if (myAdapter.getItem(position).delete(getApplicationContext(), game))
                                        myAdapter.remove(myAdapter.getItem(position));
                                    else
                                        Toast.makeText(getApplicationContext(), getString(R.string.toastCannotDeleteRound), Toast.LENGTH_SHORT).show();
                                }
                                myAdapter.notifyDataSetChanged();
                            }
                        });
        listView.setOnTouchListener(swipeTouchListener);
        // Setting this scroll listener is required to ensure that during ListView scrolling,
        // we don't look for swipes.
        listView.setOnScrollListener(swipeTouchListener.makeScrollListener());

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getApplicationContext(), CharactersActivity.class);
                i.putExtra("round", myAdapter.getItem(position));
                i.putExtra("game", game);
                startActivity(i);
            }
        });
    }

    public void buttonOnClick(View v) {
        switch(v.getId()){
            case R.id.button_newRound_rounds:
                Intent i = new Intent(getApplicationContext(), RoundFormActivity.class);
                i.putExtra("game", game);
                startActivity(i);
                break;
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        init();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_rounds, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch (item.getItemId()) {
            case R.id.action_dices:
                Intent i = new Intent(getApplicationContext(), DicesActivity.class);
                startActivity(i);
                break;
            case R.id.action_importzip:
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*");
                intent.addCategory(Intent.CATEGORY_OPENABLE);

                try {
                    startActivityForResult(Intent.createChooser(intent, "Select a File to Upload"), 0);
                } catch (android.content.ActivityNotFoundException ex) {
                    // Potentially direct the user to the Market with a Dialog
                    Toast.makeText(this, "Please install a File Manager.", Toast.LENGTH_SHORT).show();
                }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 0:
                if (resultCode == RESULT_OK) {
                    try {
                        Loader.getInstance().importRoundFromZip(getApplicationContext(), new File(data.getData().getPath()), game);
                    } catch (Exception e) {
                        Toast.makeText(this, "Error while importing ZIP file", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
