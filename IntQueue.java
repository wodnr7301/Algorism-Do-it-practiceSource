package chap04;

public class IntQueue { //생성할 때 용량을 결정하는 고정 길이 큐

	private int[] que; 		//큐용 배열
	private int capacity; 	//큐의 최대 용량 : 이 값은 배열 que에 저장할 수 있는 최대 요솟수와 같다
	private int front;		//맨 앞의 요소 커서
	private int rear;		//맨 뒤의 요소 커서
	
	private int num;		
	//현재 데이터 개수 num : 큐가 비어 있는지 가득 찼는지 구별할 수 없는 상황을 피하기 위해 이 변수가 필요 
	//큐가 비어있을 때  num = 0
	//큐가 가득 찼을 때 num = capacity
	// : front == rear일 때(큐가 가득 찼거나 비어있을 때 front와 rear이 같아짐 : 고정 용량인 큐나 링 버퍼인 큐일 때는 같아질 수밖에 없음)
	//인큐 후 리어(rear)가 +1 or 디큐 후 프론트(front)가 +1 되므로
	
	
	//실행 시 예외 : 큐가 비어 있음
	public class EmptyIntQueueException extends RuntimeException{
		public EmptyIntQueueException() {}
	}
	
	//실행 시 예외 : 큐가 가득 참
	public class OverflowIntQueueException extends RuntimeException{
		public OverflowIntQueueException() {}
	}
	
	//생성자
	public IntQueue(int maxlen) {
		
		num = front = rear = 0;
		capacity = maxlen;	//매개변수 maxlen으로 큐의 용량을 전달 받음
		
		try {
			que = new int[capacity]; //큐 본체용 배열 생성
			
		}catch(OutOfMemoryError e){ //생성할 수 없음
			
			capacity = 0;
		}
	}
	
	//큐에 데이터를 인큐 : 큐에 데이터를 인큐하고 그대로 반환하는 메서드
	public int enque(int x) throws OverflowIntQueueException{
		
		if(num >= capacity)
			throw new OverflowIntQueueException();
		else{
			que[rear++] = x;
			num++;
		}
		if(rear == capacity) // 만약 rear 값이, 큐의 최대 용량인 capacity와 같아지면
			rear = 0;		 // rear을 배열의 첫 인덱스인 0으로 변경할꺼야
		// 고정 값이 12인 큐일 때 11인 리어가 인큐 후 1이 늘어나 12가 된다면, 리어를 0으로 초기화 한 그림 4-11
		
		return x;
	}
	
	//큐에 데이터를 디큐
	public int deque() throws EmptyIntQueueException{
		
		if(num <= 0) 	//요솟수가 없으면
			throw new EmptyIntQueueException(); //비어있는 큐 오류가 뜨고
		
		int x = que[front++];
		num--;
		
		if(front == capacity) {
			front = 0;
		}
		// 고정 값이 12인 큐일 때 11인 프론트가 디큐 후 1이 늘어나 12가 된다면, 프론트를 0으로 초기화 한 그림 4-12
		
		return x;
	}
	
	//큐에서 데이터를 피크(프런트 데이터를 들여다봄)
	public int peek() throws EmptyIntQueueException{
		
		if(num <= 0) 	
			throw new EmptyIntQueueException();
		
		return que[front];
	}
	
	//큐를 비움
	public void clear() {
		
		num = front = rear = 0;
	}
	
	//큐에서 x검색(못찾으면 -1, 찾으면 찾은 인덱스 반환)
	public int indexOf(int x) {
		for(int i=0; i<num; i++) {
			int idx = (i+front) % capacity; //원형 큐에서 현재 인덱스를 계산하는 법
			// : front는 큐의 맨 앞 인덱스, i는 원형 큐에서의 상대적 위치
			// : 맨 앞인 front + i(원형 큐에서의 상대적 위치) = 원형 큐에서 현재 위치%배열의 최대 용량 capacity로 나눈 나머지 값
			// => 원형 큐에서 나온 현재 인덱스
			if(que[idx] == x)
				return idx;	//검색 성공
		}
		return -1;	//검색 실패
	}
	
	//큐의 용량을 반환
	public int getCapacity() {
		return capacity;
	}
	
	//큐에 쌓여 있는 데이터 개수를 반환
	public int size() {
		return num;
	}
	
	//큐가 비어 있나요?
	public boolean isEmpty() {
		return num<=0;
	}
	
	//큐가 가득 찼나요?
	public boolean isFull() {
		return num>=capacity;
	}
	
	//큐 안의 모든 데이터를 프런트 -> 리어 순서로 출력
	public void dump() {
		if(num<=0) {
			System.out.println("큐가 비어 있습니다");
		}
		else {
			for(int i=0; i<num; i++) 
				System.out.print(que[(i+front)%capacity]+" ");
			System.out.println();
		}
	}
	
	
}
