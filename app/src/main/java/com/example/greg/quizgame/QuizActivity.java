package com.example.greg.quizgame;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.greg.finalproject.R;

import java.util.ArrayList;

public class QuizActivity extends AppCompatActivity {
    protected static final String ACTIVITY_NAME ="ChatWindow";
    protected SQLiteDatabase db;
    protected boolean FrameExists;
    protected Cursor cursor;

    String tableName = ChatDatabaseHelper.TABLE_NAME;
    String keyID = ChatDatabaseHelper.KEY_ID;
    String keyMsg = ChatDatabaseHelper.KEY_QUESTION;
    ArrayList<Question> questions = new ArrayList<>();
    int tfA = 1;
    QuestionAdapter questionAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(ACTIVITY_NAME, "In onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        FrameExists = (findViewById(R.id.frame)!=null);

        ListView chat = findViewById(R.id.list);
        //EditText textInput = findViewById(R.id.chatText);
        Button newMC = findViewById(R.id.new_MC);
        Button newTF = findViewById(R.id.new_TF);
        Button newNum = findViewById(R.id.new_Num);
        questionAdapter =new QuestionAdapter( this );
        chat.setAdapter (questionAdapter);
        ChatDatabaseHelper myOpener = new ChatDatabaseHelper(this);
        db = myOpener.getWritableDatabase();

        cursor =db.rawQuery("SELECT * FROM "+ChatDatabaseHelper.TABLE_NAME+";", null);
        cursor.moveToFirst();

        while(!cursor.isAfterLast() ) {
            Question question;
            Log.i(ACTIVITY_NAME, "KEY TYPE IS" + cursor.getInt(cursor.getColumnIndex(ChatDatabaseHelper.KEY_TYPE)) );
            if(cursor.getInt(cursor.getColumnIndex(ChatDatabaseHelper.KEY_TYPE))==1) {

                String q = cursor.getString(cursor.getColumnIndex(ChatDatabaseHelper.KEY_QUESTION));
                String a1 = cursor.getString(cursor.getColumnIndex(ChatDatabaseHelper.KEY_ANSWER1));
                String a2 = cursor.getString(cursor.getColumnIndex(ChatDatabaseHelper.KEY_ANSWER2));
                String a3 = cursor.getString(cursor.getColumnIndex(ChatDatabaseHelper.KEY_ANSWER3));
                String a4 = cursor.getString(cursor.getColumnIndex(ChatDatabaseHelper.KEY_ANSWER4));
                int c = cursor.getInt(cursor.getColumnIndex(ChatDatabaseHelper.KEY_CORRECT));
                question = new MCQuestion(q, a1, a2, a3, a4, c);
            }else if(cursor.getInt(cursor.getColumnIndex(ChatDatabaseHelper.KEY_TYPE))==2) {
                String q = cursor.getString(cursor.getColumnIndex(ChatDatabaseHelper.KEY_QUESTION));
                boolean a = cursor.getInt(cursor.getColumnIndex(ChatDatabaseHelper.KEY_CORRECT)) == 1;
                question = new TFQuestion(q, a);

            }else if(cursor.getInt(cursor.getColumnIndex(ChatDatabaseHelper.KEY_TYPE))==3){
                String q = cursor.getString(cursor.getColumnIndex(ChatDatabaseHelper.KEY_QUESTION));
                double a = cursor.getDouble(cursor.getColumnIndex(ChatDatabaseHelper.KEY_NUMANSWER));
                int p = cursor.getInt(cursor.getColumnIndex(ChatDatabaseHelper.KEY_PRECISION));
                question = new NumericQuestion(q, a, p);

            }else{
                String q = cursor.getString(cursor.getColumnIndex(ChatDatabaseHelper.KEY_QUESTION));
                question = new MCQuestion("error"+q, "error", "error", "error", "error", 2);
            }
            questions.add(question);
            Log.i(ACTIVITY_NAME, "SQL MESSAGE:" + cursor.getString(cursor.getColumnIndex(ChatDatabaseHelper.KEY_QUESTION)));
            cursor.moveToNext();
            Log.i(ACTIVITY_NAME, "Cursor's  column count =" + cursor.getColumnCount() );
            for(int k =0; k<cursor.getColumnCount(); k++){
                Log.i(ACTIVITY_NAME, "Column name" + cursor.getColumnName(k));
            }
        }

        newMC.setOnClickListener(e ->{

                    AlertDialog.Builder builder1 = new AlertDialog.Builder(this);

                    final View view=this.getLayoutInflater().inflate(R.layout.mc_dialog,null);

                    builder1.setView(view);

                    builder1.setPositiveButton(R.string.newMC, new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int id) {

                            EditText ques = (EditText)view.findViewById(R.id.newQuestion);
                            EditText answer1 = (EditText)view.findViewById(R.id.answer1);
                            EditText answer2 = (EditText)view.findViewById(R.id.answer2);
                            EditText answer3 = (EditText)view.findViewById(R.id.answer3);
                            EditText answer4 = (EditText)view.findViewById(R.id.answer4);
                            EditText correct = (EditText)view.findViewById(R.id.correct);

                            String q = ques.getText().toString();
                            String a1= answer1.getText().toString();
                            String a2= answer2.getText().toString();
                            String a3= answer3.getText().toString();
                            String a4= answer4.getText().toString();
                            int c= Integer.parseInt(correct.getText().toString());
                            MCQuestion question = new MCQuestion(q, a1, a2, a3, a4, c);
                            questions.add(question);

                            ContentValues cv = new ContentValues();
                            cv.put(ChatDatabaseHelper.KEY_TYPE, 1);
                            cv.put(ChatDatabaseHelper.KEY_QUESTION,q);
                            cv.put(ChatDatabaseHelper.KEY_ANSWER1,a1);
                            cv.put(ChatDatabaseHelper.KEY_ANSWER2,a2);
                            cv.put(ChatDatabaseHelper.KEY_ANSWER3,a3);
                            cv.put(ChatDatabaseHelper.KEY_ANSWER4,a4);
                            cv.put(ChatDatabaseHelper.KEY_CORRECT,c);

                            db.insert(ChatDatabaseHelper.TABLE_NAME, "Null replacement value", cv);
                            cursor =db.rawQuery("SELECT * FROM "+ChatDatabaseHelper.TABLE_NAME+";", null);

                            cursor.moveToFirst();

                            questionAdapter.notifyDataSetChanged(); //this restarts the process of getCount() & getView()

                        }
                    });

                    builder1.setNegativeButton(R.string.newQCancel, new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int id) {



                        }

                    });

                    AlertDialog dialog1 = builder1.create();

                    dialog1.show();
                }
        );

        newTF.setOnClickListener(e ->{

            setTF(1);
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(this);

                    final View view=this.getLayoutInflater().inflate(R.layout.tf_dialog,null);

                    builder1.setView(view);
            RadioButton trueButton = (RadioButton) view.findViewById(R.id.trueButton);
            RadioButton falseButton = (RadioButton) view.findViewById(R.id.falseButton);

            trueButton.setOnClickListener((test)->{
                        setTF(1);
                        Log.i(ACTIVITY_NAME, tfA+" should be 1 FAAAAAAARRRRT");
                    }
            );

            falseButton.setOnClickListener((test)->{
                        setTF(2);
                        Log.i(ACTIVITY_NAME, tfA+" should be 2 FAAAAAAARRRRT");
                    }


            );

                    builder1.setPositiveButton(R.string.newTF, new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int id) {

                            EditText ques = (EditText)view.findViewById(R.id.newTFQuestion);
                            RadioGroup answer = (RadioGroup) view.findViewById(R.id.TFGroup);



                            String q = ques.getText().toString();




                            TFQuestion question = new TFQuestion(q, tfA==1);

                            Log.i(ACTIVITY_NAME, tfA+" "+new Boolean(question.isAnswer()).toString());
                            questions.add(question);

                            ContentValues cv = new ContentValues();
                            cv.put(ChatDatabaseHelper.KEY_TYPE, 2);
                            cv.put(ChatDatabaseHelper.KEY_QUESTION,q);
                            cv.put(ChatDatabaseHelper.KEY_CORRECT,tfA);


                            db.insert(ChatDatabaseHelper.TABLE_NAME, "Null replacement value", cv);
                            cursor =db.rawQuery("SELECT * FROM "+ChatDatabaseHelper.TABLE_NAME+";", null);

                            cursor.moveToFirst();

                            questionAdapter.notifyDataSetChanged(); //this restarts the process of getCount() & getView()

                        }
                    });

                    builder1.setNegativeButton(R.string.newQCancel, new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int id) {



                        }

                    });

                    AlertDialog dialog1 = builder1.create();

                    dialog1.show();
                }//}
        );

        newNum.setOnClickListener(e ->{

                    AlertDialog.Builder builder1 = new AlertDialog.Builder(this);

                    final View view=this.getLayoutInflater().inflate(R.layout.num_dialog,null);

                    builder1.setView(view);

                    builder1.setPositiveButton(R.string.newNum, new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int id) {

                            EditText ques = (EditText)view.findViewById(R.id.newQuestion);
                            EditText answer = (EditText)view.findViewById(R.id.answer);
                            EditText precision = (EditText)view.findViewById(R.id.precision);


                            String q = ques.getText().toString();
                            double a= Double.parseDouble(answer.getText().toString());

                            int p= Integer.parseInt(precision.getText().toString());
                            NumericQuestion question = new NumericQuestion(q, a, p);
                            questions.add(question);

                            ContentValues cv = new ContentValues();
                            cv.put(ChatDatabaseHelper.KEY_TYPE, 3);
                            cv.put(ChatDatabaseHelper.KEY_QUESTION,q);
                            cv.put(ChatDatabaseHelper.KEY_NUMANSWER,a);
                            cv.put(ChatDatabaseHelper.KEY_PRECISION,p);

                            db.insert(ChatDatabaseHelper.TABLE_NAME, "Null replacement value", cv);
                            cursor =db.rawQuery("SELECT * FROM "+ChatDatabaseHelper.TABLE_NAME+";", null);

                            cursor.moveToFirst();

                            questionAdapter.notifyDataSetChanged(); //this restarts the process of getCount() & getView()

                        }
                    });

                    builder1.setNegativeButton(R.string.newQCancel, new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int id) {



                        }

                    });

                    AlertDialog dialog1 = builder1.create();

                    dialog1.show();
                }
        );


        chat.setOnItemClickListener((adapterView, view, position, id) ->{
            int type = questionAdapter.getItem(position).getType();

            if(type ==1) {
                String ques = questionAdapter.getItem(position).getQuestion();
                String ans1 = ((MCQuestion) questionAdapter.getItem(position)).getAnswer1();
                String ans2 = ((MCQuestion) questionAdapter.getItem(position)).getAnswer2();
                String ans3 = ((MCQuestion) questionAdapter.getItem(position)).getAnswer3();
                String ans4 = ((MCQuestion) questionAdapter.getItem(position)).getAnswer4();
                int cor = ((MCQuestion) questionAdapter.getItem(position)).getCorrectAnswer();

                Long id_inChat = questionAdapter.getId(position);
                long ID = id;
                MCQuestionFragment myFragment = new MCQuestionFragment();
                Bundle infoToPass = new Bundle();
                infoToPass.putString("Question", ques);
                infoToPass.putString("Answer1", ans1);
                infoToPass.putString("Answer2", ans2);
                infoToPass.putString("Answer3", ans3);
                infoToPass.putString("Answer4", ans4);
                infoToPass.putInt("CorrectAnswer", cor);
                infoToPass.putLong("IDInChat", id_inChat);
                infoToPass.putLong("ID", ID);


                //if on tablet:
                if (FrameExists) {
                    Log.i(ACTIVITY_NAME, ID + " " + id_inChat);
                    myFragment.setArguments(infoToPass);
                    myFragment.setIsTablet(true);
                    getFragmentManager().beginTransaction().replace(R.id.frame, myFragment).commit();
                } else //this is a phone:
                {
                    myFragment.setIsTablet(false);
                    Intent next = new Intent(QuizActivity.this, MCQuestionDetails.class);
                    next.putExtra("QuestionItem", infoToPass);
                    startActivityForResult(next, 1, infoToPass);
                }
            }else if(type ==2){
                String ques = questionAdapter.getItem(position).getQuestion();
                boolean bans = ((TFQuestion) questionAdapter.getItem(position)).isAnswer();
                int ans;
                if(bans){
                    ans=1;
                }else{
                    ans=2;
                }


                Long id_inChat = questionAdapter.getId(position);
                long ID = id;
                TFQuestionFragment myFragment = new TFQuestionFragment();
                Bundle infoToPass = new Bundle();
                infoToPass.putString("Question", ques);
                infoToPass.putInt("Answer", ans);
                infoToPass.putLong("IDInChat", id_inChat);
                infoToPass.putLong("ID", ID);


                //if on tablet:
                if (FrameExists) {
                    Log.i(ACTIVITY_NAME, ID + " " + id_inChat);
                    myFragment.setArguments(infoToPass);
                    myFragment.setIsTablet(true);
                    getFragmentManager().beginTransaction().replace(R.id.frame, myFragment).commit();
                } else //this is a phone:
                {
                    Log.i("HUH", "SERIOUSLY HOW");
                    myFragment.setIsTablet(false);
                    Intent next = new Intent(QuizActivity.this, TFQuestionDetails.class);
                    next.putExtra("QuestionItem", infoToPass);
                    startActivityForResult(next, 1, infoToPass);
                }
            }else if(type ==3) {
                String ques = questionAdapter.getItem(position).getQuestion();
                double ans = ((NumericQuestion) questionAdapter.getItem(position)).getAnswer();
                int pres = ((NumericQuestion) questionAdapter.getItem(position)).getPrecision();


                Long id_inChat = questionAdapter.getId(position);
                long ID = id;
                NumQuestionFragment myFragment = new NumQuestionFragment();
                Bundle infoToPass = new Bundle();
                infoToPass.putString("Question", ques);
                infoToPass.putDouble("Answer", ans);
                infoToPass.putInt("Precision", pres);
                infoToPass.putLong("IDInChat", id_inChat);
                infoToPass.putLong("ID", ID);


                //if on tablet:
                if (FrameExists) {
                    Log.i(ACTIVITY_NAME, ID + " " + id_inChat);
                    myFragment.setArguments(infoToPass);
                    myFragment.setIsTablet(true);
                    getFragmentManager().beginTransaction().replace(R.id.frame, myFragment).commit();
                } else //this is a phone:
                {
                    Log.i("WHAT", "SERIOUSLY WHAT");
                    myFragment.setIsTablet(false);
                    Intent next = new Intent(QuizActivity.this, NumQuestionDetails.class);
                    next.putExtra("QuestionItem", infoToPass);
                    startActivityForResult(next, 1, infoToPass);

                }
            }

        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == 1 && resultCode == Activity.RESULT_OK){
            Bundle extras = data.getExtras();
            if(extras.getInt("action")==1) {
                long id = extras.getLong("DeleteID");
                long id_inChat = extras.getLong("IDInChat");
                Log.i(ACTIVITY_NAME,id+" "+id_inChat);
                // String query = "DELETE FROM Messages WHERE "+ keyID + " = " +id + ";";
                db.delete(ChatDatabaseHelper.TABLE_NAME, ChatDatabaseHelper.KEY_ID + " = ?", new String[]{Long.toString(id)});//.execSQL(query);
                questions.remove((int) id_inChat);
                cursor = db.rawQuery("SELECT * FROM " + ChatDatabaseHelper.TABLE_NAME + ";", null);
                cursor.moveToFirst();
                questionAdapter.notifyDataSetChanged();
                CharSequence text = "Question deleted";// "Switch is Off"
                int duration = Toast.LENGTH_SHORT; //= Toast.LENGTH_LONG if Off
                Toast toast = Toast.makeText(getApplicationContext(), text, duration); //this is the ListActivity
                toast.show();
            }else if(extras.getInt("action")==2) {
                long id = extras.getLong("UpdateID");
                long id_inChat = extras.getLong("IDInChat");
                // String query = "DELETE FROM Messages WHERE "+ keyID + " = " +id + ";";
                db.delete(ChatDatabaseHelper.TABLE_NAME, ChatDatabaseHelper.KEY_ID + " = ?", new String[]{Long.toString(id)});//.execSQL(query);
                questions.remove((int) id_inChat);
                cursor = db.rawQuery("SELECT * FROM " + ChatDatabaseHelper.TABLE_NAME + ";", null);
                cursor.moveToFirst();
                questionAdapter.notifyDataSetChanged();
                if(extras.getInt("type")==1) {
                    String q = extras.getString("Question");
                    String a1 = extras.getString("Answer1");
                    String a2 = extras.getString("Answer2");
                    String a3 = extras.getString("Answer3");
                    String a4 = extras.getString("Answer4");
                    int c = extras.getInt("Correct");
                    updateMC(q, a1, a2, a3, a4, c);

                }else if(extras.getInt("type")==2) {
                    String q = extras.getString("Question");
                    int a1 = extras.getInt("Answer");

                    updateTF(q, a1);

                }else if(extras.getInt("type")==3) {
                    String q = extras.getString("Question");
                    double a1 = extras.getDouble("Answer");
                    int pres = extras.getInt("Precision");

                    updateNum(q, a1, pres);

                }

            }
        }
    }

    public void updateMC(String ques, String ans1, String ans2, String ans3, String ans4, int correct ){
        String q = ques;
        String a1= ans1;
        String a2= ans2;
        String a3= ans3;
        String a4= ans4;
        int c= correct;
        Question question = new MCQuestion(q, a1, a2, a3, a4, c);
        questions.add(question);

        ContentValues cv = new ContentValues();
        cv.put(ChatDatabaseHelper.KEY_TYPE, 1);
        cv.put(ChatDatabaseHelper.KEY_QUESTION,q);
        cv.put(ChatDatabaseHelper.KEY_ANSWER1,a1);
        cv.put(ChatDatabaseHelper.KEY_ANSWER2,a2);
        cv.put(ChatDatabaseHelper.KEY_ANSWER3,a3);
        cv.put(ChatDatabaseHelper.KEY_ANSWER4,a4);
        cv.put(ChatDatabaseHelper.KEY_CORRECT,c);

        db.insert(ChatDatabaseHelper.TABLE_NAME, "Null replacement value", cv);
        cursor =db.rawQuery("SELECT * FROM "+ChatDatabaseHelper.TABLE_NAME+";", null);

        cursor.moveToFirst();

        questionAdapter.notifyDataSetChanged(); //this restarts the process of getCount() & getView()


        CharSequence text = "Question updated";// "Switch is Off"
        int duration = Toast.LENGTH_SHORT; //= Toast.LENGTH_LONG if Off
        Toast toast = Toast.makeText(getApplicationContext(), text, duration); //this is the ListActivity
        toast.show();


    }

    public void updateTF(String ques, int ans){
        String q = ques;
        int a1 = ans;

        Question question = new TFQuestion(q, a1==1);
        questions.add(question);

        ContentValues cv = new ContentValues();
        cv.put(ChatDatabaseHelper.KEY_TYPE, 2);
        cv.put(ChatDatabaseHelper.KEY_QUESTION,q);
        cv.put(ChatDatabaseHelper.KEY_CORRECT,a1);

        db.insert(ChatDatabaseHelper.TABLE_NAME, "Null replacement value", cv);
        cursor =db.rawQuery("SELECT * FROM "+ChatDatabaseHelper.TABLE_NAME+";", null);

        cursor.moveToFirst();

        questionAdapter.notifyDataSetChanged(); //this restarts the process of getCount() & getView()


        CharSequence text = "Question updated";// "Switch is Off"
        int duration = Toast.LENGTH_SHORT; //= Toast.LENGTH_LONG if Off
        Toast toast = Toast.makeText(getApplicationContext(), text, duration); //this is the ListActivity
        toast.show();


    }

    public void updateNum(String ques, double ans, int pres){
        String q = ques;
        double a1 = ans;
        int p = pres;

        NumericQuestion question = new NumericQuestion(q, a1, p);
        questions.add(question);

        ContentValues cv = new ContentValues();
        cv.put(ChatDatabaseHelper.KEY_TYPE, 3);
        cv.put(ChatDatabaseHelper.KEY_QUESTION,q);
        cv.put(ChatDatabaseHelper.KEY_NUMANSWER,a1);
        cv.put(ChatDatabaseHelper.KEY_PRECISION,p);


        db.insert(ChatDatabaseHelper.TABLE_NAME, "Null replacement value", cv);
        cursor =db.rawQuery("SELECT * FROM "+ChatDatabaseHelper.TABLE_NAME+";", null);

        cursor.moveToFirst();

        questionAdapter.notifyDataSetChanged(); //this restarts the process of getCount() & getView()


        CharSequence text = "Question updated";// "Switch is Off"
        int duration = Toast.LENGTH_SHORT; //= Toast.LENGTH_LONG if Off
        Toast toast = Toast.makeText(getApplicationContext(), text, duration); //this is the ListActivity
        toast.show();


    }



    public void deleteForTablet(long idInDatabase, long idInChat, boolean deleteOnly){
        long id = idInDatabase;
        long id_inChat = idInChat;
        db.delete(ChatDatabaseHelper.TABLE_NAME, ChatDatabaseHelper.KEY_ID + " = ?", new String[] {Long.toString(id)}) ;
        questions.remove((int)id_inChat);
        cursor =db.rawQuery("SELECT * FROM "+ChatDatabaseHelper.TABLE_NAME+";", null);
        cursor.moveToFirst();
        questionAdapter.notifyDataSetChanged();
        if(deleteOnly) {
            CharSequence text = "Question deleted";// "Switch is Off"
            int duration = Toast.LENGTH_SHORT; //= Toast.LENGTH_LONG if Off
            Toast toast = Toast.makeText(getApplicationContext(), text, duration); //this is the ListActivity
            toast.show();
        }

    }



    @Override
    protected void onDestroy(){
        super.onDestroy();
        db.close();

    }

    private class QuestionAdapter extends ArrayAdapter<Question>{
        QuestionAdapter(Context ctx) {
            super(ctx, 0);
        }

        public int getCount(){
            return questions.size();
        }

        public long getItemId(int position){
            cursor.moveToPosition(position);
            return cursor.getLong(cursor.getColumnIndex("ID"));
        }

        public Question getItem(int position){
            return questions.get(position);
        }

        public View getView(int position, View convertView, ViewGroup parent){
            LayoutInflater inflater = QuizActivity.this.getLayoutInflater();
            View result = null ;


                result = inflater.inflate(R.layout.question, null);

            TextView message = result.findViewById(R.id.question_text);
           message.setText(getItem(position).getQuestion()); // get the string at position
            return result;

        }

        public long getId(int position){
            return position;
        }

    }

    public class ChatDatabaseHelper extends SQLiteOpenHelper {
        public static final String DATABASE_NAME = "Questions.db";
        public static final int VERSION_NUM = 3;
        public static final String TABLE_NAME = "Questions";
        public static final String KEY_ID = "ID";
        public static final String KEY_TYPE ="Type";
        public static final String KEY_QUESTION = "Question";
        public static final String KEY_ANSWER1 = "Answer1";
        public static final String KEY_ANSWER2 = "Answer2";
        public static final String KEY_ANSWER3 = "Answer3";
        public static final String KEY_ANSWER4 = "Answer4";
        public static final String KEY_NUMANSWER = "Numeric_Answer";
        public static final String KEY_PRECISION = "Precision";
        public static final String KEY_CORRECT = "Correct_Answer";


        public ChatDatabaseHelper(Context ctx) {
            super(ctx, DATABASE_NAME, null, VERSION_NUM);
        }
        public void onCreate(SQLiteDatabase db)
        {
            Log.i("ChatDatabaseHelper", "Calling onCreate");
            db.execSQL("CREATE TABLE " + TABLE_NAME + "( "+KEY_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "
                    +KEY_TYPE+" INTEGER, " + KEY_QUESTION + " text, "+KEY_ANSWER1+" text, "+KEY_ANSWER2+" text, "+KEY_ANSWER3+" text, "
                    +KEY_ANSWER4+" text, "+KEY_NUMANSWER+" DECIMAL(10,5), "+KEY_PRECISION+" INTEGER, "+KEY_CORRECT+" INTEGER);" );
        }
        public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer) // newVer > oldVer
        {
            Log.i("ChatDatabaseHelper", "Calling onUpgrade, oldVersion= " + oldVer + ", newVersion= " + newVer);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME ); //delete any existing data
            onCreate(db);  //make a new database
        }

        public void onDowngrade(SQLiteDatabase db, int oldVer, int newVer) // newVer > oldVer
        {
            Log.i("ChatDatabaseHelper", "Calling onDowngrade, oldVersion= " + oldVer + ", newVersion= " + newVer);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME ); //delete any existing data
            onCreate(db);  //make a new database
        }


    }
    public void setTF(int tf){
        tfA = tf;
    }
}
