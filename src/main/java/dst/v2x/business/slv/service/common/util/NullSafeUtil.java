package dst.v2x.business.slv.service.common.util;

import java.util.Optional;

public class NullSafeUtil {

    public static <T> Optional<T> object(T obj) {
        return Optional.ofNullable(obj);
    }
}
