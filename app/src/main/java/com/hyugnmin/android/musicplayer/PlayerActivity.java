package com.hyugnmin.android.musicplayer;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import java.util.ArrayList;

public class PlayerActivity extends AppCompatActivity {

    ImageButton btnFf, btnPlay,btnRw;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);



        btnFf = (ImageButton)findViewById(R.id.btnFf);
        btnPlay = (ImageButton) findViewById(R.id.btnPlay);
        btnRw = (ImageButton) findViewById(R.id.btnRw);

        btnFf.setOnClickListener(clickListener);
        btnPlay.setOnClickListener(clickListener);
        btnRw.setOnClickListener(clickListener);

        //1.데이터 가져오기
        ArrayList<Music> datas = DataLoader.get(this);
        //2. 뷰페이저 가져오기
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        //3.뷰페이저용 아답터 생성
        PlayerAdapter adapter = new PlayerAdapter(datas, this);
        //4. 뷰페이저 아답터 연결
        viewPager.setAdapter(adapter);
        //5. 특정 페이지 호출
        Intent intent = getIntent();
        if(intent != null) {
            Bundle bundle = intent.getExtras();
            int position = bundle.getInt("position");

            //페이지이동
            viewPager.setCurrentItem(position);
        }
    }

    View.OnClickListener clickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            switch(v.getId()) {
                case R.id.btnPlay :
                    play();
                    break;
                case R.id.btnRw :
                    prev();
                    break;
                case R.id.btnFf :
                    next();
                    break;
            }
        }
    };

    private void play() {

    }

    private void prev() {

    }

    private void next() {

    }
}
