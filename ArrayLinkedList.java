package chap06;
// 연결 리스트 클래스(배열 커서 버전）

import java.util.Comparator;

public class ArrayLinkedList<E> {

    //--- 노드 ---//
    class Node<E> {
        private E data;          // 데이터
        private int next;        // 리스트의 뒤쪽포인터
        private int dnext;       // 프리 리스트의 뒤쪽포인터

        //--- data와 next를 설정 ---//
        void set(E data, int next) {
            this.data = data;
            this.next = next;
        }
    }

    private Node<E>[] n;        // 리스트 본체
    private int size;           // 리스트 크기(최대 데이터 개수)
    private int max;            // 사용 중인 꼬리 record
    private int head;           // 머리노드
    private int crnt;           // 선택 노드
    private int deleted;        // 프리 리스트의 머리노드
    private static final int NULL = -1;    // 뒤쪽노드가 없음 / 리스트가 가득 참

    //--- 생성자(constructor) ---//
    public ArrayLinkedList(int capacity) {
        head = crnt = max = deleted = NULL;
        try {
            n = new Node[capacity];
            for (int i = 0; i < capacity; i++)
                n[i] = new Node<E>();
            size = capacity;
        }
        catch (OutOfMemoryError e) {        // 배열 생성에 실패
            size = 0;
        }
    }

    //--- 다음에 삽입하는 record의 인덱스를 구함 ---//
    private int getInsertIndex() {
        if (deleted == NULL) {                    // 삭제 record가 존재하지 않음
            if (max < size)
                return ++max;                     // 새 record를 사용
            else
                return NULL;                      // 크기 넘침(over)
        } else {
            int rec = deleted;                    // 프리 리스트에서
            deleted = n[rec].dnext;               // 머리 rec을 꺼냄
            return rec;
        }
    }

    //--- record idx를 프리 리스트에 등록 ---//
    private void deleteIndex(int idx) {
        if (deleted == NULL) {                    // 삭제 record가 존재하지 않음
            deleted = idx;                        // idx를 프리 리스트의
            n[idx].dnext = NULL;                  // 머리에 등록
        } else {
            int rec = deleted;                    // idx를 프리 리스트의
            deleted = idx;                        // 머리에 삽입
            n[rec].dnext = rec;
        }
    }

    //--- 노드를 검색 ---//
    public E search(E obj, Comparator<? super E> c) {
        int ptr = head;                                    // 현재 스캔 중인 노드

        while (ptr != NULL) {
            if (c.compare(obj, n[ptr].data) == 0) {
                crnt = ptr;
                return n[ptr].data;                   // 검색 성공
            }
            ptr = n[ptr].next;                        // 뒤쪽 노드 선택
        }
        return null;                                  // 검색 실패
    }

    //--- 머리노드 삽입 ---//
    public void addFirst(E obj) {
        int ptr = head;                                // 삽입 전의 머리노드
        int rec = getInsertIndex();
        if (rec != NULL) {
            head = crnt = rec;                         // 제 rec 번째 레코드에 삽입
            n[head].set(obj, ptr);
        }
    }

    //--- 꼬리노드 삽입 ---//
    public void addLast(E obj) {
        if (head == NULL)                                // 리스트가 비어있으면
            addFirst(obj);                               // 머리에 삽입
        else {
            int ptr = head;
            while (n[ptr].next != NULL)
                ptr = n[ptr].next;
            int rec = getInsertIndex();
            if (rec != NULL) {                        // 제 rec 번째 레코드에 삽입
                n[ptr].next = crnt = rec;
                n[rec].set(obj, NULL);
            }
        }
    }

    //--- 머리노드 삭제 ---//
    public void removeFirst() {
        if (head != NULL) {                            // 리스트가 비어있지 않으면
            int ptr = n[head].next;
            deleteIndex(head);
            head = crnt = ptr;
        }
    }

    //--- 꼬리노드 삭제 ---//
    public void removeLast() {
        if (head != NULL) {
            if (n[head].next == NULL)            // 노드가 하나만 있으면
                removeFirst();                   // 머리노드 삭제
            else {
                int ptr = head;                  // 스캔 중인 노드
                int pre = head;                  // 스캔 중인 노드의 앞쪽노드

                while (n[ptr].next != NULL) {
                    pre = ptr;
                    ptr = n[ptr].next;
                }
                n[pre].next = NULL;                    // pre는 삭제 뒤의 꼬리 노드
                deleteIndex(pre);
                crnt = pre;
            }
        }
    }

    //--- 레코드 p를 삭제 ---//
    public void remove(int p) {
        if (head != NULL) {
            if (p == head)                                // p가 머리 노드이면
                removeFirst();                            // 머리노드 삭제
            else {
                int ptr = head;

                while (n[ptr].next != p) {
                    ptr = n[ptr].next;
                    if (ptr == NULL) return;    // p가 리스트에 없음
                }
                n[ptr].next = NULL;
                deleteIndex(ptr);
                n[ptr].next = n[p].next;
                crnt = ptr;
            }
        }
    }

    //--- 선택 노드 삭제 ---//
    public void removeCurrentNode() {
        remove(crnt);
    }

    //--- 전체 노드 삭제 ---//
    public void clear() {
        while (head != NULL)                        // 비게 될 때까지
            removeFirst();                          // 머리 노드 삭제
        crnt = NULL;
    }

    //--- 선택 노드를 하나 뒤쪽으로 진행 ---//
    public boolean next() {
        if (crnt == NULL || n[crnt].next == NULL)
            return false;                                    // 나아갈 수 없음
        crnt = n[crnt].next;
        return true;
    }

    //--- 선택 노드 표시 ---//
    public void printCurrentNode() {
        if (crnt == NULL)
            System.out.println("선택 노드가 없습니다.");
        else
            System.out.println(n[crnt].data);
    }

    //--- 전체 노드 표시 ---//
    public void dump() {
        int ptr = head;

        while (ptr != NULL) {
            System.out.println(n[ptr].data);
            ptr = n[ptr].next;
        }
    }
}
