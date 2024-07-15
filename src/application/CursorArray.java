package application;

public class CursorArray<T extends Comparable<T>> {
	CNode<T>[] ca;
	
	
	////////////////////////////////////////////////
	// constructor.
	
	public CursorArray(int capacity) {
		ca= new CNode[capacity];
		/* the warning cause he cannot
							make CNode generic. */ 
		initialization();
	}
	
	////////////////////////////////////////////////
	// initializes CursorArray.
	/* to make a circular in linked list by make
	   the last index reference to the first index. */
	
	public void initialization() { 
		for (int i = 0; i < ca.length - 1; i++) {
			ca[i] = new CNode<>(null, i + 1);
			ca[ca.length - 1] = new CNode<>(null, 0);

		}
	} 

	////////////////////////////////////////////////
	// size of CursorArray
	public int size() {
		return ca.length;
	}

	////////////////////////////////////////////////
	// malloc CursorArray.
	/* the first element (after the header) is removed
	   from the freelist or in simple say(get node from
	    free list and put it in create list/ mine list). */
	
	public int malloc() {
		int p = ca[0].next;
		ca[0].next = ca[p].next;
		return p;
	}
	
	////////////////////////////////////////////////
	// free CursorArray.
	/* we place the cell at the front of the freelist
	   or in simple say(opposite of malloc). */
	
	public void free(int p) {
		ca[p] = new CNode(null, ca[0].next);
		ca[0].next = p;
	}
	
	////////////////////////////////////////////////
	// checker CursorArray.
    /* to check if linked list is
     ( null , empty , last(specific node is the last)
      , free(CursorArray had a space) , delete. )*/ 
	
	public boolean isNull(int l) {
		return ca[l] == null;
	}

	public boolean isEmpty(int l) {
		return ca[l].next == 0;
	}

	public boolean isLast(int p) {
		return ca[p].next == 0;
	}
	
	public boolean isFree() { 
		return ca[0].next != 0;
	}
	
	public boolean isDelete(T data, int l) {
		int p = findprevnum(data, l);
		if (p != -1) {
			int c = ca[p].next;
			 CNode<T> temp = ca[c];
			 /////////////////////////
			 ca[p].next = temp.next;
			 
			/* or u can use this code:
			ca[p].next = ca[c].next;
			*/
			 
			free(c);
			return true;
		}
		return false;
	}
	
	////////////////////////////////////////////////
	// create a new linked list in CursorArray.
	/* first you have to allocate one free node using
	   malloc function, then make the next of the new node to 0. */
	
	public int createList() {
		int l = malloc();
		if (l == 0)
			System.out.println("Error: Out of space!!!");
		else
			ca[l] = new CNode("dummy", 0); /* the warning cause he cannot
			 								  make CNode generic */ 
		return l;
	}

	////////////////////////////////////////////////
	// insert at head in CursorArray.
	/* add a new data at head to a
	   specific linked list. */
	
	public void insertAtHead(T data, int l) {
		
		if (isNull(l)) { // list not created
			return; //nothing
		}
		
		else { // list is created
			int p = malloc();
			
			if (p != 0) {
				ca[p] = new CNode<T>(data, ca[l].next);
				ca[l].next = p;
			}
			else {
				System.out.println("Error: Out of space!!!");
			}
		}
		
	}

	////////////////////////////////////////////////
	// insert at last in CursorArray.
	/* add a new data at head to a
	   specific linked list. */
	
	public void insertAtLast(T data, int l) {
		if (!isNull(l)) {

			while (!isLast(l)) {
				l = ca[l].next;
			}
			int p = malloc();
			if (p != 0) {
				ca[p] = new CNode<T>(data, ca[l].next);
				ca[l].next = p;
			} else
				System.out.println("Error: Out of space!!!");
		}
	}

	////////////////////////////////////////////////
	// find data in CursorArray.
	/* to find a specific data in a
	   specific linked list. */
	
	public int find(T data, int l) {
		while (!isNull(l) && !isEmpty(l)) {
			l = ca[l].next;
			if (ca[l].data.equals(data))
				return l;
		}
		return -1; // not found
	}
	
