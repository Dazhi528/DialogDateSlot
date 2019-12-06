package com.dazhi.dateslot;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatDialog;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DialogDateSlot extends AppCompatDialog {
    // 外部入参
    final Context context;
    String timeSta;
    String timeEnd;
    final InteDateSlot inteTimeSlot;
    // ui
    private TextView tvTimeslotSta;
    private TextView tvTimeslotEnd;
    private Button btTimeslotEnt;
    // 当前日期常量
    final Date dateCur = new Date();
    final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINESE);


    public DialogDateSlot(@NonNull Context context, @NonNull String timeSta,
                          @NonNull String timeEnd, @NonNull InteDateSlot inteTimeSlot) {
        super(context, R.style.StyleDialogDateSlot);
        setContentView(R.layout.dialog_date_slot);
        //
        this.context = context;
        this.timeSta = timeSta;
        this.timeEnd = timeEnd;
        this.inteTimeSlot = inteTimeSlot;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // init
        tvTimeslotSta = findViewById(R.id.tvTimeslotSta);
        tvTimeslotEnd = findViewById(R.id.tvTimeslotEnd);
        btTimeslotEnt = findViewById(R.id.btTimeslotEnt);
        // init value
        tvTimeslotSta.setText(timeSta);
        tvTimeslotEnd.setText(timeEnd);
        // init listener
        tvTimeslotSta.setOnClickListener(mClickTimeSta);
        tvTimeslotEnd.setOnClickListener(mClickTimeEnd);
        btTimeslotEnt.setOnClickListener(mClickBtEnt);
    }

    // 点击选择开始时间
    private View.OnClickListener mClickTimeSta = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            final int tempY = Integer.parseInt(timeSta.substring(0, 4));
            final int tempM = Integer.parseInt(timeSta.substring(5, 7));
            final int tempD = Integer.parseInt(timeSta.substring(8, 10));
            new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    // 转字符串日期格式
                    Calendar calendar= Calendar.getInstance();
                    calendar.set(Calendar.YEAR, tempY);
                    calendar.set(Calendar.MONTH, tempM);
                    calendar.set(Calendar.DAY_OF_MONTH, tempD);
                    timeSta = dateFormat.format(calendar.getTime());
                    tvTimeslotSta.setText(timeSta);
                }
            }, tempY, tempM, tempD).show();
        }
    };

    // 点击选择结束时间
    private View.OnClickListener mClickTimeEnd = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            final int tempY = Integer.parseInt(timeEnd.substring(0, 4));
            final int tempM = Integer.parseInt(timeEnd.substring(5, 7));
            final int tempD = Integer.parseInt(timeEnd.substring(8, 10));
            new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    // 转字符串日期格式
                    Calendar calendar= Calendar.getInstance();
                    calendar.set(Calendar.YEAR, tempY);
                    calendar.set(Calendar.MONTH, tempM);
                    calendar.set(Calendar.DAY_OF_MONTH, tempD);
                    timeEnd = dateFormat.format(calendar.getTime());
                    tvTimeslotSta.setText(timeEnd);
                }
            }, tempY, tempM, tempD).show();
        }
    };

    // 点击确认按钮
    private View.OnClickListener mClickBtEnt = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // 校验开始日期
            Date checkedDateSta;
            try {
                checkedDateSta = dateFormat.parse(timeSta);
            } catch (ParseException e) {
                e.printStackTrace();
                Toast.makeText(context,
                        context.getString(R.string.dateslot_hintmsg_dateerror),
                        Toast.LENGTH_SHORT).show();
                return;
            }
            if(checkedDateSta.after(dateCur)){
                Toast.makeText(context,
                        context.getString(R.string.dateslot_hintmsg_dateoversta),
                        Toast.LENGTH_SHORT).show();
                return;
            }
            // 校验结束日期
            Date checkedDateEnd;
            try {
                checkedDateEnd = dateFormat.parse(timeEnd);
            } catch (ParseException e) {
                e.printStackTrace();
                Toast.makeText(context,
                        context.getString(R.string.dateslot_hintmsg_dateerror),
                        Toast.LENGTH_SHORT).show();
                return;
            }
            if(checkedDateEnd.after(dateCur)){
                Toast.makeText(context,
                        context.getString(R.string.dateslot_hintmsg_dateoverend),
                        Toast.LENGTH_SHORT).show();
                return;
            }
            // 校验开始日期小于等于结束日期
            if(checkedDateSta.after(checkedDateEnd)){
                Toast.makeText(context,
                        context.getString(R.string.dateslot_hintmsg_date_staend),
                        Toast.LENGTH_SHORT).show();
                return;
            }
            // 校验通过，接口回调，并关闭对话框
            inteTimeSlot.call(timeSta, timeEnd);
            dismiss();
        }
    };

}
