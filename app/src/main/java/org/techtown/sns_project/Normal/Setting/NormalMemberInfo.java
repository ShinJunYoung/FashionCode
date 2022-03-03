package org.techtown.sns_project.Normal.Setting;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import org.techtown.sns_project.MemberInfoClass;
import org.techtown.sns_project.Normal.NormalMemberInfoActivity;
import org.techtown.sns_project.R;

import java.util.HashMap;
import java.util.Map;

public class NormalMemberInfo extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseFirestore db;
    TextView name, address, phone, date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.member_setting_activity);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        db = FirebaseFirestore.getInstance();
        findViewById(R.id.MemberInfoChangeButton).setOnClickListener(onClickListener);
        DocumentReference docRef = db.collection("users").document(firebaseUser.getUid());
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                MemberInfoClass memberInfo = documentSnapshot.toObject(MemberInfoClass.class);
                if(memberInfo != null) {
                    name = (TextView) findViewById(R.id.NameText);
                    address = (TextView) findViewById(R.id.AddressText);
                    phone = (TextView) findViewById(R.id.PhoneText);
                    date = (TextView) findViewById(R.id.DateText);

                    name.setText(memberInfo.getName());
                    address.setText(memberInfo.getAddress());
                    phone.setText(memberInfo.getPhone());
                    date.setText(memberInfo.getDate());
                }
                else
                    Log.d("temp", "onSuccess: null");
            }
        });
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            db.collection("users").document(firebaseUser.getUid()).delete()
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            StartActivity(NormalMemberInfoActivity.class);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    // 실패 로직 처리하기
                    Log.d("memberInfo", "onFailure: fail");
                }
            });
        }
    };

    private void StartActivity(Class c) {
        Intent intent = new Intent(this, c);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
