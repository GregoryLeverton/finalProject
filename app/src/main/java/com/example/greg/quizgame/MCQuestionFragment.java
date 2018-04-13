package com.example.greg.quizgame;
/**
 * Created by Greg Leverton on 2018-03-30.
 */

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.greg.finalproject.R;

public class MCQuestionFragment extends Fragment {

    protected static final String ACTIVITY_NAME = "MCQuestionFragment";
    private boolean isTablet;
    private EditText QuestionView;
    private EditText A1View;
    private EditText A2View;
    private EditText A3View;
    private EditText A4View;
    private EditText CorrectView;

    private Button update;
    private Button delete;
    private Button back;
    private Bundle bundle;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        View gui = inflater.inflate(R.layout.activity_mc_question_fragment, container, false);
        QuestionView = (EditText) gui.findViewById(R.id.questionView);
        A1View = (EditText) gui.findViewById(R.id.a1View);
        A2View = (EditText) gui.findViewById(R.id.a2View);
        A3View = (EditText) gui.findViewById(R.id.a3View);
        A4View = (EditText) gui.findViewById(R.id.a4View);
        CorrectView = (EditText) gui.findViewById(R.id.correctView);

        update = (Button) gui.findViewById(R.id.updateButton);
        delete = (Button) gui.findViewById(R.id.deleteButton);
        back = (Button) gui.findViewById(R.id.backButton);

        bundle = getArguments();

        String question = bundle.getString("Question");
        String A1 = bundle.getString("Answer1");
        String A2 = bundle.getString("Answer2");
        String A3 = bundle.getString("Answer3");
        String A4 = bundle.getString("Answer4");
        String cor = new Integer(bundle.getInt("CorrectAnswer")).toString();
        final long id = bundle.getLong("ID");
        final long id_inChat = bundle.getLong("IDInChat");

        QuestionView.setText(question);
        A1View.setText(A1);
        A2View.setText(A2);
        A3View.setText(A3);
        A4View.setText(A4);
        CorrectView.setText(cor);

        delete.setOnClickListener((view) -> {
                    if (isTablet) {
                        QuizActivity qa = (QuizActivity) getActivity();
                        qa.deleteForTablet(id, id_inChat, true);
                        getFragmentManager().beginTransaction().remove(MCQuestionFragment.this).commit();
                    } else {
                        Intent resultIntent = new Intent();
                        resultIntent.putExtra("action", 1);
                        resultIntent.putExtra("DeleteID", id);
                        resultIntent.putExtra("IDInChat", id_inChat);
                        getActivity().setResult(Activity.RESULT_OK, resultIntent);
                        getActivity().finish();
                    }
                }

        );

        update.setOnClickListener((view) -> {
            String newQ = QuestionView.getText().toString();
            String newA1 = A1View.getText().toString();
            String newA2 = A2View.getText().toString();
            String newA3 = A3View.getText().toString();
            String newA4 = A4View.getText().toString();
            int newCor = Integer.parseInt(CorrectView.getText().toString());

            if (isTablet) {
                QuizActivity qa = (QuizActivity) getActivity();
                qa.deleteForTablet(id, id_inChat, false);
                qa.updateMC(newQ, newA1, newA2, newA3, newA4, newCor);
                getFragmentManager().beginTransaction().remove(MCQuestionFragment.this).commit();
            } else {
                Intent resultIntent = new Intent();
                resultIntent.putExtra("type", 1);
                resultIntent.putExtra("action", 2);
                resultIntent.putExtra("Question", newQ);
                resultIntent.putExtra("Answer1", newA1);
                resultIntent.putExtra("Answer2", newA2);
                resultIntent.putExtra("Answer3", newA3);
                resultIntent.putExtra("Answer4", newA4);
                resultIntent.putExtra("Correct", newCor);
                resultIntent.putExtra("UpdateID", id);
                resultIntent.putExtra("IDInChat", id_inChat);
                getActivity().setResult(Activity.RESULT_OK, resultIntent);
                getActivity().finish();
            }

        });

        back.setOnClickListener((view) -> {
            if (isTablet) {
                QuizActivity qa = (QuizActivity) getActivity();

                getFragmentManager().beginTransaction().remove(MCQuestionFragment.this).commit();
            } else {
                Intent resultIntent = new Intent();
                resultIntent.putExtra("action", 3);

                getActivity().setResult(Activity.RESULT_OK, resultIntent);
                getActivity().finish();
            }


        });

        return gui;
    }

    public void setIsTablet(boolean isTablet) {
        this.isTablet = isTablet;
    }
}
