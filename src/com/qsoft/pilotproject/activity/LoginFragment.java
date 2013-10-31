package com.qsoft.pilotproject.activity;

import android.accounts.*;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.qsoft.pilotproject.R;
import com.qsoft.pilotproject.accountmanager.AccountGeneral;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.io.IOException;

import static com.qsoft.pilotproject.accountmanager.AccountGeneral.sServerAuthenticate;

/**
 * User: thanhtd
 * Date: 10/14/13
 * Time: 2:34 PM
 */
public class LoginFragment extends AccountAuthenticatorActivity
{
    private static final String TAG = "LoginActivity";
    private ImageView imDone;
    private ImageView imBack;
    private EditText mail;
    private EditText password;
    private TextView forgotPass;
    final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    public final static String ARG_ACCOUNT_TYPE = "ACCOUNT_TYPE";
    public final static String ARG_AUTH_TYPE = "AUTH_TYPE";
    public final static String ARG_ACCOUNT_NAME = "ACCOUNT_NAME";
    public final static String ARG_IS_ADDING_NEW_ACCOUNT = "IS_ADDING_ACCOUNT";

    public static final String KEY_ERROR_MESSAGE = "ERR_MSG";

    public final static String PARAM_USER_PASS = "USER_PASS";

    private final int REQ_SIGNUP = 1;

//    private final String TAG = this.getClass().getSimpleName();

    private AccountManager mAccountManager;
    private String mAuthTokenType;

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        mAccountManager = AccountManager.get(getBaseContext());

        String accountName = getIntent().getStringExtra(ARG_ACCOUNT_NAME);
        mAuthTokenType = getIntent().getStringExtra(ARG_AUTH_TYPE);
        if (mAuthTokenType == null)
            mAuthTokenType = AccountGeneral.AUTHTOKEN_TYPE_FULL_ACCESS;

        if (accountName != null) {
            ((TextView)findViewById(R.id.login_etMail)).setText(accountName);
        }

