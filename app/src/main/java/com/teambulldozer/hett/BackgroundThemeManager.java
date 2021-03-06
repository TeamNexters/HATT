package com.teambulldozer.hett;

import android.content.Context;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by GHKwon on 2016-03-03.
 */
public class BackgroundThemeManager {
    private Context context;
    private static final BackgroundThemeManager backgroundThemeManager;
    public int selectedBackgroundThemeCodeNo;
    static {
        backgroundThemeManager = new BackgroundThemeManager();
    }
    private ImageView rainingStart1=null;
    private Animation animation1;

    private ImageView rainingStart2=null;
    private Animation animation2;

    private View rememverAnimationView;

    public static int backgroundTheme;
    public static final int BACKGROUND_WHITE=0;
    public static final int BACKGROUND_TREE=1;
    public static final int BACKGROUND_STRIPE=2;
    public static final int BACKGROUND_SPACE=3;
    public static final int BACKGROUND__SNOW=4;

    public static int rememverBackgroundInt=0;

    private BackgroundThemeManager(){

    }

    /**
     * 싱글톤 적용.
     * @return this를 return
     */
    public static BackgroundThemeManager getInstance() {
        return backgroundThemeManager;
    }

    /**
     * 배경화면을 변경해 주기 위해서 호출한다.
     * @param context 호출하는 Activity의 context
     * @param view 호출하는 Activity의 Root View.
     */
    public void setBackground(Context context,View view,int activityNo) {
        this.context=context;
        selectedBackgroundThemeCodeNo = DrawerTableController.getInstance(context).searchByBackgroundThemeCodeNo();
        try {

            view.clearAnimation();
            rainingStart1.clearAnimation();
            rainingStart2.clearAnimation();
            rainingStart1.setVisibility(View.GONE);
            rainingStart2.setVisibility(View.GONE);

        } catch(Exception ex) {
            Log.d("happendException", "BackgroundTheme");
        }

        try {
            switch (selectedBackgroundThemeCodeNo) {
                case 0 :whiteSelectImage(view);break;
                case 1 :bgPatternTree(view); break;
                case 2 :Stripe(view); break;
                case 3 :space(view); break;
                case 4 :snow(view);break;
                case 5 :break;
            }
        } catch(Exception ex) {ex.printStackTrace();}

        if(selectedBackgroundThemeCodeNo == 3) {
            switch(activityNo) {
                case ActivityNo.MAIN_ACTIVITY : brightMainActivity(view); break;
                case ActivityNo.ClosenessActivity :brightClosenessActivity(view); break;
                case ActivityNo.CompleteActivity : brightCompleteActivity(view);break;
                case ActivityNo.EditFriendNameActivity :brightEditFriendNameActivity(view); break;
                case ActivityNo.FriendSettingActivity : brightFriendSettingActivity(view);break;
                case ActivityNo.RepeatEventActivity : brightRepeatEventActivity(view); break;
                case ActivityNo.FriendTalkSettingActivity : brightFriendTalkSettingActivity(view); break;
                case ActivityNo.SettingBackgroundThemeActivity : brightSettingBackgroundThemeActivity(view); break;

            }
        } else if (selectedBackgroundThemeCodeNo==2 ){
            switch(activityNo) {
                case ActivityNo.MAIN_ACTIVITY :darkActivity(view); break;
                case ActivityNo.ClosenessActivity :  darkClosenessActivity(view); break;
                case ActivityNo.CompleteActivity : darkCompleteActivity(view); break;
                case ActivityNo.EditFriendNameActivity : break;
                case ActivityNo.FriendSettingActivity :settingFriendSettingActivity(view); break;
                case ActivityNo.RepeatEventActivity : darkRepeatEventActivity(view); break;
                case ActivityNo.FriendTalkSettingActivity : break;
                case ActivityNo.SettingBackgroundThemeActivity : break;
            }
        }
        else {
            switch(activityNo) {
                case ActivityNo.MAIN_ACTIVITY : /*if(selectedBackgroundThemeCodeNo !=1)*/ darkActivity(view); break;
            }
        }




    }
    public void whiteSelectImage(View view) {
        view.setBackgroundResource(R.color.hatt_background);
    }
    public void bgPatternTree(View view) {
        view.setBackground(context.getResources().getDrawable(R.drawable.bg_pattern_tree_dir_width2));
    }
    public void Stripe(View view) {
        view.setBackground(context.getResources().getDrawable(R.drawable.background_theme_stripe));
    }

