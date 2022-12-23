package com.dotetimer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// security 사용 시 id : user/password : 콘솔창에 나오는 비밀번호 입력
@SpringBootApplication
public class DotetimerApplication {

	public static void main(String[] args) {
		SpringApplication.run(DotetimerApplication.class, args);
	}

}
