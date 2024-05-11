package chap05;

import java.util.Scanner;

public class Factorial {
	//음이 아닌 정수 n의 팩토리얼값을 반환
	//팩토리얼 : 어떤 양의 정수 n에 대해, 팩토리얼은 n부터 1까지 모든 정수를 곱한 것
	// 5! = 5 * 4 * 3 * 2 * 1 = 120
	static int factorial(int n) {
		if(n>0)
			return n*factorial(n-1);
		else
			return 1;
	}
	
	public static void main(String[] args) {
		Scanner stdIn = new Scanner(System.in);
		
		System.out.println("정수를 입력하세요");
		int x = stdIn.nextInt();
		
		System.out.println(x+"의 팩토리얼은"+factorial(x)+"입니다");
	}
}
// 조건 연산자 사용하면 1줄로 구현
// return(n>0) ? n*factorial(n-1) : 1;