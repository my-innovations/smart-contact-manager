package com.contactmanager.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name = "USERS")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@NotBlank(message = "{NotBlank.user.name}")
	@Size(min = 4, max = 20, message = "{Size.user.name}")
	// @NotBlank(message="Name should not br empty")
	// @Size(min=4,max=20,message="Name should be min {min} and max {max}
	// characters")
	private String name;

	@Column(unique = true)
	@NotBlank
	private String email;

	@NotBlank(message = "Password should not br empty")
	@Size(min = 4, max = 20, message = "Password should be min 4 and max 20 characters")
	private String password;

	@Column(name = "role")
	private String roll;

	private boolean enabled;

	private String img;

	@Column(length = 500)
	private String about;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
	// OR
	/*
	 * @OneToMany(cascade = CascadeType.ALL) /@JoinColumn(name = "userId")
	 */
	private List<Contact> contacts = new ArrayList<>();

}
