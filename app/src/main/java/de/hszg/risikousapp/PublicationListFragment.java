package de.hszg.risikousapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import de.hszg.risikousapp.httpcommandhelper.GetXmlFromRisikous;
import de.hszg.risikousapp.models.PublicationForList;
import de.hszg.risikousapp.xmlParser.PublicationList;

public class PublicationListFragment extends Fragment {
    public final static String TAG = QuestionnaireFragment.class.getSimpleName();

    private View publicationListView;
    private View loadingView;

    public PublicationListFragment() {}

    public static PublicationListFragment newInstance() {
        return new PublicationListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    public void onActivityCreated(Bundle onSavedInstance) {
        super.onActivityCreated(onSavedInstance);

        publicationListView = getView().findViewById(R.id.publicationListLayout);
        loadingView = getView().findViewById(R.id.loading_spinner);

        publicationListView.setVisibility(View.GONE);

        new GetXmlFromRisikous() {
            @Override
            public void onPostExecute(String result) {
                PublicationList parser = new PublicationList(result);
                setdata(parser);

                loadingView.setVisibility(View.GONE);
                publicationListView.setVisibility(View.VISIBLE);
            }
        }.execute("publications");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.publication_list_fragment, container, false);
        return rootView;
    }

    public void setdata(PublicationList publications){
        final ListView listView;
        final ArrayList<PublicationForList> searchResults = publications.getData();
        listView  = (ListView) getActivity().findViewById(R.id.publicationList);
        listView.setAdapter(new PublicationListAdapter(getActivity(), R.layout.publication_item,searchResults));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                PublicationForList selectedPublication = (PublicationForList) listView.getItemAtPosition(position);
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.content_frame,
                                PublicationDetailsFragment.newInstance(selectedPublication.getId()),
                                PublicationDetailsFragment.TAG).addToBackStack("publications").commit();
            }
        });

    }
}