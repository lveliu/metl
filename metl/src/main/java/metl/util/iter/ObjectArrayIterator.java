/*
 * Copyright 2011-2013 HTTL Team.
 *  
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *  
 *      http://www.apache.org/licenses/LICENSE-2.0
 *  
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package metl.util.iter;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * ObjectArrayIterator. (Tool, Prototype, ThreadUnsafe)
 * 
 * @author Liang Fei (liangfei0201 AT gmail DOT com)
 */
public class ObjectArrayIterator<T> implements Iterator<T> {

	private final T[] array;

	private final int length;

	private int index;

	public ObjectArrayIterator(T[] array){
		this.array = array;
		this.length = array == null ? 0 : array.length;
	}

	public T[] getArray() {
		return array;
	}

	public boolean hasNext() {
		return index < length;
	}

	public T next() {
		if (! hasNext()) {
			throw new NoSuchElementException();
		}
		return array[index ++];
	}

	public void remove() {
		throw new UnsupportedOperationException("remove() method is not supported");
	}
}