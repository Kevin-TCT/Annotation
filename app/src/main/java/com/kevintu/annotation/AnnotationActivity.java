package com.kevintu.annotation;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import com.kevintu.annotation.annotation.FindView;
import com.kevintu.annotation.annotation.InjectUtils;
import com.kevintu.annotation.base.BaseActivity;
import org.jetbrains.annotations.Nullable;

/**
 * Create by Kevin-Tu on 2019/10/10.
 */
public class AnnotationActivity extends BaseActivity {

    @FindView(R.id.result)
    TextView resultView;
    @FindView(R.id.result_edit)
    TextView resultEditView;
    @FindView(R.id.btn)
    Button btnView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        InjectUtils.inject(this);

        resultView.setText("clicked");
    }

    /*@Override
    public boolean openButterKnife() {
        return true;
    }*/
}
