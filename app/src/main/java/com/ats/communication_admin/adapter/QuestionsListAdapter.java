package com.ats.communication_admin.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.ats.communication_admin.R;
import com.ats.communication_admin.bean.AfeQuestion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.ats.communication_admin.activity.HomeActivity.mapRemark;

public class QuestionsListAdapter extends RecyclerView.Adapter<QuestionsListAdapter.MyViewHolder> {

    private ArrayList<AfeQuestion> questionsList;
    private Context context;
    Boolean isTouched = false, isTouchedRemark = false;
    String[] arrRemark;

    public static Map<Integer, Boolean> map = new HashMap<Integer, Boolean>();


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public CheckBox cbCheck;
        public EditText edRemark;

        public MyViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.tvCustomQuestion_Name);
            edRemark = view.findViewById(R.id.edCustomQuestion_Remark);
            cbCheck = view.findViewById(R.id.cbCustomQuestion_Check);
        }
    }

    public QuestionsListAdapter(ArrayList<AfeQuestion> questionsList, Context context) {
        this.questionsList = questionsList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_question_layout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final AfeQuestion question = questionsList.get(position);

        Boolean value = map.get(question.getQueId());
        Log.e("QUE  : " + question.getQueId(), "------------------------VALUE : " + value);
        holder.cbCheck.setChecked(value);

        holder.name.setText(question.getQuestion());

        try {
            String remark = mapRemark.get(question.getQueId());
            holder.edRemark.setText("" + remark);
        } catch (Exception e) {
            Log.e("Exception", "----------------" + e.getMessage());
            e.printStackTrace();
            holder.edRemark.setText("");
        }

        isTouched = false;
        holder.cbCheck.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                isTouched = true;
                return false;
            }
        });


        isTouchedRemark = false;
        holder.edRemark.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                isTouchedRemark = true;
                return false;
            }
        });


        holder.cbCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (isTouched) {
                    isTouched = false;
                    if (b) {
                        map.put(question.getQueId(), true);
                    } else {
                        map.put(question.getQueId(), false);
                    }
                }
            }
        });

        holder.edRemark.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b){
                    int index=view.getId();
                    String enteredRemark=((EditText)view).getText().toString();
                    mapRemark.put(question.getQueId(),enteredRemark);
                    Log.e("INDEX : "+index,"----------------------------"+enteredRemark);
                    b=false;
                }
            }
        });


/*
        holder.edRemark.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.e("REMARK : ", "---------------beforeTextChanged-----------");
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
               if (isTouchedRemark) {
                    try {
                        mapRemark.put(question.getQueId(), charSequence.toString());
                        Log.e("REMARK : ", "-----------------------------" + charSequence.toString());
                        //isTouchedRemark = false;
                    } catch (Exception e) {
                        Log.e("Exception ", "-----onTextChanged-----------" + e.getMessage());
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                Log.e("REMARK : ", "---------------afterTextChanged-----------");
            }
        });
*/


    }

    @Override
    public int getItemCount() {
        return questionsList.size();
    }


}
