/**
 * Muye Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */
package metl.util;

/**
 *                       
 * @Filename VolatileReference.java
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
public class VolatileReference<T> {
	
	private volatile T value;

	public T get() {
		return value;
	}

	public void set(T value) {
		this.value = value;
	}
}
