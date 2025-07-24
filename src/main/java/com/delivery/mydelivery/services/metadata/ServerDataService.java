package com.delivery.mydelivery.services.metadata;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Service
public class ServerDataService {
    public String getServerFullAddress() {
        ServletRequestAttributes attributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) return null;
        HttpServletRequest request = attributes.getRequest();
        String serverName = request.getServerName();
        int port = request.getServerPort();
        String protocol = request.getScheme();
        return protocol + "://" + serverName + ":" + port;
    }
}
