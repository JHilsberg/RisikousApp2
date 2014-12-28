package de.hszg.risikousapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import de.hszg.risikousapp.httpcommandhelper.GetXmlFromRisikous;
import de.hszg.risikousapp.models.Comment;
import de.hszg.risikousapp.models.PublicationForDetails;
import de.hszg.risikousapp.xmlParser.Comments;
import de.hszg.risikousapp.xmlParser.Publicationdetails;

/**
 * Created by Hannes on 17.12.2014.
 */

public class PublicationDetailsFragment extends Fragment {

    SectionsPagerAdapter mSectionsPagerAdapter;
    public static final String TAG = PublicationDetailsFragment.class.getSimpleName();
    ViewPager mViewPager;
    private static String id;

    public static PublicationDetailsFragment newInstance(String id) {
        PublicationDetailsFragment.id = id;
        return new PublicationDetailsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.title_strip, container, false);
        mSectionsPagerAdapter = new SectionsPagerAdapter(
                getChildFragmentManager());

        mViewPager = (ViewPager) v.findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        return v;
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = new TabbedView();
            Bundle args = new Bundle();
            args.putInt(TabbedView.ARG_SECTION_NUMBER, position + 1);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public int getCount() {
            return 2;
        }

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

    public static class TabbedView extends Fragment {
        public static final String ARG_SECTION_NUMBER = "section_number";

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            final View rootView = inflater.inflate(R.layout.fragment_details,
                    container, false);

            int sectionNumber = getArguments().getInt(ARG_SECTION_NUMBER);

            LinearLayout detailView = (LinearLayout) rootView.findViewById(R.id.detailView);
            final RelativeLayout commentLayout = (RelativeLayout) rootView.findViewById(R.id.commentLayout);
            final ListView commentView = (ListView) rootView.findViewById(R.id.commmentView);

            detailView.setVisibility(View.INVISIBLE);
            commentLayout.setVisibility(View.INVISIBLE);

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

        private void loadComments(final ListView commentView) {
            new GetXmlFromRisikous() {
                @Override
                public void onPostExecute(String result) {
                    Comments parser = new Comments(result);

                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                    Date date = new Date();

                    ArrayList<Comment> commentList = parser.getData();
                    CommentAdapter commentAdapter = new CommentAdapter(getActivity(), R.layout.comment_item, commentList);
                    commentView.setAdapter(commentAdapter);
                    if (commentList.isEmpty()) {
                        commentAdapter.add(new Comment("Administrator",(dateFormat.format(date)),"Zu dieser Veröffentlichung wurde noch kein Kommentar abgegeben."));

                    }
                }
            }.execute("/comments/id/" + id);
        }

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
                    Publicationdetails parser = new Publicationdetails(result);
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
}
