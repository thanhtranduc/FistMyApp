package com.qsoft.pilotproject.accountmanager;

/**
 * User: udinic
 * Date: 3/27/13
 * Time: 2:35 AM
 */
public interface ServerAuthenticate
{
    public String userSignIn(final String user, final String pass, String authType) throws Exception;

}
