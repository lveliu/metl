/**
 * Muye Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */
package metl;

import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import metl.util.CollectionUtils;
import metl.util.ConfigUtils;
import metl.util.StringUtils;
import metl.util.VolatileReference;

/**
 *                       
 * @Filename Engine.java
 *
 * @Description 
 *
 * @Version 1.0
 *
 * @Author lveliu
 *
 * @Email lveliugy@gmail.com
 *       
 * @History
 *<li>Author: lveliu</li>
 *<li>Date: 2015年1月19日</li>
 *<li>Version: 1.0</li>
 *<li>Content: create</li>
 *
 */
public class Engine {
	
	//默认配置文件名称
	private static final String METL_DEFAULT_PROPERTIES = "metl-default.properties";
	
	//用户配置文件名称
	private static final String METL_PROPERTIES = "metl.properties";
	
	//配置文件前缀
	private static final String METL_PREFIX = "metl-";
	
	//key前缀
	private static final String METL_KEY_PREFIX = "metl.";
	
	//配置文件后缀
	private static final String PROPERTIES_SUFFIX = ".properties";
	
	//模式选择key
	private static final String MODES_KEY = "modes";
	
	//默认引擎key
	private static final String ENGINE_NAME = "engine.name";
	
	//模板缓存
	private static final ConcurrentMap<String, VolatileReference<Engine>> ENGINES = new ConcurrentHashMap<String, VolatileReference<Engine>>();
	
	//默认构造方法
	public static Engine getEngine(){
		return getEngine(null, new Properties());
	}
	
	//统一获得引擎入口
	public static Engine getEngine(String configPath, Properties configProperties) {
		if(StringUtils.isEmpty(configPath)) {
			configPath = METL_PROPERTIES;
		}
		
		VolatileReference<Engine> reference = ENGINES.get(configPath);
		if(reference == null) {
			reference = new VolatileReference<Engine>();
			VolatileReference<Engine> old = ENGINES.putIfAbsent(configPath, reference);
			if (old != null) { // 重复
				reference = old;
			}
		}
		assert(reference != null);
		Engine engine = reference.get();
		if(engine == null) {
			synchronized (reference) {//加锁
				engine = reference.get();
				if(engine == null) {
					//创建引擎
					reference.set(engine);//
				}
			}
		}
		assert(engine != null);
		return engine;
	}
	
	//初始化配置文件
	public static Properties initProperties(String configPath, Properties configProperties){
		Map<String, String> systemProperties = ConfigUtils.filterWithPrefix(METL_KEY_PREFIX, (Map) System.getProperties(), false);//获取系统配置参数
		Map<String, String> systemEnv = ConfigUtils.filterWithPrefix(METL_KEY_PREFIX, System.getenv(), true);//获取系统中环境变量
		Properties properties = ConfigUtils.mergeProperties(METL_DEFAULT_PROPERTIES, configPath, configProperties, systemProperties, systemEnv);//合并所有的配置文件
		String[] modes = StringUtils.splitByComma(properties.getProperty(MODES_KEY));//获得用户配置模式
		if(CollectionUtils.isEmpty(modes)) {//根据用户配置模式不同在一次合并配置文件
			Object[] configs = new Object[modes.length + 5];
			configs[0] = METL_DEFAULT_PROPERTIES;
			for (int i = 0; i < modes.length; i ++) {
				configs[i + 1] = METL_PREFIX + modes[i] + PROPERTIES_SUFFIX;
			}
			configs[modes.length + 1] = configPath;
			configs[modes.length + 2] = configProperties;
			configs[modes.length + 3] = systemProperties;
			configs[modes.length + 4] = systemEnv;
			properties = ConfigUtils.mergeProperties(configs);
		}
		properties.setProperty(ENGINE_NAME, configPath);
		return properties;
	}
}
