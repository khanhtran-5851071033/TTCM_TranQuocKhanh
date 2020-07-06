package com.example.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.adapter.QuizContract;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.model.Category;
import com.example.model.Question;

import java.util.ArrayList;
import java.util.List;

public class QuizDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "MyAwesomeQuiz.db";
    private static final int DATABASE_VERSION = 1;
    private static QuizDbHelper instance;
    private SQLiteDatabase db;
    private QuizDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        context.deleteDatabase("MyAwesomeQuiz1.db");
       //context.deleteDatabase("MyAwesomeQuiz1.db");
    }
    public static synchronized QuizDbHelper getInstance(Context context) {
        if (instance == null) {
            instance = new QuizDbHelper(context.getApplicationContext());
        }
        return instance;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;
        final String SQL_CREATE_CATEGORIES_TABLE = "CREATE TABLE " +
                QuizContract.CategoriesTable.TABLE_NAME + "( " +
                QuizContract.CategoriesTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                QuizContract.CategoriesTable.COLUMN_NAME + " TEXT " +
                ")";
        final String SQL_CREATE_QUESTIONS_TABLE = "CREATE TABLE " +
                QuizContract.QuestionsTable.TABLE_NAME + " ( " +
                QuizContract.QuestionsTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                QuizContract.QuestionsTable.COLUMN_QUESTION + " TEXT, " +
                QuizContract.QuestionsTable.COLUMN_OPTION1 + " TEXT, " +
                QuizContract.QuestionsTable.COLUMN_OPTION2 + " TEXT, " +
                QuizContract.QuestionsTable.COLUMN_OPTION3 + " TEXT, " +
                QuizContract.QuestionsTable.COLUMN_ANSWER_NR + " INTEGER, " +
                QuizContract.QuestionsTable.COLUMN_DIFFICULTY + " TEXT, " +
                QuizContract.QuestionsTable.COLUMN_CATEGORY_ID + " INTEGER, " +
                "FOREIGN KEY(" + QuizContract.QuestionsTable.COLUMN_CATEGORY_ID + ") REFERENCES " +
                QuizContract.CategoriesTable.TABLE_NAME + "(" + QuizContract.CategoriesTable._ID + ")" + "ON DELETE CASCADE" +
                ")";
        db.execSQL(SQL_CREATE_CATEGORIES_TABLE);
        db.execSQL(SQL_CREATE_QUESTIONS_TABLE);
        fillCategoriesTable();
        fillQuestionsTable();
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + QuizContract.CategoriesTable.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + QuizContract.QuestionsTable.TABLE_NAME);
        onCreate(db);
    }
    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }
    private void fillCategoriesTable() {
        Category c1 = new Category("Grammar");
        insertCategory(c1);
        Category c2 = new Category("Communication");
        insertCategory(c2);
        Category c3 = new Category("Vocabulary");
        insertCategory(c3);
    }
    public void addCategory(Category category) {
        db = getWritableDatabase();
        insertCategory(category);
    }
    public void addCategories(List<Category> categories) {
        db = getWritableDatabase();
        for (Category category : categories) {
            insertCategory(category);
        }
    }
    private void insertCategory(Category category) {
        ContentValues cv = new ContentValues();
        cv.put(QuizContract.CategoriesTable.COLUMN_NAME, category.getName());
        db.insert(QuizContract.CategoriesTable.TABLE_NAME, null, cv);
    }
    private void fillQuestionsTable() {
        //Gramar
        Question q1 = new Question("What would you like___eat?",
                "to", "in", "at", 1,
                Question.DIFFICULTY_EASY, Category.Grammar);
        insertQuestion(q1);
        Question q10 = new Question("Don't you remember ____this film star on TV last year?",
                "that you see", "to see", "seeing", 3,
                Question.DIFFICULTY_EASY, Category.Grammar);
        insertQuestion(q10);
        Question q6 = new Question(" If you ______ your money to mine, we shall have enough.",
                "combine", " add", "join", 2,
                Question.DIFFICULTY_MEDIUM, Category.Grammar);
        insertQuestion(q6);
        Question q3 = new Question(" Experiments in the sonic imaging of moving objects … in both the United States and Europe well before the Second World War.",
                "were conducting", "have been conducted", "had been conducted", 3,
                Question.DIFFICULTY_HARD, Category.Grammar);
        insertQuestion(q3);


        //Communication
        Question q5 = new Question("That's a very nice dress you're wearing. -______",
                " I'm glad you like it", "That's all right", "That's nice", 1,
                Question.DIFFICULTY_EASY, Category.Communication);
        insertQuestion(q5);
        Question q7 = new Question(" ______? – Not now, but I used to.",
                "Won't you have some tea", "Do you watch TV very often", "Are you going to bed", 2,
                Question.DIFFICULTY_EASY, Category.Communication);
        insertQuestion(q7);
        Question q9 = new Question(" Would you please ______so loudly?",
                "don't speak", "not to speak", "not speak", 3,
                Question.DIFFICULTY_MEDIUM, Category.Communication);
        insertQuestion(q9);
        Question q2 = new Question("Would you like some beer? – Yes, just______",
                "few", "a few", "little", 3,
                Question.DIFFICULTY_MEDIUM, Category.Communication);
        insertQuestion(q2);
        Question q8 = new Question("You are standing too near the stage. Can you move_________?",
                "a little farther", "a bit far", "a little far", 1,
                Question.DIFFICULTY_HARD, Category.Communication);
        insertQuestion(q8);
        Question q4 = new Question("To whom does English belong? -____",
                "It belongs to the English people.", "It is the private property of Americans.", " It belongs to those who use it.", 3,
                Question.DIFFICULTY_HARD, Category.Communication);
        insertQuestion(q4);

        //Vocabulary
        Question v1 = new Question("What is the meaning of this word : STRENGTHEN",
                "Béo phì", "Gầy,ốm", "Làm mạnh lên, củng cố", 3,
                Question.DIFFICULTY_HARD, Category.Vocabulary);
        insertQuestion(v1);
        Question v2 = new Question("What is the meaning of this word : EVALUATE",
                "Đánh giá", "Phàn nàn", "Khuyên nhủ", 1,
                Question.DIFFICULTY_HARD, Category.Vocabulary);
        insertQuestion(v2);
        Question v3 = new Question("What is the meaning of this word : DEMONSTRATE",
                "Trình bày, làm rõ", "Tin cậy,nhờ cậy", "Giải quyết", 2,
                Question.DIFFICULTY_HARD, Category.Vocabulary);
        insertQuestion(v3);
        Question v4 = new Question("What is the meaning of this word : COMBINE",
                "Tách rời", "Kết hợp", "Phản đối", 2,
                Question.DIFFICULTY_HARD, Category.Vocabulary);
        insertQuestion(v4);
        Question v5 = new Question("What is the meaning of this word : AGGRESSIVE",
                "Cố gắng,nghị lực", "Tâm huyết,kiên cường", "Xông xáo, quyết liệt", 3,
                Question.DIFFICULTY_HARD, Category.Vocabulary);
        insertQuestion(v5);
        Question v6 = new Question("What is the meaning of this word in IT: Cluster controller ",
                "mô hình chuẩn OSI", "Phân loại tổng quát", "Bộ điều khiển trùm", 3,
                Question.DIFFICULTY_MEDIUM, Category.Vocabulary);
        insertQuestion(v6);
        Question v7 = new Question("What is the meaning of this word in IT: Microprocessor",
                "Bộ vi xử lý", "Giao thức", "Vòng nhiễm từ", 1,
                Question.DIFFICULTY_MEDIUM, Category.Vocabulary);
        insertQuestion(v7);
        Question v8 = new Question("What is the meaning of this word in IT : Technical",
                " Ứng dụng", "Thuộc về kỹ thuật", "Phân tích", 2,
                Question.DIFFICULTY_MEDIUM, Category.Vocabulary);
        insertQuestion(v8);
        Question v9 = new Question("What is the meaning of this word in IT: Compatible",
                "Giám đốc", "Tương thích", "Chuyên viên tham vấn  ", 2,
                Question.DIFFICULTY_MEDIUM, Category.Vocabulary);
        insertQuestion(v9);
        Question v10 = new Question("What is the meaning of this word in IT: Dependable",
                "Khả năng", "Phát minh.", "Có thể tin cậy được.", 3,
                Question.DIFFICULTY_MEDIUM, Category.Vocabulary);
        insertQuestion(v10);


    }
    public void addQuestion(Question question) {
        db = getWritableDatabase();
        insertQuestion(question);
    }
    public void addQuestions(List<Question> questions) {
        db = getWritableDatabase();
        for (Question question : questions) {
            insertQuestion(question);
        }
    }
    private void insertQuestion(Question question) {
        ContentValues cv = new ContentValues();
        cv.put(QuizContract.QuestionsTable.COLUMN_QUESTION, question.getQuestion());
        cv.put(QuizContract.QuestionsTable.COLUMN_OPTION1, question.getOption1());
        cv.put(QuizContract.QuestionsTable.COLUMN_OPTION2, question.getOption2());
        cv.put(QuizContract.QuestionsTable.COLUMN_OPTION3, question.getOption3());
        cv.put(QuizContract.QuestionsTable.COLUMN_ANSWER_NR, question.getAnswerNr());
        cv.put(QuizContract.QuestionsTable.COLUMN_DIFFICULTY, question.getDifficulty());
        cv.put(QuizContract.QuestionsTable.COLUMN_CATEGORY_ID, question.getCategoryID());
        db.insert(QuizContract.QuestionsTable.TABLE_NAME, null, cv);
    }
    public List<Category> getAllCategories() {
        List<Category> categoryList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + QuizContract.CategoriesTable.TABLE_NAME, null);
        if (c.moveToFirst()) {
            do {
                Category category = new Category();
                category.setId(c.getInt(c.getColumnIndex(QuizContract.CategoriesTable._ID)));
                category.setName(c.getString(c.getColumnIndex(QuizContract.CategoriesTable.COLUMN_NAME)));
                categoryList.add(category);
            } while (c.moveToNext());
        }
        c.close();
        return categoryList;
    }
    public ArrayList<Question> getAllQuestions() {
        ArrayList<Question> questionList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + QuizContract.QuestionsTable.TABLE_NAME, null);
        if (c.moveToFirst()) {
            do {
                Question question = new Question();
                question.setId(c.getInt(c.getColumnIndex(QuizContract.QuestionsTable._ID)));
                question.setQuestion(c.getString(c.getColumnIndex(QuizContract.QuestionsTable.COLUMN_QUESTION)));
                question.setOption1(c.getString(c.getColumnIndex(QuizContract.QuestionsTable.COLUMN_OPTION1)));
                question.setOption2(c.getString(c.getColumnIndex(QuizContract.QuestionsTable.COLUMN_OPTION2)));
                question.setOption3(c.getString(c.getColumnIndex(QuizContract.QuestionsTable.COLUMN_OPTION3)));
                question.setAnswerNr(c.getInt(c.getColumnIndex(QuizContract.QuestionsTable.COLUMN_ANSWER_NR)));
                question.setDifficulty(c.getString(c.getColumnIndex(QuizContract.QuestionsTable.COLUMN_DIFFICULTY)));
                question.setCategoryID(c.getInt(c.getColumnIndex(QuizContract.QuestionsTable.COLUMN_CATEGORY_ID)));
                questionList.add(question);
            } while (c.moveToNext());
        }
        c.close();
        return questionList;
    }
    public ArrayList<Question> getQuestions(int categoryID, String difficulty) {
        ArrayList<Question> questionList = new ArrayList<>();
        db = getReadableDatabase();
        String selection = QuizContract.QuestionsTable.COLUMN_CATEGORY_ID + " = ? " +
                " AND " + QuizContract.QuestionsTable.COLUMN_DIFFICULTY + " = ? ";
        String[] selectionArgs = new String[]{String.valueOf(categoryID), difficulty};
        Cursor c = db.query(
                QuizContract.QuestionsTable.TABLE_NAME,
                null,
                selection,
                selectionArgs,
                null,
                null,
                null
        );
        if (c.moveToFirst()) {
            do {
                Question question = new Question();
                question.setId(c.getInt(c.getColumnIndex(QuizContract.QuestionsTable._ID)));
                question.setQuestion(c.getString(c.getColumnIndex(QuizContract.QuestionsTable.COLUMN_QUESTION)));
                question.setOption1(c.getString(c.getColumnIndex(QuizContract.QuestionsTable.COLUMN_OPTION1)));
                question.setOption2(c.getString(c.getColumnIndex(QuizContract.QuestionsTable.COLUMN_OPTION2)));
                question.setOption3(c.getString(c.getColumnIndex(QuizContract.QuestionsTable.COLUMN_OPTION3)));
                question.setAnswerNr(c.getInt(c.getColumnIndex(QuizContract.QuestionsTable.COLUMN_ANSWER_NR)));
                question.setDifficulty(c.getString(c.getColumnIndex(QuizContract.QuestionsTable.COLUMN_DIFFICULTY)));
                question.setCategoryID(c.getInt(c.getColumnIndex(QuizContract.QuestionsTable.COLUMN_CATEGORY_ID)));
                questionList.add(question);
            } while (c.moveToNext());
        }
        c.close();
        return questionList;
    }
}
