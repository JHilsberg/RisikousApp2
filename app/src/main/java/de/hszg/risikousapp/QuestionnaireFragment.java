package de.hszg.risikousapp;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;

import de.hszg.risikousapp.httpcommandhelper.GetXmlFromRisikous;
import de.hszg.risikousapp.models.ReportingArea;
import de.hszg.risikousapp.xmlParser.QuestionnaireSkeleton;
import de.hszg.risikousapp.xmlParser.ReportingAreas;

/**
 * Created by Julian on 08.12.2014.
 */
public class QuestionnaireFragment extends Fragment implements View.OnClickListener {

    public final static String TAG = QuestionnaireFragment.class.getSimpleName();

    private View questionnaireContentView;
    private View loadingView;

    public QuestionnaireFragment() {
    }

    public static QuestionnaireFragment newInstance() {
        return new QuestionnaireFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_questionnaire, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle onSavedInstance) {
        super.onActivityCreated(onSavedInstance);

        questionnaireContentView = getView().findViewById(R.id.questionnaireContent);
        loadingView = getView().findViewById(R.id.loading_spinner);

        questionnaireContentView.setVisibility(View.GONE);

        Button sendQuestionnaire = (Button) getView().findViewById(R.id.sendQuestionnaire);
        sendQuestionnaire.setOnClickListener(this);

        new GetXmlFromRisikous(getActivity()) {
            @Override
            public void onPostExecute(String result) {
                QuestionnaireSkeleton parser = new QuestionnaireSkeleton(result);
                new QuestionnaireElementsTextSetter(parser, getActivity());

                loadingView.setVisibility(View.GONE);
                questionnaireContentView.setVisibility(View.VISIBLE);
            }
        }.execute("questionnaire");

        new GetXmlFromRisikous(getActivity()) {
            @Override
            public void onPostExecute(String result) {
                ReportingAreas parser = new ReportingAreas(result);
                setReportingAreaSpinner(parser);
            }
        }.execute("reportingareas");
    }


    private void setReportingAreaSpinner(ReportingAreas areas) {
        Spinner reportingAreaSpinner = (Spinner) getActivity().findViewById(R.id.reportingAreaSelection);
        final ArrayList<ReportingArea> reportingAreas = areas.getReportingAreas();

        final ArrayAdapter spinnerArrayAdapter = new ReportingAreasSpinAdapter(getActivity(),
                android.R.layout.simple_spinner_item, reportingAreas);
        reportingAreaSpinner.setAdapter(spinnerArrayAdapter);

        reportingAreaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long id) {
                ReportingArea reportingArea = (ReportingArea) spinnerArrayAdapter.getItem(position);
                Log.i("Reporting Area", "Ausgewählter Meldekreis: " + reportingArea.getName() +
                        " Shortcut: " + reportingArea.getShortcut());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.sendQuestionnaire) {
            Log.i("listener", "button listener activated");
            new QuestionnaireValidator(getActivity());
        }
    }
}