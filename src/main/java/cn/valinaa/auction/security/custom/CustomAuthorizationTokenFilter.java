package cn.valinaa.auction.security.custom;

import cn.valinaa.auction.utils.JwtUtil;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.jose4j.jwt.MalformedClaimException;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.jose4j.lang.JoseException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Valinaa
 * @Date : 2022/7/23
 * @Description : 自定义验证token
 */
@RequiredArgsConstructor
public class CustomAuthorizationTokenFilter extends OncePerRequestFilter {
    
    private final UserDetailsService userDetailsService;
    
    private final JwtUtil jwtUtil;
    /**
     * JWT存储的请求头
     */
    private static final String TOKEN_HEADER = "Authorization";
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull FilterChain filterChain) throws ServletException, IOException {
        // 获取Header
        String authToken = request.getHeader(TOKEN_HEADER);
        // 存在token但不是tokenHead开头
        // if (null != authHeader && authHeader.startsWith(TOKEN_HEAD)) {
        // 字段截取authToken
        // String authToken = authHeader.substring(TOKEN_HEAD.length());
        
        // 没有token,直接放行
        if (authToken == null || "".equals(authToken)) {
            System.out.println("authToken is null come from TokenFilter");
            filterChain.doFilter(request, response);
            return;
        }
        // 根据authToken获取username
        String username;
        try {
            username = jwtUtil.getUsernameByToken(authToken);
        } catch (InvalidJwtException | MalformedClaimException e) {
            throw new RuntimeException(e);
        }
        // token存在用户名但未登录
        if (null != username && null == SecurityContextHolder.getContext().getAuthentication()) {
            // 登录
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            // 验证token是否有效，如果有效，将他重新放到用户对象里。
            try {
                if (null != jwtUtil.verifyToken(authToken, userDetails)) {
                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    // 重新设置到用户对象里
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            } catch (MalformedClaimException | JoseException e) {
                throw new RuntimeException(e);
            }
        }
        // 放行
        filterChain.doFilter(request, response);
    }
}
