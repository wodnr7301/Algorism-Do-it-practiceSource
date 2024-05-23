package chap06;

import java.util.Comparator;
import java.util.Scanner;

public class ArrayLinkedListTester {
	
	static Scanner stdIn = new Scanner(System.in);
	
	/*
	 	출처 : https://gymdev.tistory.com/73
	 	
	 	static : '정적인, 고정적인' : 자바 관점에서 '클래스의, 공통적인'
	 	
	 	1. java 애플리케이션을 실행하면 JVM(자바 실행을 위한 가상 컴퓨터)은 OS로부터 메모리를 할당한다.
	 	
	 	2. 자바 컴파일러(javac)가 자바 소스코드 (Xxx.java)를 바이트코드 (Xxx.class)로 컴파일 한다.
	 	
	 	3. 클래스 로더(Class Loader) 를 통해 JVM 메모리(Runtime Data Areas) 로 로딩한다.
	 	
	 	4. 로드된 클래스 파일 (Xxx.class) 실행 엔진 (Execution Engine)을 통해 기계어로 변환하여 실행한다.
	 	
	 	5. 이 과정에서 실행엔진에 의해 가비지 콜렉터(Garbage Collector)등도 작동된다.
	 	
	 	자바 어플리케이션이 실행되면 JVM은 시스템으로부터 필요한 메모리를 할당받고
	 	, 용도에따라 여러 영역으로 나누어 관리한다. 
	 	
	 	Method Area : JVM이 클래스 파일(*.class)를 읽어 클래스 데이터를 저장하는 영역이다. 
	 	이 때 클래스 변수(클래스 영역에서 선언되었으며 앞에 static이 붙어 있는)도 함께 생성된다.
	 	
	 	Call Stack : 메소드의 작업에 필요한 메모리 공간을 제공한다.
	 	메소드가 호출되면, 호출 스택에 메모리가 할당되고, 해당 메소드가 수행되는 동안 필요한 값 등을 저장하는데 사용된다.
	 	메소드 실행이 완료되면 할당되었던 메모리 공간은 반환된다.
	 	
	 	Heap : 인스턴스가 생성되는 공간이다.
	 	 
	*/
	static class Data{
		
	/*
		static 변수는 한 클래스에서 공통적인 값을 유지해야 할 때 선언한다
		
		클래스가 메모리에 로딩 될 때 생성되어 프로그램이 종료될 때 까지 유지된다
		
		객체를 생성하지 않고도 '클래스 이름. 변수명'으로 호출이 가능하다
		
	 */
		
	/*
		static 메소드는?
		
		인스턴스 변수를 사용할 수 없으므로 인스턴스와 관계없는 메소드를 클래스 메소드(static 메소드)로 정의한다
		
		객체를 생성하지 않고도 '클래스 이름.메소드 명'으로 호출이 가능하다
	 */
	
		static final int NO =1; // 번호 입력받기
		static final int NAME =2; // 이름 입력받기
		
		private Integer no; // 회원번호
		private String name; //이름
		
		//문자열 출력을 반환
		public String toString() {
			
			return "("+no+")" + name;
		}
		
		//데이터 받기 입력
		void scanData(String guide, int sw) {
			System.out.println(guide + "할 데이터를 입력하세요");
			
			if((sw & NO) == NO) {
				System.out.print("번호 : ");
				no = stdIn.nextInt();
			}
			if((sw & NAME) == NAME) {
				System.out.print("이름 : ");
				name = stdIn.next();
			}
		}
		
		//회원 번호로 순서를 정하는 comparator
		public static final Comparator<Data> NO_ORDER = 
				new NoOrderComparator();
		
		private static class NoOrderComparator implements Comparator<Data>{
			//Comparator<Data>는 인터페이스, implements!!!
			//Comparator<Data>를 구현하기 위한 메서드 compare(Data d1, Data d2)
			public int compare(Data d1, Data d2) {
				//return : 메소드의 결과 값을 돌려주는 명령어
				return (d1.no > d2.no)?1:(d1.no < d2.no)?-1:0;
			}
		}
		
		//이름으로 순서를 정하는 comparator
		public static final Comparator<Data> NAME_ORDER =
				new NameOrderComparator();
		
