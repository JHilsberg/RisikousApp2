package de.hszg.risikousapp;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.ArrayList;

import de.hszg.risikousapp.httpcommandhelper.GetXmlFromRisikous;
import de.hszg.risikousapp.models.PublicationForList;
import de.hszg.risikousapp.xmlParser.PublicationList;

/**
 * Created by besitzer on 17.12.14.
 */
public class PublicationFragment extends Fragment {
    public final static String TAG = QuestionnaireFragment.class.getSimpleName();
    private LinearLayout linearLayout;
    private ListView listView;
    private ProgressBar progressBar;
    private ArrayList<PublicationForList> publicationList;

    public PublicationFragment() {}

    public static PublicationFragment newInstance() {
        return new PublicationFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    public void onActivityCreated(Bundle onSavedInstance) {
        super.onActivityCreated(onSavedInstance);

        linearLayout = (LinearLayout) getActivity().findViewById(R.id.publicationListLayout);
        progressBar = (ProgressBar) getActivity().findViewById(R.id.loading_spinner);
        linearLayout.setVisibility(View.INVISIBLE);

         new GetXmlFromRisikous() {
            @Override
            public void onPostExecute(String result) {
                PublicationList parser = new PublicationList(result);
                setData(parser);

                progressBar.setVisibility(View.GONE);
                linearLayout.setVisibility(View.VISIBLE);
            }
        }.execute("publications");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.publication_list_fragment, container, false);
        return rootView;
    }

    public void setData(PublicationList publications){

        publicationList = publications.getData();
        listView  = (ListView) getActivity().findViewById(R.id.publicationList);
        listView.setAdapter(new PublicationListAdapter(getActivity(), R.layout.publication_item, publicationList));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                PublicationForList selectedPublication = (PublicationForList) listView.getItemAtPosition(position);
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.content_frame,
                                PublicationDetailsFragment.newInstance(selectedPublication.getId()),
                                PublicationDetailsFragment.TAG).commit();
            }
        });

    }
}
