package edu.smith.cs.csc212.lists;

import me.jjfoley.adt.ArrayWrapper;
import me.jjfoley.adt.ListADT;
import me.jjfoley.adt.errors.BadIndexError;
import me.jjfoley.adt.errors.RanOutOfSpaceError;
import me.jjfoley.adt.errors.TODOErr;

/**
 * A GrowableList is also known as an ArrayList. It starts at a particular size
 * and grows as needed, replacing its inner array with a larger one when more
 * space is necessary.
 * 
 * @author jfoley
 *
 * @param <T> - the type of item stored in this list.
 */
public class GrowableList<T> extends ListADT<T> {
	/**
	 * How big should the initial list be?
	 * This is not private for use in tests.
	 */
	static final int START_SIZE = 10;
	/**
	 * This is the current array held by the GrowableList. It may be replaced.
	 */
	private ArrayWrapper<T> array;
	/**
	 * This is the number of elements in the array that are used.
	 */
	private int fill;

	/**
	 * Construct a new, empty, GrowableList.
	 */
	public GrowableList() {
		this.array = new ArrayWrapper<>(START_SIZE);
		this.fill = 0;
	}

	@Override
	public T removeFront() {
		this.checkNotEmpty();
		return removeIndex(0);
	}

	@Override
	public T removeBack() {
		this.checkNotEmpty();
		return removeIndex(fill - 1);
	}

	@Override
	public T removeIndex(int index) {
		// slide to the left
		// grab and hold the thing to delete
		T removed = this.getIndex(index);
		fill --;
		//slide to the left
		for (int i = index; i < fill; i++) {
			this.array.setIndex(i, array.getIndex(i+1));
		}
		//erase the duplicate
		this.array.setIndex(fill, null);
			return removed;
	}

	@Override
	public void addFront(T item) {
		addIndex(0, item);
	}

	@Override
	public void addBack(T item) {
		if (fill >= array.size()) {
			this.resizeArray();
		}
		array.setIndex(fill++, item);
	}

	/**
	 * This private method is called when we need to make room in our GrowableList.
	 */
	private void resizeArray() {
		// use this where necessary (already called in addBack!)
		ArrayWrapper<T> newArray = new ArrayWrapper<>(array.size()*2);
		for (int i = 0; i < array.size(); i++) {
			T item = array.getIndex(i);
			newArray.setIndex(i, item);
		}
		this.array = newArray;
	}

	@Override
	public void addIndex(int index, T item) {
		// slide to the right
		if (index < 0 || index > fill) {
			throw new BadIndexError(index);
		}
		if (fill >= array.size()) {
			resizeArray();
		}
		for (int i = fill; i > index; i--) {
			array.setIndex(i, array.getIndex(i-1));
			}
	    this.array.setIndex(index, item);
	    fill++;
	}

	@Override
	public T getFront() {
		checkNotEmpty();
		return this.getIndex(0);
	}

	@Override
	public T getBack() {
		checkNotEmpty();
		return this.getIndex(this.fill - 1);
	}

	@Override
	public T getIndex(int index) {
		checkNotEmpty();
		checkExclusiveIndex(index);
		return this.array.getIndex(index);
	}

	@Override
	public int size() {
		return this.fill;
	}

	@Override
	public boolean isEmpty() {
		return this.fill == 0;
	}

	@Override
	public void setIndex(int index, T value) {
		checkNotEmpty();
		checkExclusiveIndex(index);
		this.array.setIndex(index, value);
	}

}
