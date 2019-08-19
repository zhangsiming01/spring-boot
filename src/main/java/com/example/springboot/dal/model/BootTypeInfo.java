package com.example.springboot.dal.model;

import java.io.Serializable;
import javax.persistence.*;

@Table(name = "boot_type_info")
public class BootTypeInfo implements Serializable {
    /**
     * 自增id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 书籍类型code
     */
    @Column(name = "book_type_code")
    private String bookTypeCode;

    /**
     * 书籍类型名称
     */
    @Column(name = "book_type_name")
    private String bookTypeName;

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

    /**
     * 获取书籍类型名称
     *
     * @return book_type_name - 书籍类型名称
     */
    public String getBookTypeName() {
        return bookTypeName;
    }

    /**
     * 设置书籍类型名称
     *
     * @param bookTypeName 书籍类型名称
     */
    public void setBookTypeName(String bookTypeName) {
        this.bookTypeName = bookTypeName;
    }
}