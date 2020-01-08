package edu.smith.cs.csc212.lists;

import me.jjfoley.adt.ArrayWrapper;
import me.jjfoley.adt.ListADT;
import me.jjfoley.adt.errors.RanOutOfSpaceError;
import me.jjfoley.adt.errors.TODOErr;

/**
 * FixedSizeList is a List with a maximum size.
 * @author jfoley
 *
 * @param <T>
 */
public class FixedSizeList<T> extends ListADT<T> {
	/**
	 * This is the array of fixed size.
	 */
	private ArrayWrapper<T> array;
	/**
	 * This keeps track of what we have used and what is left.
	 */
	private int fill;

	/**
	 * Construct a new FixedSizeList with a given maximum size.
	 * @param maximumSize - the size of the array to use.
	 */
	public FixedSizeList(int maximumSize) {
		this.array = new ArrayWrapper<>(maximumSize);
		this.fill = 0;
	}

	@Override
	public boolean isEmpty() {
		return this.fill == 0;
	}

	@Override
	public int size() {
		return this.fill;
	}

	@Override
	public void setIndex(int index, T value) {
		checkNotEmpty();
		this.checkExclusiveIndex(index);
		this.array.setIndex(index, value);
	}

	@Override
	public T getIndex(int index) {
		checkNotEmpty();
		this.checkExclusiveIndex(index);
		return this.array.getIndex(index);
	}

	@Override
	public T getFront() {
		this.checkNotEmpty();
		return this.array.getIndex(0);
	}
	
	@Override
	public T getBack() {
		this.checkNotEmpty();
		return this.array.getIndex(fill-1);
	}

	@Override
	public void addIndex(int index, T value) {
		// slide to the right
		if (fill >= array.size()) {
			throw new RanOutOfSpaceError();
		}
		for (int i = fill; i > index; i--) {
			array.setIndex(i, array.getIndex(i-1));
			}
	    this.array.setIndex(index, value);
	    fill++;
	}

	@Override
	public void addFront(T value) {
//		this.checkNotEmpty();
		this.addIndex(0, value);
	}

	@Override
	public void addBack(T value) {
		if (fill < array.size()) {
			array.setIndex(fill++, value);
		} else {
			throw new RanOutOfSpaceError();
		}
	}

	@Override
	public T removeIndex(int index) {
	checkNotEmpty();
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
	public T removeBack() {
		return removeIndex(fill-1);
		
	}

	@Override
	public T removeFront() {
		return removeIndex(0);
	}

	/**
	 * Is this data structure full? Used in challenge: {@linkplain ChunkyArrayList}.
	 * 
	 * @return if true this FixedSizeList is full.
	 */
	public boolean isFull() {
		return this.fill == this.array.size();
	}

}
