package de.hszg.risikousapp.connectionError;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import de.hszg.risikousapp.R;
import de.hszg.risikousapp.welcome.WelcomeFragment;

/**
 * Fragment that is shown, if device has no network connection.
 */
public class ConnectionErrorFragment extends Fragment implements View.OnClickListener{

    public final static String TAG = ConnectionErrorFragment.class.getSimpleName();

    /**
     * Returns new Instance of the fragment.
     * @return
     */
    public static ConnectionErrorFragment newInstance() {
        return new ConnectionErrorFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * Returns the root view and set button listeners.
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.fragment_connection_error, container, false);

        Button backToWelcome = (Button) rootView.findViewById(R.id.buttonBackToWelcome);
        backToWelcome.setOnClickListener(this);
        Button openSettings = (Button) rootView.findViewById(R.id.buttonOpenSettings);
        openSettings.setOnClickListener(this);
        return rootView;
    }

    /**
     * App returns to welcome fragment, if button is clicked.
     * @param view
     */
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.buttonBackToWelcome){
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.content_frame,
                            WelcomeFragment.newInstance(),
                            WelcomeFragment.TAG).commit();
        } else{
            startActivity(new Intent(android.provider.Settings.ACTION_SETTINGS));
        }
    }

    /**
     * pop the back stack to avoid overlapping fragments
     */
    @Override
    public void onDestroy(){
        super.onDestroy();
        getActivity().getSupportFragmentManager().popBackStack();
    }
}
