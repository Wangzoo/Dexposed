package com.taobao.patch;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import android.widget.TextView;

import com.taobao.android.dexposed.DexposedBridge;
import com.taobao.android.dexposed.XC_MethodReplacement;
import com.taobao.android.dexposed.XposedHelpers;

public class TextPatch implements IPatch {

	@Override
	public void handlePatch(final PatchParam arg0) throws Throwable {    	
    	Class<?> cls = null;
		try {
			cls= arg0.context.getClassLoader()
					.loadClass("com.wangzuo.dexposeddemo.MainActivity");
		} catch (ClassNotFoundException e) {
			Log.i("jw", "patch error:"+Log.getStackTraceString(e));
			return;
		}     	
     	DexposedBridge.findAndHookMethod(cls, "setText",
				new XC_MethodReplacement() {
			@Override
			protected Object replaceHookedMethod(MethodHookParam param) throws Throwable {
				Log.i("jw", "invoke method:"+param);
				Activity mainActivity = (Activity) param.thisObject;

				TextView textView = (TextView) XposedHelpers.getObjectField(mainActivity, "textView");
				textView.setText("no bug");
				return null;                 
			}
		});
	}
}
