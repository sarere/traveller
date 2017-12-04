package com.dimilionux.traveller;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by sarere on 12/4/17.
 */

public class ActivityLogin extends AppCompatActivity {
    private SharedPreferences sp;
    private Button btnLogin;
    private TextView txtEmail, txtPassword, txtFeedback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_login);
        getSupportActionBar().setTitle(R.string.login);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        sp = getSharedPreferences("database",MODE_PRIVATE);

        txtEmail = findViewById(R.id.txtEmail);
        txtPassword = findViewById(R.id.txtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        txtFeedback = findViewById(R.id.txtFeedbackLogin);

        txtEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                String s1 = txtEmail.getText().toString().trim();
                if(s1.equals("")){
                    txtFeedback.setVisibility(View.GONE);
                }
                else if (s1.matches(emailPattern) && s1.length() > 0)
                {
                    // or
                    txtFeedback.setText(R.string.valid_email);
                    txtFeedback.setTextColor(getResources().getColor(R.color.success));
                    btnLogin.setEnabled(true);
                }
                else
                {
                    //or
                    txtFeedback.setText(R.string.invalid_email);
                    txtFeedback.setTextColor(getResources().getColor(R.color.failed));
                    txtFeedback.setVisibility(View.VISIBLE);
                    btnLogin.setEnabled(false);
                }

            }


            @Override
            public void afterTextChanged(Editable s) {
                validatorField();
            }
        });

        txtPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                validatorField();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login(txtEmail.getText().toString(), txtPassword.getText().toString());
            }
        });
    }

    private void validatorField(){
        String s1 = txtEmail.getText().toString().trim();
        String s2 = txtPassword.getText().toString().trim();

        if(s1.equals("")|| s2.equals("") ){
            btnLogin.setEnabled(false);
        } else {
            btnLogin.setEnabled(true);
        }
    }

    private void login(final String email, String password){
        String exactUrl = Endpoint.urlEndpoint + "login";
        String dataPost = "{\"email\":\""+email+"\",\"password\":\"" + password + "\"}";
        OkHttpClient client = new OkHttpClient();
        RequestBody reqBody = RequestBody.create(Endpoint.JSON,dataPost);
        Request req = new Request.Builder()
                .url(exactUrl)
                .post(reqBody)
                .build();
        client.newCall(req).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();
                Log.d("Response",""+json);
                try {
                    JSONObject reader = new JSONObject(json);
                    String status = reader.getString("message");
                    Log.d("Response",""+reader.getString("message"));
                    if(status.equals("failed")){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Log.d("Response","masukk");

                                Toast.makeText(getBaseContext(),R.string.failed_login,Toast.LENGTH_LONG).show();
                            }
                        });
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                SharedPreferences.Editor spEdit = sp.edit();
                                spEdit.putBoolean("isLogin", true);
                                spEdit.putString("userEmail",email);
                                spEdit.commit();
                                Toast.makeText(getBaseContext(),R.string.success_login,Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(getBaseContext(), ActivityBase.class);
                                ActivityBase.parentFragment = R.string.me;
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            }
                        });
//
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }
}
