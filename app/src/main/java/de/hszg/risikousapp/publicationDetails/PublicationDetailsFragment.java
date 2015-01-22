package de.hszg.risikousapp.publicationDetails;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.InputFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import de.hszg.risikousapp.httpHelper.PostXmlToRisikous;
import de.hszg.risikousapp.publicationDetails.comments.AnswerFragment;
import de.hszg.risikousapp.publicationDetails.comments.CommentAdapter;
import de.hszg.risikousapp.R;
import de.hszg.risikousapp.httpHelper.GetXmlFromRisikous;
import de.hszg.risikousapp.publicationDetails.comments.Comment;
import de.hszg.risikousapp.publicationDetails.comments.CommentSerializer;
import de.hszg.risikousapp.publicationDetails.comments.CommentsParser;

/**
 * Fragment that shows the details and comments of a publication.
 */

public class PublicationDetailsFragment extends Fragment {

    SectionsPagerAdapter mSectionsPagerAdapter;
    public static final String TAG = PublicationDetailsFragment.class.getSimpleName();
    ViewPager mViewPager;

    /**
     * id of the selected publication
     */
    private static String id;

    /**
     * Fragment to show details and the comments of the selected publication.
     * @param id of selected publication
     * @return PublicationDetailsFragment
     */
    public static PublicationDetailsFragment newInstance(String id) {
        PublicationDetailsFragment.id = id;
        return new PublicationDetailsFragment();
    }

    /**
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    /**
     * Create view with PagerAdapter to implement tabbed view.
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return root view
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.title_strip, container, false);
        mSectionsPagerAdapter = new SectionsPagerAdapter(
                getChildFragmentManager());

        mViewPager = (ViewPager) v.findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        return v;
    }

    /**
     * Inner class for tab functionality.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        /**
         * @param fm {@link android.support.v4.app.FragmentManager}
         */
        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        /**
         * Return the fragment for the selected tab (details- or comments-view).
         * @param position
         * @return
         */
        @Override
        public Fragment getItem(int position) {
            Fragment fragment = new TabbedView();
            Bundle args = new Bundle();
            args.putInt(TabbedView.ARG_SECTION_NUMBER, position + 1);
            fragment.setArguments(args);
            return fragment;
        }

        /**
         * @return number of tabs
         */
        @Override
        public int getCount() {
            return 2;
        }

