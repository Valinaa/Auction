package cn.valinaa.auction.utils;

import lombok.Setter;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author Valinaa
 * @Date: 2022/8/17
 * @Description: redis工具类，封装并简化redis功能的实现
 */
@Component
@Slf4j
@SuppressWarnings("all")
public class RedisUtil {
    @Resource
    private RedisTemplate<String, Object> redisTemplate;
    //! =============================common============================
    
    /**
     * 指定缓存失效时间
     *
     * @param key Key值  键
     * @param time 时间(秒)
     */
    public boolean expire(String key, long time) {
        try {
            if (time > 0) {
                redisTemplate.expire(key, time, TimeUnit.SECONDS);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * 指定缓存失效时间,携带失效时间的类型
     *
     * @param key Key值  键
     * @param time 时间(秒)
     * @param unit 时间的类型  TimeUnit枚举
     */
    public boolean expire(String key, long time, TimeUnit unit) {
        try {
            if (time > 0) {
                redisTemplate.expire(key, time, unit);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * 根据key 获取过期时间
     *
     * @param key Key值 键 不能为null
     * @return 时间(秒) 返回0代表为永久有效
     */
    public long getExpire(String key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }
    
    /**
     * 判断key是否存在
     *
     * @param key Key值 键
     * @return true 存在 false不存在
     */
    public boolean hasKey(String key) {
        try {
            return redisTemplate.hasKey(key);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * 删除缓存
     *
     * @param key Key值 可以传一个值 或多个
     */
    public void delete(String... key) {
        if (key != null && key.length > 0) {
            if (key.length == 1) {
                redisTemplate.delete(key[0]);
            } else {
                redisTemplate.delete((Collection<String>) CollectionUtils.arrayToList(key));
            }
        }
    }
    
    //! ============================String=============================
    
    /**
     * 普通缓存获取
     *
     * @param key Key值 键
     * @return 值
     */
    public Object get(String key) {
        return key == null ? null : redisTemplate.opsForValue().get(key);
    }
    
    /**
     * 普通缓存放入
     *
     * @param key Key值   键
     * @param value 值
     * @return true成功 false失败
     */
    
    public boolean set(String key, Object value) {
        try {
            redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * 普通缓存放入并设置时间
     *
     * @param key Key值   键
     * @param value 值
     * @param time  时间(秒) time要大于0 如果time小于等于0 将设置无限期
     * @return true成功 false 失败
     */
    
    public boolean set(String key, Object value, long time) {
        try {
            if (time > 0) {
                redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
            } else {
                set(key, value);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * 递增
     *
     * @param key Key值   键
     * @param delta 要增加几(大于0)
     */
    public long increment(String key, long delta) {
        if (delta < 0) {
            throw new RuntimeException("递增因子必须大于0");
        }
        return redisTemplate.opsForValue().increment(key, delta);
    }
    
    /**
     * 递减
     *
     * @param key Key值   键
     * @param delta 要减少几(小于0)
     */
    public long decrement(String key, long delta) {
        if (delta < 0) {
            throw new RuntimeException("递减因子必须大于0");
        }
        return redisTemplate.opsForValue().increment(key, -delta);
    }
    
    //! ================================Map=================================
    
    /**
     * HashGet
     *
     * @param key Key值  键 不能为null
     * @param item 项 不能为null
     */
    public Object hashGet(String key, String item) {
        return redisTemplate.opsForHash().get(key, item);
    }
    
    /**
     * 获取hashKey对应的所有键值
     *
     * @param key Key值 键
     * @return 对应的多个键值
     */
    public Map<Object, Object> hashEntries(String key) {
        return redisTemplate.opsForHash().entries(key);
    }
    
    /**
     * HashSet
     *
     * @param key Key值 键
     * @param map 对应多个键值
     */
    public boolean hashSetMap(String key, Map<String, Object> map) {
        try {
            redisTemplate.opsForHash().putAll(key, map);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * HashSet 并设置时间
     *
     * @param key Key值  键
     * @param map  对应多个键值
     * @param time 时间(秒)
     * @return true成功 false失败
     */
    public boolean hashSetMapWithTime(String key, Map<String, Object> map, long time) {
        try {
            redisTemplate.opsForHash().putAll(key, map);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * 向一张hash表中放入数据,如果不存在将创建
     *
     * @param key Key值   键
     * @param item  项
     * @param value 值
     * @return true 成功 false失败
     */
    public boolean hashSet(String key, String item, Object value) {
        try {
            redisTemplate.opsForHash().put(key, item, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * 向一张hash表中放入数据,如果不存在将创建
     *
     * @param key Key值   键
     * @param item  项
     * @param value 值
     * @param time  时间(秒) 注意:如果已存在的hash表有时间,这里将会替换原有的时间
     * @return true 成功 false失败
     */
    public boolean hashSet(String key, String item, Object value, long time) {
        try {
            redisTemplate.opsForHash().put(key, item, value);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * 删除hash表中的值
     *
     * @param key Key值  键 不能为null
     * @param item 项 可以使多个 不能为null
     */
    public void hashDelete(String key, Object... item) {
        redisTemplate.opsForHash().delete(key, item);
    }
    
    /**
     * 判断hash表中是否有该项的值
     *
     * @param key Key值  键 不能为null
     * @param item 项 不能为null
     * @return true 存在 false不存在
     */
    public boolean hashHasKey(String key, String item) {
        return redisTemplate.opsForHash().hasKey(key, item);
    }
    
    /**
     * hash递增 如果不存在,就会创建一个 并把新增后的值返回
     *
     * @param key Key值  键
     * @param item 项
     * @param by   要增加几(大于0)
     */
    public double hashIncrement(String key, String item, double by) {
        return redisTemplate.opsForHash().increment(key, item, by);
    }
    
    /**
     * hash递减
     *
     * @param key Key值  键
     * @param item 项
     * @param by   要减少记(小于0)
     */
    public double hashDecrement(String key, String item, double by) {
        return redisTemplate.opsForHash().increment(key, item, -by);
    }
    
    //! ============================set=============================
    
    /**
     * 根据key获取Set中的所有值
     *
     * @param key Key值 键
     */
    public Set<Object> setGet(String key) {
        try {
            return redisTemplate.opsForSet().members(key);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * 根据value从一个set中查询,是否存在
     *
     * @param key Key值   键
     * @param value 值
     * @return true 存在 false不存在
     */
    public boolean setHasKey(String key, Object value) {
        try {
            return redisTemplate.opsForSet().isMember(key, value);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * 将数据放入set缓存
     *
     * @param key Key值    键
     * @param values 值 可以是多个
     * @return 成功个数
     */
    public long setSet(String key, Object... values) {
        try {
            return redisTemplate.opsForSet().add(key, values);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
    
    /**
     * 将set数据放入缓存
     *
     * @param key Key值    键
     * @param time   时间(秒)
     * @param values 值 可以是多个
     * @return 成功个数
     */
    public long setSetWithTime(String key, long time, Object... values) {
        try {
            Long count = redisTemplate.opsForSet().add(key, values);
            if (time > 0) {
                expire(key, time);
            }
            return count;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
    
    /**
     * 获取set缓存的长度
     *
     * @param key Key值 键
     */
    public long setGetSize(String key) {
        try {
            return redisTemplate.opsForSet().size(key);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
    
    /**
     * 移除值为value的
     *
     * @param key Key值    键
     * @param values 值 可以是多个
     * @return 移除的个数
     */
    
    public long setRemove(String key, Object... values) {
        try {
            return redisTemplate.opsForSet().remove(key, values);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
    
    //! ============================Sorted Set=============================
    /**
     * 增加有序集合
     *
     * @param key Key值
     * @param value
     * @param seqNo
     * @return Boolean
     */
    public Boolean addZset(String key, Object value, double seqNo) {
        try {
            return redisTemplate.opsForZSet().add(key, value, seqNo);
        } catch (Exception e) {
            log.error("[RedisUtils.addZset] [error]", e);
            return false;
        }
    }
    /**
     * 获取zset集合数量
     *
     * @param key Key值
     * @return Long
     */
    public Long countZset(String key) {
        try {
            return redisTemplate.opsForZSet().size(key);
        } catch (Exception e) {
            log.error("[RedisUtils.countZset] [error] [key is {}]", key, e);
            return 0L;
        }
    }
    /**
     * 获取zset指定范围内的集合
     *
     * @param key Key值
     * @param start
     * @param end
     * @return Set<Object>
     */
    public Set<Object> rangeZset(String key, long start, long end) {
        try {
            return redisTemplate.opsForZSet().range(key, start, end);
        } catch (Exception e) {
            log.error("[RedisUtils.rangeZset] [error] [key is {},start is {},end is {}]", key, start, end, e);
            return null;
        }
    }
    /**
     * 根据key和value移除指定元素
     *
     * @param key Key值
     * @param value
     * @return Long
     */
    public Long removeZset(String key, Object value) {
        return redisTemplate.opsForZSet().remove(key, value);
    }
    /**
     * 获取对应key和value的score
     *
     * @param key Key值
     * @param value Value值
     * @return Double
     */
    public Double score(String key, Object value) {
        return redisTemplate.opsForZSet().score(key, value);
    }
    /**
     * 指定范围内元素排序
     *
     * @param key Key值
     * @param v1 起始值
     * @param v2 结束值
     * @return Set<Object>
     */
    public Set<Object> rangeByScore(String key, double v1, double v2) {
        return redisTemplate.opsForZSet().rangeByScore(key, v1, v2);
    }
    /**
     * 指定元素增加指定值
     *
     * @param key Key值
     * @param obj
     * @param score
     * @return Double
     */
    public Double addScore(String key, Object obj, double score) {
        return redisTemplate.opsForZSet().incrementScore(key, obj, score);
    }
    /**
     * 获取指定范围的元素排名(逆序)
     *
     * @param key Key值
     * @param start
     * @param end
     * @return Set<Object>
     */
    public Set<Object> reverseRange(String key, long start, long end) {
        return redisTemplate.opsForZSet().reverseRange(key, start, end);
    }
    /**
     * 获取指定范围的元素排名(逆序)(附有scores)
     *
     * @param key Key值
     * @param start
     * @param end
     * @return Set
     */
    public Set<ZSetOperations.TypedTuple<Object>> reverseRangeWithScores(String key, long start, long end) {
        List<Map<String,String>> list=new LinkedList<>();
        return redisTemplate.opsForZSet().reverseRangeWithScores(key, start, end);
    }
    /**
     * 获取指定元素排名
     *
     * @param key Key值
     * @param obj
     * @return Long
     */
    public Long rank(String key, Object obj) {
        return redisTemplate.opsForZSet().rank(key, obj);
    }
    /**
     * 获取指定元素排名(逆序)
     *
     * @param key Key值
     * @param obj
     * @return Long
     */
    public Long reverseRank(String key, Object obj) {
        return redisTemplate.opsForZSet().reverseRank(key, obj);
    }
    
    //! ===============================list=================================
    
    /**
     * 获取list缓存的内容
     *
     * @param key Key值   键
     * @param start 开始
     * @param end   结束 0 到 -1 代表所有值
     */
    public List<Object> listGet(String key, long start, long end) {
        try {
            return redisTemplate.opsForList().range(key, start, end);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * 获取list缓存的长度
     *
     * @param key Key值 键
     */
    public long listGetSize(String key) {
        try {
            return redisTemplate.opsForList().size(key);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
    
    /**
     * 通过索引 获取list中的值
     *
     * @param key Key值   键
     * @param index 索引 index>=0时， 0 表头，1 第二个元素，依次类推；index<0时，-1，表尾，-2倒数第二个元素，依次类推
     */
    public Object listGetIndex(String key, long index) {
        try {
            return redisTemplate.opsForList().index(key, index);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * 将list放入缓存
     *
     * @param key Key值   键
     * @param value 值
     */
    public boolean listSet(String key, Object value) {
        try {
            redisTemplate.opsForList().rightPush(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * 将list放入缓存
     *
     * @param key Key值   键
     * @param value 值
     * @param time  时间(秒)
     */
    public boolean listSet(String key, Object value, long time) {
        try {
            redisTemplate.opsForList().rightPush(key, value);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        
    }
    
    /**
     * 将list放入缓存
     *
     * @param key Key值   键
     * @param value 值
     */
    public boolean listSet(String key, List<Object> value) {
        try {
            redisTemplate.opsForList().rightPushAll(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        
    }
    
    /**
     * 将list放入缓存
     *
     * @param key Key值   键
     * @param value 值
     * @param time  时间(秒)
     */
    public boolean listSet(String key, List<Object> value, long time) {
        try {
            redisTemplate.opsForList().rightPushAll(key, value);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * 功能描述：在list的右边添加元素
     * 如果键不存在，则在执行推送操作之前将其创建为空列表
     *
     * @param key Key值   键
     * @param value 值
     */
    public Long listRightPush(String key, Object value) {
        return redisTemplate.opsForList().rightPush(key, value);
    }
    
    /**
     * 功能描述：在list的右边添加集合元素
     * 如果键不存在，则在执行推送操作之前将其创建为空列表
     *
     * @param key Key值    键
     * @param values 值
     */
    public Long listRightPushList(String key, List<Object> values) {
        return redisTemplate.opsForList().rightPushAll(key, values);
    }
    
    /**
     * 根据索引修改list中的某条数据
     *
     * @param key Key值   键
     * @param index 索引
     * @param value 值
     */
    
    public boolean listUpdateIndex(String key, long index, Object value) {
        try {
            redisTemplate.opsForList().set(key, index, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * 移除N个值为value
     *
     * @param key Key值   键
     * @param count 移除多少个
     * @param value 值
     * @return 移除的个数
     */
    
    public long listRemove(String key, long count, Object value) {
        try {
            return redisTemplate.opsForList().remove(key, count, value);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
        
    }

    //! ===============================stream=================================
    //? 待补充
    /*
    public void streamSet() {
        redisTemplate.opsForStream().add();
    }
    */
}
