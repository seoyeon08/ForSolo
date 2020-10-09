package com.example.forsolo;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ManagementData {
    private static ManagementData mData = new ManagementData();
    private UserData userData;                  // 유저 정보

    private ManagementData() {
        userData = null;
    }

    public static ManagementData getInstance() {
        return mData;
    }

    // 모든 데이터 초기화
    public void delAllData() {
        userData = null;
    }

    // UserData 설정
    public void setUserData(UserData userData) {
        this.userData = userData;
    }

//    // UserData 반환
//    public UserData getUserData() {
//        return userData;
//    }

    // 디비에 유저 등록
    public static void registerUser(final FirebaseUser user) {
        ManagementData mData;   // 싱글톤 객체(앱상에서 전반적인 데이터 관리)

        // 싱글톤 객체에 유저 정보 등록
        mData = ManagementData.getInstance();
        mData.setUserData(new UserData(user.getUid(), user.getDisplayName(), user.getEmail(), null));

        // 이미 DB에 존재하는 유저면 화면만 넘기기
        FirebaseDatabase.getInstance().getReference().child(DB_Data.DB_CHILD_USER).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserData userData;

                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    // Uid를 비교해서 같으면 디비에 등록X
                    if (user.getUid().equals(data.getValue(UserData.class).getUser_Id())) {
                        return;
                    }
                }

                // 유저 정보 생성
                userData = new UserData(user.getUid(), user.getDisplayName(), user.getEmail(), null);

                // DB에 유저 정보 등록
                insertUserToDatabase(userData);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    // 유저 정보를 디비에 등록
    private static void insertUserToDatabase(UserData userData) {
        DatabaseReference userRef;

        userRef = FirebaseDatabase.getInstance().getReference().child(DB_Data.DB_CHILD_USER).child(userData.getUser_Id());

        // 디비에 유저 생성
        userRef.setValue(userData);
    }
}
