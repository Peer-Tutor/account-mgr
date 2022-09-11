package com.peertutor.AccountMgr.model.viewmodel.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class AuthenticationReq {
    @NotNull
    @NotEmpty
    public String name;

    @NotNull
    @NotEmpty
    public String sessionToken;
}
