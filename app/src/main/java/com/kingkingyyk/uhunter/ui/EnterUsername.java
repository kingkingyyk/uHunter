package com.kingkingyyk.uhunter.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.os.AsyncTask;

import com.kingkingyyk.uhunter.Config;
import com.kingkingyyk.uhunter.DataManager;
import com.kingkingyyk.uhunter.databinding.ActivityEnterUsernameBinding;
import com.kingkingyyk.uhunter.utility.Utility;
import com.kingkingyyk.uhunter.R;
import android.content.Intent;

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
        binding.btnVerifyUsername.setOnClickListener(view -> {
            TaskVerifyUsername task = new TaskVerifyUsername();
            task.execute(binding.textFieldUVAUsername.getText().toString());
        });

        checkAndLoadStoredData();
    }

    private void checkAndLoadStoredData() {
        String savedUsername = DataManager.loadString(this, "username","NO_USER");
        if (!savedUsername.equals("NO_USER")) {
            binding.textFieldUVAUsername.setText(savedUsername);
            binding.btnVerifyUsername.performClick();
        }
    }

    private class TaskVerifyUsername extends AsyncTask<String,Void,Integer> {
        @Override protected Integer doInBackground(String... params) {
            int id=Utility.username2id(Config.UHUNT_URL,params[0]);
            if (id > 0) {
                Config.CURRENT_USER = Utility.getUserById(Config.UHUNT_URL,id);
                if (Config.CURRENT_USER==null) id=0;
                System.out.println(Config.CURRENT_USER.getId());
            }
            return id;
        }

        @Override
        protected void onPostExecute(Integer result) {
            if (result == -1) binding.lblUsernameStatus.setText("Error connecting to uHunt server!");
            else if (result==0) binding.lblUsernameStatus.setText("Invalid username!");
            else {
                DataManager.saveString(EnterUsername.this, "username", binding.textFieldUVAUsername.getText().toString());
                finish();
                Intent intent = new Intent(EnterUsername.this, ActivityHome.class);
                startActivity(intent);
            }
            binding.textFieldUVAUsername.setEnabled(result <= 0);
            binding.btnVerifyUsername.setEnabled(result <= 0);
            binding.btnVerifyUsername.setText("Next");
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
