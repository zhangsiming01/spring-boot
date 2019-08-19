package com.example.springboot.common.dto;

import java.math.BigDecimal;
import java.util.Date;

import com.example.springboot.common.Page;

public class BookDto extends Page {
	/**
     * 书籍名称
     */
    private String bookName;

    /**
     * 作者
     */
    private String bookAuthor;

    /**
     * 出版时间
     */
    private Date publishingTime;

    /**
     * 单价
     */
    private BigDecimal unitPrice;

    /**
     * 书籍类型code
     */
    private String bookTypeCode;
	public String getBookName() {
		return bookName;
	}
	public void setBookName(String bookName) {
		this.bookName = bookName;
	}
	public Date getPublishingTime() {
		return publishingTime;
	}
	public void setPublishingTime(Date publishingTime) {
		this.publishingTime = publishingTime;
	}
	public String getBookTypeCode() {
		return bookTypeCode;
	}
	public void setBookTypeCode(String bookTypeCode) {
		this.bookTypeCode = bookTypeCode;
	}
	public String getBookAuthor() {
		return bookAuthor;
	}
	public void setBookAuthor(String bookAuthor) {
		this.bookAuthor = bookAuthor;
	}
	public BigDecimal getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}
	
	

}