	// another way in recursion.
	
	public int findRec(T data, int l) {
		if (!isNull(l) && !isEmpty(l)) {
			l = ca[l].next;
			if (ca[l].data.equals(data))
				return l;
			return find(data, l);
		}
		return -l;
	}

	////////////////////////////////////////////////
	// find previous data in CursorArray.
	/* to find a previous data(Node) in a
	   specific linked list. */
	
	public CNode<T> findPrevNode(T data, int l) {
		while (!isNull(l) && !isEmpty(l)) {
			if (ca[ca[l].next].data.equals(data)) {
				return ca[l];
			}
			l = ca[l].next;
		}
		return null;
	}

	// find prev num(IDN).
	
	public int findprevnum(T data, int l) {
		while (!isNull(l) && !isEmpty(l)) {
			if (ca[ca[l].next].data == data) {
				return l;
			}
			l = ca[l].next;
		}
		return -1;
	}
	
	////////////////////////////////////////////////
	// find topNode in CursorArray.
	
	public T TopNode(int l) {
		if (!isEmpty(l)) {
			l = ca[l].next;
		}
		return ca[l].data;
	}
    
	////////////////////////////////////////////////
	// delete data at first in CursorArray.
	
	public T deleteAtFirst(int l) {

		if (isNull(l))
			return null;

		if (!isEmpty(l)) {
			int p = ca[l].next;
			T t = ca[p].data;
			ca[l].next = ca[p].next;
			free(p);
			return t;

		}
		return null;
	}
	
	////////////////////////////////////////////////
	// delete specific data in CursorArray.
	/* to delete a specific data in a
	   specific linked list. */
	
	public CNode<T> deleteSpecific(T data, int l) {
		int p = findprevnum(data, l);
		if (p != -1) {
			int c = ca[p].next;
			 CNode<T> temp = ca[c];
			 ca[p].next = temp.next;
			//ca[p].next = ca[c].next;
			free(c);
		}
		  return null;
	}
	
	////////////////////////////////////////////////
	// delete all data in CursorArray.
	
	public void deleteAll(int l) {
		while (!isEmpty(l))
			deleteAtFirst(l);

	}

	////////////////////////////////////////////////
	// print data in CursorArray.
	
	public void traversList(int f) {
		System.out.print("list_" + f + "-->");
		while (!isNull(f) && !isEmpty(f)) {
			f = ca[f].next;
			System.out.print(ca[f] + "-->");
		}
		System.out.println("null");
	}

	////////////////////////////////////////////////
	public T getFirst(int l) {
		if (!isNull(l) && !isEmpty(l))
			return ca[ca[l].next].getData();
		return null;
	}
	
	////////////////////////////////////////////////
	public T getEnd(int l) {
	    if (isNull(l) || isEmpty(l)) {
	        return null; // Empty list
	    }

	    while (ca[l].next != 0) {
	        l = ca[l].next;
	    }

	    return ca[l].data;
	}
	
	////////////////////////////////////////////////
	
	// previous data
	public T getPrev(T data, int l) {
        while (!isNull(l) && !isEmpty(l)) {
            l = ca[l].next;
            if (!isNull(l) && ca[l].data.equals(data)) {
                int nextNodeIndex = ca[l].next;
                if (!isNull(nextNodeIndex)) {
                    return (T) ca[nextNodeIndex].data;
                }
            }
        }
        return null; // not found
    }
	
	// next data
	public T getNext(T data, int l) {
        while (!isNull(l) && !isEmpty(l)) {
            if (ca[ca[l].next].data.equals(data))
                return (T) ca[l].data;
            l = ca[l].next;
        }
        return null; // not found
    }
	
	////////////////////////////////////////////////
	// toString for CursorArray.
	
	@Override
	public String toString() {
		String s = "";
		for (int i = 0; i < ca.length; i++) {
			s += i + " : " + ca[i] + "\n";
		}
		return s;
	}
	
}
