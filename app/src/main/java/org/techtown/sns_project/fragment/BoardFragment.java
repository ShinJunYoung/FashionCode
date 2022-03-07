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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.techtown.sns_project.Board.BoardAdapter;
import org.techtown.sns_project.R;
import org.techtown.sns_project.UploadActivity;

public class BoardFragment extends Fragment {
 private View view;

 private String TAG = "프래그먼트";
 RecyclerView recyclerView_BoardItem;
 static BoardAdapter adapter;
 @Nullable
 @Override
 public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
  view = inflater.inflate(R.layout.board_fragment, container, false);
  Button upload_btn = (Button)view.findViewById(R.id.upload);
  recyclerView_BoardItem = view.findViewById(R.id.recyclerView_BoardItem);

  LinearLayoutManager BoardItem = new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
  recyclerView_BoardItem.setLayoutManager(BoardItem); //Linear로 recyclerview layout 만들기
  adapter = new BoardAdapter();
  recyclerView_BoardItem.setAdapter(adapter);



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
