package com.dotetimer.service;

import com.dotetimer.domain.User;
import com.dotetimer.dto.PaymentDto.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.nio.charset.StandardCharsets;

import static com.dotetimer.infra.config.PaymentConfig.*;

@Service
@RequiredArgsConstructor
@Transactional
public class PaymentService {
    String tid;
    @Transactional
    public ReadyPaymentDto readyPayment(User user) {
        RestTemplate restTemplate = new RestTemplate();

        // Header 세팅
        HttpHeaders httpHeaders = setHeaders();

        // Body 세팅
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("cid", cid);
        params.add("partner_order_id", partner_order_id);
        params.add("partner_user_id", partner_user_id);
        params.add("item_name", item_name);
        params.add("quantity", String.valueOf(quantity));
        params.add("total_amount", String.valueOf(total_amount));
        params.add("var_amount", String.valueOf(var_amount));
        params.add("tax_free_amount", String.valueOf(tax_free_amount));
        params.add("approval_url", approval_url+"?"+pg_token);
        params.add("fail_url", fail_url);
        params.add("cancel_url", cancel_url);
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<MultiValueMap<String, String>>(params, httpHeaders);

        URI targetUrl = UriComponentsBuilder
                .fromUriString(HOST+READY_URL)
                .encode(StandardCharsets.UTF_8)
                .build().toUri(); // uri component, session, queryParam
        tid = restTemplate.exchange(targetUrl, HttpMethod.POST, requestEntity, ReadyPaymentDto.class).getBody().getTid();
        return restTemplate.exchange(targetUrl, HttpMethod.POST, requestEntity, ReadyPaymentDto.class).getBody();
    }

    @Transactional
    public ApprovePaymentDto approvePayment(User user) {
        RestTemplate restTemplate = new RestTemplate();

        // Header 세팅
        HttpHeaders httpHeaders = setHeaders();

        // Body 세팅
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("cid", cid);
        params.add("tid", tid);
        params.add("partner_order_id", partner_order_id);
        params.add("partner_user_id", partner_user_id);
        params.add("pg_token", pg_token);
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<MultiValueMap<String, String>>(params, httpHeaders);

        // Premium 변경
        user.updatPremium(true);

        URI targetUrl = UriComponentsBuilder
                .fromUriString(HOST+APPROVE_URL)
                .encode(StandardCharsets.UTF_8)
                .build().toUri();
        return restTemplate.exchange(targetUrl, HttpMethod.POST, requestEntity, ApprovePaymentDto.class).getBody();
    }

    @Transactional
    public OrderPaymentDto orderPayment(User user) {
        RestTemplate restTemplate = new RestTemplate();

        // Header 세팅
        HttpHeaders httpHeaders = setHeaders();

        // Body 세팅
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("cid", cid);
        params.add("tid", tid);
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<MultiValueMap<String, String>>(params, httpHeaders);

        URI targetUrl = UriComponentsBuilder
                .fromUriString(HOST+ORDER_URL)
                .encode(StandardCharsets.UTF_8)
                .build().toUri();
        return restTemplate.exchange(targetUrl, HttpMethod.POST, requestEntity, OrderPaymentDto.class).getBody();
    }

    @Transactional
    public CancelPaymentDto cancelPayment(User user) {
        RestTemplate restTemplate = new RestTemplate();

        // Header 세팅
        HttpHeaders httpHeaders = setHeaders();

        // Body 세팅
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("cid", cid);
        params.add("tid", tid);
        params.add("cancel_amount", String.valueOf(total_amount));
        params.add("cancel_tax_free_amount", String.valueOf(tax_free_amount));
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<MultiValueMap<String, String>>(params, httpHeaders);

        // Premium 변경
        user.updatPremium(false);

        URI targetUrl = UriComponentsBuilder
                .fromUriString(HOST+CANCEL_URL)
                .encode(StandardCharsets.UTF_8)
                .build().toUri();
        return restTemplate.exchange(targetUrl, HttpMethod.POST, requestEntity, CancelPaymentDto.class).getBody();
    }

    private HttpHeaders setHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "KakaoAK " + adminKey);
        httpHeaders.add("Accept", MediaType.APPLICATION_JSON_UTF8_VALUE);
        httpHeaders.add("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE + ";charset=UTF-8");
        return httpHeaders;
    }
}
