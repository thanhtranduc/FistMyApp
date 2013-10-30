package com.qsoft.pilotproject.model;

/**
 * User: binhtv
 * Date: 10/17/13
 * Time: 3:37 PM
 */
public enum ProgramTab
{
    THUMB_NAIL("thumbnail"),
    DETAIL("detail"),
    COMMENT("comment");
    private String alias;

    private ProgramTab(String alias)
    {
        this.alias = alias;
    }
}
