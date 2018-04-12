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

public class NumQuestionFragment extends Fragment {

    protected static final String ACTIVITY_NAME ="NumQuestionFragment";
    private boolean isTablet;
    private EditText QuestionView;
    private EditText AnswerView;
    private EditText PrecisionView;


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
        View gui = inflater.inflate(R.layout.activity_num_question_fragment, container, false);
        QuestionView =(EditText) gui.findViewById(R.id.newQuestion);
        AnswerView = (EditText) gui.findViewById(R.id.answer);
        PrecisionView = (EditText) gui.findViewById(R.id.precision);


        update= (Button) gui.findViewById(R.id.updateButton);
        delete = (Button) gui.findViewById(R.id.deleteButton);
        back = (Button) gui.findViewById(R.id.backButton);

        bundle = getArguments();

        String question = bundle.getString("Question");
        Double ans = bundle.getDouble("Answer");
        int pres = bundle.getInt("Precision");

        final long id = bundle.getLong("ID");
        final long id_inChat= bundle.getLong("IDInChat");
        Log.i(ACTIVITY_NAME,"id: "+id+" idInChat: "+id_inChat+" tablet?: "+isTablet+" Question: "+question+" Answer: "+ans+ " Pres: "+ pres);

        QuestionView.setText(question);
        AnswerView.setText(new Double(ans).toString());
        PrecisionView.setText(new Integer(pres).toString());




        delete.setOnClickListener((view)->{
                    if(isTablet){
                        QuizActivity qa = (QuizActivity)getActivity();
                        qa.deleteForTablet(id, id_inChat, true);
                        getFragmentManager().beginTransaction().remove(NumQuestionFragment.this).commit();
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
            double newAnswer = Double.parseDouble(AnswerView.getText().toString());
            int newPres = Integer.parseInt(PrecisionView.getText().toString());

            if(isTablet){
                QuizActivity qa = (QuizActivity)getActivity();
                qa.deleteForTablet(id, id_inChat, false);
                qa.updateNum(newQ, newAnswer, newPres);
                getFragmentManager().beginTransaction().remove(NumQuestionFragment.this).commit();
            }else{
                Intent resultIntent = new Intent();
                resultIntent.putExtra("type", 3);
                resultIntent.putExtra("action", 2);
                resultIntent.putExtra("Question", newQ);
                resultIntent.putExtra("Answer", newAnswer);
                resultIntent.putExtra("Precision", newPres);
                resultIntent.putExtra("UpdateID", id);
                resultIntent.putExtra("IDInChat", id_inChat);
                getActivity().setResult(Activity.RESULT_OK, resultIntent);
                getActivity().finish();
            }

        });

        back.setOnClickListener((view)->{
            if(isTablet){
                QuizActivity qa = (QuizActivity)getActivity();

                getFragmentManager().beginTransaction().remove(NumQuestionFragment.this).commit();
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
