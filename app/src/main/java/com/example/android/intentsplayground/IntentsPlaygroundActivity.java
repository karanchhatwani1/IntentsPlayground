package com.example.android.intentsplayground;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import com.example.android.intentsplayground.databinding.ActivityIntentsPlaygroundBinding;

public class IntentsPlaygroundActivity extends AppCompatActivity {

    private static final int REQUEST_COUNT = 0;
    ActivityIntentsPlaygroundBinding b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = ActivityIntentsPlaygroundBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());
        setTitle("Intents Playground");

        setUpHideError();
    }

    private void setUpHideError() {
        b.data.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                b.data.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        b.data2.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                b.data2.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void openMainActivity(View view) {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    public void sendImplicitIntent(View view) {
        String input = b.data.getEditText().getText().toString().trim();

        if(input.isEmpty()){
            b.data.setError("Please enter something");
            return;
        }

        int type = b.radio.getCheckedRadioButtonId();
        if(type == R.id.open_web_page){
            openWebPage(input);
        }
        else if(type == R.id.open_phone){
            dialNumber(input);
        }
        else if(type == R.id.share_text){
            shareText(input);
        }
        else{
            Toast.makeText(this, "Please select an intent type", Toast.LENGTH_SHORT).show();
        }
    }

    private void shareText(String text) {
        Intent intent = new Intent(android.content.Intent.ACTION_SEND);
        /*The type of the content is text, obviously.*/
        intent.setType("text/plain");
        /*Applying information Subject and Body.*/
        intent.putExtra(android.content.Intent.EXTRA_TEXT, text);
        /*Fire!*/
        startActivity(Intent.createChooser(intent, "Share via"));
    }

    private void dialNumber(String number) {
        if(!number.matches("^\\d{10}$")){
            b.data.setError("Invalid Number");
            return;
        }
        b.data.setError(null);
        Uri uri = Uri.parse("tel:"+number);
        Intent intent = new Intent(Intent.ACTION_VIEW,uri);
        startActivity(intent);

    }

    private void openWebPage(String url) {
        if(!url.matches("https?:\\/\\/(www\\.)?[-a-zA-Z0-9@:%._\\+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6}\\b([-a-zA-Z0-9()@:%_\\+.~#?&//=]*)")){
            b.data.setError("Invalid Url");
            return;
        }
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW,uri);
        startActivity(intent);

    }

    //sending data to main activity
    public void sendData(View view) {
        String input = b.data2.getEditText().getText().toString().trim();

        //Validating data
        if(input.isEmpty()){
            b.data2.setError("Please enter something");
            return;
        }

        int initialCount = Integer.parseInt(input);

        Intent intent = new Intent(this,MainActivity.class);

        //putting data or setting data in bundles form
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.INITIAL_COUNT,initialCount);
        bundle.putInt(Constants.MIN_VALUE,-100);
        bundle.putInt(Constants.MAX_VALUE, 100);

        intent.putExtras(bundle);
        startActivityForResult(intent, REQUEST_COUNT);
    }

    //Calling this after result received
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_COUNT && resultCode == RESULT_OK){
            int count = data.getIntExtra(Constants.FINAL_VALUE, Integer.MIN_VALUE);


            b.result.setText("final count recieved : "+count);
            b.result.setVisibility(View.VISIBLE);
        }
    }
}