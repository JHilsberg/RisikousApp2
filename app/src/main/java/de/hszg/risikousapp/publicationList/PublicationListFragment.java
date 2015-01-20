package de.hszg.risikousapp.publicationList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import de.hszg.risikousapp.R;
import de.hszg.risikousapp.httpHelper.GetXmlFromRisikous;
import de.hszg.risikousapp.publicationDetails.PublicationDetailsFragment;
import de.hszg.risikousapp.questionnaire.QuestionnaireFragment;

/**
 * Fragment that shows a list of all available publications.
 */
public class PublicationListFragment extends Fragment {

    public final static String TAG = QuestionnaireFragment.class.getSimpleName();

    private boolean viewWasAlreadyCreated = false;
    private String publicationList = "";

    /**
     * Returns a new Instance of a PublicationList fragment.
     * @return PublicationListFragment
     */
    public static PublicationListFragment newInstance() {
        return new PublicationListFragment();
    }

    /**
     * Starts the download of the publication list. Set view elements, when view is created the first time.
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        getPublications();
    }

    /**
     * Sets the root view of the fragment.
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.publication_list_fragment, container, false);
        return rootView;
    }

    /**
     * Generates the publication list view, when view is restored.
     * For example after changing the orientation.
     * @param onSavedInstance
     */
    @Override
    public void onActivityCreated(Bundle onSavedInstance) {
        super.onActivityCreated(onSavedInstance);

        if(viewWasAlreadyCreated){
            generatePublicationList(publicationList);
        }

        viewWasAlreadyCreated = true;
    }

    /**
     * Download available Publications from Risikous server. Set the view elements if view is created first time.
     */
    private void getPublications() {
        new GetXmlFromRisikous() {
            @Override
            protected void onPreExecute(){
                getActivity().setProgressBarIndeterminateVisibility(true);
            }

            @Override
            public void onPostExecute(String result) {
                if (result.equals("error")){
                    showErrorMessage();
                }else{
                    publicationList = result;
                    generatePublicationList(result);
                }
                getActivity().setProgressBarIndeterminateVisibility(false);
            }
        }.execute("publications");
    }

    /**
     * Generate publications as list view, set on itemClickListener to select a publication.
     * @param publications downloaded publication xml
     */
    public void generatePublicationList(String publications){

        final ListView listView;
        PublicationListParser publicationListParser = new PublicationListParser(publications);
        final ArrayList<PublicationForList> publicationList = publicationListParser.getData();


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
                                PublicationDetailsFragment.TAG).addToBackStack("publications").commit();
            }
        });

    }

    /**
     * Shows a connection error message if download of publications fails.
     */
    public void showErrorMessage(){
        View publicationListContentView = getActivity().findViewById(R.id.publicationListLayout);
        publicationListContentView.setVisibility(View.GONE);

        View errorView = getActivity().findViewById(R.id.connectionErrorView);
        errorView.setVisibility(View.VISIBLE);
    }
}