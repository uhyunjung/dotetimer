package com.dotetimer.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/* Exception
- RuntimeException
400 NullPointException
400 IllegalArgumentException
400 IllegalStateException
400 MethodArgumentNotValidException
404 NotFoundException -> Service
404 NoHandlerFoundException
405 HttpRequestMethodNotSupportedException
415 HttpMediaTypeException
500 Exception
500 RuntimeException
ConstraintViolationException
MissingArgumentTypeMismatchException
DateTimeParseException

- Custom
401 UnauthorizedException -> Controller
401 AccessDeniedException
401 AuthenticationEntryPoint
403 AccessDeniedHandler
404 NotFoundDataException
DuplicateException
NoSuchDataException
InvalidReqParamException
InvalidReqBodyException
S3Exception
*/

@Getter
@AllArgsConstructor
public enum ErrorCode {
    // 400 BAD_REQUEST : 잘못된 요청
    INVALID_REFRESH_TOKEN(HttpStatus.BAD_REQUEST, "refresh token이 유효하지 않습니다"),
    MISMATCH_REFRESH_TOKEN(HttpStatus.BAD_REQUEST, "refresh token의 유저 정보가 일치하지 않습니다"),
    CANNOT_FOLLOW_MYSELF(HttpStatus.BAD_REQUEST, "자기 자신은 팔로우할 수 없습니다"),

    // 401 UNAUTHORIZED : 인증되지 않은 사용자
    INVALID_AUTH_TOKEN(HttpStatus.UNAUTHORIZED, "인증 정보가 없는 토큰입니다"), // 토큰 잘못된 경우(시그니처 불일치)
    EXPIRE_AUTH_TOKEN(HttpStatus.UNAUTHORIZED, "만료된 토큰입니다"), // 토큰 만료된 경우

    // 403 FORBIDDEN : 권한 제한 사용자
    FORBIDDEN(HttpStatus.FORBIDDEN, "권한이 없는 요청입니다"),

    // 404 NOT_FOUND : Resource를 찾을 수 없음
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 사용자를 찾을 수 없습니다"),
    PASSWORD_NOT_FOUND(HttpStatus.NOT_FOUND, "비밀번호가 틀렸습니다"),
    REFRESH_TOKEN_NOT_FOUND(HttpStatus.NOT_FOUND, "refresh token이 만료되었습니다"),
    NOT_FOLLOW(HttpStatus.NOT_FOUND, "팔로우 중이지 않습니다"),

    // 409 CONFLICT : Resource 의 현재 상태와 충돌. 보통 중복된 데이터 존재
    DUPLICATE_RESOURCE(HttpStatus.CONFLICT, "이미 존재하는 데이터입니다");
    private final HttpStatus httpStatus;
    private final String detail;
}