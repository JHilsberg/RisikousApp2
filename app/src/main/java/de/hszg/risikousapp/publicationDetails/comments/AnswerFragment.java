package de.hszg.risikousapp.publicationDetails.comments;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

import de.hszg.risikousapp.R;
import de.hszg.risikousapp.httpHelper.PostXmlToRisikous;

/**
 * Fragment that shows a list with all answers of the selected comment.
 */
public class AnswerFragment extends Fragment implements View.OnClickListener {
    public final static String TAG = AnswerFragment.class.getSimpleName();

    private static ArrayList<Comment> listOfAnswers = new ArrayList<>();
    private static String id;

    /**
     * Returns a new Instance of a answer fragment.
     * @return AnswerFragment
     */
    public static AnswerFragment newInstance(ArrayList<Comment> listOfAnswers, String id) {
        AnswerFragment.listOfAnswers = listOfAnswers;
        AnswerFragment.id = id;
        return new AnswerFragment();
    }

    /**
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        if(id.equals("")){
            rootView.findViewById(R.id.newAnswerLayout).setVisibility(View.INVISIBLE);
        }
        rootView.findViewById(R.id.sendAnswerButton).setOnClickListener(this);

        return rootView;
    }


    /**
     * Generates the answer list view, when view is restored.
     * For example after changing the orientation.
     * @param onSavedInstance
     */
    @Override
    public void onActivityCreated(Bundle onSavedInstance) {
        super.onActivityCreated(onSavedInstance);
        generateAnswerList();
    }

    /**
     * Onclick listener for send button
     * @param view
     */
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.sendAnswerButton){
            EditText answerText = (EditText) getActivity().findViewById(R.id.answerText);
            if (answerText.getText().toString().trim().length() > 0){
                hideKeyboard();
                sendAnswer();
            }else{
                missingAnswerText();
            }
        }
    }

    /**
     * Serializes the answer-xml and sends the answer to the risikous server.
     */
    private void sendAnswer() {
        String answerXml = "";
        CommentSerializer serializer = null;
        EditText author = (EditText) getActivity().findViewById(R.id.answerAuthor);
        EditText answerText = (EditText) getActivity().findViewById(R.id.answerText);

        try {
            serializer = new CommentSerializer(id, author.getText().toString(), answerText.getText().toString());
            answerXml = serializer.getXmlAsString();
        } catch (IOException e) {
            Log.e("serializer", "Fehler bei der Serialisierung");
        }

        new PostXmlToRisikous(){
            @Override
            protected void onPreExecute(){
                getActivity().setProgressBarIndeterminateVisibility(true);
            }

            @Override
            protected void onPostExecute(String result) {
                Log.i("status", "" + result);
                getActivity().setProgressBarIndeterminateVisibility(false);
                if (result.equals("201")){
                    Toast.makeText(getActivity(), "Die Antwort wurde erfolgreich gesendet.", Toast.LENGTH_LONG).show();
                } else{
                    Toast.makeText(getActivity(), "Leider ist ein Fehler aufgetreten. Probieren Sie es später nochmal.", Toast.LENGTH_LONG).show();
                }

            }
        }.execute("comment/addAnswer", answerXml);
    }

    /**
     * Shows an error message, if user has forgot to write a answer.
     */
    private void missingAnswerText() {
        TextView answerCaption = (TextView) getActivity().findViewById(R.id.newAnswerCaption);
        answerCaption.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
        Toast.makeText(getActivity(), "Bitte geben Sie eine Antwort ein.", Toast.LENGTH_LONG).show();
    }

    /**
     * Hide the virtual keyboard after click on the send button.
     */
    private void hideKeyboard(){
        InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * Generates the list view of all answers.
     */
    public void generateAnswerList(){
        ListView listView  = (ListView) getActivity().findViewById(R.id.answerView);
        CommentAdapter adapter = new CommentAdapter(getActivity(), R.layout.comment_item, listOfAnswers);
        listView.setAdapter(adapter);
    }


}
