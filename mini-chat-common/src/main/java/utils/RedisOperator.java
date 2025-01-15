package utils;

import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.StringRedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;


@Component
public class RedisOperator {

	@Autowired
	private StringRedisTemplate redisTemplate;

	// ---------------------------- 字符串操作 ---------------------------- //

	// 设置字符串值
	public void setString(String key, String value, long timeout, TimeUnit unit) {
		redisTemplate.opsForValue().set(key, value, timeout, unit);
	}

	// 获取字符串值
	public String getString(String key) {
		return redisTemplate.opsForValue().get(key);
	}

	// 删除字符串
	public void deleteString(String key) {
		redisTemplate.delete(key);
	}

	// ---------------------------- 哈希操作 ---------------------------- //

	// 设置哈希值
	public void setHash(String mapName, String key, String value) {
		redisTemplate.opsForHash().put(mapName, key, value);
	}

	// 获取哈希值
	public String getHash(String mapName, String key) {
		Object value = redisTemplate.opsForHash().get(mapName, key);
		return value != null ? value.toString() : null;
	}

	// 获取整个哈希
	public Map<Object, Object> getAllHash(String mapName) {
		return redisTemplate.opsForHash().entries(mapName);
	}

	// 删除哈希中的字段
	public void deleteHash(String mapName, String key) {
		redisTemplate.opsForHash().delete(mapName, key);
	}

	// 判断哈希中是否包含字段
	public boolean containsHash(String mapName, String key) {
		return redisTemplate.opsForHash().hasKey(mapName, key);
	}

	// ---------------------------- Set操作 ---------------------------- //

	// 向set中添加元素
	public void addSet(String setName, String value) {
		redisTemplate.opsForSet().add(setName, value);
	}

	// 从set中移除元素
	public void removeSet(String setName, String value) {
		redisTemplate.opsForSet().remove(setName, value);
	}

	// 判断set中是否包含某个元素
	public boolean containsSet(String setName, String value) {
		return Boolean.TRUE.equals(redisTemplate.opsForSet().isMember(setName, value));
	}

	// ---------------------------- 工具方法 ---------------------------- //

	// 检查键是否存在
	public boolean exists(String key) {
		return Boolean.TRUE.equals(redisTemplate.hasKey(key));
	}
}