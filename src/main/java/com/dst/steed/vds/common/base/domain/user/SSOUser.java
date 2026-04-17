package com.dst.steed.vds.common.base.domain.user;

import lombok.Data;

@Data
public class SSOUser {
    private Long id;
    private String username;
    private String realname;
    private String jobNumber;
}
