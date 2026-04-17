package com.dst.steed.vds.common.util;

import java.util.Optional;

public class NullSafeUtil {

    public static <T> Optional<T> object(T obj) {
        return Optional.ofNullable(obj);
    }
}
