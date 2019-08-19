package com.example.springboot.dal.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@Table(name = "book_info")
public class BookInfo implements Serializable {
    /**
     * 自增id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 书籍名称
     */
    @Column(name = "book_name")
    private String bookName;
    /**
     * 作者
     */
    @Column(name = "book_author")
    private String bookAuthor;

    /**
     * 出版时间
     */
    @Column(name = "publishing_time")
    private Date publishingTime;

    /**
     * 单价
     */
    @Column(name = "unit_price")
    private BigDecimal unitPrice;

    /**
     * 书籍类型code
     */
    @Column(name = "book_type_code")
    private String bookTypeCode;

    private static final long serialVersionUID = 1L;

    /**
     * 获取自增id
     *
     * @return id - 自增id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置自增id
     *
     * @param id 自增id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取书籍名称
     *
     * @return book_name - 书籍名称
     */
    public String getBookName() {
        return bookName;
    }

    /**
     * 设置书籍名称
     *
     * @param bookName 书籍名称
     */
    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    /**
     * 获取作者
     *
     * @return book_author - 作者
     */
    public String getBookAuthor() {
        return bookAuthor;
    }

    /**
     * 设置作者
     *
     * @param bookAuthor 作者
     */
    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
    }

    /**
     * 获取出版时间
     *
     * @return publishing_time - 出版时间
     */
    public Date getPublishingTime() {
        return publishingTime;
    }

    /**
     * 设置出版时间
     *
     * @param publishingTime 出版时间
     */
    public void setPublishingTime(Date publishingTime) {
        this.publishingTime = publishingTime;
    }

    /**
     * 获取单价
     *
     * @return unit_price - 单价
     */
    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    /**
     * 设置单价
     *
     * @param unitPrice 单价
     */
    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    /**
     * 获取书籍类型code
     *
     * @return book_type_code - 书籍类型code
     */
    public String getBookTypeCode() {
        return bookTypeCode;
    }

    /**
     * 设置书籍类型code
     *
     * @param bookTypeCode 书籍类型code
     */
    public void setBookTypeCode(String bookTypeCode) {
        this.bookTypeCode = bookTypeCode;
    }
}