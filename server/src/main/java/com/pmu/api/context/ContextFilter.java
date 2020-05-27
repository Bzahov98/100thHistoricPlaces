package com.pmu.api.context;

import com.pmu.util.CustomJwtTokenConverter;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@Component
@AllArgsConstructor
public class ContextFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {

        Context context = new Context();
        populateContext(context);
        ContextHolder.set(context);

        filterChain.doFilter(httpServletRequest, httpServletResponse);
        ContextHolder.clear();

    }

    private void populateContext(Context ctx) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getDetails() instanceof OAuth2AuthenticationDetails) {
            OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) authentication.getDetails();
            if (details != null && details.getDecodedDetails() != null) {
                Map<String, Object> decodedDetails = (Map<String, Object>) details.getDecodedDetails();
                ctx.setUserId(UUID.fromString(decodedDetails.get(CustomJwtTokenConverter.USER_ID).toString()));
                ctx.setEmail((String) decodedDetails.get(CustomJwtTokenConverter.EMAIL));
            }
        }
    }
}
