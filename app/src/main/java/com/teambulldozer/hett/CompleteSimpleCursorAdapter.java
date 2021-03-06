package com.teambulldozer.hett;

import android.app.ActionBar;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Typeface;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.teambulldozer.hett.DatabaseHelper;
import com.teambulldozer.hett.R;

import org.w3c.dom.Text;

import java.util.Calendar;
import java.util.StringTokenizer;

/**
 * Created by SEONGBONG on 2016-02-23.
 */
public class CompleteSimpleCursorAdapter extends SimpleCursorAdapter{
    private Context mContext; // In order to call MainActivity's method
    CompleteEventTableController completeEventCtr;
    Typeface NanumSquare_B;
    Typeface NanumBarunGothic_R;
    ViewHolder viewHolder;

    private static String maxDate="";
    public static boolean isOnEditMenu = true;

    /*1) context : ListView의 context
    2) layout : list의 Layout
    3) c : DB에서 가져온 Data를 가리키는 Cursor.
    4) from : DB 필드 이름
    5) to : DB 필드에 대응되는 component의 id*/
    public CompleteSimpleCursorAdapter(Context context, int layout, Cursor c, String[] from, int[] to, int flags,CompleteEventTableController completeEventCtr) {
        super(context, layout, c, from, to, flags);
        this.mContext = context;
        this.completeEventCtr = completeEventCtr;
        NanumSquare_B = Typeface.createFromAsset(context.getAssets(), "NanumSquare_Bold.ttf");
        NanumBarunGothic_R = Typeface.createFromAsset(context.getAssets(), "NanumBarunGothic_Regular.ttf");
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        viewHolder = new ViewHolder();
        LayoutInflater li = (LayoutInflater)mContext.getSystemService(mContext.LAYOUT_INFLATER_SERVICE);
        View view = li.inflate(R.layout.list_item_complete, parent, false);

        viewHolder.borderline = (TextView) view.findViewById(R.id.border_line);
        viewHolder.borderline.setTypeface(NanumSquare_B);

        viewHolder.deleteButton = (ImageView) view.findViewById(R.id.delete_btn);

        viewHolder.completelistItem = (RelativeLayout) view.findViewById(R.id.list_item_complete);

        viewHolder.memoContent = (TextView) view.findViewById(R.id.memo_content);
        viewHolder.memoContent.setTypeface(NanumSquare_B);
        view.setTag(viewHolder);

        initializeAllButtons();
        isOnEditMenu();

        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        ViewHolder holder = (ViewHolder)view.getTag();

        String dateInfo = dayConverter(cursor.getString(cursor.getColumnIndex("_id")));
        if(maxDate == null || !maxDate.equals(dateInfo)){
            maxDate = dateInfo;
            int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,35, mContext.getResources().getDisplayMetrics());
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,height);
            holder.borderline.setLayoutParams(params);
            holder.borderline.setPadding(12,0,0,0);
            holder.borderline.setText(dateInfo);
        }

        holder.memoContent.setText(cursor.getString(cursor.getColumnIndex("MEMO")));
        holder.deleteButton.setTag(cursor.getString(cursor.getColumnIndex("_id")));
        super.bindView(view, context, cursor);
    }

    private static class ViewHolder {
        public TextView borderline;
        public RelativeLayout completelistItem;
        public ImageView deleteButton;
        public TextView memoContent;
    }



    private void initializeAllButtons(){
        viewHolder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (completeEventCtr.deleteData(String.valueOf(v.getTag())) == 1) {
                    swapCursor(completeEventCtr.getEventTableCompleteData());
                    notifyDataSetChanged();
                    maxDate = null;
                } else {
                    Toast.makeText(mContext, "Data Not Deleted", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    private void isOnEditMenu(){
        if(isOnEditMenu){
            viewHolder.deleteButton.setVisibility(View.INVISIBLE);
        }else{
            viewHolder.deleteButton.setVisibility(View.VISIBLE);
        }
    }
    private String dayConverter(String dateInfo){
        Calendar calendar = Calendar.getInstance();
        String year = null,month = null, date = null,hour=null,min=null,sec=null;
        StringTokenizer st = new StringTokenizer(dateInfo,"/");
        while(st.hasMoreElements()){
            year = st.nextElement().toString();
            month = st.nextElement().toString();
            date = st.nextElement().toString();
            hour = st.nextElement().toString();
            min = st.nextElement().toString();
            sec = st.nextElement().toString();
        }
        /*String year = dateInfo.substring(0,2);
        String month = dateInfo.substring(2,4);
        String date = dateInfo.substring(4,6);*/

        calendar.set(Calendar.YEAR,Integer.parseInt(year));
        calendar.set(Calendar.MONTH,Integer.parseInt(month));
        calendar.set(Calendar.DATE,Integer.parseInt(date));

        String dayOfWeek="";
        switch (calendar.get(Calendar.DAY_OF_WEEK)){
            case 1:
                Log.i("ca",String.valueOf(calendar.get(Calendar.DAY_OF_WEEK)));
                dayOfWeek = "토";
                break;
            case 2:
                dayOfWeek = "일";
                break;
            case 3:
                dayOfWeek = "월";
                break;
            case 4:
                dayOfWeek = "화";
                break;
            case 5:
                dayOfWeek = "수";
                break;
            case 6:
                dayOfWeek = "목";
                break;
            case 7:
                dayOfWeek = "금";
                break;
        }
        return month + "월 " + date + "일 " + "(" + dayOfWeek + ")";
    }
    public static void setMaxDate(String maxDate) {
        CompleteSimpleCursorAdapter.maxDate = maxDate;
    }
}
