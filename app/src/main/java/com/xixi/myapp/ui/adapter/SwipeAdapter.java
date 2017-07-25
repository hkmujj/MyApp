package com.xixi.myapp.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xixi.myapp.R;

import java.util.List;

/**
 * Created by xixi on 2016/4/9.
 */
public class SwipeAdapter extends RecyclerView.Adapter<SwipeAdapter.MyViewHolder>{

    private Context mContext;
    private List<String> datalist;
    public SwipeAdapter(Context context , List<String> datalist){
        mContext = context;
        this.datalist = datalist;
    }

    public interface OnItemClickLitener
    {
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
    }

    private OnItemClickLitener mOnItemClickLitener;

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener)
    {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_recycler_main, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.name.setText(datalist.get(position));
    }

    @Override
    public int getItemCount() {
        return null == datalist ? 0 : datalist.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener{
        public TextView name;

        public MyViewHolder(View itemView){
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (null != mOnItemClickLitener) {
                mOnItemClickLitener.onItemClick(v,getLayoutPosition());
                }
        }

        @Override
        public boolean onLongClick(View v) {
            if(null != mOnItemClickLitener){
                 mOnItemClickLitener.onItemLongClick(v,getLayoutPosition());
            }
            return true;
        }
    }
}
