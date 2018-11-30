package com.jgprogram.sjug.httpclient;

import java.net.Authenticator;
import java.net.PasswordAuthentication;

public class BasicAuthenticator extends Authenticator {
    private final String userName;
    private final char[] password;

    public static BasicAuthenticator of(String userName, char[] password) {
        return new BasicAuthenticator(userName, password);
    }

    private BasicAuthenticator(String userName, char[] password) {
        this.userName = userName;
        this.password = password;
    }

    @Override
    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(userName, password);
    }
}
