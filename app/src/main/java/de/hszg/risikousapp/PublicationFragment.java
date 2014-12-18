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
import de.hszg.risikousapp.models.SearchingResults;
import de.hszg.risikousapp.xmlParser.PublicationParser;

/**
 * Created by besitzer on 17.12.14.
 */
public class PublicationFragment extends Fragment {

    public final static String TAG = QuestionnaireFragment.class.getSimpleName();

    public PublicationFragment() {
    }

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




        new GetXmlFromRisikous(getActivity()) {
            @Override
            public void onPostExecute(String result) {
                PublicationParser parser = new PublicationParser(result);
                setdatatolist(parser);

            }
        }.execute("publications");

    }







    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.listview, container, false);
        return rootView;
    }


    public void setdatatolist(PublicationParser publications){

        setdata(publications);

    }


    public void setdata(PublicationParser publications){
        final ListView listView;



        final ArrayList<SearchingResults> searchResults = publications.getData();


        //searchResults = publications.get();



        listView  = (ListView) getActivity().findViewById(R.id.addinglist);


        listView.setAdapter(new CustomAdapter(this.getActivity(),searchResults));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                Object o = listView.getItemAtPosition(position);

              //showdetails(position);
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.content_frame,
                                PublicationDetailFragment.newInstance(),
                                PublicationDetailFragment.TAG).commit();


            }
        });


    }
}
