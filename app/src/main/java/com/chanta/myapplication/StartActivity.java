package com.chanta.myapplication;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;

public class StartActivity extends AppCompatActivity {

    private EditText editText;
    private Button button;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

//        editText = (EditText) this.findViewById(R.id.edit_text_language);
//        editText.setOnKeyListener(new View.OnKeyListener() {
//            @Override
//            public boolean onKey(View viewForKey, int keyCode, KeyEvent keyEvent) {
//                if ((keyEvent.getAction() == android.view.KeyEvent.ACTION_DOWN)
//                        && (keyCode == android.view.KeyEvent.KEYCODE_ENTER)) {
//                    ScrollView scrollView = (ScrollView) this.findViewById(R.id.scrollViewLanguage);
//                    scrollView.setVisibility(View.VISIBLE);
//                    InputMethodManager manager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
//                    manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
////                    Toast.makeText(getContext(), editText.getText(), Toast.LENGTH_LONG).show();
//                    return true;
//                }
//                return false;
//            }
//        });
    }


}
