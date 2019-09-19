package com.zhouww.first.springboot.service.ServiceImpl;

import com.zhouww.first.springboot.CommonUtils.JsonConvertUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisCommands;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Redis实现类
 */
@Service
public class RedisService {
	public static final String LOCK_PREFIX = "redis_lock";
	public static final int LOCK_EXPIRE = 1000; // ms

	@Autowired
	private RedisTemplate<String, ?> redisTemplate;

	public boolean set(final String key, final String value) {
		boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {
			@Override
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
				connection.set(serializer.serialize(key), serializer.serialize(value));
				return true;
			}
		});
		return result;
	}

	public String get(final String key) {
		String result = redisTemplate.execute(new RedisCallback<String>() {
			@Override
			public String doInRedis(RedisConnection connection) throws DataAccessException {
				RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
				byte[] value = connection.get(serializer.serialize(key));
				return serializer.deserialize(value);
			}
		});
		return result;
	}

	public String lpop(final String key) {
		String result = redisTemplate.execute(new RedisCallback<String>() {
			@Override
			public String doInRedis(RedisConnection connection) throws DataAccessException {
				RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
				byte[] res = connection.lPop(serializer.serialize(key));
				return serializer.deserialize(res);
			}
		});
		return result;
	}

	public Long delete(final String key) {
		String result = redisTemplate.execute(new RedisCallback<String>() {
			@Override
			public String doInRedis(RedisConnection connection) throws DataAccessException {
				RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
				Long res = connection.del(serializer.serialize(key));
				if (res == null) {
					return "-1";
				} else {
					return String.valueOf(res);
				}
			}
		});
		return Long.valueOf(result);
	}

	public boolean expire(final String key, long expire) {
		return redisTemplate.expire(key, expire, TimeUnit.SECONDS);
	}

	public <T> boolean setList(String key, List<T> list) {
		String value = JsonConvertUtils.writeTAsJson(list);
		return set(key, value);
	}

	/**
	 *  最终加强分布式锁
	 *利用lambda表达式
	 * @param key key值
	 * @return 是否获取到
	 */
	/*public boolean lock(String key){
		String lock = LOCK_PREFIX + key;
		// 利用lambda表达式
		return (Boolean) redisTemplate.execute((RedisCallback) connection -> {

			long expireAt = System.currentTimeMillis() + LOCK_EXPIRE + 1;
			Boolean acquire = connection.setNX(lock.getBytes(), String.valueOf(expireAt).getBytes());


			if (acquire) {
				return true;
			} else {

				byte[] value = connection.get(lock.getBytes());

				if (Objects.nonNull(value) && value.length > 0) {

					long expireTime = Long.parseLong(new String(value));

					if (expireTime < System.currentTimeMillis()) {
						// 如果锁已经过期
						byte[] oldValue = connection.getSet(lock.getBytes(), String.valueOf(System.currentTimeMillis() + LOCK_EXPIRE + 1).getBytes());
						// 防止死锁
						return Long.parseLong(new String(oldValue)) < System.currentTimeMillis();
					}
				}
			}
			return false;
		});
	}*/
	/**
	 *  最终加强分布式锁
	 *
	 * @param key key值
	 * @return 是否获取到
	 */
	/*public boolean lock( final String key) {
		String lock = LOCK_PREFIX + key;
		boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {
			@Override
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				long expireAt = System.currentTimeMillis() + LOCK_EXPIRE + 1;
				Boolean acquire = connection.setNX(lock.getBytes(), String.valueOf(expireAt).getBytes());


				if (acquire) {
					return true;
				} else {

					byte[] value = connection.get(lock.getBytes());

					if (Objects.nonNull(value) && value.length > 0) {

						long expireTime = Long.parseLong(new String(value));

						if (expireTime < System.currentTimeMillis()) {
							// 如果锁已经过期
							byte[] oldValue = connection.getSet(lock.getBytes(), String.valueOf(System.currentTimeMillis() + LOCK_EXPIRE + 1).getBytes());
							// 防止死锁
							return Long.parseLong(new String(oldValue)) < System.currentTimeMillis();
						}
					}
				}
				return false;
			}
		});
		return result;
	}


	*//**
	 * 删除锁
	 *
	 * @param key
	 *//*
	public void deleteLock(final String key) {
		redisTemplate.delete(key);
	}*/

	/**
	 * 用于设置分布式添加锁可以用于处理防重操作
	 * @param key
	 * @param expire
	 * @return
	 */
	public boolean setLock(String key, long expire) {
		try {
			RedisCallback<String> callback = (connection) -> {
				JedisCommands commands = (JedisCommands) connection.getNativeConnection();
				String uuid = UUID.randomUUID().toString();
				return commands.set(key, uuid, "NX", "PX", expire);
			};
			String result = redisTemplate.execute(callback);

			return !StringUtils.isEmpty(result);
		} catch (Exception e) {

		}
		return false;
	}

}