		private static class NameOrderComparator implements Comparator<Data>{
			public int compare(Data d1, Data d2) {
				
				return d1.name.compareTo(d2.name);
			}
			
		}
	}//static class Data 끝!!
	
	//--- 메뉴 열거형 ---//
    enum Menu {
        ADD_FIRST(  "머리 노드 삽입"),
        ADD_LAST(   "꼬리 노드 삽입"),
        RMV_FIRST(  "머리 노드 삭제"),
        RMV_LAST(   "꼬리 노드 삭제"),
        RMV_CRNT(   "선택 노드 삭제"),
        CLEAR(      "전체 노드 삭제"),
        SEARCH_NO(  "번호 검색"),
        SEARCH_NAME("이름 검색"),
        NEXT(       "선택 노드 진행"),
        PRINT_CRNT( "선택 노드 표시"),
        DUMP(       "전체 노드 표시"),
        TERMINATE(  "종료");

        private final String message; // 출력할 문자열 : 재할당 할 수 없는 변수
        
        static Menu MenuAt(int idx) {
        	for(Menu m : Menu.values())
        		if(m.ordinal()==idx)
        			return m;
        	return null;
        }
        
        Menu(String string){	//생성자
        	message = string;
        }
        
        String getMessage() { 	//출력할 문자열을 반환
        	return message;
        }
        
    } // 메뉴 열거형 끝!!
    
    //--- 메뉴 선택 ---//
    static Menu SelectMenu() {
        int key;
        
        do {
        	
            for (Menu m : Menu.values()) {
                System.out.printf("(%d) %s  ", m.ordinal(), m.getMessage());
                if ((m.ordinal() % 3) == 2 &&
                      m.ordinal() != Menu.TERMINATE.ordinal())
                    System.out.println();
            }
            
            System.out.print(" : ");
            key = stdIn.nextInt();
            
        } while (key < Menu.ADD_FIRST.ordinal() || 
                                            key > Menu.TERMINATE.ordinal());
        return Menu.MenuAt(key);
    }
    
    
    public static void main(String[] args) {
		
    	Menu menu; //메뉴
    	Data data; //추가용 데이터 참조
    	Data ptr;  //검색용 데이터 참조
    	Data temp = new Data(); //입력용 데이터
    	
    	ArrayLinkedList<Data> list = new ArrayLinkedList<Data>(100);
    	
    	do {
    		switch(menu = SelectMenu()) {
    			
    		case ADD_FIRST:	//머리에 노드를 삽입
    			data = new Data();
    			data.scanData("머리에 삽입", Data.NO | Data.NAME);
    			list.addFirst(data);
    			
    		case ADD_LAST:	//꼬리 노드를 삽입
    			data = new Data();
    			data.scanData("꼬리에 삽입", Data.NO | Data.NAME);
    		
    		case RMV_FIRST:	//머리 노드를 삭제
    			list.removeFirst();
    			break;
    			
    		case RMV_LAST:	//꼬리 노드를 삭제
    			list.removeLast();
    			break;
    			
    		case RMV_CRNT:	//선택 노드를 삭제
    			list.removeCurrentNode();
    			break;
    			
    		case SEARCH_NO :                          // 회원번호 검색
                temp.scanData("검색", Data.NO);
                ptr = list.search(temp, Data.NO_ORDER);
                if (ptr == null)
                    System.out.println("그 번호의 데이터가 없습니다.");
                else
                    System.out.println("검색 성공 : " + ptr);
                break;
                
    		 case SEARCH_NAME :                       // 이름 검색
                 temp.scanData("검색", Data.NAME);
                ptr = list.search(temp, Data.NAME_ORDER);
                if (ptr == null)
                    System.out.println("그 이름의 데이터가 없습니다.");
                else
                    System.out.println("검색 성공 : " + ptr);
                 break;

    		 case NEXT :                                   // 선택 노드를 뒤쪽으로 진행
                list.next();
                 break;

    		 case PRINT_CRNT :                            // 선택 노드의 데이터를 표시
                list.printCurrentNode();
                 break;

    		 case DUMP :                                  // 모든 데이터를 리스트 순서로 표시
                list.dump();
                 break;

    		 case CLEAR :                                 // 전체노드 삭제
                list.clear();
                 break;
    		}
    	} while (menu != Menu.TERMINATE);
    }
    
    
}//public class ArrayLinkedListTester 끝!!