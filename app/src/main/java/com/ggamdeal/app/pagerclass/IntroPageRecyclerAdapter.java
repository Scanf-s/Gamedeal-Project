package com.ggamdeal.app.pagerclass;

import android.content.Context;
import android.graphics.pdf.PdfDocument;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ggamdeal.app.R;

import java.util.ArrayList;
import java.util.List;


public class IntroPageRecyclerAdapter extends RecyclerView.Adapter {

    String TAG = "RecyclerViewAdapter";
    //RecyclerView에 넣을 데이터 리스트 -> WelcomeText
    List<PageItem> pageItemList;
    Context context;

    public IntroPageRecyclerAdapter(Context context, ArrayList<PageItem> pageItemList) {
        this.pageItemList = pageItemList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder");

        //자신이 만든 itemview를 inflate한 다음 뷰홀더 생성
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.welcomepage,parent,false);
        PagerViewHolder viewHolder = new PagerViewHolder(view);

        //생선된 뷰홀더를 리턴하여 onBindViewHolder에 전달한다.
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Log.d(TAG,"onBindViewHolder");

        RecyclerView.ViewHolder myViewHolder = (PagerViewHolder) holder;
    }

    @Override
    public int getItemCount() {
        //DataList의 크기 return
        return pageItemList.size();
    }
}