    public void space(View view ) {
        rememverAnimationView=view;
        view.setBackground(context.getResources().getDrawable(R.drawable.space));
        animation1 = AnimationUtils.loadAnimation(context,R.anim.tranlate);

        rainingStart1 = new ImageView(context);
        rainingStart1.setImageDrawable(context.getResources().getDrawable(R.drawable.raining_star));

        animation2 = AnimationUtils.loadAnimation(context,R.anim.tranlate2);
        rainingStart2 = new ImageView(context);
        rainingStart2.setImageDrawable(context.getResources().getDrawable(R.drawable.raining_star));
        settingAnimation(view);


    }
    public void settingAnimation (View view) {
        rainingStart1.startAnimation(animation1);
        rainingStart2.startAnimation(animation2);
        try {
            ((SoftKeyboardLsnedRelativeLayout)view).addView(rainingStart1);
            ((SoftKeyboardLsnedRelativeLayout)view).addView(rainingStart2);
        } catch(Exception ex) {
            ex.printStackTrace();
            Log.d("error",ex.getMessage());
        }
        try {
            ((LinearLayout)view).addView(rainingStart1);
            ((LinearLayout)view).addView(rainingStart2);
        } catch(Exception ex) {
            ex.printStackTrace();
            Log.d("error1",ex.getMessage());
        }
        try {
            ((RelativeLayout)view).addView(rainingStart1);
            ((RelativeLayout)view).addView(rainingStart2);
        } catch(Exception ex) {
            ex.printStackTrace();
            Log.d("error1",ex.getMessage());
        }
    }
    public void snow(View view) {
        view.setBackground(context.getResources().getDrawable(R.drawable.snow_1));
        animation1 = AnimationUtils.loadAnimation(context,R.anim.snow);

        rainingStart1 = new ImageView(context);
        rainingStart1.setImageDrawable(context.getResources().getDrawable(R.drawable.snow_3));

        animation2 = AnimationUtils.loadAnimation(context,R.anim.snow);
        rainingStart2 = new ImageView(context);
        rainingStart2.setImageDrawable(context.getResources().getDrawable(R.drawable.snow_3));
        settingAnimation(view);

        //changeOwnTextColorByMainActivity(view);
    }
    public void brightMainActivity(View view) {
        ((TextView)view.findViewById(R.id.todayBar)).setTextColor(context.getResources().getColor(R.color.hatt_white));
        ((TextView)view.findViewById(R.id.dateBar)).setTextColor(context.getResources().getColor(R.color.hatt_white));
        ((TextView)view.findViewById(R.id.finishMenu)).setTextColor(context.getResources().getColor(R.color.hatt_white));
        ((TextView)view.findViewById(R.id.editMenu)).setTextColor(context.getResources().getColor(R.color.hatt_white));
        ((ImageView)view.findViewById(R.id.friendBtn)).setImageDrawable(context.getResources().getDrawable(R.drawable.white_menu));
    }
    public void brightClosenessActivity(View view) {
        ((TextView)view.findViewById(R.id.tvClCloseness)).setTextColor(context.getResources().getColor(R.color.hatt_white));
        ((TextView)view.findViewById(R.id.tvClDesc)).setTextColor(context.getResources().getColor(R.color.hatt_white));
        ((TextView)view.findViewById(R.id.tvClNext)).setTextColor(context.getResources().getColor(R.color.hatt_white));
    }
    public void brightCompleteActivity(View view) {
        ((TextView)view.findViewById(R.id.complete_title)).setTextColor(context.getResources().getColor(R.color.hatt_white));
        ((TextView)view.findViewById(R.id.finish_menu)).setTextColor(context.getResources().getColor(R.color.hatt_white));//edit_menu
        ((TextView)view.findViewById(R.id.edit_menu)).setTextColor(context.getResources().getColor(R.color.hatt_white));

    }
    public void brightEditFriendNameActivity(View view) {
        ((TextView)view.findViewById(R.id.editFriendNameTextView)).setTextColor(context.getResources().getColor(R.color.hatt_white));
        ((TextView)view.findViewById(R.id.okayBtn)).setTextColor(context.getResources().getColor(R.color.hatt_white));
        ((TextView)view.findViewById(R.id.friend_name_description_view)).setTextColor(context.getResources().getColor(R.color.hatt_white));
    }
    public void brightFriendSettingActivity(View view) {
        ((TextView)view.findViewById(R.id.tvFsFriend)).setTextColor(context.getResources().getColor(R.color.hatt_white));
        ((TextView)view.findViewById(R.id.tvBtnFreindOk)).setTextColor(context.getResources().getColor(R.color.hatt_white));

    }
    public void brightRepeatEventActivity(View view) {
        ((TextView)view.findViewById(R.id.repeat_title)).setTextColor(context.getResources().getColor(R.color.hatt_white));
        ((TextView)view.findViewById(R.id.repeat_delete_menu)).setTextColor(context.getResources().getColor(R.color.hatt_white));
    }
    public void brightFriendTalkSettingActivity(View view) {
        ((TextView)view.findViewById(R.id.tvFtsTalkStyle)).setTextColor(context.getResources().getColor(R.color.hatt_white));
        ((TextView)view.findViewById(R.id.tvBtnTalkStOk)).setTextColor(context.getResources().getColor(R.color.hatt_white));
    }
    public void brightSettingBackgroundThemeActivity(View view) {
        ((TextView)view.findViewById(R.id.settingBackgroundTextView)).setTextColor(context.getResources().getColor(R.color.hatt_white));
        ((TextView)view.findViewById(R.id.settingBackgroundThemeOkBtn)).setTextColor(context.getResources().getColor(R.color.hatt_white));
    }


