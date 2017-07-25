package com.xixi.myapp.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xixi.myapp.R;

/**
 * Created by Administrator on 2016/4/9.
 */
public class RecyclerMainAdapter extends RecyclerView.Adapter<RecyclerMainAdapter.MaterialViewHolder>{

    private Context mContext;
    private String mTitles[];
    public RecyclerMainAdapter(Context context ,String mTitles[]){
        mContext = context;
        this.mTitles = mTitles;
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
    public MaterialViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_recycler_main, null);
        return new MaterialViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MaterialViewHolder holder, int position) {
        holder.name.setText(mTitles[position]);
    }

    @Override
    public int getItemCount() {
        return mTitles.length;
    }

    public class MaterialViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener{
        public TextView name;

        public MaterialViewHolder(View itemView){
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
