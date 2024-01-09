package com.notyficationsystem.NotyficationSystem.model;
import jakarta.persistence.*;
import lombok.*;
import com.notyficationsystem.NotyficationSystem.model.constant.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Getter
@Setter
@ToString
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fullname", nullable = false)
    private String fullname;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Enumerated(value = EnumType.ORDINAL)
    @Column(name = "role", nullable = false)
    private Role role;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "enabled", nullable = false)
    private boolean enabled;

    @Column(name = "tg_chat_id")
    private Long chatId;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private Set<TelegramContact> telegramContacts = new HashSet<>();

    public User(String fullname, String email, Role role, String password, Long chatId) {
        this.fullname = fullname;
        this.email = email;
        this.role = role;
        this.password = password;
        this.chatId = chatId;
    }

    public User(String fullname, String email, Role role, String password) {
        this.fullname = fullname;
        this.email = email;
        this.role = role;
        this.password = password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User person = (User) o;
        return Objects.equals(id, person.id) ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fullname, email);
    }

}
