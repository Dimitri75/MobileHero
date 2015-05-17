package kei.mobilehero.activities;

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

import java.util.ArrayList;

import kei.mobilehero.R;
import kei.mobilehero.classes.general.Game;
import kei.mobilehero.classes.utils.persistence.Loader;
import kei.mobilehero.classes.utils.swipe.SwipeDismissListViewTouchListener;

public class GamesActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_games);

        init();
    }

    public void buttonOnClick(View v) {
        switch(v.getId()){
            case R.id.button_new_games:
                Intent i = new Intent(getApplicationContext(), NewGameActivity.class);
                startActivity(i);
                break;
        }
    }

    public void init() {
        ListView listView;
        ArrayList<Game> games = Loader.getInstance().loadGames(getApplicationContext());

        // This is the array adapter, it takes the context of the activity as a
        // first parameter, the type of list view as a second parameter and your
        // array as a third parameter.
        final ArrayAdapter<Game> mAdapter = new ArrayAdapter<Game>(this,
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                games);

        listView = (ListView) findViewById(R.id.listView_games);
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
                                if (mAdapter.getItem(position).getRounds().isEmpty()) return true;
                                else {
                                    Log.v("Games init()", "Cannot delete a game which is not empty");
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
                Intent i = new Intent(getApplicationContext(), RoundsActivity.class);
                i.putExtra("game", mAdapter.getItem(position));
                startActivity(i);
            }
        });
    }




    @Override
    protected void onRestart() {
        super.onRestart();
        recreate();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
            Intent i = new Intent(getApplicationContext(), DicesActivity.class);
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }
}
