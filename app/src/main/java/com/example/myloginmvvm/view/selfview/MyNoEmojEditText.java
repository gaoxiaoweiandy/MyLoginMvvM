package com.example.myloginmvvm.view.selfview;
import android.content.Context;
import android.content.res.TypedArray;
import android.text.InputFilter;
import android.util.AttributeSet;
import android.widget.EditText;
import com.example.myloginmvvm.R;
import com.example.myloginmvvm.utils.CommonUtil;


/**
 * Created by Administrator on 2018/1/24.
 */

public class MyNoEmojEditText extends EditText {
    private Context mContext;
    public MyNoEmojEditText(Context context) {
        super(context);
        this.setFilters(new InputFilter[]{CommonUtil.newFilter(context)});
    }

    public MyNoEmojEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        //gxw- this.setFilters(new InputFilter[]{CommonUtil.newFilter(context)});
        mContext = context;
        final TypedArray attributes = getContext().obtainStyledAttributes(
                attrs, R.styleable.MyEditText);
        int maxLength = attributes.getInteger(R.styleable.MyEditText_maxLength, 25);
        int editType = attributes.getInteger(R.styleable.MyEditText_editType, 0);

        this.setFilters(new InputFilter[]{new CommonUtil.MyLengthFilter(mContext,maxLength,editType)});
    }

    public MyNoEmojEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.setFilters(new InputFilter[]{CommonUtil.newFilter(context)});
    }


}

