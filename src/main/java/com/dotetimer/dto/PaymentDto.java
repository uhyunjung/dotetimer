package com.dotetimer.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate; // java.util.Date

public class PaymentDto {
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @NotNull
    public static class ReadyPaymentDto {
        private String tid;
        private String next_redirect_app_url;
        private String next_redirect_mobile_url;
        private String next_redirect_pc_url;
        private String android_app_scheme;
        private String ios_app_scheme;
        private String created_at;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ApprovePaymentDto {
        private String aid, tid, cid, sid;
        private String partner_order_id;
        private String partner_user_id;
        private String payment_method_type;
        private String item_name, item_code;
        private Amount amount;
        private CardInfo card_info;
        private Integer quantity;
        private LocalDate created_at, approved_at;
        private String payload;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class OrderPaymentDto {
        private String tid, cid;
        private String status;
        private String partner_order_id, partner_user_id;
        private String payment_method_type;
        private String item_name;
        private Integer quantity;
        private Amount amount;
        private CanceledAmount canceled_amount;
        private CancelAvailableAmount canceled_available_amount;

        private LocalDate created_at, approved_at;
        private PaymentActionDetails[] payment_action_details;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class CancelPaymentDto {
        private String aid, tid, cid;
        private String status;
        private String partner_order_id, partner_user_id;
        private String payment_method_type;
        private String item_name;
        private Integer quantity;
        private Amount amount;
        private ApprovedCancelAmount approved_cancel_amount;
        private CanceledAmount canceled_amount;
        private CancelAvailableAmount canceled_available_amount;
        private LocalDate created_at, approved_at, canceled_at;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    private static class Amount{
        int total, tax_free, vat, point, discount, green_deposit;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    private static class ApprovedCancelAmount{
        int total, tax_free, vat, point, discount, green_deposit;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    private static class CanceledAmount{
        int total, tax_free, vat, point, discount, green_deposit;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    private static class CancelAvailableAmount{
        int total, tax_free, vat, point, discount, green_deposit;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    private static class CardInfo{
        String purchase_corp, purchase_corp_code, issuer_corp, issuer_corp_code, kakaopay_purchase_corp, kakaopay_purchase_corp_code, kakaopay_issuer_corp, kakaopay_issuer_corp_code;
        String bin, card_type, install_month, approved_id, card_mid, interest_free_install, card_item_code;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    private static class PaymentActionDetails {
        private String aid;
        private String payment_action_type;
        private String payment_method_type;
        private int amount;
        private int point_amount;
        private int discount_amount;
        private LocalDate approved_at;
        private int green_deposit;
    }
}
