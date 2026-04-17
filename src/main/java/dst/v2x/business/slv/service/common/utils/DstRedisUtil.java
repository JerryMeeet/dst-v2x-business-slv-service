package dst.v2x.business.slv.service.common.utils;

import com.dst.steed.vds.common.base.exception.BusinessException;
import com.dst.steed.vds.common.util.DstSpringUtil;
import com.dst.steed.vds.common.util.NullSafeUtil;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBucket;
import org.redisson.api.RLock;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.Codec;

import java.util.concurrent.TimeUnit;

/**
 * redis工具, 不足的api, 请使用RedissonClient 或 RedisTemplate
 *
 * @author 郑楷山
 * @since 2022/12/7
 **/
@Slf4j
public final class DstRedisUtil {

    private static volatile RedissonClient redissonClient;

    public static RedissonClient getClient() {
        if (null == redissonClient) {
            synchronized (DstRedisUtil.class) {
                if (null == redissonClient) {
                    redissonClient = NullSafeUtil.object(DstSpringUtil.getBean(RedissonClient.class))
                            .orElseThrow(() -> new BusinessException("找不到RedissonClient"));
                }
            }
        }
        return redissonClient;
    }

    /**
     * 规范key, 后续可能变更<br>
     * 如果直接使用RedissonClient 或 RedisTemplate, key必须经过该接口获取
     */
    public static String toKey(String key) {
        return DstSpringUtil.getAppName() + ":" + key;
    }

    // ====== RBucket

    /**
     * 获取Bucket
     */
    public static <T> RBucket<T> getBucket(String key) {
        return getClient().getBucket(toKey(key));
    }

    /**
     * 获取Bucket
     */
    public static <T> RBucket<T> getBucket(String key, Codec codec) {
        return getClient().getBucket(toKey(key), codec);
    }

    /**
     * 获取数据
     *
     * @deprecated 多加了一层toKey, 不直接调整怕影响业务历史, 建议采用新方法:
     * {@link DstRedisUtil#getBucketValue(String)}
     */
    @Deprecated
    public static <T> T getValue(String key) {
        RBucket<T> bucket = getBucket(toKey(key));
        return bucket.get();
    }

    /**
     * 设置数据
     *
     * @deprecated 多加了一层toKey, 不直接调整怕影响业务历史, 建议采用新方法:
     * {@link DstRedisUtil#setBucketValue(String, Object)}
     */
    @Deprecated
    public static void setValue(String key, Object value) {
        getBucket(toKey(key)).set(value);
    }

    /**
     * 设置数据
     *
     * @deprecated 多加了一层toKey, 不直接调整怕影响业务历史, 建议采用新方法:
     * {@link DstRedisUtil#setBucketValue(String, Object, long, TimeUnit)}
     */
    @Deprecated
    public static void setValue(String key, Object value, long ttl, TimeUnit unit) {
        getBucket(toKey(key)).set(value, ttl, unit);
    }

    /**
     * 获取数据
     */
    public static <T> T getBucketValue(String key) {
        RBucket<T> bucket = getBucket(key);
        return bucket.get();
    }

    /**
     * 设置数据
     */
    public static void setBucketValue(String key, Object value) {
        getBucket(key).set(value);
    }

    /**
     * 设置数据
     */
    public static void setBucketValue(String key, Object value, long ttl, TimeUnit unit) {
        getBucket(key).set(value, ttl, unit);
    }

    // ====== AtomicLong

    /**
     * 加指定值
     */
    public static Long addAndGet(String key, long val) {
        return getClient().getAtomicLong(toKey(key)).addAndGet(val);
    }

    /**
     * 加一
     */
    public static Long incrementAndGet(String key) {
        return getClient().getAtomicLong(toKey(key)).incrementAndGet();
    }

    // ====== RMap

    /**
     * 获取Map
     */
    public static <K, V> RMap<K, V> getMap(String key) {
        return getClient().getMap(toKey(key));
    }

    /**
     * 获取Map
     */
    public static <K, V> RMap<K, V> getMap(String key, long ttl, TimeUnit unit) {
        RMap<K, V> map = getMap(key);
        map.expire(ttl, unit);
        return map;
    }

    /**
     * 获取数据
     */
    public static <V> V getMapValue(String key, Object mapKey) {
        RMap<Object, V> map = getMap(key);
        return map.get(mapKey);
    }

    /**
     * 设置数据, 如果有多个建议使用getMap()再逐个put
     */
    public static void setMapValue(String key, Object mapKey, Object mapValue) {
        RMap<Object, Object> map = getMap(key);
        map.put(mapKey, mapValue);
    }

    // ====== RLock

    /**
     * 获取锁
     */
    public static RLock getLock(String lockKey) {
        return getClient().getLock(toKey(lockKey));
    }

    /**
     * 尝试锁
     */
    public static boolean tryLock(String lockKey) {
        RLock lock = getLock(lockKey);
        return lock.tryLock();
    }

    /**
     * 尝试锁
     *
     * @param lockKey   key
     * @param waitTime  等待时间
     * @param leaseTime 执行过程释放时间
     * @param unit      类型
     */
    public static boolean tryLock(String lockKey, long waitTime, long leaseTime, TimeUnit unit) {
        RLock lock = getLock(lockKey);
        try {
            return lock.tryLock(waitTime, leaseTime, unit);
        } catch (InterruptedException e) {
            log.warn("获取锁线程中断, 名称: {}", lock.getName(), e);
            return false;
        }
    }

    /**
     * 尝试锁
     *
     * @param lockKey   key
     * @param waitTime  等待时间
     * @param leaseTime 执行过程释放时间
     * @param unit      类型
     */
    public static boolean tryLockThrows(String lockKey, long waitTime, long leaseTime, TimeUnit unit) throws InterruptedException {
        RLock lock = getLock(lockKey);
        return lock.tryLock(waitTime, leaseTime, unit);
    }

    /**
     * 安全的释放锁
     *
     * @param lock 锁
     */
    public static void safeUnlock(RLock lock) {
        if (lock != null && lock.isHeldByCurrentThread()) {
            lock.unlock();
        }
    }
}
