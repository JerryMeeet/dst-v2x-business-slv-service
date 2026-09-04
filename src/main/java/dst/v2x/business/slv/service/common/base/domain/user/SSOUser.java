package dst.v2x.business.slv.service.common.base.domain.user;

import lombok.Data;

@Data
public class SSOUser {
    private Long id;
    private String username;
    private String realname;
    private String jobNumber;
}
