package org.techtown.sns_project.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import org.techtown.sns_project.Board.BoardAdapter;
import org.techtown.sns_project.Board.BoardPostClickEvent;
import org.techtown.sns_project.Model.PostInfo;
import org.techtown.sns_project.R;
import org.techtown.sns_project.Board.Upload.UploadActivity;
import org.techtown.sns_project.qr.ProductInfo;

import java.util.ArrayList;
import java.util.HashMap;

public class BoardFragment extends Fragment {

 FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
 FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
 FirebaseFirestore db = FirebaseFirestore.getInstance();

 private View view;
 private String TAG = "프래그먼트";
 RecyclerView recyclerView_BoardItem;
 static BoardAdapter adapter;
 static String url;

// HashMap<String,Object> List = new HashMap<String,Object>();
 DataFormat df;

 public static ArrayList<String> listImgUrl = new ArrayList<>();
 static ArrayList<String> listDescription = new ArrayList<>();
 static ArrayList<String> listPublisher = new ArrayList<>();
 static ArrayList<ArrayList<ProductInfo>> listOfList = new ArrayList<>();
 @Nullable
 @Override
 public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
  Log.i(TAG, "onCreateView");
  view = inflater.inflate(R.layout.board_fragment, container, false);
  Button upload_btn = (Button)view.findViewById(R.id.upload);
  recyclerView_BoardItem = view.findViewById(R.id.recyclerView_BoardItem);


  GridLayoutManager BoardItem = new GridLayoutManager(getContext(),2);
  recyclerView_BoardItem.setLayoutManager(BoardItem);

/*  LinearLayoutManager BoardItem = new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
  recyclerView_BoardItem.setLayoutManager(BoardItem); //Linear로 recyclerview layout 만들기*/
  adapter = new BoardAdapter();
  recyclerView_BoardItem.setAdapter(adapter);

  adapter.setOnItemClickListener (new BoardAdapter.OnItemClickListener () {

   //아이템 클릭시 토스트메시지
   @Override
   public void onItemClick(View v, int position) {

    StartActivity(BoardPostClickEvent.class,position);
   }

   private void StartActivity(Class<BoardPostClickEvent> boardPostClickEventClass, int position) {
    Intent intent = new Intent(getContext(),boardPostClickEventClass);
    intent.putExtra("position",position);
    intent.putExtra("listImgUrl",listImgUrl);
    intent.putExtra("listPublisher",listPublisher);
    intent.putExtra("listDescription",listDescription);
    intent.putExtra("listOfList", listOfList);

    System.out.println("Start activity :" + listImgUrl);

    startActivity(intent);
   }

  });

  db.collectionGroup("board_Data").get().
          addOnCompleteListener(task -> {
           if(task.isSuccessful()) {
        /*   listImgUrl.clear();
           listDescription.clear();
           listPublisher.clear();
           adapter.listData.clear();*/

            for(QueryDocumentSnapshot document : task.getResult()) {

//             List = (HashMap<String, Object>) document.getData();
               df = document.toObject(DataFormat.class);
               listImgUrl.add(df.getImageUrl());
               listPublisher.add(df.getPublisher());
               listDescription.add(df.getDescription());
               listOfList.add(df.getList());
               PostInfo data = new PostInfo(df.getPublisher(),df.getImageUrl(),df.getDescription());
               adapter.addItem(data);
//             listImgUrl.add((String)list.get
//             listPublisher.add((String)List.get("publisher"));
//             listDescription.add((String)List.get("description"));
//             listOfList.add((ArrayList<ProductInfo>) List.get("clothes_info"));
//             PostInfo data = new PostInfo((String)List.get("publisher"),(String)List.get("ImageUrl"),(String)List.get("description"));
//             adapter.addItem(data);
//             System.out.println(listImgUrl);
            }

            adapter.notifyDataSetChanged();
           }


          });



  upload_btn.setOnClickListener(new View.OnClickListener() {
   @Override
   public void onClick(View view) {
    Intent intent = new Intent(getContext(), UploadActivity.class);
    startActivity(intent);
   }
  });

  return view;
 }

}



