package com.zeecle.springboot.shiro;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.serializer.support.DeserializingConverter;
import org.springframework.core.serializer.support.SerializingConverter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {
	
	@Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(connectionFactory);
		redisTemplate.setKeySerializer(new StringRedisSerializer());
		redisTemplate.setValueSerializer(new RedisObjSerializer());
        return redisTemplate;
    }
	
	
	class RedisObjSerializer implements RedisSerializer<Object> {
		
		private Converter<Object, byte[]> serializer = new SerializingConverter();
		private Converter<byte[], Object> deserializer = new DeserializingConverter();
		final byte[] EMPTY_ARRAY = new byte[0];

		@Override
		public byte[] serialize(Object object) throws SerializationException {
			if (object == null) {
				return EMPTY_ARRAY;
			}
			try {
				return serializer.convert(object);
			} catch (Exception ex) {
				return EMPTY_ARRAY;
			}
		}

		@Override
		public Object deserialize(byte[] bytes) throws SerializationException {
			if (isEmpty(bytes)) {
				return null;
			}
			try {
				return deserializer.convert(bytes);
			} catch (Exception ex) {
				throw new SerializationException("Cannot deserialize", ex);
			}
		}
		
		private boolean isEmpty(byte[] data) {
			return (data == null || data.length == 0);
		}
		
	}

}
