package com.erproject.busgo.services.authManager;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;

public class AuthController {
    private static final String TYPE_ACCOUNT = "com.erproject.busgo";

    public static void addAccount(Context context, String username, String password, String token) {
        AccountManager accountManager = AccountManager.get(context);
        Account account = new Account(username, TYPE_ACCOUNT);
        accountManager.addAccountExplicitly(account, password, null);
        accountManager.setAuthToken(account, TYPE_ACCOUNT, token);
    }

    public static Account getUserAccount(Context context) {
        AccountManager accountManager = AccountManager.get(context);
        Account account = null;

        try {
            account = accountManager.getAccountsByType(TYPE_ACCOUNT)[0];
        } catch (Exception ignored) {

        }
        return account;
    }

    public static String getToken(Context context) {
        AccountManager accountManager = AccountManager.get(context);
        Account account = getUserAccount(context);
        if (account == null) return null;
        return accountManager.peekAuthToken(account, TYPE_ACCOUNT);
    }

    public static void removeAccount(Context context, Account account) {
        AccountManager accountManager = AccountManager.get(context);
        if (account != null)
            accountManager.removeAccount(account, null, null);
    }
}
