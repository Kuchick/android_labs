package com.seryozhka.getnews.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

import com.seryozhka.getnews.MainActivity;
import com.seryozhka.getnews.R;
import com.seryozhka.getnews.database.DBHelper;
import com.seryozhka.getnews.models.Category;

import java.util.List;

/**
 * Created by seryozhka on 23.11.16.
 */
public class CategoryListAdapter extends BaseAdapter {

    private final List<Category> mysuperlist;
    //private List<Category> mysuperlist;
    private Context context;
    private final LayoutInflater lInflater;
    private boolean showCheckBoxes = false;
    public CategoryListAdapter(List<Category> list, Context ctx) {
        mysuperlist = list;
        context = ctx;
        lInflater = (LayoutInflater) ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mysuperlist.size();
    }

    @Override
    public Object getItem(int position) {
        return mysuperlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            view = lInflater.inflate(R.layout.item_category, null);
        }
        TextView categoryTextView = (TextView) view.findViewById(R.id.textViewCategory);
        categoryTextView.setText(mysuperlist.get(position).getName());
        categoryTextView.setOnClickListener(textViewListener);
        categoryTextView.setTag(position);

        CheckBox categoryCheckBox = (CheckBox) view.findViewById(R.id.checkBoxCategory);
        categoryCheckBox.setOnCheckedChangeListener(myCheckChangeList);
        categoryCheckBox.setTag(position);
        categoryCheckBox.setChecked(mysuperlist.get(position).isInteresting());
        //Log.d("SERZH", "textView"+categoryTextView.getText()+"\nisInteresting"+ categoryCheckBox.isChecked());
        if (showCheckBoxes){
            categoryCheckBox.setVisibility(View.VISIBLE);
        } else {
            categoryCheckBox.setVisibility(View.GONE);
        }
        return view;
    }

    public void setCheckboxVisibility(boolean bool){
        showCheckBoxes = bool;
    }

    public void applyChanges(){
        //Log.d("SERZH", Integer.toString(mysuperlist.size()));
        for (int i = 0; i < mysuperlist.size(); i++){
            //Log.d("SERZH1", "NAME= "+mysuperlist.get(i).getName()+"\n VALUE= "+mysuperlist.get(i).isInteresting());
            if (!mysuperlist.get(i).isInteresting()){
                mysuperlist.remove(i);
                new DBHelper(context).removeCategoryByName(mysuperlist.get(i).getName());
                i--;
            }
        }
        //Log.d("SERZH", Integer.toString(mysuperlist.size()));
    }

    OnCheckedChangeListener myCheckChangeList = new OnCheckedChangeListener() {
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            mysuperlist.get((Integer) buttonView.getTag()).setInteresting(isChecked);
        }
    };

    View.OnClickListener textViewListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (showCheckBoxes){return;}
            String categoryName = mysuperlist.get((Integer) v.getTag()).getName();
            //Log.d("SERZH", "categoryName= "+categoryName);
            ((MainActivity)context).goToArticleListActivity(categoryName);
        }
    };
}