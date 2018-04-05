package org.easyweb;
//the Ternary search tries
public class TST<Value> {

	private static class Node<Value> {
		private char c;
		private Value val;
		private Node<Value> left, mid, right;
	}	

	private Node<Value> root; //the root 

	public void put(String key, Value val) {
		if(null == key || key.trim().equals("")) throw new IllegalArgumentException("the key not null or empty");

		root = put(root, key, val, 0);
	}

	public Value get(String key) {
		if(null == key || key.trim().equals("")) throw new IllegalArgumentException("the key not null or empty");

		return get(root, key, 0);
	}

	private Value get(Node<Value> x, String key, int d) {
		if( x == null) return null;

		char c = key.charAt(d);

		if(c < x.c) return get(x.left, key, d);

		else if(c > x.c) return get(x.right, key, d);

		else if(d < key.length() - 1) return get(x.mid, key, d + 1);

		else return x.val;
	}

	private Node<Value> put(Node<Value> x, String key, Value val, int d) {
		
		char c = key.charAt(d);

		if(x == null) {
			x = new Node<Value>();
			x.c = c;
		}
		//// 
		if( c < x.c) {
			x.left = put(x.left, key, val, d);
		}else if( c > x.c) {
			x.right = put(x.right, key, val, d);
		}else if(d < key.length() - 1) {
			x.mid = put(x.mid, key, val, d + 1);	
		}else {
			x.val = val;
		}

		return x;
	}

	//for test
	public static void main(String[] args) {

		TST<Integer> tst = new TST<Integer>();

		tst.put("hello", 1);
		tst.put("hello/say",2);

		System.out.println("hello :" + tst.get("hello"));
		System.out.println("hello/say :" + tst.get("hello/say"));
	}
}
