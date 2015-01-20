package de.hszg.risikousapp.publicationDetails.comments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import de.hszg.risikousapp.R;
import de.hszg.risikousapp.questionnaire.QuestionnaireFragment;

public class AnswerFragment extends Fragment{
    public final static String TAG = QuestionnaireFragment.class.getSimpleName();

    private static ArrayList<Comment> listOfAnswers;

    /**
     * Returns a new Instance of a PublicationList fragment.
     * @return PublicationListFragment
     */
    public static AnswerFragment newInstance(ArrayList<Comment> listOfAnswers) {
        AnswerFragment.listOfAnswers = listOfAnswers;
        return new AnswerFragment();
    }

    /**
     * Starts the download of the publication list. Set view elements, when view is created the first time.
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
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
        View rootView = inflater.inflate(R.layout.fragment_answers, container, false);
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
    }

    public void generateAnswerList(){

        ListView listView;

        listView  = (ListView) getActivity().findViewById(R.id.answerList);
        listView.setAdapter(new CommentAdapter(getActivity(), R.layout.comment_item, listOfAnswers));

    }
}
