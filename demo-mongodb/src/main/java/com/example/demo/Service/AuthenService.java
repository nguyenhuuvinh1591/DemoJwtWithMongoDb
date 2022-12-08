package com.example.demo.Service;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.Entity.UserAuthen;
import com.example.demo.Repository.AuthenRepository;

@Service
@Transactional(rollbackFor = Exception.class)
public class AuthenService {
    @Autowired
    private AuthenRepository authenRepository;

    public boolean isAccountValid(String username, String password) {
        if (StringUtils.isNotBlank(username) && StringUtils.isNotBlank(password)) {
            List<UserAuthen> data = authenRepository.findByUsername(username);
            if (CollectionUtils.isNotEmpty(data)) {
                for (UserAuthen userAuthen : data) {
                    if (username.equals(userAuthen.getUsername()) && password.equals(userAuthen.getPassword())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
