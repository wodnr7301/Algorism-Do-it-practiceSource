package chap04;

import java.util.Scanner;

public class LastNElements {
	public static void main(String[] args) {
		Scanner stdIn = new Scanner(System.in);
		
		final int N = 10;
		int[] a = new int[N]; //입력받은 값을 저장
		int cnt = 0;
		int retry;
		
		System.out.println("정수를 입력하세요");
		
		do {
			System.out.printf("%d번째 정수 :", cnt+1);
			a[cnt++ % N] = stdIn.nextInt();
			
			 System.out.print("계속 할까요? (예.1／아니요.0) : ");
			 retry = stdIn.nextInt();
		}while(retry==1);
		
		int i = cnt - N;
		if(i<0) i=0;	// 입력한 값의 개수가 10이하라면 a[0] ~ a[cnt -1]순서로 출력
		
		for(; i<cnt; i++) // i가 초기화된 값에서 시작해서 cnt까지 증가하면서 반복
			System.out.printf("%2d번째 정수 = %d\n", i+1, a[i%N]);
	}
}
