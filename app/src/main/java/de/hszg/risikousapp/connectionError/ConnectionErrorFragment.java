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
 * Created by Julian on 02.01.2015.
 */
public class ConnectionErrorFragment extends Fragment implements View.OnClickListener{

    public final static String TAG = ConnectionErrorFragment.class.getSimpleName();

    public static ConnectionErrorFragment newInstance() {
        return new ConnectionErrorFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

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

}
