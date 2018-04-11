package com.example.greg.quizgame;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.greg.finalproject.R;

public class TFQuestionFragment extends Fragment {

    protected static final String ACTIVITY_NAME ="TFQuestionFragment";
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
        View gui = inflater.inflate(R.layout.activity_tf_question_fragment, container, false);
        QuestionView =(EditText) gui.findViewById(R.id.questionView);

        RadioButton trueButton = (RadioButton) gui.findViewById(R.id.trueButton);
        RadioButton falseButton = (RadioButton) gui.findViewById(R.id.falseButton);

        update= (Button) gui.findViewById(R.id.updateButton);
        delete = (Button) gui.findViewById(R.id.deleteButton);
        back = (Button) gui.findViewById(R.id.backButton);

        bundle = getArguments();

        String question = bundle.getString("Question");
        int ans = bundle.getInt("Answer");

        final long id = bundle.getLong("ID");
        final long id_inChat= bundle.getLong("IDInChat");
        Log.i(ACTIVITY_NAME,id+" "+id_inChat+" "+isTablet+" "+ans);

        QuestionView.setText(question);
        if(ans ==1){
            falseButton.setChecked(false);
            trueButton.setChecked(true);

        }else{
            falseButton.setChecked(true);
            trueButton.setChecked(false);
        }



        delete.setOnClickListener((view)->{
                    if(isTablet){
                        QuizActivity qa = (QuizActivity)getActivity();
                        qa.deleteForTablet(id, id_inChat);
                        getFragmentManager().beginTransaction().remove(TFQuestionFragment.this).commit();
                    }else{
                        Intent resultIntent = new Intent();
                        resultIntent.putExtra("action", 1);
                        resultIntent.putExtra("DeleteID", id);
                        resultIntent.putExtra("IDInChat", id_inChat);
                        getActivity().setResult(Activity.RESULT_OK, resultIntent);
                        getActivity().finish();
                    }
                }

        );

        update.setOnClickListener((view)-> {
            String newQ = QuestionView.getText().toString();
            int a1;
            if(trueButton.isChecked()){
                a1=1;
            }else{
                a1=2;
            }

            if(isTablet){
                QuizActivity qa = (QuizActivity)getActivity();
                qa.deleteForTablet(id, id_inChat);
                qa.updateTF(newQ, a1);
                getFragmentManager().beginTransaction().remove(TFQuestionFragment.this).commit();
            }else{
                Intent resultIntent = new Intent();
                resultIntent.putExtra("type", 2);
                resultIntent.putExtra("action", 2);
                resultIntent.putExtra("Question", newQ);
                resultIntent.putExtra("Answer", a1);
                resultIntent.putExtra("UpdateID", id);
                resultIntent.putExtra("IDInChat", id_inChat);
                getActivity().setResult(Activity.RESULT_OK, resultIntent);
                getActivity().finish();
            }

        });

        back.setOnClickListener((view)->{
            if(isTablet){
                QuizActivity qa = (QuizActivity)getActivity();

                getFragmentManager().beginTransaction().remove(TFQuestionFragment.this).commit();
            }else{
                Intent resultIntent = new Intent();
                resultIntent.putExtra("action", 3);

                getActivity().setResult(Activity.RESULT_OK, resultIntent);
                getActivity().finish();
            }


        });

        return gui;
    }

    public void setIsTablet(boolean isTablet){
        this.isTablet=isTablet;
    }
}
