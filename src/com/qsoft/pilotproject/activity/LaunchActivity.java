package com.qsoft.pilotproject.activity;

import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.qsoft.pilotproject.R;
import com.qsoft.pilotproject.accountmanager.AccountGeneral;

/**
 * User: binhtv
 * Date: 10/14/13
 * Time: 11:27 AM
 */
public class LaunchActivity extends Activity
{
    Button btLoginFB;
    Button btLogin;
    Button btSignOut;
    private AccountManager mAccountManager;


    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.laucher);
        mAccountManager = AccountManager.get(this);
        btLogin = (Button) findViewById(R.id.btLogin);
        btLogin.setOnClickListener(btLoginClickListener);
    }

    private View.OnClickListener btLoginClickListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View view)
        {
            addNewAccount(AccountGeneral.ACCOUNT_TYPE, AccountGeneral.AUTHTOKEN_TYPE_FULL_ACCESS);
//            Intent intent = new Intent(LaunchActivity.this, LoginFragment.class);
//            startActivity(intent);
        }
    };

    private void addNewAccount(String accountType, String authTokenType) {
        final AccountManagerFuture<Bundle> future = mAccountManager.addAccount(accountType, authTokenType, null, null, this, new AccountManagerCallback<Bundle>() {
            @Override
            public void run(AccountManagerFuture<Bundle> future) {
                try {
                    Bundle bnd = future.getResult();
                    showMessage("Account was created");
                    Log.d("udinic", "AddNewAccount Bundle is " + bnd);

                } catch (Exception e) {
                    e.printStackTrace();
                    showMessage(e.getMessage());
                }
            }
        }, null);
    }

    private void showMessage(final String msg) {
        if (TextUtils.isEmpty(msg))
            return;

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getBaseContext(), msg, Toast.LENGTH_SHORT).show();
            }
        });
    }


}