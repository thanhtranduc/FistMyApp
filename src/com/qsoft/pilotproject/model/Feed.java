package com.qsoft.pilotproject.model;

/**
 * User: binhtv
 * Date: 10/15/13
 * Time: 9:05 AM
 */
public class Feed
{
    private String title;
    private String composer;
    private int likeNumber;
    private int commentNumber;
    private String updateStatus;

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getComposer()
    {
        return composer;
    }

    public void setComposer(String composer)
    {
        this.composer = composer;
    }

    public int getLikeNumber()
    {
        return likeNumber;
    }

    public void setLikeNumber(int likeNumber)
    {
        this.likeNumber = likeNumber;
    }

    public int getCommentNumber()
    {
        return commentNumber;
    }

    public void setCommentNumber(int commentNumber)
    {
        this.commentNumber = commentNumber;
    }

    public String getUpdateStatus()
    {
        return updateStatus;
    }

    public void setUpdateStatus(String updateStatus)
    {
        this.updateStatus = updateStatus;
    }
}
