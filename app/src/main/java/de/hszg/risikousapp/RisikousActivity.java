package de.hszg.risikousapp;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.content.res.Configuration;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import de.hszg.risikousapp.connectionError.ConnectionErrorFragment;
import de.hszg.risikousapp.publicationList.PublicationListFragment;
import de.hszg.risikousapp.questionnaire.QuestionnaireFragment;
import de.hszg.risikousapp.welcome.WelcomeFragment;

public class RisikousActivity extends FragmentActivity {

	private static final String TAG = RisikousActivity.class.getSimpleName();

	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;
	
	private CharSequence mDrawerTitle;
	private CharSequence mTitle;
	private String[] mDrawerItems;

    /**
     * Start the App and initialize the navigation drawer.
     * Set a custom shadow that overlays the main content when the drawer opens,
     * set the OnItemClickListener so something happens when a user clicks on an item in the navigation drawer.
     * Enable ActionBar app icon to behave as action to toggle nav drawer.
     * @param savedInstanceState
     */
    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setContentView(R.layout.activity_main);
		
		mTitle = mDrawerTitle = getTitle();
		
		mDrawerItems = getResources().getStringArray(R.array.drawer_titles);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);

		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,  GravityCompat.START);

		mDrawerList.setAdapter(new ArrayAdapter<>(this, R.layout.drawer_list_item, mDrawerItems));
		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

		mDrawerToggle = new ActionBarDrawerToggle(
				this, 
				mDrawerLayout,
				R.string.drawer_open, 
				R.string.drawer_close
				) {
			public void onDrawerClosed(View view) {
				getActionBar().setTitle(mTitle);
				invalidateOptionsMenu();
			}
			
			public void onDrawerOpened(View drawerView) {
				getActionBar().setTitle(mDrawerTitle);
				invalidateOptionsMenu();
			}
		};
		
		mDrawerLayout.setDrawerListener(mDrawerToggle);

		if(savedInstanceState == null) {
			navigateTo(0);
		}
	
	}

    /**
     * Function to open or close the NavigationDrawer when the user clicking the ActionBar app icon.
     * @param item
     * @return
     */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if(mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		
		return super.onOptionsItemSelected(item);
	}
	
	/*
	 * When using the ActionBarDrawerToggle, you must call it during onPostCreate()
	 * and onConfigurationChanged()
	 */
	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		mDrawerToggle.syncState();
	}
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

    /**
     * Sets app title in actionbar.
     * @param title
     */
    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getActionBar().setTitle(mTitle);
    }
	
	private class DrawerItemClickListener implements OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			navigateTo(position);
            mDrawerLayout.closeDrawers();
		}
	}

    /**
     * Navigate to the selected fragment when menu item is clicked.
     * @param position
     */
    private void navigateTo(int position) {

        switch(position) {
            case 0:
                getSupportFragmentManager().popBackStack();
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.content_frame,
                                WelcomeFragment.newInstance(),
                                WelcomeFragment.TAG).commit();
                break;
            case 1:
                getSupportFragmentManager().popBackStack();
                if (isOnline()){
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.content_frame,
                                    QuestionnaireFragment.newInstance(),
                                    QuestionnaireFragment.TAG).commit();
                }else{
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.content_frame,
                                    ConnectionErrorFragment.newInstance(),
                                    ConnectionErrorFragment.TAG).addToBackStack("connection-error").commit();
                }
                break;
            case 2:
                getSupportFragmentManager().popBackStack();
                if(isOnline()){
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.content_frame,
                                    PublicationListFragment.newInstance(),
                                    PublicationListFragment.TAG).commit();
                    break;
                }else{
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.content_frame,
                                    ConnectionErrorFragment.newInstance(),
                                    ConnectionErrorFragment.TAG).addToBackStack("connection-error").commit();
                }

        }
    }

    /**
     * Check if system has an WLAN or broadband connection.
     * @return true if device is connected to a network
     */
    private boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnected();
    }

}
