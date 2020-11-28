package com.example.forsolo.chat;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.forsolo.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;

public class ChattingActivity extends AppCompatActivity {
    EditText et;
    ListView listView;

    ArrayList<MessageItem> messageItems=new ArrayList<>();
    ChatAdapter adapter;

    //Firebase Database 관리 객체참조변수
    FirebaseDatabase firebaseDatabase;

    //'chat'노드의 참조객체 참조변수
    DatabaseReference chatRef;
    private String roomCode;
    private String roomTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatting);

        // TODO: 3.대화방 상대의 정보를 받아옴
        roomCode = getIntent().getStringExtra("roomCode");
        roomTitle = getIntent().getStringExtra("roomTitle");
        Log.d("TEST", "onCreate: roomcode: "+roomCode + "/"+roomTitle);

        //제목줄 제목글시를 닉네임으로(또는 채팅방)
        // TODO: 4. 대화방 이름 수정
//        getSupportActionBar().setTitle(G.nickName);
        getSupportActionBar().setTitle("\uD83D\uDCAC" + roomTitle);

        et=findViewById(R.id.et);
        listView=findViewById(R.id.listview);
        adapter=new ChatAdapter(messageItems,getLayoutInflater());
        listView.setAdapter(adapter);

        //Firebase DB관리 객체와 'caht'노드 참조객체 얻어오기
        firebaseDatabase= FirebaseDatabase.getInstance();
        chatRef= firebaseDatabase.getReference("chat");


        //firebaseDB에서 채팅 메세지들 실시간 읽어오기..
        //'chat'노드에 저장되어 있는 데이터들을 읽어오기
        //chatRef에 데이터가 변경되는 것으 듣는 리스너 추가
        chatRef.addChildEventListener(new ChildEventListener() {
            //새로 추가된 것만 줌 ValueListener는 하나의 값만 바뀌어도 처음부터 다시 값을 줌
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                //새로 추가된 데이터(값 : MessageItem객체) 가져오기
                MessageItem messageItem= dataSnapshot.getValue(MessageItem.class);

                // TODO: 6. 이 채팅방에 해당하는 메세지만 가져온다.
                if(messageItem.roomCode !=null && messageItem.roomCode.equals(roomCode)) {
                    //새로운 메세지를 리스뷰에 추가하기 위해 ArrayList에 추가
                    messageItems.add(messageItem);

                    //리스트뷰를 갱신
                    adapter.notifyDataSetChanged();
                    listView.setSelection(messageItems.size()-1); //리스트뷰의 마지막 위치로 스크롤 위치 이동
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void clickSend(View view) {

        //firebase DB에 저장할 값들( 닉네임, 메세지, 프로필 이미지URL, 시간)
        String nickName= G.nickName;
        String message= et.getText().toString();
        String pofileUrl= G.porfileUrl;

        //메세지 작성 시간 문자열로..
        Calendar calendar= Calendar.getInstance(); //현재 시간을 가지고 있는 객체
        String time=calendar.get(Calendar.HOUR_OF_DAY)+":"+calendar.get(Calendar.MINUTE); //14:16

        //firebase DB에 저장할 값(MessageItem객체) 설정
        MessageItem messageItem= new MessageItem(nickName,message,time,pofileUrl, roomCode);
        //'char'노드에 MessageItem객체를 통해
        chatRef.push().setValue(messageItem);

        //EditText에 있는 글씨 지우기
        et.setText("");

    }
}
