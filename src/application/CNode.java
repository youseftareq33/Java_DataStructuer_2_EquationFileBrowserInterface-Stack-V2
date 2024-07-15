package application;

public class CNode<T extends Comparable<T>> {
	T data;
	int next;
	
	////////////////////////////////////////////////
	// constructor
	public CNode(T data,int next) {
		this.data=data;
		this.next=next;
	}
	
	////////////////////////////////////////////////
	// getter
	public T getData() {
		return data;
	}
	public int getNext() {
		return next;
	}
	////////////////////////////////////////////////
	//setter
	public void setData(T data) {
		this.data=data;
	}
	public void setNext(int next) {
		this.next=next;
	}
	///////////////////////////////////////////////
	@Override
	public String toString() {
		return "[" + data + "," + next + "]";
	}
	
	
}
