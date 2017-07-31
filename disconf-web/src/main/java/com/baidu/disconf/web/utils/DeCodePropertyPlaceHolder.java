package com.baidu.disconf.web.utils;

import java.util.Properties;


import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

public class DeCodePropertyPlaceHolder extends PropertyPlaceholderConfigurer {

	@Override
	protected void processProperties(ConfigurableListableBeanFactory beanFactory, Properties props)
			throws BeansException {
		try {
			// DesEncrypt des = new DesEncrypt();

			String jdbc_password = props.getProperty("jdbc.db_0.password");
			if (jdbc_password != null) {
				props.setProperty("jdbc.db_0.password", DESUtil.decrypt(jdbc_password));
			}
			String redis01_password = props.getProperty("redis.group1.client1.password");
			if (jdbc_password != null) {
				props.setProperty("redis.group1.client1.password", DESUtil.decrypt(redis01_password));
			}
			String redis02_password = props.getProperty("redis.group1.client2.password");
			if (jdbc_password != null) {
				props.setProperty("redis.group1.client2.password", DESUtil.decrypt(redis02_password));
			}
			super.processProperties(beanFactory, props);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BeanInitializationException(e.getMessage());
		}
	}

}