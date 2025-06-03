package com.myproject.cardshop.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {

	public static final String SUCCESS_CODE = "200";
	public static final String FAIL_CODE = "400";
	public static final String USER_NO_LOGIN = "401";

	private String code;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String msg;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private T data;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Long total;

	// ===== 成功回應 =====

	public static <T> ApiResponse<T> success(String msg, T data, Long total) {
		return new ApiResponse<>(SUCCESS_CODE, msg != null ? msg : "操作成功", data, total);
	}

	public static <T> ApiResponse<T> success(String msg, T data) {
		return success(msg, data, null);
	}

	public static <T> ApiResponse<T> success(String msg) {
		return success(msg, null);
	}

	public static <T> ApiResponse<T> success(T data) {
		return success(null, data);
	}

	public static <T> ApiResponse<T> success() {
		return success(null, null); // msg == null 時會自動補「操作成功」
	}

	// ===== 失敗回應 =====

	public static <T> ApiResponse<T> fail(String msg, T data, Long total) {
		return new ApiResponse<>(FAIL_CODE, msg != null ? msg : "操作失敗", data, total);
	}

	public static <T> ApiResponse<T> fail(String msg, T data) {
		return fail(msg, data, null);
	}

	public static <T> ApiResponse<T> fail(String msg) {
		return fail(msg, null);
	}

	public static <T> ApiResponse<T> fail(T data) {
		return fail(null, data);
	}

	public static <T> ApiResponse<T> fail() {
		return fail(null, null); // msg == null 時會補「操作失敗」
	}

	// ===== 未登入 =====
	public static <T> ApiResponse<T> noLogin() {
		return new ApiResponse<>(USER_NO_LOGIN, "使用者尚未登入!", null, null);
	}
}
