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
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.greg.finalproject.R;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class QuizActivity extends AppCompatActivity {
    protected static final String ACTIVITY_NAME = "QuizActivity";
    protected SQLiteDatabase db;
    protected boolean FrameExists;
    protected Cursor cursor;

    ProgressBar progressBar;
    String tableName = QuizDatabaseHelper.TABLE_NAME;
    String keyID = QuizDatabaseHelper.KEY_ID;
    String keyMsg = QuizDatabaseHelper.KEY_QUESTION;
    ArrayList<Question> questions = new ArrayList<>();
    int tfA = 1;
    QuestionAdapter questionAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(ACTIVITY_NAME, "In onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        FrameExists = (findViewById(R.id.frame) != null);

        progressBar = (ProgressBar) findViewById(R.id.progress);
        ListView questionsList = findViewById(R.id.list);
        Button newMC = findViewById(R.id.new_MC);
        Button newTF = findViewById(R.id.new_TF);
        Button newNum = findViewById(R.id.new_Num);
        Button load = findViewById(R.id.loadxml);
        Button stats = findViewById(R.id.stats);
        questionAdapter = new QuestionAdapter(this);
        questionsList.setAdapter(questionAdapter);
        QuizDatabaseHelper myOpener = new QuizDatabaseHelper(this);
        db = myOpener.getWritableDatabase();

        cursor = db.rawQuery("SELECT * FROM " + QuizDatabaseHelper.TABLE_NAME + ";", null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            Question question;

            if (cursor.getInt(cursor.getColumnIndex(QuizDatabaseHelper.KEY_TYPE)) == 1) {
                String q = cursor.getString(cursor.getColumnIndex(QuizDatabaseHelper.KEY_QUESTION));
                String a1 = cursor.getString(cursor.getColumnIndex(QuizDatabaseHelper.KEY_ANSWER1));
                String a2 = cursor.getString(cursor.getColumnIndex(QuizDatabaseHelper.KEY_ANSWER2));
                String a3 = cursor.getString(cursor.getColumnIndex(QuizDatabaseHelper.KEY_ANSWER3));
                String a4 = cursor.getString(cursor.getColumnIndex(QuizDatabaseHelper.KEY_ANSWER4));
                int c = cursor.getInt(cursor.getColumnIndex(QuizDatabaseHelper.KEY_CORRECT));
                question = new MCQuestion(q, a1, a2, a3, a4, c);
            } else if (cursor.getInt(cursor.getColumnIndex(QuizDatabaseHelper.KEY_TYPE)) == 2) {
                String q = cursor.getString(cursor.getColumnIndex(QuizDatabaseHelper.KEY_QUESTION));
                boolean a = cursor.getInt(cursor.getColumnIndex(QuizDatabaseHelper.KEY_CORRECT)) == 1;
                question = new TFQuestion(q, a);
            } else if (cursor.getInt(cursor.getColumnIndex(QuizDatabaseHelper.KEY_TYPE)) == 3) {
                String q = cursor.getString(cursor.getColumnIndex(QuizDatabaseHelper.KEY_QUESTION));
                double a = cursor.getDouble(cursor.getColumnIndex(QuizDatabaseHelper.KEY_NUMANSWER));
                int p = cursor.getInt(cursor.getColumnIndex(QuizDatabaseHelper.KEY_PRECISION));
                question = new NumericQuestion(q, a, p);
            } else {
                String q = cursor.getString(cursor.getColumnIndex(QuizDatabaseHelper.KEY_QUESTION));
                question = new MCQuestion("error" + q, "error", "error", "error", "error", 2);
            }
            questions.add(question);
            Log.i(ACTIVITY_NAME, "SQL MESSAGE:" + cursor.getString(cursor.getColumnIndex(QuizDatabaseHelper.KEY_QUESTION)));
            cursor.moveToNext();
            }

        load.setOnClickListener(e -> {
            QuizQuery query = new QuizQuery();
            query.execute("http://torunski.ca/CST2335/QuizInstance.xml");
        });

        stats.setOnClickListener(e -> {
            int totMC = 0;
            int totTF = 0;
            int totNum = 0;
            for (Question question : questions) {
                if (question.getType() == 1) {
                    totMC++;
                }
                if (question.getType() == 2) {
                    totTF++;
                }
                if (question.getType() == 3) {
                    totNum++;
                }
            }
            Bundle bundle = new Bundle();
            bundle.putInt("tot", questions.size());
            bundle.putInt("mc", totMC);
            bundle.putInt("tf", totTF);
            bundle.putInt("num", totNum);

            Intent statsIntent = new Intent(QuizActivity.this, QuestionStatsActivity.class);
            statsIntent.putExtra("StatsItem", bundle);
            startActivity(statsIntent, bundle);
        });

        newMC.setOnClickListener(e -> {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
                    final View view = this.getLayoutInflater().inflate(R.layout.mc_dialog, null);
                    builder1.setView(view);
                    builder1.setPositiveButton(R.string.newMC, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            EditText ques = (EditText) view.findViewById(R.id.newQuestion);
                            EditText answer1 = (EditText) view.findViewById(R.id.answer1);
                            EditText answer2 = (EditText) view.findViewById(R.id.answer2);
                            EditText answer3 = (EditText) view.findViewById(R.id.answer3);
                            EditText answer4 = (EditText) view.findViewById(R.id.answer4);
                            EditText correct = (EditText) view.findViewById(R.id.correct);
                            String q = ques.getText().toString();
                            String a1 = answer1.getText().toString();
                            String a2 = answer2.getText().toString();
                            String a3 = answer3.getText().toString();
                            String a4 = answer4.getText().toString();
                            int c = Integer.parseInt(correct.getText().toString());
                            MCQuestion question = new MCQuestion(q, a1, a2, a3, a4, c);
                            questions.add(question);

                            ContentValues cv = new ContentValues();
                            cv.put(QuizDatabaseHelper.KEY_TYPE, 1);
                            cv.put(QuizDatabaseHelper.KEY_QUESTION, q);
                            cv.put(QuizDatabaseHelper.KEY_ANSWER1, a1);
                            cv.put(QuizDatabaseHelper.KEY_ANSWER2, a2);
                            cv.put(QuizDatabaseHelper.KEY_ANSWER3, a3);
                            cv.put(QuizDatabaseHelper.KEY_ANSWER4, a4);
                            cv.put(QuizDatabaseHelper.KEY_CORRECT, c);

                            db.insert(QuizDatabaseHelper.TABLE_NAME, "Null replacement value", cv);
                            cursor = db.rawQuery("SELECT * FROM " + QuizDatabaseHelper.TABLE_NAME + ";", null);
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
        newTF.setOnClickListener(e -> {
                    setTF(1);
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
                    final View view = this.getLayoutInflater().inflate(R.layout.tf_dialog, null);
                    builder1.setView(view);
                    RadioButton trueButton = (RadioButton) view.findViewById(R.id.trueButton);
                    RadioButton falseButton = (RadioButton) view.findViewById(R.id.falseButton);
                    trueButton.setOnClickListener((test) -> {
                                setTF(1);
                            }
                    );
                    falseButton.setOnClickListener((test) -> {
                                setTF(2);
                            }
                    );
                    builder1.setPositiveButton(R.string.newTF, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            EditText ques = (EditText) view.findViewById(R.id.newTFQuestion);
                            RadioGroup answer = (RadioGroup) view.findViewById(R.id.TFGroup);
                            String q = ques.getText().toString();
                            TFQuestion question = new TFQuestion(q, tfA == 1);
                            questions.add(question);
                            ContentValues cv = new ContentValues();
                            cv.put(QuizDatabaseHelper.KEY_TYPE, 2);
                            cv.put(QuizDatabaseHelper.KEY_QUESTION, q);
                            cv.put(QuizDatabaseHelper.KEY_CORRECT, tfA);
                            db.insert(QuizDatabaseHelper.TABLE_NAME, "Null replacement value", cv);
                            cursor = db.rawQuery("SELECT * FROM " + QuizDatabaseHelper.TABLE_NAME + ";", null);
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
        newNum.setOnClickListener(e -> {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
                    final View view = this.getLayoutInflater().inflate(R.layout.num_dialog, null);
                    builder1.setView(view);
                    builder1.setPositiveButton(R.string.newNum, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            EditText ques = (EditText) view.findViewById(R.id.newQuestion);
                            EditText answer = (EditText) view.findViewById(R.id.answer);
                            EditText precision = (EditText) view.findViewById(R.id.precision);
                            String q = ques.getText().toString();
                            double a = Double.parseDouble(answer.getText().toString());
                            int p = Integer.parseInt(precision.getText().toString());
                            NumericQuestion question = new NumericQuestion(q, a, p);
                            questions.add(question);
                            ContentValues cv = new ContentValues();
                            cv.put(QuizDatabaseHelper.KEY_TYPE, 3);
                            cv.put(QuizDatabaseHelper.KEY_QUESTION, q);
                            cv.put(QuizDatabaseHelper.KEY_NUMANSWER, a);
                            cv.put(QuizDatabaseHelper.KEY_PRECISION, p);
                            db.insert(QuizDatabaseHelper.TABLE_NAME, "Null replacement value", cv);
                            cursor = db.rawQuery("SELECT * FROM " + QuizDatabaseHelper.TABLE_NAME + ";", null);
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
        questionsList.setOnItemClickListener((adapterView, view, position, id) -> {
            Log.i("WHAT THE CRAP", "position: "+position+" id: "+id);
            int type = questionAdapter.getItem(position).getType();
            if (type == 1) {
                String ques = questionAdapter.getItem(position).getQuestion();
                String ans1 = ((MCQuestion) questionAdapter.getItem(position)).getAnswer1();
                String ans2 = ((MCQuestion) questionAdapter.getItem(position)).getAnswer2();
                String ans3 = ((MCQuestion) questionAdapter.getItem(position)).getAnswer3();
                String ans4 = ((MCQuestion) questionAdapter.getItem(position)).getAnswer4();
                int cor = ((MCQuestion) questionAdapter.getItem(position)).getCorrectAnswer();
                Long id_inList = questionAdapter.getId(position);
                long ID = id;
                MCQuestionFragment myFragment = new MCQuestionFragment();
                Bundle infoToPass = new Bundle();
                infoToPass.putString("Question", ques);
                infoToPass.putString("Answer1", ans1);
                infoToPass.putString("Answer2", ans2);
                infoToPass.putString("Answer3", ans3);
                infoToPass.putString("Answer4", ans4);
                infoToPass.putInt("CorrectAnswer", cor);
                infoToPass.putLong("IDInChat", id_inList);
                infoToPass.putLong("ID", ID);

                //if on tablet:
                if (FrameExists) {
                    myFragment.setArguments(infoToPass);
                    myFragment.setIsTablet(true);
                    getFragmentManager().beginTransaction().replace(R.id.frame, myFragment).commit();
                } else{//this is a phone:

                    myFragment.setIsTablet(false);
                    Intent next = new Intent(QuizActivity.this, MCQuestionDetails.class);
                    next.putExtra("QuestionItem", infoToPass);
                    startActivityForResult(next, 1, infoToPass);
                }
            } else if (type == 2) {
                String ques = questionAdapter.getItem(position).getQuestion();
                boolean bans = ((TFQuestion) questionAdapter.getItem(position)).isAnswer();
                int ans;
                if (bans) {
                    ans = 1;
                } else {
                    ans = 2;
                }
                Long id_inList = questionAdapter.getId(position);
                long ID = id;
                TFQuestionFragment myFragment = new TFQuestionFragment();
                Bundle infoToPass = new Bundle();
                infoToPass.putString("Question", ques);
                infoToPass.putInt("Answer", ans);
                infoToPass.putLong("IDInChat", id_inList);
                infoToPass.putLong("ID", ID);
                //if on tablet:
                if (FrameExists) {
                    myFragment.setArguments(infoToPass);
                    myFragment.setIsTablet(true);
                    getFragmentManager().beginTransaction().replace(R.id.frame, myFragment).commit();
                } else //this is a phone:
                {
                    myFragment.setIsTablet(false);
                    Intent next = new Intent(QuizActivity.this, TFQuestionDetails.class);
                    next.putExtra("QuestionItem", infoToPass);
                    startActivityForResult(next, 1, infoToPass);
                }
            } else if (type == 3) {
                String ques = questionAdapter.getItem(position).getQuestion();
                double ans = ((NumericQuestion) questionAdapter.getItem(position)).getAnswer();
                int pres = ((NumericQuestion) questionAdapter.getItem(position)).getPrecision();
                Long id_inList = questionAdapter.getId(position);
                long ID = id;
                NumQuestionFragment myFragment = new NumQuestionFragment();
                Bundle infoToPass = new Bundle();
                infoToPass.putString("Question", ques);
                infoToPass.putDouble("Answer", ans);
                infoToPass.putInt("Precision", pres);
                infoToPass.putLong("IDInChat", id_inList);
                infoToPass.putLong("ID", ID);
                //if on tablet:
                if (FrameExists) {
                    myFragment.setArguments(infoToPass);
                    myFragment.setIsTablet(true);
                    getFragmentManager().beginTransaction().replace(R.id.frame, myFragment).commit();
                } else{ //this is a phone:
                    myFragment.setIsTablet(false);
                    Intent next = new Intent(QuizActivity.this, NumQuestionDetails.class);
                    next.putExtra("QuestionItem", infoToPass);
                    startActivityForResult(next, 1, infoToPass);
                }
            }

        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            Bundle extras = data.getExtras();
            if (extras.getInt("action") == 1) {
                long id = extras.getLong("DeleteID");
                long id_inList = extras.getLong("IDInChat");

                db.delete(QuizDatabaseHelper.TABLE_NAME, QuizDatabaseHelper.KEY_ID + " = ?", new String[]{Long.toString(id)});//.execSQL(query);
                questions.remove((int) id_inList);
                cursor = db.rawQuery("SELECT * FROM " + QuizDatabaseHelper.TABLE_NAME + ";", null);
                cursor.moveToFirst();
                questionAdapter.notifyDataSetChanged();
                CharSequence text = "Question deleted";// "Switch is Off"
                int duration = Toast.LENGTH_SHORT; //= Toast.LENGTH_LONG if Off
                Toast toast = Toast.makeText(getApplicationContext(), text, duration); //this is the ListActivity
                toast.show();
            } else if (extras.getInt("action") == 2) {
                long id = extras.getLong("UpdateID");
                long id_inList = extras.getLong("IDInChat");

                db.delete(QuizDatabaseHelper.TABLE_NAME, QuizDatabaseHelper.KEY_ID + " = ?", new String[]{Long.toString(id)});//.execSQL(query);
                questions.remove((int) id_inList);
                cursor = db.rawQuery("SELECT * FROM " + QuizDatabaseHelper.TABLE_NAME + ";", null);
                cursor.moveToFirst();
                questionAdapter.notifyDataSetChanged();
                if (extras.getInt("type") == 1) {
                    String q = extras.getString("Question");
                    String a1 = extras.getString("Answer1");
                    String a2 = extras.getString("Answer2");
                    String a3 = extras.getString("Answer3");
                    String a4 = extras.getString("Answer4");
                    int c = extras.getInt("Correct");
                    updateMC(q, a1, a2, a3, a4, c);
                } else if (extras.getInt("type") == 2) {
                    String q = extras.getString("Question");
                    int a1 = extras.getInt("Answer");
                    updateTF(q, a1);
                } else if (extras.getInt("type") == 3) {
                    String q = extras.getString("Question");
                    double a1 = extras.getDouble("Answer");
                    int pres = extras.getInt("Precision");
                    updateNum(q, a1, pres);
                }
            }
        }
    }
    public void updateMC(String ques, String ans1, String ans2, String ans3, String ans4, int correct) {
        String q = ques;
        String a1 = ans1;
        String a2 = ans2;
        String a3 = ans3;
        String a4 = ans4;
        int c = correct;
        Question question = new MCQuestion(q, a1, a2, a3, a4, c);
        questions.add(question);
        ContentValues cv = new ContentValues();
        cv.put(QuizDatabaseHelper.KEY_TYPE, 1);
        cv.put(QuizDatabaseHelper.KEY_QUESTION, q);
        cv.put(QuizDatabaseHelper.KEY_ANSWER1, a1);
        cv.put(QuizDatabaseHelper.KEY_ANSWER2, a2);
        cv.put(QuizDatabaseHelper.KEY_ANSWER3, a3);
        cv.put(QuizDatabaseHelper.KEY_ANSWER4, a4);
        cv.put(QuizDatabaseHelper.KEY_CORRECT, c);
        db.insert(QuizDatabaseHelper.TABLE_NAME, "Null replacement value", cv);
        cursor = db.rawQuery("SELECT * FROM " + QuizDatabaseHelper.TABLE_NAME + ";", null);
        cursor.moveToFirst();
        questionAdapter.notifyDataSetChanged(); //this restarts the process of getCount() & getView()
        CharSequence text = "Question updated";// "Switch is Off"
        int duration = Toast.LENGTH_SHORT; //= Toast.LENGTH_LONG if Off
        Toast toast = Toast.makeText(getApplicationContext(), text, duration); //this is the ListActivity
        toast.show();
    }

    public void updateTF(String ques, int ans) {
        String q = ques;
        int a1 = ans;
        Question question = new TFQuestion(q, a1 == 1);
        questions.add(question);
        ContentValues cv = new ContentValues();
        cv.put(QuizDatabaseHelper.KEY_TYPE, 2);
        cv.put(QuizDatabaseHelper.KEY_QUESTION, q);
        cv.put(QuizDatabaseHelper.KEY_CORRECT, a1);
        db.insert(QuizDatabaseHelper.TABLE_NAME, "Null replacement value", cv);
        cursor = db.rawQuery("SELECT * FROM " + QuizDatabaseHelper.TABLE_NAME + ";", null);
        cursor.moveToFirst();
        questionAdapter.notifyDataSetChanged(); //this restarts the process of getCount() & getView()
        CharSequence text = "Question updated";// "Switch is Off"
        int duration = Toast.LENGTH_SHORT; //= Toast.LENGTH_LONG if Off
        Toast toast = Toast.makeText(getApplicationContext(), text, duration); //this is the ListActivity
        toast.show();
    }

    public void updateNum(String ques, double ans, int pres) {
        String q = ques;
        double a1 = ans;
        int p = pres;
        NumericQuestion question = new NumericQuestion(q, a1, p);
        questions.add(question);
        ContentValues cv = new ContentValues();
        cv.put(QuizDatabaseHelper.KEY_TYPE, 3);
        cv.put(QuizDatabaseHelper.KEY_QUESTION, q);
        cv.put(QuizDatabaseHelper.KEY_NUMANSWER, a1);
        cv.put(QuizDatabaseHelper.KEY_PRECISION, p);
        db.insert(QuizDatabaseHelper.TABLE_NAME, "Null replacement value", cv);
        cursor = db.rawQuery("SELECT * FROM " + QuizDatabaseHelper.TABLE_NAME + ";", null);
        cursor.moveToFirst();
        questionAdapter.notifyDataSetChanged(); //this restarts the process of getCount() & getView()
        CharSequence text = "Question updated";// "Switch is Off"
        int duration = Toast.LENGTH_SHORT; //= Toast.LENGTH_LONG if Off
        Toast toast = Toast.makeText(getApplicationContext(), text, duration); //this is the ListActivity
        toast.show();
    }
    public void deleteForTablet(long idInDatabase, long idInChat, boolean deleteOnly) {
        long id = idInDatabase;
        long id_inChat = idInChat;
        db.delete(QuizDatabaseHelper.TABLE_NAME, QuizDatabaseHelper.KEY_ID + " = ?", new String[]{Long.toString(id)});
        questions.remove((int) id_inChat);
        cursor = db.rawQuery("SELECT * FROM " + QuizDatabaseHelper.TABLE_NAME + ";", null);
        cursor.moveToFirst();
        questionAdapter.notifyDataSetChanged();
        if (deleteOnly) {
            CharSequence text = "Question deleted";// "Switch is Off"
            int duration = Toast.LENGTH_SHORT; //= Toast.LENGTH_LONG if Off
            Toast toast = Toast.makeText(getApplicationContext(), text, duration); //this is the ListActivity
            toast.show();
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.close();
    }

    public void setTF(int tf) {
        tfA = tf;
    }

    private class QuestionAdapter extends ArrayAdapter<Question> {
        QuestionAdapter(Context ctx) {
            super(ctx, 0);
        }

        public int getCount() {
            return questions.size();
        }

        public long getItemId(int position) {
            cursor.moveToPosition(position);
            return cursor.getLong(cursor.getColumnIndex("ID"));
        }

        public Question getItem(int position) {
            return questions.get(position);
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = QuizActivity.this.getLayoutInflater();
            View result = null;
            result = inflater.inflate(R.layout.question, null);
            TextView message = result.findViewById(R.id.question_text);
            message.setText(getItem(position).getQuestion()); // get the string at position
            return result;
        }

        public long getId(int position) {
            return position;
        }
    }

    public class QuizDatabaseHelper extends SQLiteOpenHelper {
        public static final String DATABASE_NAME = "Questions.db";
        public static final int VERSION_NUM = 3;
        public static final String TABLE_NAME = "Questions";
        public static final String KEY_ID = "ID";
        public static final String KEY_TYPE = "Type";
        public static final String KEY_QUESTION = "Question";
        public static final String KEY_ANSWER1 = "Answer1";
        public static final String KEY_ANSWER2 = "Answer2";
        public static final String KEY_ANSWER3 = "Answer3";
        public static final String KEY_ANSWER4 = "Answer4";
        public static final String KEY_NUMANSWER = "Numeric_Answer";
        public static final String KEY_PRECISION = "Precision";
        public static final String KEY_CORRECT = "Correct_Answer";

        public QuizDatabaseHelper(Context ctx) {
            super(ctx, DATABASE_NAME, null, VERSION_NUM);
        }

        public void onCreate(SQLiteDatabase db) {
            Log.i("QuizDatabaseHelper", "Calling onCreate");
            db.execSQL("CREATE TABLE " + TABLE_NAME + "( " + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + KEY_TYPE + " INTEGER, " + KEY_QUESTION + " text, " + KEY_ANSWER1 + " text, " + KEY_ANSWER2 + " text, " + KEY_ANSWER3 + " text, "
                    + KEY_ANSWER4 + " text, " + KEY_NUMANSWER + " DECIMAL(10,5), " + KEY_PRECISION + " INTEGER, " + KEY_CORRECT + " INTEGER);");
        }
        public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer){ // newVer > oldVer
            Log.i("QuizDatabaseHelper", "Calling onUpgrade, oldVersion= " + oldVer + ", newVersion= " + newVer);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME); //delete any existing data
            onCreate(db);  //make a new database
        }

        public void onDowngrade(SQLiteDatabase db, int oldVer, int newVer){ // newVer > oldVer
            Log.i("QuizDatabaseHelper", "Calling onDowngrade, oldVersion= " + oldVer + ", newVersion= " + newVer);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME); //delete any existing data
            onCreate(db);  //make a new database
        }
    }

    private class QuizQuery extends AsyncTask<String, Integer, String> {
        int i = 0;
        MCQuestion MCquestion = new MCQuestion();
        private String ques;
        private ArrayList<String> a = new ArrayList<String>();
        private String correct;
        private String pres;
        protected String doInBackground(String... args) {
            try {
                for (String siteUrl : args) {
                    URL url = new URL(siteUrl);
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    InputStream iStream = urlConnection.getInputStream();
                    XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                    factory.setNamespaceAware(false);
                    XmlPullParser xpp = factory.newPullParser();
                    xpp.setInput(iStream, "UTF-8");
                    //start reading:
                    //While you're not at the end of the document:
                    while (xpp.next() != XmlPullParser.END_DOCUMENT) {
                        if (xpp.getEventType() == XmlPullParser.START_TAG) {
                            if (xpp.getName().equals("MultipleChoiceQuestion")) {
                                MCquestion.setQuestion(xpp.getAttributeValue(null, "question"));
                                publishProgress(25);
                                MCquestion.setCorrectAnswer(Integer.parseInt(xpp.getAttributeValue(null, "correct")));
                            } else if ((xpp.getName().equals("Answer"))) {
                                if (i == 0) {
                                    MCquestion.setAnswer1(xpp.nextText());
                                } else if (i == 1) {
                                    MCquestion.setAnswer2(xpp.nextText());
                                } else if (i == 2) {
                                    MCquestion.setAnswer3(xpp.nextText());
                                } else if (i == 3) {
                                    MCquestion.setAnswer4(xpp.nextText());
                                }
                                i++;
                                if (i == 4) {
                                    questions.add(MCquestion);
                                    ContentValues cv = new ContentValues();
                                    cv.put(QuizDatabaseHelper.KEY_TYPE, 1);
                                    cv.put(QuizDatabaseHelper.KEY_QUESTION, MCquestion.getQuestion());
                                    cv.put(QuizDatabaseHelper.KEY_ANSWER1, MCquestion.getAnswer1());
                                    cv.put(QuizDatabaseHelper.KEY_ANSWER2, MCquestion.getAnswer2());
                                    cv.put(QuizDatabaseHelper.KEY_ANSWER3, MCquestion.getAnswer3());
                                    cv.put(QuizDatabaseHelper.KEY_ANSWER4, MCquestion.getAnswer4());
                                    cv.put(QuizDatabaseHelper.KEY_CORRECT, MCquestion.getCorrectAnswer());
                                    db.insert(QuizDatabaseHelper.TABLE_NAME, "Null replacement value", cv);
                                    cursor = db.rawQuery("SELECT * FROM " + QuizDatabaseHelper.TABLE_NAME + ";", null);
                                    cursor.moveToFirst();
                                    i = 0;
                                    MCquestion = new MCQuestion();
                                }
                            } else if (xpp.getName().equals("NumericQuestion")) {
                                ques = xpp.getAttributeValue(null, "question");
                                correct = xpp.getAttributeValue(null, "answer");
                                pres = xpp.getAttributeValue(null, "accuracy");
                                NumericQuestion question = new NumericQuestion(ques, Double.parseDouble(correct), Integer.parseInt(pres));
                                questions.add(question);
                                ContentValues cv = new ContentValues();
                                cv.put(QuizDatabaseHelper.KEY_TYPE, 3);
                                cv.put(QuizDatabaseHelper.KEY_QUESTION, ques);
                                cv.put(QuizDatabaseHelper.KEY_NUMANSWER, Double.parseDouble(correct));
                                cv.put(QuizDatabaseHelper.KEY_PRECISION, Integer.parseInt(pres));
                                db.insert(QuizDatabaseHelper.TABLE_NAME, "Null replacement value", cv);
                                cursor = db.rawQuery("SELECT * FROM " + QuizDatabaseHelper.TABLE_NAME + ";", null);
                                cursor.moveToFirst();
                            } else if (xpp.getName().equals("TrueFalseQuestion")) {
                                ques = xpp.getAttributeValue(null, "question");
                                correct = xpp.getAttributeValue(null, "answer");
                                TFQuestion question = new TFQuestion(ques, correct.equals("true"));
                                questions.add(question);
                                int j;
                                if (correct.equals("true")) {
                                    j = 1;
                                } else {
                                    j = 2;
                                }
                                ContentValues cv = new ContentValues();
                                cv.put(QuizDatabaseHelper.KEY_TYPE, 2);
                                cv.put(QuizDatabaseHelper.KEY_QUESTION, ques);
                                cv.put(QuizDatabaseHelper.KEY_CORRECT, j);
                                db.insert(QuizDatabaseHelper.TABLE_NAME, "Null replacement value", cv);
                                cursor = db.rawQuery("SELECT * FROM " + QuizDatabaseHelper.TABLE_NAME + ";", null);
                                cursor.moveToFirst();
                            }
                        }
                    }
                }
            } catch (Exception mfe) {
                Log.e("Error", mfe.getMessage());
            }
            //Send a string to the GUI thread through onPostExecute
            return "Finished";
        }

        public void onProgressUpdate(Integer... data) {
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setProgress(data[0]);
        }
        //gui thread
        public void onPostExecute(String result) {
            //now you can post your result to the GUI
            questionAdapter.notifyDataSetChanged();
            Snackbar.make(findViewById(R.id.loadxml), "Questions Loaded", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            progressBar.setVisibility(View.INVISIBLE);
        }
    }
}
