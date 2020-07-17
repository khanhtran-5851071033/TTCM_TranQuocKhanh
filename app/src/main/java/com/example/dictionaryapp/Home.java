package com.example.dictionaryapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.muddzdev.styleabletoastlibrary.StyleableToast;

import maes.tech.intentanim.CustomIntent;

public class Home extends AppCompatActivity {

    Animation animation_rigth,animation_left;
    LinearLayout l1,l2,l3,l4;
    TextView txt_intro,txt_intro1;
    Database database = new Database(this, "Dictionary_db.sqlite", null, 1);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getSupportActionBar().hide();
        animation_left= AnimationUtils.loadAnimation(this,R.anim.line_animation);
        animation_rigth= AnimationUtils.loadAnimation(this,R.anim.home_rigth_animation);
        addView();
        addAmimation();
        PrepareDB();
    }

    private void addAmimation() {
        l1.setAnimation(animation_left);
        l2.setAnimation(animation_rigth);
        l3.setAnimation(animation_left);
        l4.setAnimation(animation_rigth);
        txt_intro.setAnimation(animation_left);
        txt_intro1.setAnimation(animation_rigth);
    }

    private void addView() {
        l1=findViewById(R.id.l1);
        l2=findViewById(R.id.l2);
        l3=findViewById(R.id.l3);
        l4=findViewById(R.id.l4);
        txt_intro=findViewById(R.id.txt_intro);
        txt_intro1=findViewById(R.id.txt_intro1);
    }
    private void PrepareDB() {

       // database.QueryData("DROP TABLE wordss");
        database.QueryData("CREATE TABLE IF NOT EXISTS Wordss(ID Integer PRIMARY KEY AUTOINCREMENT, WordName VARCHAR(50),WordSpell VARCHAR(50)" +
                ",WordType VARCHAR(50),WordMean VARCHAR(100),WordSynonym VARCHAR(100),WordExample VARCHAR(200),favourite INT,history INT)");
        //database.QueryData("DELETE FROM wordss");
       // database.QueryData("DELETE FROM wordss");
        Cursor cursor=database.GetData("SELECT * FROM Wordss ");
        int count=cursor.getCount();
        if(count==0)
        {
            database.QueryData("INSERT INTO Wordss VALUES(null,'Hello','[helou]','Động từ','Xin chào','Hi','Hello Khánh',0,0)");
            database.QueryData("INSERT INTO Wordss VALUES(null,'Go','/gəʊ/','Động từ','Đi','go','go to market',0,0)");
            database.QueryData("INSERT INTO Wordss VALUES(null,'Project','/ˈprədʒekt/','Danh từ','Dự án, đề án','scheme, plan','a project to establish a new national park',0,0)");
            database.QueryData("INSERT INTO Wordss VALUES(null,'Code','/gəʊ/','/kəʊd/','mã; mật mã; mã số','...','crack a code',0,0)");
            database.QueryData("INSERT INTO Wordss VALUES(null,'Air','/eə[r]/ ','Danh từ','không khí, bầu không khí, không trung','air base, air beacon, air brush,','letˈs go out for some fresh air',0,0)");
            database.QueryData("INSERT INTO Wordss VALUES(null,'Open','/ˈəʊpən/','Động từ','mở, ngỏ','go','with both eyes open',0,0)");
            database.QueryData("INSERT INTO Wordss VALUES(null,'Because','/biˈkɒz/','Liên từ','vì, bởi vì','go','I did it because he told me',0,0)");
            database.QueryData("INSERT INTO Wordss VALUES(null,'All','/ɔ:l/','Định từ','Tất cả','altogether, everything','all my plants have died',0,0)");
            database.QueryData("INSERT INTO Wordss VALUES(null,'About','/əˈbaʊt/ ','Phó từ','khoảng [chừng], gần','about-face, about-sledge, about-turn','it costs about ₤10',0,0)");
            database.QueryData("INSERT INTO Wordss VALUES(null,'Help','/help/','Động từ','giúp đỡ, cứu giúp, giúp','support','may I help with a washing-up?',0,0)");
            database.QueryData("INSERT INTO Wordss VALUES(null,'Head','/hed/','Danh từ','Đầu','...','the ball hit her on the head',0,0)");
            database.QueryData("INSERT INTO Wordss VALUES(null,'Never','/ˈnevə[r]/','Phó từ','không bao giờ','not ever, not once','she never goes to the cinema',0,0)");
            database.QueryData("INSERT INTO Wordss VALUES(null,'Next','/nekst/','Tính từ','sau, tới, tiếp','next of kin, next-best','the next name on the list',0,0)");
            database.QueryData("INSERT INTO Wordss VALUES(null,'Like','/laik/','Động từ','Thích, ưa','likeliness, likely, liken','she likes him but doesnˈt love him',0,0)");
            database.QueryData("INSERT INTO Wordss VALUES(null,'Know','/nəʊ/','Động từ','Biết','understand','she doesnˈt know your address',0,0)");
            database.QueryData("INSERT INTO Wordss VALUES(null,'House','/haʊs/','Danh từ','nhà; người ở trong nhà','home, abode','be quiet or you will wake the whole house',0,0)");
            database.QueryData("INSERT INTO Wordss VALUES(null,'Document','/ˈdɒkjʊmənt/','Danh từ','Văn kiện, tư liệu','text, file','can you document these claims?',0,0)");
            database.QueryData("INSERT INTO Wordss VALUES(null,'Support','/səˈpɔ:t/','Động từ','nâng đỡ, ủng hộ','help, prop, foundation','he was weak with hunger, so I had to support him',0,0)");
            database.QueryData("INSERT INTO Wordss VALUES(null,'Food','/fu:d/','Danh từ','thức ăn; thực phẩm','food chain, food poisoning','we cannot survive for long without food and drink',0,0)");
            database.QueryData("INSERT INTO Wordss VALUES(null,'Study','/ˈstʌdi/','Danh từ','Sự học hỏi, sự nghiên cứu','learning','give all oneˈs spare time to study',0,0)");
            database.QueryData("INSERT INTO Wordss VALUES(null,'Good','/gud/','Tính từ','Tốt, hay, giỏi','better; best','the car has very good brakes',0,0)");
            database.QueryData("INSERT INTO Wordss VALUES(null,'Practicable','/ˈpræktikəbl/','Tính từ','Làm được, khả thi','feasible,realistic','the mountain route that is practicable only in summer',0,0)");
            database.QueryData("INSERT INTO Wordss VALUES(null,'Design','/dizain/','Danh từ','Bản thiết kế, kế hoạch','Plan, scheme','The design of machine',0,0)");
            database.QueryData("INSERT INTO Wordss VALUES(null,'Information',' /ˌɪn.fəˈmeɪ.ʃən/','Danh từ','Thông tin','Info,Data',' I would like some information about your flights to the USA',0,0)");
            database.QueryData("INSERT INTO Wordss VALUES(null,'Technology','/tekˈnɒl.ə.dʒi/','Danh từ','Công nghệ, khoa học','knowledge, machinery','The technology of computers',0,0)");
            database.QueryData("INSERT INTO Wordss VALUES(null,'Condition','/kənˈdiʃn/','Danh từ','Điều kiện','State,form,order,..','A man of condition',0,0)");
            database.QueryData("INSERT INTO Wordss VALUES(null,'Positive','/ˈpɔzətiv/','Tính từ','Xác thực, rõ ràng','Sure, certain','A positive proof',0,0)");
            database.QueryData("INSERT INTO Wordss VALUES(null,'Negative','/ˈnegətiv/','Tính từ','Phủ định, phủ nhận, ','unenthusiastic, pessimistic','To give a negative answers',0,0)");
            database.QueryData("INSERT INTO Wordss VALUES(null,'Technical','/ˈteknikəl/','Tính từ','Kỹ thuật',' technological, technical foul','technical school, technical terms',0,0)");
            database.QueryData("INSERT INTO Wordss VALUES(null,'Computer','/kəmˈpju:tə/','Danh từ','Máy tính',' computing device','electronic computer',0,0)");
            database.QueryData("INSERT INTO Wordss VALUES(null,'Reservation','/rezəˈveiʃn/','Danh từ','Sự hạn chế, điều kiện hạn chế','mental reservation,  arriere pensee','mental reservation',0,0)");
            database.QueryData("INSERT INTO Wordss VALUES(null,'Book','/buk/','Danh từ','Sách','Bible','old book, to writer a book',0,0)");
            database.QueryData("INSERT INTO Wordss VALUES(null,'Install','/inˈstɔ:l/','Ngoại động từ','Cài đặt','set up, put in','installing sofwares',0,0)");
            database.QueryData("INSERT INTO Wordss VALUES(null,'Instruction','/insˈtrʌkʃn/','Danh từ','kiến thức truyền cho, tài liệu cung cấp cho','education, statement','course of instruction',0,0)");
            database.QueryData("INSERT INTO Wordss VALUES(null,'Network','/ˈnetwə:k/','Danh từ','mạng lưới, hệ thống','electronic network','a network of railways',0,0)");
            database.QueryData("INSERT INTO Wordss VALUES(null,'Database','/ˈdeitəbeis/','Danh từ','Cơ sở dữ liệu','database service,','database manager, database processor,',0,0)");
            database.QueryData("INSERT INTO Wordss VALUES(null,'Developer','/diˈveləpə[r]/','Danh từ','Nhà phát triển','Creator, maker','Sofware deverloper',0,0)");
        }

    }
    public void findWord(View view) {
        Intent intent=new Intent(Home.this, findword.class);
        Home.this.startActivity(intent);
        CustomIntent.customType(this,"up-to-bottom");
    }

    public void test(View view) {
        Intent intent=new Intent(Home.this, test.class);
        Home.this.startActivity(intent);
        CustomIntent.customType(this,"up-to-bottom");
    }

    public void favourite(View view) {
        Intent intent=new Intent(Home.this, findword.class);
        Bundle bundle =new Bundle();
        bundle.putInt("favourite",1);
        intent.putExtra("my",bundle);
        Home.this.startActivity(intent);
        CustomIntent.customType(this,"up-to-bottom");
    }

    public void remindWord(View view) {
        Intent intent=new Intent(Home.this, RemindWord.class);
        Home.this.startActivity(intent);
        CustomIntent.customType(this,"up-to-bottom");
    }
    public void history(View view) {
        Intent intent=new Intent(Home.this, findword.class);
        Bundle bundle =new Bundle();
        bundle.putInt("history",1);
        intent.putExtra("my",bundle);
        Home.this.startActivity(intent);
        CustomIntent.customType(this,"up-to-bottom");
    }

    public void toSetting(View view) {
        Intent intent=new Intent(Home.this, Setting.class);
        Home.this.startActivity(intent);
        CustomIntent.customType(this,"left-to-right");
    }

    public void toTheme(View view) {
        Intent intent=new Intent(Home.this, Theme.class);
        Home.this.startActivity(intent);
        CustomIntent.customType(this,"left-to-right");
    }

    public void toTranslate(View view) {
        Intent intent=new Intent(Home.this, Translate.class);
        Home.this.startActivity(intent);
        CustomIntent.customType(this,"up-to-bottom");
    }
}
