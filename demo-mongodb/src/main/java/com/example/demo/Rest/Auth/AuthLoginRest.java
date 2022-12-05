package com.example.demo.Rest.Auth;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Entity.UserAuthen;
import com.example.demo.Service.AuthenService;
import com.example.demo.base.AbstractRest;
import com.example.demo.common.DtsApiResponse;
import com.example.demo.constant.CommonConstant;
import com.example.demo.exception.DetailException;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RestController
public class AuthLoginRest extends AbstractRest {
	

	@Autowired
	private AuthenService authenService;

	@PostMapping("login")
	public DtsApiResponse login(@RequestBody UserAuthen userAuthen) {
		long start = System.currentTimeMillis();
		try {
			String md5Hex = DigestUtils.md5Hex(userAuthen.getPassword()).toUpperCase();
			UserAuthen UserAuthen = null;
			if (authenService.isAccountValid(userAuthen.getUsername(), md5Hex)) {
				UserAuthen = new UserAuthen();
				String token = getJWTToken(userAuthen.getUsername());
				UserAuthen.setToken(token);
			} else {
				DetailException de = new DetailException(CommonConstant.E90006_USER_NOT_EXITS_OR_PASSWORD_NOT_CORRECT);
				return this.errorHandler.handlerException(de, start);
			}
			return successHandler.handlerSuccess(UserAuthen, start);
		} catch (Exception e) {
			DetailException de = new DetailException(CommonConstant.E90006_USER_NOT_EXITS_OR_PASSWORD_NOT_CORRECT);
			return this.errorHandler.handlerException(de, start);
		}
	}

	private String getJWTToken(String UserAuthenname) {
		String secretKey = "mySecretKey";
		List<GrantedAuthority> grantedAuthorities = AuthorityUtils
				.commaSeparatedStringToAuthorityList("ROLE_UserAuthen");

		String token = Jwts.builder().setId("softtekJWT").setSubject(UserAuthenname)
				.claim("authorities",
						grantedAuthorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 600000))
				.signWith(SignatureAlgorithm.HS512, secretKey.getBytes()).compact();

		return "Bearer " + token;
	}
}
