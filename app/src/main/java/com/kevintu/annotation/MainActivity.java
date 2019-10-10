package com.kevintu.annotation;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.kevintu.annotation.base.BaseActivity;
import org.jetbrains.annotations.Nullable;

/**
 * Create by Kevin-Tu on 2019/10/10.
 */
public class MainActivity extends BaseActivity {

    @BindView(R.id.result)
    TextView resultView;
    @BindView(R.id.result_edit)
    TextView resultEditView;
    @BindView(R.id.btn)
    Button btnView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean openButterKnife() {
        return true;
    }

    @OnClick(R.id.btn)
    void btnOnclick() {
        resultView.setText("clicked");
        resultEditView.setText("clicked");
    }

    @OnClick(R.id.btn2)
    void btn2Onclick() {
        Intent intent = new Intent(this, MainActivity2.class);
        startActivity(intent);
    }

    @OnClick(R.id.btn3)
    void btn3Onclick() {
        Intent intent = new Intent(this, AnnotationActivity.class);
        startActivity(intent);
    }
}
