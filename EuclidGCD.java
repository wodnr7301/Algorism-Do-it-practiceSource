package chap05;

import java.util.Scanner;

public class EuclidGCD {
	//정수 x, y, z의 최대공약수를 반환
	//최대공약수를 구하는 방식 : 직사각형 하나에, 짧은 변을 한 변으로 하는 정사각형으로 분할
	//그러면 직사각형을 다 채우지 못하고 남는 직사각형이 하나 남음
	//그 직사각형에 대해 같은 과정을 반복
	//정사각형으로 모두 채워질 때 그 정사각형의 변이 최대공약수
	
	// -> 두 정수가 주어 질 때, 큰 값을 작은 값으로 나누었을 때, 나누어 떨어지면 그중에 작은 값이 최대공약수
	static int gcd(int x, int y) {
		if(y==0)
			return x;
		else
			return gcd(y, x%y);
	} 
	
	public static void main(String[] args) {
		Scanner stdIn = new Scanner(System.in);
		
		System.out.println("두 정수의 최대공약수를 구합니다");
		
		System.out.println("x정수를 입력하세요"); int x = stdIn.nextInt();
		System.out.println("y정수를 입력하세요"); int y = stdIn.nextInt();
		
		System.out.println("최대 공약수는 " + gcd(x, y) + "입니다");
	}
	
}
/*
 	먼저, 첫 번째 호출에서는 사용자로부터 입력받은 두 정수 x와 y를 매개변수로 받습니다. 
 
 	그 후, y가 0인지를 확인하여 x를 반환하는 기본 경우(base case)를 처리합니다.
 	
 	하지만  y가 0이 아니라면 재귀 호출(gcd(y, x%y)를 호출)이 이루어집니다. 
 	
 	재귀 호출이 반복될 때마다 y의 값은, 이전 호출에서의 x % y로 갱신됩니다 
 	
 	결국에는 y가 0이 될 때까지 호출이 반복됩니다
*/
