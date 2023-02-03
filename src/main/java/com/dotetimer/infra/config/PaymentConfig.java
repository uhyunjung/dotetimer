package com.dotetimer.infra.config;

public class PaymentConfig {
    public static final String adminKey = "c61a8719b6dd2c437e50b83727cfaacf";
    public static final String cid = "TC0ONETIME";
    public static final String partner_order_id = "dotetimer";
    public static final String partner_user_id = "dotetimer";
    public static final String pg_token = "XXXXXXXXXX";
    public static final String item_name = "premium";
    public static final int quantity = 1;
    public static final int total_amount = 3000;
    public static final int var_amount = 300;
    public static final int tax_free_amount = 0;
    public static final String approval_url = "http://localhost:8080/api/payment/success";
    public static final String fail_url = "http://localhost:8080/api/payment/fail";
    public static final String cancel_url = "http://localhost:8080/api/payment/cancel";
    public static final String HOST = "https://kapi.kakao.com";
    public static final String READY_URL = "/v1/payment/ready";
    public static final String APPROVE_URL = "/v1/payment/approve";
    public static final String ORDER_URL = "/v1/payment/order";
    public static final String CANCEL_URL = "/v1/payment/cancel";
}
