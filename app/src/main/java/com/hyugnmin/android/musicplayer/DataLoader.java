package com.hyugnmin.android.musicplayer;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.MediaStore;

import java.util.ArrayList;

/**
 * Created by besto on 2017-02-01.
 */

public class DataLoader {

    private ArrayList<Music> datas = new ArrayList<>();
    private Context context;

    public DataLoader(Context context) {

        this.context = context;
    }
    public ArrayList<Music> get()
    {
        return datas;
    }

    public void load() {
        //1. 데이터에 접근하기 위해 ContentResolver를 불러오고
        ContentResolver resolver = context.getContentResolver();

        //2. 데이터 컨텐츠 uri 정의
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;

        //3. 데이터에서 가져올 데이터 컬럼명을 string 배열에 담는다.
        //데이터 컬럼명은 Content uri 패키지에 들어있다.
        String proj[] = {
                MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.ALBUM_ID,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.ARTIST
        };
        //4. ContentResolver로 쿼리한 데이터를 커서에 담는다.
        Cursor cursor = resolver.query(uri, proj, null, null, null);

        //5. Cursor에 담긴 데이터를 반복문을 돌면서 꺼낸다
        if(cursor != null) {
            while(cursor.moveToNext()) {
                Music music = new Music ();

                int idx = cursor.getColumnIndex(proj[0]);
                music.id = cursor.getString(idx);
                idx = cursor.getColumnIndex(proj[1]);
                music.albumId = cursor.getString(idx);
                idx = cursor.getColumnIndex(proj[2]);
                music.title = cursor.getString(idx);
                idx = cursor.getColumnIndex(proj[3]);
                music.artist = cursor.getString(idx);

                datas.add(music);
            }
        cursor.close(); //6. 처리 후 커서를 닫아준다.
        }
    }
}
