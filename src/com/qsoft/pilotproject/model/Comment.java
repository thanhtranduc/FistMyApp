package com.qsoft.pilotproject.model;

import java.io.Serializable;

/**
 * User: BinkaA
 * Date: 10/18/13
 * Time: 1:58 AM
 */
public class Comment implements Serializable
{
    private String title;
    private String content;
    private String timeCreated;

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    public String getTimeCreated()
    {
        return timeCreated;
    }

    public void setTimeCreated(String timeCreated)
    {
        this.timeCreated = timeCreated;
    }
}
