package edu.smith.cs.csc212.lists;

import me.jjfoley.adt.ListADT;
import me.jjfoley.adt.errors.BadIndexError;
import me.jjfoley.adt.errors.EmptyListError;
import me.jjfoley.adt.errors.TODOErr;

/**
 * This is a data structure that has an array inside each node of an ArrayList.
 * Therefore, we only make new nodes when they are full. Some remove operations
 * may be easier if you allow "chunks" to be partially filled.
 * 
 * @author jfoley
 * @param <T> - the type of item stored in the list.
 */
public class ChunkyArrayList<T> extends ListADT<T> {
	/**
	 * How big is each chunk?
	 */
	private int chunkSize;
	/**
	 * Where do the chunks go?
	 */
	private GrowableList<FixedSizeList<T>> chunks;

	/**
	 * Create a ChunkedArrayList with a specific chunk-size.
	 * @param chunkSize - how many items to store per node in this list.
	 */
	public ChunkyArrayList(int chunkSize) {
		this.chunkSize = chunkSize;
		chunks = new GrowableList<>();
	}
	
	private FixedSizeList<T> makeChunk() {
		return new FixedSizeList<>(chunkSize);
	}

	@Override
	public T removeFront() {
		FixedSizeList<T> front = chunks.getFront(); T deleted = front.removeFront();
		if (front.isEmpty()) {
		chunks.removeFront(); }
		  return deleted;
		}
	

	@Override
	public T removeBack() {
		FixedSizeList<T> back = chunks.getBack(); T deleted = back.removeBack();
		if (back.isEmpty()) {
		chunks.removeBack(); }
		  return deleted;
		}
	

	@Override
	public T removeIndex(int index) {
		if (this.isEmpty()) {
			throw new EmptyListError();
		}
//		FixedSizeList<T> chunky = makeChunk();
		int start = 0;
		int chunkIndex  = 0;
		for (FixedSizeList<T> chunk : this.chunks) {
			// calculate bounds of this chunk.
			int end = start + chunk.size();
			
			// Check whether the index should be in this chunk:
			if (start <= index && index < end) {
				T deleted = chunk.removeIndex(index - start);
				if (chunk.isEmpty()) {
					chunks.removeIndex(chunkIndex);
				}
				return deleted;
			}
			
			// update bounds of next chunk.
			start = end;
			chunkIndex++;
		}
//		start = 0;
//		int end = chunky.size()- 1;
		throw new BadIndexError(index);
	}

	@Override
	public void addFront(T item) {
		if (chunks.isEmpty()) {
			chunks.addBack(makeChunk()); 
			}
	FixedSizeList<T> front = chunks.getFront(); if (front.isFull()) {
	    front = makeChunk();
	chunks.addFront(front); }
	front.addFront(item); 
	}

	@Override
	public void addBack(T item) {
		if (chunks.isEmpty()) {
			chunks.addBack(makeChunk()); 
			}
		FixedSizeList<T> back = chunks.getBack(); if (back.isFull()) {
		    back = makeChunk();
		chunks.addBack(back); }
		back.addBack(item); 
		
	}

	@Override
	public void addIndex(int index, T item) {
		if (index == size()) {
			addBack(item);
			return;
		}
		if (this.chunks.isEmpty()) {
			this.chunks.addBack(makeChunk());
			return;
		}
		int chunkIndex = 0;
		int start = 0;
		for (FixedSizeList<T> chunk : this.chunks) {
			// calculate bounds of this chunk.
			int end = start + chunk.size();
			
			// Check whether the index should be in this chunk:
			if (start <= index && index < end) {
				if (chunk.isFull()) {
					// check can roll to next
					// or need a new chunk
					FixedSizeList<T> newChunk = makeChunk();
					for(int i = index - start; i < end - start; i++) {
						T value = chunk.getIndex(i);
						newChunk.addBack(value);
					}
					for ( int i = end - start-1; i >= index-start; i--) {
						chunk.removeIndex(i);
					}
					this.chunks.addIndex(chunkIndex+1, newChunk);
					chunk.addIndex(index-start, item);
					
				} else {
					// put right in this chunk, there's space.
					chunk.addIndex(index-start, item);	
				}	
				// upon adding, return.
				 return;
			}
			// update bounds of next chunk.
			start = end;
			chunkIndex++;
		}
		throw new BadIndexError(index);
	}
	
	@Override
	public T getFront() {
		return this.chunks.getFront().getFront();
	}

	@Override
	public T getBack() {
		return this.chunks.getBack().getBack();
	}


	@Override
	public T getIndex(int index) {
		if (this.isEmpty()) {
			throw new EmptyListError();
		}
		int start = 0;
		for (FixedSizeList<T> chunk : this.chunks) {
			// calculate bounds of this chunk.
			int end = start + chunk.size();
			
			// Check whether the index should be in this chunk:
			if (start <= index && index < end) {
				return chunk.getIndex(index - start);
			}
			
			// update bounds of next chunk.
			start = end;
		}
		throw new BadIndexError(index);
	}
	
	@Override
	public void setIndex(int index, T value) {
		if (this.chunks.isEmpty()) {
			throw new EmptyListError();
		}
		// check for the right index
		if (index< 0 || index >= size()) {
			throw new BadIndexError(index);
		}
		int start = 0;
		for (FixedSizeList<T> chunk : this.chunks) {
//			calculate bounds of this chunk
			int end = start + chunk.size();
			
//			check whether the index should be in this chunk
			if(start <= index && index <end){
				chunk.setIndex(index-start, value);
			}
		start = end;
		}
	}
	@Override
	public int size() {
		int total = 0;
		for (FixedSizeList<T> chunk : this.chunks) {
			total += chunk.size();
		}
		return total;
	}

	@Override
	public boolean isEmpty() {
		return this.chunks.isEmpty();
	}
}