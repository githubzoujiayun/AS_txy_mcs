package com.txy.tools;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.txy.activity.IndexActivity;
import com.txy.activity.StartActivity;
import com.txy.constants.Constants;
import com.txy.txy_mcs.R;
import com.txy.util.SPUtils;
import com.txy.util.ToastUtils;

/**
 * Created by Administrator on 2015/8/6.
 */
public class EditDoalog {

    private Context mContext;
    private EditText edtText_ipset;
    private EditText edtText_portset;

    public EditDoalog(Context context){
        mContext = context;
    }


}
