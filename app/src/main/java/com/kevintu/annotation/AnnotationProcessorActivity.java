package com.kevintu.annotation;

import android.os.Bundle;
import android.widget.TextView;
import com.kevintu.annotation.base.BaseActivity;
import com.kevintu.annotationlibrary.ViewInjector;
import org.jetbrains.annotations.Nullable;

/**
 * Create by Kevin-Tu on 2019/10/11.
 */
public class AnnotationProcessorActivity extends BaseActivity {

    @BindViewById(R.id.result)
    TextView resultView;
    @BindViewById(R.id.result_edit)
    TextView resultEditView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewInjector.injectView(this);

        //resultView.setText("injected");
    }
}
