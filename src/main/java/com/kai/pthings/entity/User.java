package com.kai.pthings.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "user")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class User implements UserDetails {

    @Id
    @Column(name = "id",unique = true)
    private String id;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name ="role_user",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles;

    @OneToMany(targetEntity = Article.class, mappedBy = "author", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Article> articles;

    @Column(name = "name")
    private String name;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "username", unique = true)
    private String uname;

    @Column(name = "joined_at", updatable = false)
    private String joined_at;

    @Column(name = "day_of_birth")
    private String DayOfBirth;

    @JsonIgnore
    private String upassword;

    private boolean active;

    @Column(length = 512)
    private String profile_image;

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<Role> roles = getRoles();
        List<SimpleGrantedAuthority> authorities =new ArrayList<>();
        for (Role role : roles){
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        return authorities;
    }

    @Override
    @JsonIgnore
    public String getPassword() {
        return this.getUpassword();
    }

    @Override
    @JsonIgnore
    public String getUsername() {
        return this.getUname();
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return isActive();
    }
}
