package com.kingkingyyk.uhunter;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.os.AsyncTask;

import com.kingkingyyk.uhunter.databinding.ActivityEnterUsernameBinding;
import com.kingkingyyk.uhunter.utility.Utility;

public class EnterUsername extends AppCompatActivity {
    private ActivityEnterUsernameBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_enter_username);

        binding.textFieldUVAUsername.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override public void afterTextChanged(Editable editable) { binding.btnVerifyUsername.setEnabled(editable.length()>0); }
        });
        binding.btnVerifyUsername.setOnClickListener( view -> {;
            TaskVerifyUsername task=new TaskVerifyUsername();
            task.execute(binding.textFieldUVAUsername.getText().toString());
        });
    }

    private class TaskVerifyUsername extends AsyncTask<String,Void,Integer> {
        @Override protected Integer doInBackground(String... params) { return Utility.username2id(Config.UHUNT_URL,params[0]); }

        @Override
        protected void onPostExecute(Integer result) {
            binding.textFieldUVAUsername.setEnabled(true);
            binding.btnVerifyUsername.setEnabled(true);
            binding.btnVerifyUsername.setText("Next");
            if (result == -1) binding.lblUsernameStatus.setText("Error connecting to uHunt server!");
            else if (result==0) binding.lblUsernameStatus.setText("Invalid username!");
            else binding.lblUsernameStatus.setText("Welcome back!");
        }

        @Override
        protected void onPreExecute() {
            binding.textFieldUVAUsername.setEnabled(false);
            binding.btnVerifyUsername.setEnabled(false);
            binding.lblUsernameStatus.setText("");
            binding.btnVerifyUsername.setText("Loading...");
        }

        @Override protected void onProgressUpdate(Void... values) {}
    }
}
