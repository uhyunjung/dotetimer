package com.dotetimer.service;

import com.dotetimer.domain.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Service
@RequiredArgsConstructor
@Transactional
public class ProfileService {
    private final String HOST = "https://kapi.kakao.com";
    private final String READY_URL = "/v1/payment/ready";
    private final String APPROVE_URL = "/v1/payment/approve";
    private final String CANCEL_URL = "/v1/payment/cancel";

    public void updatePremium(User user, String status) {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> httpEntity;
        URI targetUrl;

        //return restTemplate.exchange(targetUrl, HttpMethod.POST, httpEntity, ).getBody();
    }
}
