package com.peertutor.AccountMgr.service.dto;

import com.peertutor.AccountMgr.model.enumeration.UserType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.peertutor.AccountMgr.model.Account} entity.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountDTO implements Serializable {

    private Long id;

    private String name;

    private String sessionToken;

    private String password;

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

    public String getSessionToken() {
        return sessionToken;
    }

    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

        AccountDTO accountDTO = (AccountDTO) o;
        if (accountDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), accountDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AccountDTODTO{" +
                "id=" + getId() +
                ", name='" + getName() + "'" +
                "}";
    }

}
