package dst.v2x.business.slv.service.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static java.util.stream.Collectors.toSet;

/**
 * 集合工具类
 */
@Slf4j
public final class CollectionUtilsWrapper extends CollectionUtils {

    private CollectionUtilsWrapper() {

    }

    /**
     * 获取两个集合的差集
     *
     * @param all
     * @param list
     * @return
     */
    public static <T> Collection<T> getSubtract(Collection<T> all, Collection<T> list) {
        return all.stream().filter(item -> !list.contains(item)).collect(toSet());
    }

    /**
     * 将一个集合固定分组，每组n个元素
     *
     * @param source 要分组的数据源
     * @param n      每组n个元素
     * @param <T>
     * @return
     */
    public static <T> List<List<T>> fixedGrouping(List<T> source, int n) {
        Assert.isTrue(n > 0, "n小于或等于0");

        if (isEmpty(source)) {
            log.warn("source集合为空");
            return Collections.emptyList();
        }

        int sourceSize = source.size();
        int size = sourceSize / n + 1;
        List<List<T>> result = new ArrayList<>(size);
        for (int i = 0; i < sourceSize; i++) {
            List<T> subset = new ArrayList<>(n);
            for (int j = i * n; j < (i + 1) * n; j++) {
                if (j < sourceSize) {
                    subset.add(source.get(j));
                }
            }
            if (!subset.isEmpty()) {
                result.add(subset);
            }
        }
        return result;
    }

    /**
     * 合并为一个List
     *
     * @param sources
     * @param <T>
     * @return
     */
    public static <T> List<T> mergeList(List<List<T>> sources) {
        List<T> lists = new ArrayList<>();
        sources.forEach(lists::addAll);

        return lists;
    }

}
