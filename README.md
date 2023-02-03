<div align="center">
  <h1>도트타이머 백엔드</h1>
</div>
<br />

## 목차
1. [**프로젝트 소개**](#1)
1. [**기술 스택**](#2)
1. [**주요 기능**](#3)
1. [**ER 다이어그램**](#4)
1. [**API 명세서**](#5)
1. [**기간 및 일정**](#6)
1. [**실행 방법**](#7)
1. [**기술 블로그 및 고민 흔적들**](#8)
1. [**디렉토리 구조**](#9)

<br />

<div id="1"></div>

## ❤️ 프로젝트 소개
> 시간 관리 플래너 어플 [**도트타이머**](https://dotetimer.com/%eb%8f%84%ed%8a%b8%ed%83%80%ec%9d%b4%eb%a8%b8-%ed%83%80%ec%9e%84-%ed%8a%b8%eb%9e%98%ed%82%b9-%ec%8b%9c%ea%b0%84-%ea%b4%80%eb%a6%ac-%ed%96%a5%ec%83%81-%ec%96%b4%ed%94%8c/)의 백엔드를 클론코딩한 프로젝트입니다.
<br />

<div id="2"></div>

## ⚙️ 기술 스택
![](https://img.shields.io/badge/Spring%20Boot-3.0.0-deepgreen)
![](https://img.shields.io/badge/Spring%20Data%20JPA-3.0.0-green)
![](https://img.shields.io/badge/Spring%20Security-3.0.0-green)
![](https://img.shields.io/badge/QueryDSL-5.0.0-blue)
![](https://img.shields.io/badge/MapStruct-1.5.3-red)
![](https://img.shields.io/badge/Lombok-1.18.24-red)
![](https://img.shields.io/badge/MySQL-8.0.27-blue)
<br /><br />

<div id="3"></div>

## 📲 주요 기능

| <div align="center"/>기능 | <div align="center"/>과정 |
| :------------------------ | :-------------------------------------------------------------------- |
| <div align="center"/>회원가입 및 로그인|- Jwt 구현하여 Access Token과 Refresh Token 발급하여 구현하였다.|
| <div align="center"/>그룹 생성 및 참가|- 그룹 테이블과 그룹참가 테이블을 분리하여 n:m 연관관계를 매핑하였다.<br/>- 그룹장 탈퇴 시 그룹 삭제되도록 구현하였다.|
| <div align="center"/>할일 및 한일 기록|- 싱글 테이블 전략으로 같은 테이블에 기록하고, record 속성으로 할일과 한일을 구분해 저장하였다.|
| <div align="center"/>하루한줄 기록|- 하루한줄 테이블과 하루한줄 좋아요 테이블을 분리하여 n:m 연관관계를 매핑하였다.<br/>- 내가 작성한 리뷰 리스트 및 타인이 작성한 리뷰 리스트 분리하여 반환하였다.|
| <div align="center"/>사용자 팔로우|- 파라미터에 따라 팔로우 및 팔로우 취소하도록 구현하였다.|
| <div align="center"/>프리미엄 결제|- 카카오페이 API를 이용해 단건결제 기능을 구현하였다.<br/>- restTemplate을 이용하여 API URI에 접속하였다.|
| <div align="center"/>사용자 및 그룹 검색|- QueryDSL 구현하여 커스텀 쿼리를 작성하였다.|
| <div align="center"/>날짜별, 그룹별 시간 통계|- treeMap 자료구조 이용 및 검색 후 정렬하여 통계 결과를 반환하였다.|

<br />

<div id="4"></div>

## 🗄️ [ER 다이어그램](https://www.erdcloud.com/d/g5AyQBakXYHyB3jng)
| <div align="center"/>ERD| 
| :------------------------ |
| <div align="center"/><img src="https://user-images.githubusercontent.com/67186222/215780795-001f5738-4d03-42c8-9b7b-63bbd142e139.png" alt="architecture" width="900px" height="600px"/>
<br />

<div id="5"></div>

## 📽️ [API 명세서](https://www.postman.com/yhyeonjg/workspace/my-workspace/api/2c1e9812-297a-4dc5-8206-8a9018530a36)

<br /><br />

<div id="6"></div>

## 📅 기간 및 일정
**2022.9~2023.2**
<br /><br />

<div id="7"></div>

## 💡 실행 방법
### 개발환경
**1. 원격 저장소 복제**

```bash
$ git clone https://github.com/yhyeonjg/dotetimer.git
```
**2. build.gradle**
> build.gradle에서 Ctrl+Shift+O 실행
<br/>

<div id="8"></div>

## 🖥️ 기술 블로그
🔗[**1. ERD 모델 만들기**](https://whyou-story.tistory.com/244)<br />
🔗[**2. 스프링부트에 MySQL 연동**](https://whyou-story.tistory.com/251)<br />
🔗[**3. API 명세서 작성**](https://whyou-story.tistory.com/270)<br />
🔗[**4. JPA 폴더링 및 회원가입 구현**](https://whyou-story.tistory.com/278)<br />
🔗[**5. Spring Security 추가 및 Jwt 구현**](https://whyou-story.tistory.com/281)<br />
🔗[**6. 커스텀 예외처리**](https://whyou-story.tistory.com/282)<br />
🔗[**7. JPA 객체 지향 쿼리(QueryDSL)**](https://whyou-story.tistory.com/283)<br />
🔗[**8. MapStruct**](https://whyou-story.tistory.com/284)<br />
🔗[**9. 영속성 컨텍스트**](https://whyou-story.tistory.com/299)<br />
🔗[**10.스프링 계층별 특징**](https://whyou-story.tistory.com/300)<br />
🔗[**11. 카카오페이 API 단건결제**](https://whyou-story.tistory.com/301)<br />
🔗[**12. 에러 해결 흔적들**](https://whyou-story.tistory.com/303)<br />
🔗[**13. 회고**](https://whyou-story.tistory.com/305)

<br/><br />

<div id="9"></div>

## 📂 디렉토리 구조

```
dotetimer
├── src/
│   ├── main/
│   │   └── java.com.dotetimer/
│   │   │    ├── infra/
│   │   │    │   ├── config/                                                          - 설정
│   │   │    │   │   └── SecurityConfig, OpenEntityManagerConfig, QueryDSLConfig, PaymentConfig
│   │   │    │   ├── exception/                                                       - 에러 및 예외 처리
│   │   │    │   │   └── CustomException, ErrorCode, ErrorResponse, GeneralExceptionHandler
│   │   │    │   ├── jwt/                                                             - Jwt 관련
│   │   │    │   │   └── JwtTokenProvider, JwtAuthenticationEntryPoint, JwtAuthenticationFilter, JwtAccessDeniedHandler
│   │   │    │   ├── mapper/                                                          - MapStruct 관련
│   │   │    │   │   └── GroupMapper, PlanMapper, ProfileMapper, ReviewMapper, StatMapper
│   │   │    │   └── Green, Theme                                                     - Enum 클래스
│   │   │    ├── domain/                                                              - Domain 클래스
│   │   │    │   └── Coin, Donate, Follow, GroupJoin, Plan, PlanInfo, Review, ReviewLike, StudyGroup, User
│   │   │    ├── repository/                                                          - Repositiory 클래스
│   │   │    │   └── CoinRepository, DonateRepository, FollowRepository, GroupJoinRepository, PlanInfoRepository, PlanRepository, ReviewLikeRepository, ReviewRepository, StudyGroupRepository, UserRepository
│   │   │    │   └── QueryDSLRepository, QueryDSLRepositoryImpl
│   │   │    ├── dto/                                                                 - DTO 클래스
│   │   │    │   └── GroupDto, PaymentDto, PlanDto, ProfileDto, ReviewDto, StatDto, UserDto
│   │   │    ├── service/                                                             - Service 클래스
│   │   │    │   └── GroupService, PaymentService, PlanService, ProfileService, ReviewService, StatService, UserService
│   │   │    │   └── CustomUserDetailsService, SearchService
│   │   │    ├── controller/                                                          - Controller 클래스
│   │   │    │   └── GroupController, PaymentController,  PlanController, ProfileController, ReviewController, StatController, UserController
│   │   │    │   └── SearchController
│   │   │    └── DotetimerApplication                                                 - Main 함수
│   │   │
│   │   └── resources/
│   │        ├── application.properties                                               - SQL 설정
│   │        ├── schema.sql                                                           - DB 스키마
│   │        └── data.sql                                                             - 데이터 생성
│   │
│   └── test/
│       └── java.com.dotetimer/
│            ├── dto/                                                                 - DTO Test 클래스
│            │   └── GroupDtoTest, PlanDtoTest, UserDtoTest.java
│            ├── service/                                                             - Service Test 클래스
│            │   └── PlanServiceTest, StatServiceTest, UserServiceTest.java
│            ├── controller/                                                          - Controller Test클래스
│            │   └── UserControllerTest
│            └── DotetimerApplicationTests
├── build.gradle
├── .gitignore
└── README.md
```
<br/>
