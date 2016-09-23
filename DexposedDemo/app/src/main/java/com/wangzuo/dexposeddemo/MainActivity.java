package com.wangzuo.dexposeddemo;

import android.icu.text.UnicodeSetSpanner;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.taobao.patch.PatchMain;
import com.taobao.patch.PatchResult;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private Button button;
    private boolean isSupport;
    private boolean isLDevice;
    private  final  static String PATH = "/Android/data/com.wangzuo.dexposeddemo/cache";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        init();
    }

    /**
     * 初始化
     */
    private void init() {
        findView();

        initSdPath();

        initDexposed();
    }

    /**
     * SD卡地址
     */
    private void initSdPath() {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())){
            File path = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+PATH);
            if (!path.exists()){
                path.mkdirs();
            }
        }else{
            Toast.makeText(this,"no sdcard",Toast.LENGTH_LONG).show();
        }
    }

    /**
     * 初始化补丁配置
     */
    private void initDexposed() {
        isSupport = true;
        isLDevice = android.os.Build.VERSION.SDK_INT >= 20;
    }

    /**
     * 找到控件
     */
    private void findView() {
        textView = (TextView) findViewById(R.id.main_tv);
        button = (Button) findViewById(R.id.main_btn);

        setText();

        button.setOnClickListener(new PatchListener());
    }

    /**
     * 设置文本内容
     */
    private void setText() {
        textView.setText("There is a bug");
    }

    /**
     * 按钮监听
     */
    class PatchListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            runPatchApk();
        }
    }

    /**
     * 运行补丁
     */
    public void runPatchApk() {
        Log.d("dexposed", "runPatchApk button clicked.");
        if (isLDevice) {
            Toast.makeText(this,"It doesn't support this function on L device.",Toast.LENGTH_LONG).show();
            return;
        }
        if (!isSupport) {
            Toast.makeText(this,"This device doesn't support dexposed!",Toast.LENGTH_LONG).show();
            return;
        }
        File cacheDir = getExternalCacheDir();
        if(cacheDir != null){
            String fullpath = cacheDir.getAbsolutePath() + File.separator + "PatchDemo.apk";
            PatchResult result = PatchMain.load(this, fullpath, null);
            if (result.isSuccess()) {
                Toast.makeText(this,"patch success!",Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this,"patch error is " + result.getErrorInfo(),Toast.LENGTH_LONG).show();
            }
        }
        setText();
    }
}
