package com.naixue.interceptor;

import com.naixue.common.Result;
import com.naixue.utils.JwtUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * JWT拦截器
 *
 * 用于验证请求头中的JWT Token
 * 将解析出的会员ID存入request属性中
 *
 * Token格式: Authorization: Bearer <token>
 *
 * @author naixue-backend
 * @version 1.0.0
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class JwtInterceptor implements HandlerInterceptor {

    /** JWT工具类 */
    private final JwtUtils jwtUtils;

    /** JSON序列化工具 */
    private final ObjectMapper objectMapper;

    /**
     * 请求预处理
     *
     * 验证JWT Token并解析会员ID
     *
     * @param request HTTP请求
     * @param response HTTP响应
     * @param handler 处理器
     * @return 是否放行
     */
    @Override
    public boolean preHandle(HttpServletRequest request,
                            HttpServletResponse response,
                            Object handler) throws Exception {

        // OPTIONS请求直接放行 (预检请求)
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            return true;
        }

        // 获取Authorization请求头
        String authHeader = request.getHeader("Authorization");

        // 验证Token格式
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            sendError(response, 2001, "请先登录");
            return false;
        }

        // 提取Token
        String token = authHeader.substring(7);

        // 解析Token获取会员ID
        Long memberId = jwtUtils.getMemberId(token);

        if (memberId == null) {
            sendError(response, 2002, "登录已过期");
            return false;
        }

        // 将会员ID存入请求属性,供Controller使用
        request.setAttribute("memberId", memberId);

        return true;
    }

    /**
     * 发送错误响应
     *
     * @param response HTTP响应
     * @param code 错误码
     * @param message 错误消息
     */
    private void sendError(HttpServletResponse response, int code, String message) throws Exception {
        response.setStatus(401);
        response.setContentType("application/json;charset=UTF-8");
        Result<Void> result = Result.error(code, message);
        response.getWriter().write(objectMapper.writeValueAsString(result));
    }
}
