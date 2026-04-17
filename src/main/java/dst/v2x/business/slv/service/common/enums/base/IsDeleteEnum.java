package dst.v2x.business.slv.service.common.enums.base;


import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

/**
 * 删除标记 0正常 1删除
 * @author: kwk
 * @date: 2023/6/8 10:48
 */
@Getter
public enum IsDeleteEnum {
    //删除标记 0正常 1删除
    DELETED(true, "删除"),
    EFFECTIVE(false, "正常");

    //成员变量
    @EnumValue
    private Boolean index;
    private String desc;

    //构造方法
    IsDeleteEnum(Boolean index, String desc) {
        this.index = index;
        this.desc = desc;
    }

}
