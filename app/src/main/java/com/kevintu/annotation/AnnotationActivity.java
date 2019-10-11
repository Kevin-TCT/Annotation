package com.kevintu.annotation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.kevintu.annotation.annotation.BindStr;
import com.kevintu.annotation.annotation.FindView;
import com.kevintu.annotation.annotation.InjectUtils;
import com.kevintu.annotation.annotation.ViewClick;
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
    @BindStr(R.string.app_name)
    String bingStr;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        InjectUtils.getInstance().inject(this);

        resultView.setText("clicked");
        resultEditView.setText(bingStr);
    }

    /*@Override
    public boolean openButterKnife() {
        return true;
    }*/

    @ViewClick(R.id.btn)
    public void btnClick(View view) {
        Toast.makeText(this, "Button clicked!!", Toast.LENGTH_SHORT).show();
    }
}
