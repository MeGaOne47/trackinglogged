package com.example.demo.controller;

import com.example.demo.entity.UserTracking;
import com.example.demo.repository.IUserTrackingRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.RequestMapping;

import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/users/tracking")
public class UserTrackingController {
    @Autowired
    private IUserTrackingRepository userTrackingRepository;

    @GetMapping
    public String getUser(Authentication authentication, Model model, HttpServletRequest request) {
        String username = authentication.getName();
        LocalDateTime accessTime = LocalDateTime.now();
        String ipAddress = request.getHeader("X-Forwarded-For");
        if (ipAddress == null) {
            ipAddress = request.getRemoteAddr();
        }
        try {
            InetAddress inetAddress = InetAddress.getByName(ipAddress);
            if (inetAddress instanceof Inet6Address) {
                ipAddress = null;
            } else {
                ipAddress = inetAddress.getHostAddress();
            }
        } catch (UnknownHostException ex) {
            ipAddress = null;
        }
        UserTracking userTracking = new UserTracking(username, ipAddress, accessTime);
        userTrackingRepository.save(userTracking);
        List<String> roles = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        model.addAttribute("username", username);
        model.addAttribute("roles", roles);
        model.addAttribute("accessTime", accessTime);
        model.addAttribute("ipAddress", ipAddress);
        return "user/users";
    }

}
