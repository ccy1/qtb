package com.example.ntb.ui.membermanagement.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.ntb.ui.R;
import com.example.ntb.ui.membermanagement.acticity.AddMemberActivity;
import com.example.ntb.ui.membermanagement.bean.JsonTest;

import java.util.List;

/**
 * Created by ccy.
 * Date: 2021/11/24
 * Describe :
 */
public class AddMemberAdapter extends BaseAdapter {

    private List<JsonTest.DataBean>_list;
    private Context context;
    private LayoutInflater layoutInflater;

    private OnIitem onIitem;

    public AddMemberAdapter(AddMemberActivity context, List<JsonTest.DataBean> list, OnIitem onIitem) {
        this._list = list;
        this.context = context;
        this.onIitem = onIitem;
        if (context != null){
            layoutInflater = LayoutInflater.from(context);
        }
    }

    @Override
    public int getCount() {
        return _list.size();
    }

    @Override
    public Object getItem(int i) {
        return _list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        final ViewHolder viewHolder;
        if (view == null){
            view = layoutInflater.inflate(R.layout.item_menber,viewGroup,false);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        }else {
            viewHolder =(ViewHolder) view.getTag();
        }
        viewHolder.iv_add_member.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onIitem.Onitem("添加",i,viewHolder.et_province.getText().toString());
            }
        });

        viewHolder.iv_delete_member.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String content = viewHolder.et_province.getText().toString()+viewHolder.et_letter.getText().toString()+viewHolder.et_number.getText().toString();
                _list.get(i).setProvince(content);
                onIitem.Onitem("删除",i,content);
            }
        });
        return view;
    }
    public interface  OnIitem{
        void Onitem (String type,int position,String content);
    }

    public class ViewHolder{
        private ImageView iv_add_member,iv_delete_member;
        private EditText et_province,et_letter,et_number;
        public ViewHolder(View view){
            iv_add_member =(ImageView) view.findViewById(R.id.iv_add_member);
            iv_delete_member = view.findViewById(R.id.iv_delete_member);
            et_province = view.findViewById(R.id.et_province);
            et_letter = view.findViewById(R.id.et_letter);
            et_number = view.findViewById(R.id.et_number);
        }
    }
}