        imBack = (ImageView) findViewById(R.id.login_ivBack);
        imDone = (ImageView) findViewById(R.id.login_ivLogin);
        imBack.setOnClickListener(btBackClickListener);
        imDone.setOnClickListener(btDoneClickListener);
        mail = (EditText) findViewById(R.id.login_etMail);
        password = (EditText) findViewById(R.id.login_etPassword);
        forgotPass = (TextView) findViewById(R.id.login_tvForgotPass);
        forgotPass.setOnClickListener(btForgotPassListener);
        mail.addTextChangedListener(textChangeListener);
        password.addTextChangedListener(textChangeListener);
    }

    private final TextWatcher textChangeListener = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        @Override
        public void afterTextChanged(Editable editable) {
            if(mail.getText().toString().isEmpty() || password.getText().toString().isEmpty() )
            {
                imDone.setBackgroundDrawable(getResources().getDrawable(R.drawable.login_btdone_invisible));
                imDone.setClickable(false);
            }else{
                imDone.setBackgroundDrawable(getResources().getDrawable(R.drawable.login_btdone));
                imDone.setClickable(true);
            }
        }
    };

    View.OnClickListener btBackClickListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View view)
        {
            Intent intentBack = new Intent(LoginFragment.this, ProfileSetupFragment.class);
            startActivity(intentBack);
            Log.d(TAG, "come back to launch screen");
        }
    };

    View.OnClickListener btDoneClickListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View view)
        {
            if (isOnlineNetwork() && validateMailAndPassword(mail, password))
            {
                submit();
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        // The sign up activity returned that the user has successfully created an account
        if (requestCode == REQ_SIGNUP && resultCode == RESULT_OK) {
            finishLogin(data);
        } else
            super.onActivityResult(requestCode, resultCode, data);
    }

    public void submit() {

        final String userName = ((TextView) findViewById(R.id.login_etMail)).getText().toString();
        final String userPass = ((TextView) findViewById(R.id.login_etPassword)).getText().toString();

        final String accountType = getIntent().getStringExtra(ARG_ACCOUNT_TYPE);

        new AsyncTask<String, Void, Intent>() {

            @Override
            protected Intent doInBackground(String... params) {

                Log.d("udinic", TAG + "> Started authenticating");

                String authtoken = null;
                Bundle data = new Bundle();
                try {
                    authtoken = sServerAuthenticate.userSignIn(userName, userPass, mAuthTokenType);

                    data.putString(AccountManager.KEY_ACCOUNT_NAME, userName);
                    data.putString(AccountManager.KEY_ACCOUNT_TYPE, accountType);
                    data.putString(AccountManager.KEY_AUTHTOKEN, authtoken);
                    data.putString(PARAM_USER_PASS, userPass);

                } catch (Exception e) {
                    data.putString(KEY_ERROR_MESSAGE, e.getMessage());
                }

                final Intent res = new Intent();
                res.putExtras(data);
                return res;
            }

            @Override
            protected void onPostExecute(Intent intent) {
                if (intent.hasExtra(KEY_ERROR_MESSAGE)) {
                    Toast.makeText(getBaseContext(), intent.getStringExtra(KEY_ERROR_MESSAGE), Toast.LENGTH_SHORT).show();
                } else {
                    finishLogin(intent);

                }
            }
        }.execute();
    }

    private void finishLogin(Intent intent) {
        Log.d("udinic", TAG + "> finishLogin");

        String accountName = intent.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
        String accountPassword = intent.getStringExtra(PARAM_USER_PASS);
        final Account account = new Account(accountName, intent.getStringExtra(AccountManager.KEY_ACCOUNT_TYPE));

        if (getIntent().getBooleanExtra(ARG_IS_ADDING_NEW_ACCOUNT, false)) {
            Log.d("udinic", TAG + "> finishLogin > addAccountExplicitly");
            String authtoken = intent.getStringExtra(AccountManager.KEY_AUTHTOKEN);
            String authtokenType = mAuthTokenType;

            // Creating the account on the device and setting the auth token we got
            // (Not setting the auth token will cause another call to the server to authenticate the user)
            mAccountManager.addAccountExplicitly(account, accountPassword, null);
            mAccountManager.setAuthToken(account, authtokenType, authtoken);
        } else {
            Log.d("udinic", TAG + "> finishLogin > setPassword");
            mAccountManager.setPassword(account, accountPassword);
        }

        setAccountAuthenticatorResult(intent.getExtras());
        setResult(RESULT_OK, intent);
        finish();
        intent = new Intent(LoginFragment.this, HomeFragment.class);
        startActivity(intent);
        Log.d(TAG, "Login successfully");
    }

    View.OnClickListener btForgotPassListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View view)
        {
            AlertDialog dialog = showAlertDialogResetPassword("Forgot Password", "To reset your password, please enter your" +
                    " email address");
        }
    };


    private boolean isOnlineNetwork()
    {
        // checkTimeoutService();
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting())
        {
            Log.d(TAG, "network available");
            return true;
        }
        else
        {
            AlertDialog dialog = showAlertDialog("Error Signing In", "There is no connection to the internet.");
            dialog.show();
            Log.d(TAG, "network no connection");
            return false;
        }
    }

    private void checkTimeoutService()
    {
        HttpGet httpGet = new HttpGet("http://www.google.com");
        HttpParams httpParameters = new BasicHttpParams();
        int timeoutConnection = 15000;
        HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
        int timeoutSocket = 15000;
        HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);
        DefaultHttpClient httpClient = new DefaultHttpClient(httpParameters);
        try
        {
            Log.d(TAG, "Checking connection...");
            httpClient.execute(httpGet);
            Log.d(TAG, "request service successfully");
            return;
        }
        catch (ClientProtocolException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        Log.d(TAG, "Connection timeout");
    }

    private boolean validateMailAndPassword(EditText mail, EditText password)
    {
        String _mail = mail.getText().toString();
        String _password = password.getText().toString();
        if (_mail.matches(EMAIL_PATTERN) == false)
        {
            AlertDialog dialog = showAlertDialog("Error Signing In", "Email address is incorrect.");
            dialog.show();
            mail.requestFocus();
            return false;
        }
        else if (_password.length() <= 0)
        {
            AlertDialog dialog = showAlertDialog("Error Signing In", "Password is incorrect.");
            dialog.show();
            password.requestFocus();
            return false;
        }
        else
        {
            return true;
        }
    }

    private AlertDialog showAlertDialog(String txtTitle, String message)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final TextView title = new TextView(this);
        title.setText(txtTitle);
        title.setTextSize(20);
        title.setTextColor(Color.WHITE);
        title.setGravity(Gravity.CENTER);
        builder.setCustomTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("OK", null);
        AlertDialog dialog = builder.show();
        TextView messageText = (TextView) dialog.findViewById(android.R.id.message);
        messageText.setGravity(Gravity.CENTER);
        return dialog;
    }

    private AlertDialog showAlertDialogResetPassword(String txtTitle, String message)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final EditText emailAddress = new EditText(this);
        final TextView title = new TextView(this);
        emailAddress.setHint("Email Address");
        builder.setView(emailAddress);
        title.setText(txtTitle);
        title.setTextSize(20);
        title.setTextColor(Color.WHITE);
        title.setGravity(Gravity.CENTER);
        builder.setCustomTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("Cancel", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int which)
            {
            }
        });
        builder.setNegativeButton("Reset", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int which)
            {
                String _emailAddress = emailAddress.getText().toString();
                if (_emailAddress.matches(EMAIL_PATTERN) == false)
                {
                    AlertDialog dialogError = showAlertDialog("Request Error", "Invalid email address");
                    dialogError.show();
                    mail.requestFocus();
                }
            }
        });
        AlertDialog dialog = builder.show();
        TextView messageText = (TextView) dialog.findViewById(android.R.id.message);
        messageText.setGravity(Gravity.CENTER);
        return dialog;
    }
}