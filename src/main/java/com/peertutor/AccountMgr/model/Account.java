package com.peertutor.AccountMgr.model;

import com.peertutor.AccountMgr.model.enumeration.UserType;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "account")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "session_token", nullable = true)
    private String sessionToken;

    @Column(name = "password", nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_type", nullable = false)
    private UserType userType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSessionToken() {
        return sessionToken;
    }

    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Account account = (Account) o;
        if (account.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), account.getId()) &&
                Objects.equals(getName(), account.getName()) &&
                Objects.equals(getPassword(), account.getPassword()) &&
                Objects.equals(getSessionToken(), account.getSessionToken()) &&
                Objects.equals(getUserType(), account.getUserType());
    }
}
