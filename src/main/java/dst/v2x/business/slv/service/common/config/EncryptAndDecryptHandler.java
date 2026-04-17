package dst.v2x.business.slv.service.common.config;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import dst.v2x.business.slv.service.common.utils.EncryptUtils;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 将数据库字段加密入库  解密出库
 * 注意：对应需要解密实体需要添加autoResultMap = true
 */
@MappedTypes(String.class)
@MappedJdbcTypes(JdbcType.VARCHAR)
public class EncryptAndDecryptHandler extends BaseTypeHandler<String> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, String parameter, JdbcType jdbcType) throws SQLException {
        try {
            final String encrypt = EncryptUtils.encrypt(parameter);
            ps.setString(i, encrypt);
        } catch (Exception e) {
            throw new SQLException("加密值错误: " + e.getMessage());
        }
    }

    @Override
    public String getNullableResult(ResultSet rs, String columnName) throws SQLException {
        try {
            String encryptedValue = rs.getString(columnName);
            if (StringUtils.isNotEmpty(encryptedValue)) {
                return EncryptUtils.decrypt(encryptedValue);
            }
            return null;
        } catch (Exception e) {
            throw new SQLException("解密值错误: " + e.getMessage());
        }
    }

    @Override
    public String getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        try {
            String encryptedValue = rs.getString(columnIndex);
            if (StringUtils.isNotEmpty(encryptedValue)) {
                return EncryptUtils.decrypt(encryptedValue);
            }
            return null;
        } catch (Exception e) {
            throw new SQLException("解密值错误: " + e.getMessage());
        }
    }

    @Override
    public String getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        try {
            final String encryptedValue = cs.getString(columnIndex);
            if (StringUtils.isNotEmpty(encryptedValue)) {
                return EncryptUtils.decrypt(encryptedValue);
            }
            return null;
        } catch (Exception e) {
            throw new SQLException("解密值出错: " + e.getMessage());
        }
    }

}