package com.yondervision.mi.dto;

public class Mi701WithBLOBs extends Mi701 {
    /**
     * This field was generated by Abator for iBATIS.
     * This field corresponds to the database column MI701.CONTENT
     *
     * @abatorgenerated Thu Apr 28 17:32:31 CST 2016
     */
    private String content;

    /**
     * This field was generated by Abator for iBATIS.
     * This field corresponds to the database column MI701.CONTENTTXT
     *
     * @abatorgenerated Thu Apr 28 17:32:31 CST 2016
     */
    private String contenttxt;

    /**
     * This method was generated by Abator for iBATIS.
     * This method returns the value of the database column MI701.CONTENT
     *
     * @return the value of MI701.CONTENT
     *
     * @abatorgenerated Thu Apr 28 17:32:31 CST 2016
     */
    public String getContent() {
        return content;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method sets the value of the database column MI701.CONTENT
     *
     * @param content the value for MI701.CONTENT
     *
     * @abatorgenerated Thu Apr 28 17:32:31 CST 2016
     */
    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method returns the value of the database column MI701.CONTENTTXT
     *
     * @return the value of MI701.CONTENTTXT
     *
     * @abatorgenerated Thu Apr 28 17:32:31 CST 2016
     */
    public String getContenttxt() {
        return contenttxt;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method sets the value of the database column MI701.CONTENTTXT
     *
     * @param contenttxt the value for MI701.CONTENTTXT
     *
     * @abatorgenerated Thu Apr 28 17:32:31 CST 2016
     */
    public void setContenttxt(String contenttxt) {
        this.contenttxt = contenttxt == null ? null : contenttxt.trim();
    }
}