    public void darkActivity(View view ){
        ((TextView)view.findViewById(R.id.todayBar)).setTextColor(context.getResources().getColor(R.color.hatt_turquoise_opacity100));
        ((TextView)view.findViewById(R.id.dateBar)).setTextColor(context.getResources().getColor(R.color.hatt_turquoise_opacity100));
        ((TextView)view.findViewById(R.id.finishMenu)).setTextColor(context.getResources().getColor(R.color.hatt_turquoise_opacity100));
        ((TextView)view.findViewById(R.id.editMenu)).setTextColor(context.getResources().getColor(R.color.hatt_turquoise_opacity100));
        ((ImageView)view.findViewById(R.id.friendBtn)).setImageDrawable(context.getResources().getDrawable(R.drawable.menu));
    }//complete_layout_line
    public void darkClosenessActivity(View view) {//
        view.findViewById(R.id.line1).setBackgroundColor(context.getResources().getColor(R.color.hatt_turquoise_opacity100));
        ((TextView)view.findViewById(R.id.tvClDesc)).setTextColor(context.getResources().getColor(R.color.hatt_turquoise_opacity100));
        view.findViewById(R.id.line2).setBackgroundColor(context.getResources().getColor(R.color.hatt_turquoise_opacity100));
        ((TextView)view.findViewById(R.id.tvClCloseness)).setTextColor(context.getResources().getColor(R.color.hatt_turquoise_opacity100));
        ((TextView)view.findViewById(R.id.tvClNext)).setTextColor(context.getResources().getColor(R.color.hatt_turquoise_opacity100));
    }
    public void darkCompleteActivity(View view) {
        view.findViewById(R.id.complete_layout_line).setBackgroundColor(context.getResources().getColor(R.color.hatt_turquoise_opacity100));
        ((TextView)view.findViewById(R.id.complete_title)).setTextColor(context.getResources().getColor(R.color.hatt_turquoise_opacity100));
    }
    public void darkRepeatEventActivity(View view) {//
        view.findViewById(R.id.repeat_layout_line).setBackgroundColor(context.getResources().getColor(R.color.hatt_turquoise_opacity100));
        ((TextView)view.findViewById(R.id.repeat_title)).setTextColor(context.getResources().getColor(R.color.hatt_turquoise_opacity100));
    }
    public void darkSettingBackgroundThemeActivity(View view) {


        ((TextView)view.findViewById(R.id.settingBackgroundTextView)).setTextColor(context.getResources().getColor(R.color.hatt_turquoise_opacity100));
        ((TextView)view.findViewById(R.id.settingBackgroundThemeOkBtn)).setTextColor(context.getResources().getColor(R.color.hatt_turquoise_opacity100));
    }
    public void settingFriendSettingActivity(View view) {
        view.findViewById(R.id.activityFriendSettingLine).setBackgroundColor(context.getResources().getColor(R.color.hatt_turquoise_opacity100));
    }



}
/*
* for(int i=0;i<viewGroup.getChildCount();i++) {
            try {
                if(viewGroup.getChildAt(i) instanceof TextView)
                    ((TextView)viewGroup.getChildAt(i)).setTextColor(context.getResources().getColor(R.color.hatt_date));
            } catch(Exception ex) {
                continue;
            }
        }
* */
/*public void changeTextColor(View view) {
        if(view.findViewById(R.id.myLayout)!=null) {
            ((SoftKeyboardLsnedRelativeLayout) view).addView(rainingStart1);
            rainingStart1.startAnimation(animation1);
            ((SoftKeyboardLsnedRelativeLayout) view).addView(rainingStart2);
            rainingStart2.startAnimation(animation2);
            changeTextColorByLightByMainActivity(view);
        }
        else if(view.findViewById(R.id.closenessActivity)!=null) {
            changeTextColorByLightClosenessActivity(view);
        }
    }

    public void changeTextColorByLight(View view) {
        ViewGroup viewGroup  = (ViewGroup)view;
        for(int i=0;i<viewGroup.getChildCount();i++) {
            View v = viewGroup.getChildAt(i);
            TextView textView;
            if(v instanceof TextView)
                textView = (TextView)v;
            else
                continue;
            textView.setTextColor(context.getResources().getColor(R.color.hatt_date));
        }
    }


    public void changeOwnTextColorByMainActivity(View view) {
        ((TextView)view.findViewById(R.id.todayBar)).setTextColor(context.getResources().getColor(R.color.hatt_date));
        ((TextView)view.findViewById(R.id.dateBar)).setTextColor(context.getResources().getColor(R.color.hatt_date));
        ((TextView)view.findViewById(R.id.finishMenu)).setTextColor(context.getResources().getColor(R.color.hatt_date));
        ((TextView)view.findViewById(R.id.editMenu)).setTextColor(context.getResources().getColor(R.color.hatt_date));
        ((ImageView)view.findViewById(R.id.friendBtn)).setImageDrawable(context.getResources().getDrawable(R.drawable.menu));
    }
    public void changeTextColorByLightClosenessActivity(View view) {
        ((TextView)view.findViewById(R.id.tvClCloseness)).setTextColor(context.getResources().getColor(R.color.hatt_date));
        ((TextView)view.findViewById(R.id.tvClDesc)).setTextColor(context.getResources().getColor(R.color.hatt_date));
        ((TextView)view.findViewById(R.id.tvClNext)).setTextColor(context.getResources().getColor(R.color.hatt_date));
        view.findViewById(R.id.closenessTopLine1).setBackgroundColor(context.getResources().getColor(R.color.hatt_date));
        view.findViewById(R.id.closenessTopLine2).setBackgroundColor(context.getResources().getColor(R.color.hatt_date));
    }
    public void changeTextColorByOwnTextColorClosenessActivity(View view) {
        ((TextView)view.findViewById(R.id.tvClCloseness)).setTextColor(context.getResources().getColor(R.color.hatt_white));
        ((TextView)view.findViewById(R.id.tvClDesc)).setTextColor(context.getResources().getColor(R.color.hatt_white));
        ((TextView)view.findViewById(R.id.tvClNext)).setTextColor(context.getResources().getColor(R.color.hatt_white));
        view.findViewById(R.id.closenessTopLine1).setBackgroundColor(context.getResources().getColor(R.color.hatt_white));
        view.findViewById(R.id.closenessTopLine2).setBackgroundColor(context.getResources().getColor(R.color.hatt_white));
    }
    public void changeTextColorByOwnTextColorColpleteActivity(View view) {
        view.findViewById(R.id.complete_layout_line).setBackgroundColor(context.getResources().getColor(R.color.hatt_white));
    }
    public void changeTextViewBright(TextView textView) {
        textView.setTextColor(context.getResources().getColor(R.color.hatt_white));
    }
    public void changeTextViewDark(TextView textView) {
        textView.setTextColor(context.getResources().getColor(R.color.hatt_date));
    }*/