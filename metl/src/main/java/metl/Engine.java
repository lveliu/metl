/**
 * Muye Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */
package metl;

import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

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
			configPath = METL_DEFAULT_PROPERTIES;
		}
		
		VolatileReference<Engine> reference = ENGINES.get(configPath);
		if(reference == null) {
			reference = new VolatileReference<Engine>();
			VolatileReference<Engine> old = ENGINES.putIfAbsent(configPath, reference);
			if (old != null) { // 重复
				reference = old;
			}
		}
		
		
		Engine engine = reference.get();
		
		return engine;
	}
}
