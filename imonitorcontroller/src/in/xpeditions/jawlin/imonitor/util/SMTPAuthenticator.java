/* Copyright © 2012 iMonitor Solutions India Private Limited */
package in.xpeditions.jawlin.imonitor.util;

import in.xpeditions.jawlin.imonitor.controller.properties.ControllerProperties;

import javax.mail.PasswordAuthentication;

public class SMTPAuthenticator extends javax.mail.Authenticator
{

    public PasswordAuthentication getPasswordAuthentication()
    {
        String username = ControllerProperties.getProperties().get(Constants.MAIL_USERNAME);
        String password = ControllerProperties.getProperties().get(Constants.MAIL_PASSWORD);
        return new PasswordAuthentication(username, password);
    }
}