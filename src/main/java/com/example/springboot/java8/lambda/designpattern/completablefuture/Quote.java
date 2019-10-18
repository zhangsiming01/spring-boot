package com.example.springboot.java8.lambda.designpattern.completablefuture;

/**
 * @author zt
 * @Auther: zhangsiming
 * @Date: 2019-09-29 10:33
 * @Description: 实现折扣服务
 */
public class Quote {

    private final String shopName;

    private final double price;

    private final Discount.Code discountCode;

    public Quote(String shopName, double price, Discount.Code discountCode) {
        this.shopName = shopName;
        this.price = price;
        this.discountCode = discountCode;
    }

    public static Quote parse(String str) {
        String[] split = str.split(":");
        String shopName = split[0];
        double price = Double.parseDouble(split[1]);
        Discount.Code code = Discount.Code.valueOf(split[2]);
        return new Quote(shopName, price, code);
    }

    public String getShopName() {
        return shopName;
    }

    public double getPrice() {
        return price;
    }

    public Discount.Code getDiscountCode() {
        return discountCode;
    }


}
