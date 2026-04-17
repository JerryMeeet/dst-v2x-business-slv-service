package dst.v2x.business.slv.service.common.utils;

import com.dst.steed.vds.common.base.exception.BusinessException;
import com.dst.steed.vds.common.domain.response.RespType;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * 数据比对工具。设计逻辑：https://www.processon.com/view/link/622579e163768953892348ee
 *
 * @param <T>
 */
@Builder
public class DataCompareHelper<T> {

    /**
     * 新数据集合获取组件
     */
    @NotNull
    private Supplier<List<T>> newDataSupplier;
    /**
     * 旧数据集合获取组件
     */
    @NotNull
    private Supplier<List<T>> oldDataSupplier;

    /**
     * 唯一键生成组件。唯一键用于判断数据更新操作
     */
    @NotNull
    private Function<T, Object> uniqueKeyFunction;

    /**
     * 新旧数据唯一键重复时，判断是否需要更新。返回true代表需要更新
     */
    @Builder.Default
    private BiPredicate<T, T> updatePredicate = (newData, oldData) -> true;

    /**
     * 结合新旧数据，产出更新结果。输入：newData，oldData；输出：计算结果
     */
    @Builder.Default
    private BiFunction<T, T, T> updateFunction = (newData, oldData) -> newData;
    /**
     * 新增数据处理，输入：newData
     */
    @Builder.Default
    private Function<T, T> addFunction = Function.identity();
    /**
     * 删除数据处理，输入：oldData
     */
    @Builder.Default
    private Function<T, T> deleteFunction = Function.identity();
    /**
     * 未变动数据处理，输入：oldData
     */
    @Builder.Default
    private Function<T, T> unmodifiedFunction = Function.identity();

    /**
     * 是否考虑删除操作
     */
    @Builder.Default
    private boolean genDelete = false;

    public CompareResult<T> compare() {
        // 检查参数
        checkParams();

        // 初始化结果
        CompareResult<T> result = new CompareResult<>();

        // 准备比对数据
        List<T> newDatas = newDataSupplier.get();
        List<T> oldDatas = oldDataSupplier.get();
        newDatas = newDatas == null ? new ArrayList<>() : newDatas;
        oldDatas = oldDatas == null ? new ArrayList<>() : oldDatas;

        // 开始比对

        // 两个应用场景：excel导入和页面数据更新。
        // 1、页面数据更新一般是用id的，如果id为空，说明是需要新增的
        // 2、excel导入，则一般使用其他的唯一键
        // 不管哪种场景，原数据中都应该存在完整的信息，可以获取到不为空的唯一键
        Map<Object, T> oldDataMap = oldDatas.stream()
                .collect(Collectors.toMap(data -> uniqueKeyFunction.apply(data), data -> data));
        for (T newData : newDatas) {
            Object key = uniqueKeyFunction.apply(newData);
            // 如果匹配，就移除命中数据
            T oldData = key == null ? null : oldDataMap.remove(key);
            // 新增：没有找到对应的旧数据
            if (oldData == null) {
                result.addAdd(addFunction.apply(newData));
            } else {
                // 可能更新：找到了对应的数据
                if (updatePredicate.test(newData, oldData)) {
                    // 更新
                    result.addUpdate(updateFunction.apply(newData, oldData));
                } else {
                    // 不需要更新
                    result.addUnmodified(unmodifiedFunction.apply(oldData));
                }
            }
        }
        // 判断是否要删除
        // 如果要考虑删除操作，map中就是没有匹配的，就是需要删除的数据
        if (genDelete) {
            // 删除
            oldDataMap.values().forEach(data -> result.addDelete(deleteFunction.apply(data)));
        } else {
            oldDataMap.values().forEach(data -> result.addUnmodified(unmodifiedFunction.apply(data)));
        }
        return result;
    }

    /**
     * 检查必要参数
     */
    private void checkParams() {
        if (newDataSupplier == null || oldDataSupplier == null
                || uniqueKeyFunction == null) {
            throw new BusinessException(RespType.BUSINESS_ERROR, "数据比对工具初始化异常");
        }
    }

    @Getter
    public static class CompareResult<T> {
        /**
         * 待删除数据
         */
        private final List<T> toDelete = new ArrayList<>();
        /**
         * 待更新数据
         */
        private final List<T> toUpdate = new ArrayList<>();
        /**
         * 待新增数据
         */
        private final List<T> toAdd = new ArrayList<>();
        /**
         * 未修改数据
         */
        private final List<T> unModified = new ArrayList<>();

        /**
         * 更新后的完整数据
         */
        private final List<T> allDataAfterUpdate = new ArrayList<>();

        private void addDelete(T data) {
            toDelete.add(data);
        }

        private void addUpdate(T data) {
            toUpdate.add(data);
            allDataAfterUpdate.add(data);
        }

        private void addAdd(T data) {
            toAdd.add(data);
            allDataAfterUpdate.add(data);
        }

        private void addUnmodified(T data) {
            unModified.add(data);
            allDataAfterUpdate.add(data);
        }
    }
}
