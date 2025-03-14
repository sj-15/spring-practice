package com.jwtAuth.smp.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.*;


@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String firstName;
	private String lastName;
	private String email;
	private String phone;
	private String nationality;
	
	@JsonFormat(pattern = "dd/mm/yyyy")
	private LocalDate dob;
	private String gender;
	private String password;
	private String kvkNumber;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "users_to_roles", joinColumns = @JoinColumn(name="user_id"), inverseJoinColumns = @JoinColumn(name="role_id"))
	private Set<RoleEntity> roles = new HashSet<>();

}
