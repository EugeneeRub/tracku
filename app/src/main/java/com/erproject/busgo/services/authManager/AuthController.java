package com.erproject.busgo.services.authManager;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;

import com.erproject.busgo.data.exceptions.UserNotFoundException;

public class AuthController {
    private static final String PACKAGE_NAME = "com.erproject.busgo";

    public static void addAccount(Context context, String username, String password, String token) {
        AccountManager accountManager = AccountManager.get(context);
        Account account = new Account(username, PACKAGE_NAME);
        accountManager.addAccountExplicitly(account, password, null);
        accountManager.setAuthToken(account, PACKAGE_NAME, token);
    }

    public static Account getAccount(Context context) throws UserNotFoundException {
        AccountManager accountManager = AccountManager.get(context);
        Account account;
        try {
            account = accountManager.getAccountsByType(PACKAGE_NAME)[0];
        } catch (Exception e) {
            throw new UserNotFoundException();
        }
        return account;
    }

    public static String getToken(Context context) throws UserNotFoundException {
        AccountManager accountManager = AccountManager.get(context);
        Account account = getAccount(context);
        if (account == null) throw new UserNotFoundException();
        return accountManager.peekAuthToken(account, PACKAGE_NAME);
    }

    public static void removeAccount(Context context) {
        try {
            Account account = getAccount(context);
            AccountManager accountManager = AccountManager.get(context);
            if (account != null) accountManager.removeAccount(account, null, null);
        } catch (UserNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static String getLoginString(Context context) throws UserNotFoundException {
        Account account = getAccount(context);
        if (account == null || account.name == null) throw new UserNotFoundException();
        String email[] = account.name.split("@");
        return email[0];
    }
}
