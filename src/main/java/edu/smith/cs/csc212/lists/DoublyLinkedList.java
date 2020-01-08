package edu.smith.cs.csc212.lists;




import me.jjfoley.adt.ListADT;
import me.jjfoley.adt.errors.BadIndexError;
import me.jjfoley.adt.errors.TODOErr;

/**
 * A Doubly-Linked List is a list based on nodes that know of their successor and predecessor.
 * @author jfoley
 *
 * @param <T>
 */
public class DoublyLinkedList<T> extends ListADT<T> {
	/**
	 * This is a reference to the first node in this list.
	 */
	private Node<T> start;
	/**
	 * This is a reference to the last node in this list.
	 */
	private Node<T> end;
	
	/**
	 * A doubly-linked list starts empty.
	 */
	public DoublyLinkedList() {
		this.start = null;
		this.end = null;
	}
	

	@Override
	public T removeFront() {
		checkNotEmpty();	
		Node<T> removed = this.start;
		if (size()==1) {
			this.start = null;
			this.end = null;	
		}
		else {
			this.start = this.start.after;
			start.before = null;
		}
		return removed.value;
	}

	@Override
	public T removeBack() {	
		checkNotEmpty();
		Node<T> removed = this.end;
		
		if (size()==1) {
			this.start = null;
			this.end = null;	
		}
		else {
			this.end = this.end.before;
			end.after = null;
		}
		return removed.value;
	}

	@Override
	public T removeIndex(int index) {
		checkNotEmpty();
		
		if(this.start.after == null) {
			return removeFront();
		}
		Node<T> current = this.start;
		for (int i = 0; i < index -1; i++) {
			current = current.after;
		}
		Node<T> removed = current.after;
		current.after = removed.after;
		
		return removed.value;
	}
		
		

	@Override
	public void addFront(T item) {
		Node<T> oldStart = this.start;
		this.start = new Node <T> (item);
		
		if(oldStart == null) {
			this.end = this.start;
		}
		else {
			this.start.after = oldStart;
			oldStart.before = this.start;
		}
	}

	@Override
	public void addBack(T item) {
		if (end == null) {
			start = end = new Node<T>(item);
		} else {
			Node<T> secondLast = end;
			end = new Node<T>(item);
			end.before = secondLast;
			secondLast.after = end;
		}
	}

	@Override
	public void addIndex(int index, T item) {
		int count = 0;
		if (index==0) {
			addFront(item);
			return;
		}
		if (index==size()) {
			addBack(item);
			return;
		}
		else {
			for (Node<T> current = this.start; current!=null; current = current.after) {
				if (count==index) {
					Node<T> oldBefore = current.before;
					current.before = new Node<T>(item);
					current.before.after = current;
					oldBefore.after = current.before;
					current.before.before = oldBefore;
					return;
				}
			count++;
			}
		}
		throw new BadIndexError(index);
	}

	@Override
	public T getFront() {
		checkNotEmpty();
		return this.start.value;
	}

	@Override
	public T getBack() {
		checkNotEmpty();
		return this.end.value;
	}
	
	@Override
	public T getIndex(int index) {
		checkNotEmpty();
		int at = 0;
		for (Node<T> n = this.start; n != null; n = n.after) {
			if (at++ == index) { return n.value;
			} 
		}
		throw new BadIndexError(index);
	}
	
	public void setIndex(int index, T value) {
		checkNotEmpty();
		int count = 0;
		if (index==0) {
			this.start.value = value;
			return;
		}
		if (index == size()-1) {
			this.end.value = value;
			return;
		}
		else {
			for (Node<T> current = this.start; current!=null; current = current.after) {
				if (count==index) {
					current.value= value;
					return;
				}
			count++;
			}
		}
		throw new BadIndexError(index);
		}
		
	@Override
	public int size() {
		int count = 0;
		for (Node<T> n = this.start; n != null; n = n.after) {
		count++;
		}
		  return count;
		}
	

	@Override
	public boolean isEmpty() {
		return this.start == null;
	}
	
	/**
	 * The node on any linked list should not be exposed.
	 * Static means we don't need a "this" of DoublyLinkedList to make a node.
	 * @param <T> the type of the values stored.
	 */
	private static class Node<T> {
		/**
		 * What node comes before me?
		 */
		public Node<T> before;
		/**
		 * What node comes after me?
		 */
		public Node<T> after;
		/**
		 * What value is stored in this node?
		 */
		public T value;
		/**
		 * Create a node with no friends.
		 * @param value - the value to put in it.
		 */
		public Node(T value) {
			this.value = value;
			this.before = null;
			this.after = null;
		}
	}
}
