package com.ggamdeal.app;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.ggamdeal.app.R;

public class MainActivity extends AppCompatActivity {

    //숨겨진 페이지가 열렸는지 확인하는 변수
    int pageStatus = 0;
    Animation tranlateLeftAnim;
    Animation tranlateRightAnim;
    LinearLayout page1;
    LinearLayout page2;
    Button button;
    TextView welcomePage_BaseTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        page1 = findViewById(R.id.page1);
        page2 = findViewById(R.id.page2);

        //anim 폴더의 애니메이션을 가져와서 준비
        tranlateLeftAnim = AnimationUtils.loadAnimation(this, R.anim.translate_left);
        tranlateRightAnim = AnimationUtils.loadAnimation(this, R.anim.translate_right);

        //페이지 슬라이딩 이벤트가 발생했을때 애니메이션이 시작 됐는지 종료 됐는지 감지할 수 있다.
        SlidingPageAnimationListener animListener = new SlidingPageAnimationListener();
        tranlateLeftAnim.setAnimationListener(animListener);
        tranlateRightAnim.setAnimationListener(animListener);

        button = findViewById(R.id.button);
        welcomePage_BaseTextView = findViewById(R.id.welcomePageBaseTextView);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pageStatus == 0) {
                    welcomePage_BaseTextView.setVisibility(View.INVISIBLE);
                    page1.setVisibility(View.VISIBLE);
                    page1.startAnimation(tranlateLeftAnim);
                } else if (pageStatus == 1) {
                    page2.setVisibility(View.VISIBLE);
                    page2.startAnimation(tranlateLeftAnim);
                } else if (pageStatus == 2) {
                    page2.startAnimation(tranlateRightAnim);
                    page1.startAnimation(tranlateRightAnim);
                } else if (pageStatus == 3) {
                    page1.setVisibility(View.INVISIBLE);
                    page2.setVisibility(View.INVISIBLE);
                    pageStatus = 0;
                    button.setText("다음");
                    welcomePage_BaseTextView.setVisibility(View.VISIBLE);
                }

            }
        });

    }

    private class SlidingPageAnimationListener implements Animation.AnimationListener {
        @Override
        public void onAnimationStart(Animation animation) {
            pageStatus++;
        }

        public void onAnimationEnd(Animation animation) {
            if (pageStatus == 2) {
                button.setText("닫기");
                pageStatus++;
            } else {
                button.setText("다음");
            }
        }

        @Override
        public void onAnimationRepeat(Animation animation) {}
    }
}