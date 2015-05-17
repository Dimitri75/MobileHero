package kei.mobilehero.activities.round;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import kei.mobilehero.R;
import kei.mobilehero.activities.character.CharactersActivity;
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

        if((game = (Game) getIntent().getExtras().get("game")) != null)
            init(game);
    }

    public void init(Game game){
        ListView listView;
        game = Loader.getInstance().loadRounds(getApplicationContext(), game);

        if(!game.getRounds().isEmpty()) {
            // This is the array adapter, it takes the context of the activity as a
            // first parameter, the type of list view as a second parameter and your
            // array as a third parameter.
            final ArrayAdapter<Round> mAdapter = new ArrayAdapter<Round>(this,
                    android.R.layout.simple_list_item_1,
                    android.R.id.text1,
                    game.getRounds());

            listView = (ListView) findViewById(R.id.listView_rounds);
            listView.setAdapter(mAdapter);

            // Create a ListView-specific touch listener. ListViews are given special treatment because
            // by default they handle touches for their list items... i.e. they're in charge of drawing
            // the pressed state (the list selector), handling list item clicks, etc.
            SwipeDismissListViewTouchListener swipeTouchListener =
                    new SwipeDismissListViewTouchListener(
                            listView,
                            new SwipeDismissListViewTouchListener.DismissCallbacks() {
                                @Override
                                public boolean canDismiss(int position) {
                                    if (mAdapter.getItem(position).getCharacters().isEmpty())
                                        return true;
                                    else {
                                        Log.v("Rounds init()", "Cannot delete a round which is not empty");
                                        return false;
                                    }
                                }

                                @Override
                                public void onDismiss(ListView listView, int[] reverseSortedPositions) {
                                    for (int position : reverseSortedPositions) {
                                        {
                                            if (mAdapter.getItem(position).delete(getApplicationContext()))
                                                mAdapter.remove(mAdapter.getItem(position));
                                        }
                                    }
                                    mAdapter.notifyDataSetChanged();
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
                    i.putExtra("round", mAdapter.getItem(position));
                    startActivity(i);
                }
            });
        }
    }

    public void buttonOnClick(View v) {
        switch(v.getId()){
            case R.id.button_new_rounds:
                Intent i = new Intent(getApplicationContext(), NewRoundActivity.class);
                startActivity(i);
                break;
        }
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
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
