package com.example.webapi.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name="utilisateurs", uniqueConstraints = {
		@UniqueConstraint(columnNames = "username"),
		@UniqueConstraint(columnNames = "email")
})
@EntityListeners(AuditingEntityListener.class)
public class User implements Serializable, UserDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@NotBlank
	@Size(max=40)
	private String name;
	
	@NotBlank
	@Size(max=40)
	private String email;	
	
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "utilisateurs_roles",
		joinColumns = @JoinColumn(name="utilsateur_id"),
		inverseJoinColumns = @JoinColumn(name = "role_id")
	)
	private Set<Role> roles = new HashSet<>();


	public User() {
		super();
	}


	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}

	public Set<Role> getRoles() {
		return roles;
	}


	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}


	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return null;
	}

}
