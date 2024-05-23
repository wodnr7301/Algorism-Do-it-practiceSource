package chap06;

import java.util.Comparator;

public class BinTree<K, V> {
	
	//노드
	static class Node<K, V>{
		
		private K key;	//키 값
		private V data; //데이터
		private Node<K, V> left; //왼쪽 포인터
		private Node<K, V> right; //오른쪽 포인터
		
		//생성자
		Node(K key, V data, Node<K, V> left, Node<K, V> right){
			this.key = key;
			this.data = data;
			this.left = left;
			this.right = right;
		}
		
		//키 값을 반환
		K getKey() {
			return key;
		}
		
		//데이터를 반환
		V getValue() {
			return data;
		}
		
		//데이터를 출력
		void print() {
			System.out.println(data);
		}
		
	}
	
	private Node<K, V> root;	//루트
	private Comparator<? super K> comparator = null;	//비교자
	
	//생성자
	public BinTree() {
		root = null;
	}
	
	//생성자
	public BinTree(Comparator<? super K> c) {
		
		this();
		comparator = c;
	}
	
	//두 키값을 비교
	private int comp(K key1, K key2) {
		
		return(comparator == null)?((Comparable<K>)key1).compareTo(key2):comparator.compare(key1, key2);
	}
	
	//키로 검색
	public V search(K key) {
		Node<K,V> p = root;
		
		while(true) {
			if(p == null)
				return null;
			int cond = comp(key, p.getKey());
			if(cond==0)
				return p.getValue();
			else if(cond<0)
				p=p.left;
			else
				p=p.right;
		}
	}
	
	//node를 루트로 하는 서브트리에 노드<K, V>를 삽입
	private void addNode(Node<K,V> node, K key, V data) {
		int cond = comp(key, node.getKey());
		if(cond == 0)
			return;
		else if(cond < 0) {
			if(node.left == null)
				node.left = new Node<K,V>(key, data, null, null);
			else
				addNode(node.right, key, data);
		}
	}
	
	//노드를 삽입
	public void add(K key, V data) {
		if(root == null)
			root = new Node<K, V>(key, data, null, null);
		else
			addNode(root, key, data);
	}
	
	//키값이 key인 노드를 삭제
	public boolean remove(K key) {
		
		Node<K,V> p = root;		//스캔 중인 노드
		Node<K,V> parent = null;	//스캔 중인 노드의 부모 노드
		boolean isLeftChild = true;	//p가 부모의 왼쪽 노드?
		
		while(true) {
			if(p==null) {		//더 이상 나아갈 수 없으면
				return false;	//그 키값은 없음
			}
			int cond = comp(key, p.getKey()); //key와 노드 p의 key값을 비교
			if(cond == 0)	//같으면
				break;		//검색 성공
			else {
				parent = p; //가지로 내려가기 전에 부모를 설정
				if(cond < 0) {	//key쪽이 작으면
					isLeftChild = true;	//왼쪽 자식으로 내려감
					p=p.left;	//왼쪽 서브트리에서 검색
				}else {
					isLeftChild = false; //오른쪽 자식으로 내려감
					p = p.right;	//오른쪽 서브트리에서 검색
				}
			}
		}
		
		if(p.left == null) {	//p에 왼쪽 자식이 없음
			if(p == root)
				root = p.right;
			else if(isLeftChild)
				parent.left = p.right; // 부모의 왼쪽 포인터가 오른쪽 자식을 가리킴
			else
				parent.right = p.right;	// 부모의 오른쪽 포인터가 오른쪽 자식을 가리킴
		}else if(p.right == null) {
			if(p == root)
				root = p.left;
			else if(isLeftChild)
				parent.left = p.left;	// 부모의 왼쪽 포인터가 왼쪽 자식을 가리킴
			else
				parent.right = p.right;	// 부모의 오른쪽 포인터가 왼쪽 자식을 가리킴
		}else {
			parent = p;
			Node<K, V> left = p.left;	// 서브트리 가운데 가장 큰 노드
			isLeftChild = false;
			while(left.right != null) {	// 가장 큰 노드 left를 검색
				parent = left;
				left = left.right;
				isLeftChild = false;
			}
			p.key = left.key; //left의 키값을 p로 옮김
			p.data = left.data; //left의 데이터를 p로 옮김
			if(isLeftChild)
				parent.left = left.left; //left를 삭제
			else
				parent.right = left.left; //left를 삭제
		}
		
		return true;
	}
	
	private void printSubTree(Node node) {
		if(node != null) {
			printSubTree(node.left);	// 왼쪽 서브트리를 키값의 오름차순으로 출력
			System.out.println(node.key + " " + node.data);	//node를 출력
			printSubTree(node.right);	// 오른쪽 서브트리를 키값의 오름차순으로 출력
		}
	}
	
	//모든 노드를 키값의 오름차순으로 출력
	public void print() {
		
		printSubTree(root);
	}
}
