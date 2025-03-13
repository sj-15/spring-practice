package com.jwtAuth.generic.service;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.jwtAuth.generic.entity.UserEntity;


@Getter
@Setter
public class UserDetailsImpl implements UserDetails {
	
	private Long id;
	private String firstName;
	private String lastName;
	private String email;
	private String phone;
	private String nationality;
	private LocalDate dob;
	private String gender;
	private String password;
	private String kvkNumber;
	
	private Collection<? extends GrantedAuthority> authorities;
	
	
	
	public UserDetailsImpl(Long id, String firstName, String lastName, String email, String phone, String nationality,
			LocalDate dob, String gender, String password, String kvkNumber,
			Collection<? extends GrantedAuthority> authorities) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.phone = phone;
		this.nationality = nationality;
		this.dob = dob;
		this.gender = gender;
		this.password = password;
		this.kvkNumber = kvkNumber;
		this.authorities = authorities;
	}

	public static UserDetailsImpl build(UserEntity user) {
		List<GrantedAuthority> authorities = user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName().name())).collect(Collectors.toList());
		return new UserDetailsImpl(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getPhone(), user.getNationality(), user.getDob(), user.getGender(), user.getPassword(), user.getKvkNumber(), authorities);
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return email;
	}
}
