package edu.smith.cs.csc212.lists;

import me.jjfoley.adt.ListADT;
import me.jjfoley.adt.errors.BadIndexError;
import me.jjfoley.adt.errors.TODOErr;

/**
 * A Singly-Linked List is a list that has only knowledge of its very first
 * element. Elements after that are chained, ending with a null node.
 * 
 * @author jfoley
 *
 * @param <T> - the type of the item stored in this list.
 */
public class SinglyLinkedList<T> extends ListADT<T> {
	/**
	 * The start of this list. Node is defined at the bottom of this file.
	 */
	Node<T> start;

	@Override
	public T removeFront() {
		checkNotEmpty();
		Node<T> removed = this.start;
		this.start = this.start.next;
		return removed.value;
	}

	@Override
	public T removeBack() {
		checkNotEmpty();
		return removeIndex(size()-1);
	}

	@Override
	public T removeIndex(int index) {
		checkNotEmpty();
		if(this.start.next == null) {
			return removeFront();
		}
		Node<T> current = this.start;
		for (int i = 0; i < index -1; i++) {
			current = current.next;
		}
		Node<T> removed = current.next;
		current.next = removed.next;
		
		return removed.value;
	}

	@Override
	public void addFront(T item) {
		this.start = new Node<T>(item, start);
	}

	@Override
	public void addBack(T item) {
		if (this.isEmpty()) {
			this.addFront(item); 
			return;
			}
		
		for (Node<T> b = this.start; b != null; b = b.next) { if (b.next == null) {
			b.next = new Node<T>(item, null);
			return;
			}
		}
		
	}
	
	@Override
	public void addIndex(int index, T item) {
		int count = 0;
		if (index==0) {
			addFront(item);
			return;
		}
		else {
			for (Node<T> current = this.start; current!=null; current = current.next) {
				if (count+1==index) {
					current.next = new Node<T>(item, current.next);
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
		return getIndex(size()-1);
	}

	@Override
	public T getIndex(int index) {
		checkNotEmpty();
		int at = 0;
		for (Node<T> n = this.start; n != null; n = n.next) {
			if (at++ == index) {
				return n.value;
			}
		}
		throw new BadIndexError(index);
	}

	@Override
	public void setIndex(int index, T value) {
		checkNotEmpty();
		int count = 0;
		if (index==0) {
			this.start.value = value;
			return;
		}
		else {
			for (Node<T> current = this.start; current!=null; current = current.next) {
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
		for (Node<T> n = this.start; n != null; n = n.next) {
			count++;
		}
		return count;
	}

	@Override
	public boolean isEmpty() {
		return this.start == null;
	}

	/**
	 * The node on any linked list should not be exposed. Static means we don't need
	 * a "this" of SinglyLinkedList to make a node.
	 * 
	 * @param <T> the type of the values stored.
	 */
	private static class Node<T> {
		/**
		 * What node comes after me?
		 */
		public Node<T> next;
		/**
		 * What value is stored in this node?
		 */
		public T value;
		/** This is the same variable for all Nodes! */ 
		static int numNodes = 0;


		/**
		 * Create a node with no friends.
		 * 
		 * @param value - the value to put in it.
		 * @param next - the successor to this node.
		 */
		public Node(T value, Node<T> next) {
			numNodes += 1;
			if (numNodes > 10000) {
			throw new RuntimeException("Probably an infinite loop..."); }
			this.value = value;
			this.next = next;
		}
	}

}
