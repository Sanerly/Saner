package com.saner;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.saner.ui.canvas.CanvasActivity;
import com.saner.ui.measure.MeasureActivity;
import com.saner.ui.photo.ShowPhotoActivity;
import com.saner.ui.round.RoundAvatarActivity;
import com.saner.ui.shader.ShaderActivity;
import com.saner.ui.touch.TouchActivity;
import com.saner.ui.xfermode.XfermodeActivity;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onRoundAvatar(View view) {
        start(RoundAvatarActivity.class);
    }

    public void onSelectedPhoto(View view) {
        start(ShowPhotoActivity.class);
    }

    public void Xfermode(View view) {
        start(XfermodeActivity.class);
    }

    public void Shader(View view) {
        start(ShaderActivity.class);
    }

    public void Canvas(View view) {
        start(CanvasActivity.class);
    }

    public void Measure(View view) {
        start(MeasureActivity.class);
    }

    public void Touch(View view) {
        start(TouchActivity.class);
    }

    public void start(Class<?> cls) {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
    }
}
