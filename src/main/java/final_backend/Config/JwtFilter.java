package final_backend.Config;

import final_backend.Member.service.UserService;
import final_backend.Utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
public class JwtFilter extends OncePerRequestFilter {

    private final UserService userService;
    private final String secretKey;


    // 권한 부여
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws SecurityException, ServletException, IOException {
       final String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
       log.info("authorization :{}", authorization);

       // 토큰이 없으면, 블럭
       if(authorization == null || !authorization.startsWith("Bearer ")){
           log.error("authorization을 잘못보냈습니다.");
           filterChain.doFilter(request, response);
           return;
       }

       // 토큰 꺼내기
        String token = authorization.split(" ")[1];

        // Token Expired 여부
        if(JwtUtil.isExpired(token, secretKey)){
            log.error("Token이 만료되었습니다.");
            filterChain.doFilter(request,response);
            return;
        }

        // nickName Token에서 꺼내기
        String nickName = JwtUtil.getNickName(token, secretKey);
        log.info("nickName:{}",nickName);

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(nickName, null, List.of(new SimpleGrantedAuthority("Normal")));
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(request,response);

    }
}