        /**
         * Sets the tab titles
         * @param position
         * @return title names
         */
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getString(R.string.title_section5).toUpperCase();
                case 1:
                    return getString(R.string.title_section6).toUpperCase();
            }
            return null;
        }
    }

    /**
     * Inner class that represents a nested fragment of one tab.
     */
    public static class TabbedView extends Fragment implements View.OnClickListener {
        public static final String ARG_SECTION_NUMBER = "section_number";

        @Override
        public void onCreate(Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
            setRetainInstance(false);
        }

        /**
         * Create root view, change view after tab has changed.
         * @param inflater
         * @param container
         * @param savedInstanceState
         * @return root view
         */
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            final View rootView = inflater.inflate(R.layout.fragment_details,
                    container, false);

            int sectionNumber = getArguments().getInt(ARG_SECTION_NUMBER);

            LinearLayout detailView = (LinearLayout) rootView.findViewById(R.id.detailView);
            final RelativeLayout commentLayout = (RelativeLayout) rootView.findViewById(R.id.commentLayout);
            final ListView commentView = (ListView) rootView.findViewById(R.id.commentView);

            detailView.setVisibility(View.INVISIBLE);
            commentLayout.setVisibility(View.INVISIBLE);

            EditText commentEdit = (EditText) rootView.findViewById(R.id.newComment);
            commentEdit.setFilters(getMaxCharsFilter());

            Button sendComment = (Button) rootView.findViewById(R.id.sendComment);
            sendComment.setOnClickListener(this);

            switch(sectionNumber) {
                case 1:
                    commentLayout.setVisibility(View.GONE);
                    detailView.setVisibility(View.VISIBLE);
                    setText(rootView);
                    break;
                case 2:
                    detailView.setVisibility(View.GONE);
                    commentLayout.setVisibility(View.VISIBLE);
                    loadComments(commentView);
                    break;
            }
            return rootView;
        }

        /**
         * Load all comments of the selected publication and shows them in the ListView, add OnClickListener to each comment view.
         * If comment is not released yet, no OnClickListener is set.
         * @param commentView
         */
        private void loadComments(final ListView commentView) {
            new GetXmlFromRisikous() {
                @Override
                public void onPostExecute(String result) {
                    CommentsParser parser = new CommentsParser(result);
                    ArrayList<Comment> commentList = parser.getComments();
                    final CommentAdapter commentAdapter = new CommentAdapter(getActivity(), R.layout.comment_item, commentList);

                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                    Date date = new Date();

                    if (commentList.isEmpty()) {
                        commentAdapter.add(new Comment("000" ,"Administrator", dateFormat.format(date), "Zu dieser Veröffentlichung wurde noch kein Kommentar abgegeben."));
                    }

                    commentView.setAdapter(commentAdapter);
                    commentView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                            Comment comment = (Comment) commentView.getItemAtPosition(position);
                            if (!comment.getId().equals("000")){
                                getActivity().getSupportFragmentManager()
                                        .beginTransaction()
                                        .replace(R.id.content_frame,
                                                AnswerFragment.newInstance(comment.getListOfAnswers(), comment.getId()),
                                                AnswerFragment.TAG).addToBackStack("answers").commit();
                            }
                        }
                    });
                }
            }.execute("/comments/id/" + id);
        }

        /**
         * Add OnClickListener to send comment button.
         * @param view
         */
        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.sendComment){
                EditText commentText = (EditText) getView().findViewById(R.id.newComment);
                if (commentText.getText().toString().trim().length() > 0){
                    hideKeyboard();
                    sendComment();
                }else{
                    missingCommentText();
                }
            }
        }

        /**
         * Hide keyboard after comment was sent.
         */
        private void hideKeyboard(){
            InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }

        /**
         * Send a comment to the Risikous server.
         * Serializes the inputs with the {@link de.hszg.risikousapp.publicationDetails.comments.CommentSerializer}
         */
        private void sendComment() {
            String commentXml = "";
            CommentSerializer serializer = null;
            EditText author = (EditText) getView().findViewById(R.id.newCommentAuthor);
            EditText commentText = (EditText) getView().findViewById(R.id.newComment);

            try {
                serializer = new CommentSerializer(id, author.getText().toString(), commentText.getText().toString());
                commentXml = serializer.getXmlAsString();
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
                        Toast.makeText(getActivity(), "Der Kommentar wurde erfolgreich gesendet.", Toast.LENGTH_LONG).show();
                        getActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.content_frame, newInstance(id)).commit();
                    } else{
                        Toast.makeText(getActivity(), "Leider ist ein Fehler aufgetreten. Probieren Sie es später nochmal.", Toast.LENGTH_LONG).show();
                    }

                }
            }.execute("publication/addComment", commentXml);
        }

        /**
         * Shows an error message, if user has forgot to write a answer.
         */
        private void missingCommentText(){
            TextView commentCaption = (TextView) getActivity().findViewById(R.id.addComment);
            commentCaption.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
            Toast.makeText(getActivity(), "Bitte geben Sie einen Kommentar ein.", Toast.LENGTH_LONG).show();
        }

        /**
         * Download publication details and set all elements in the view.
         * @param rootView
         */
        private void setText(final View rootView) {
            new GetXmlFromRisikous() {

                TextView title = (TextView) rootView.findViewById(R.id.titleR);
                TextView action = (TextView) rootView.findViewById(R.id.measureR);
                TextView assignedReports = (TextView) rootView.findViewById(R.id.assignedMessageR);
                TextView minRPZofReporter = (TextView) rootView.findViewById(R.id.minPriorityReporter);
                TextView avgRPZofReporter = (TextView) rootView.findViewById(R.id.avgPriorityReporter);
                TextView maxRPZofReporter = (TextView) rootView.findViewById(R.id.maxPriorityReporter);
                TextView minRPZofQBM = (TextView) rootView.findViewById(R.id.minPriorityQMB);
                TextView avgRPZofQBM = (TextView) rootView.findViewById(R.id.avgPriorityQMB);
                TextView maxRPZofQBM = (TextView) rootView.findViewById(R.id.maxPriorityQMB);
                TextView category = (TextView) rootView.findViewById(R.id.categoryR);
                TextView incidentReport = (TextView) rootView.findViewById(R.id.descriptionR);
                TextView reasonDifference = (TextView) rootView.findViewById(R.id.reasonDifferenceR);

                @Override
                public void onPostExecute(String result) {
                    PublicationDetailsParser parser = new PublicationDetailsParser(result);
                    PublicationForDetails publication = parser.getPublication();

                    title.setText(publication.getTitle());
                    action.setText(publication.getAction());
                    assignedReports.setText(publication.getAssignedReports());
                    minRPZofReporter.setText(publication.getMinRPZofReporter());
                    avgRPZofReporter.setText(publication.getAvgRPZofReporter());
                    maxRPZofReporter.setText(publication.getMaxRPZofReporter());
                    minRPZofQBM.setText(publication.getMinRPZofQBM());
                    avgRPZofQBM.setText(publication.getAvgRPZofQBM());
                    maxRPZofQBM.setText(publication.getMaxRPZofQBM());
                    category.setText(publication.getCategory());
                    incidentReport.setText(publication.getIncidentReport());
                    reasonDifference.setText(publication.getDifferenceStatement());
                }
            }.execute("publication/id/" +id);
        }
    }

    /**
     * Input filter, to limit the characters the user can write into the field.
     * @return filter array with chars input filter
     */
    private static InputFilter[] getMaxCharsFilter(){
        InputFilter[] filterArray = new InputFilter[1];
        filterArray[0] = new InputFilter.LengthFilter(1000);
        return filterArray;
    }
}
