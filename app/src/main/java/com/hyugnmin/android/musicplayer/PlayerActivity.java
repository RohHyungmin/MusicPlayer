package com.hyugnmin.android.musicplayer;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;

public class PlayerActivity extends AppCompatActivity {

    ImageButton btnFf, btnPlay,btnRw;
    ViewPager viewPager;
    ArrayList<Music> datas;
    PlayerAdapter adapter;
    MediaPlayer player;
    SeekBar seekBar;
    TextView textSec, textCurrent;

    int position = 0;

    private static final int PLAY = 0;
    private static final int PAUSE = 1;
    private static final int STOP = 2;
    private static int playStatus = STOP; //플레이어상태

    // 핸들러 상태 플래그
    public static final int PROGRESS_SET = 101;

    //핸들러
    Handler handler = new Handler () {
        @Override
        public void handleMessage(Message msg) {
            //super.handleMessage(msg);
            switch (msg.what) {
                case PROGRESS_SET:
                    if(player != null) {
                        seekBar.setProgress(player.getCurrentPosition());
                        textCurrent.setText(player.getCurrentPosition()/1000 + "");
                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        playStatus = STOP;

        setVolumeControlStream(AudioManager.STREAM_MUSIC); //볼륨조절버튼으로 미디어 음량 조절

        seekBar = (SeekBar)findViewById(R.id.seekBar);
        textSec = (TextView) findViewById(R.id.textSec);
        textCurrent = (TextView) findViewById(R.id.textCurrent);

        btnFf = (ImageButton)findViewById(R.id.btnFf);
        btnPlay = (ImageButton) findViewById(R.id.btnPlay);
        btnRw = (ImageButton) findViewById(R.id.btnRw);

        btnFf.setOnClickListener(clickListener);
        btnPlay.setOnClickListener(clickListener);
        btnRw.setOnClickListener(clickListener);

        //1.데이터 가져오기
         datas = DataLoader.get(this);
        //2. 뷰페이저 가져오기
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        //3.뷰페이저용 아답터 생성
        adapter = new PlayerAdapter(datas, this);
        //4. 뷰페이저 아답터 연결
        viewPager.setAdapter(adapter);
        //5. 특정 페이지 호출
        Intent intent = getIntent();
        if(intent != null) {
            Bundle bundle = intent.getExtras();
            position = bundle.getInt("position");
            //페이지이동
            viewPager.setCurrentItem(position);
        }
    }

    View.OnClickListener clickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            switch(v.getId()) {
                case R.id.btnFf:
                    next();
                    break;
                case R.id.btnRw :
                    prev();
                    break;
                case R.id.btnPlay:
                    play();
                    break;
            }
        }
    };

    private void play() {

        switch (playStatus) {
            case STOP: //플레이어에 음원 세팅
                Uri musicUri = datas.get(position).uri;
                player = MediaPlayer.create(this, musicUri);
                player.setLooping(false); //반복여부

                //seekbar  길이
                seekBar.setMax(player.getDuration());
                textSec.setText(player.getDuration()/1000 + "Sec.");
                player.start();
                playStatus = PLAY;
                btnPlay.setImageResource(android.R.drawable.ic_media_pause);

               //sub thread를 생성해서 mediaplayer의 현재 포지션 값으로 seekbar를 변경해준다 매 1초마다
                new Thread() {
                    @Override
                    public void run() {
                        //super.run();
                        while(playStatus < STOP) {
                            handler.sendEmptyMessage(PROGRESS_SET);
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }.start();

                break;
            case PLAY : //플레이중이면 멈춤
                player.pause();
                playStatus = PAUSE;
                btnPlay.setImageResource(android.R.drawable.ic_media_play);
                break;
            case PAUSE: //멈춤 상태면 거기서부터 재생
                player.seekTo(player.getCurrentPosition());
                player.start();
                playStatus = PLAY;
                btnPlay.setImageResource(android.R.drawable.ic_media_pause);
                break;
        }
    }

    private void prev() {

    }

    private void next() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(player != null) {
            player.release(); //사용이 끝나면 해제해야만 한다.
        }
        playStatus  = STOP;
    }

}