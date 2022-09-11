package com.peertutor.AccountMgr.service.dto;

import com.peertutor.AccountMgr.model.enumeration.UserType;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.LongFilter;

import java.io.Serializable;
import java.util.Objects;

/**
 * Criteria class for the {@link com.peertutor.AccountMgr.model.Account} entity. This class is used
 * to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /households?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class AccountCriteria implements Serializable, Criteria {
    private LongFilter id;
    private UserTypeFilter userType;

    public AccountCriteria() {
    }

    public AccountCriteria(AccountCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.userType = other.userType == null ? null : other.userType.copy();
    }

    @Override
    public AccountCriteria copy() {
        return new AccountCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public UserTypeFilter getUserType() {
        return userType;
    }

    public void setHousingType(UserTypeFilter userType) {
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
        final AccountCriteria that = (AccountCriteria) o;
        return
                Objects.equals(id, that.id) &&
                        Objects.equals(userType, that.userType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                id,
                userType
        );
    }

    @Override
    public String toString() {
        return "AccountCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (userType != null ? "housingType=" + userType + ", " : "") +
                "}";
    }

    /**
     * Class for filtering HousingType
     */
    public static class UserTypeFilter extends Filter<UserType> {

        public UserTypeFilter() {
        }

        public UserTypeFilter(UserTypeFilter filter) {
            super(filter);
        }

        @Override
        public UserTypeFilter copy() {
            return new UserTypeFilter(this);
        }

    }

}
