package com.peertutor.AccountMgr.model.viewmodel.response;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class AccountRegistrationRes {
    @NotNull
    @NotEmpty
    public String name;

    @NotNull
    @NotEmpty
    public String sessionToken;

    @NotNull
    @NotEmpty
    public String usertype;
}
