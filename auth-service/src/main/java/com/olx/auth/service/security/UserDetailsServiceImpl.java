package com.olx.auth.service.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.olx.auth.service.businesslogic.UserManager;

@Service   
public class UserDetailsServiceImpl implements UserDetailsService  {

	@Autowired
	private UserManager userManager;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {		
		
		com.olx.auth.service.models.User user = userManager.getUserByEmail(username);		
		
		if(user != null) {
			List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                	.commaSeparatedStringToAuthorityList("ROLE_" + user.getRole());
			return new User(user.getEmail(), user.getPassword(), grantedAuthorities);
		}
		
		throw new UsernameNotFoundException("Username: " + username + " not found");
	}
}