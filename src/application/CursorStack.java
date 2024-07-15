package application;

public class CursorStack<T extends Comparable<T>> {
	CursorArray<T> ca;
	int l;
	
	// constructor
	
	public CursorStack(int capacity) {
		ca = new CursorArray<>(capacity);
		l=ca.createList();
	}

	// to insert data
	/**/
	
	public void push(T data) {
		if (ca.isFree()) {
			ca.insertAtHead(data , l);
		} else {
			System.out.println("STACKOVERFLOW!!!!");
		}
	}

	// to remove data
	/**/
	public T pop() {
		if (!ca.isEmpty(l)) {
			return ca.deleteAtFirst(l);
		}
		else {
			return null;
		}
		
	}

	// return topNode
	/**/
	public T peek() {
		return ca.getFirst(l);
		//return ca.TopNode(size);
	}

	// clear all data
	
	public void clear() {
		ca.deleteAll(l);
	}

	// if cursorStack empty
	
	public boolean isEmpty() {
		return ca.isEmpty(l);
	}

	// get size of stack
	
	public int cursorStack_Size() {
		return ca.size();
	}
	
	// to string
	public String toString() {
		return ca.toString();
	}
	
	// print data 
	public void tra(int f) {
		ca.traversList(f);
	}
	
	
}
