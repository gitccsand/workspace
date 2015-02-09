package alg.dastru;

import java.util.Scanner;
import java.util.Stack;

public class Brackets {
	

	public static void main(String[] args) {
		input();
	} 
	
	public static void input(){
		
		Scanner sc = new Scanner(System.in);
		String input ="";
		
		while(true){
			
			System.out.println("«Î ‰»Î¿®∫≈¥Æ£∫");
			input = sc.nextLine();
			if("exit".equals(input))
				break;
			
			System.out.println(valid(input));
		}
	}
	
	public static boolean valid(String bar_arr){
		
		Stack<Character> s_stack = new Stack<Character>(); 
		Stack<Character> m_stack = new Stack<Character>(); 
		Stack<Character> b_stack = new Stack<Character>(); 
		
		int i = 0;
		while(i<bar_arr.length()){
			
			switch (bar_arr.charAt(i)) {
			
			case '(':
				s_stack.push(Character.valueOf(bar_arr.charAt(i)));
				break;
			case '[':
				m_stack.push(Character.valueOf(bar_arr.charAt(i)));
				break;
			case '{':
				b_stack.push(Character.valueOf(bar_arr.charAt(i)));
				break;
			case ')':
				if(s_stack.isEmpty())
					return false;
				s_stack.pop();
				break;
			case ']':
				if(m_stack.isEmpty())
					return false;
				m_stack.pop();
				break;
			case '}':
				if(b_stack.isEmpty())
					return false;
				b_stack.pop();
				break;

			default:
				return false;
			}

			i++;
		}
		
		if(s_stack.isEmpty() & m_stack.isEmpty() & b_stack.isEmpty())
			return true;
		
		return false;
		
		
	}
	
//	static String input = "{}{}[]()((((())))){{}}}}}}";
//	String bar_arr = new 

//		char[] s_arr = new char[bar_arr.length];
//		char[] m_arr = new char[bar_arr.length];
//		char[] b_arr = new char[bar_arr.length];
//		int s_top=-1,m_top=-1,b_top=-1;
//				s_top = push(s_arr,s_top,bar_arr.charAt(i));
//				m_top = push(m_arr,m_top,bar_arr.charAt(i));
//				b_top = push(b_arr,b_top,bar_arr.charAt(i));
//				s_top = pop(s_arr,s_top);
//				m_top = pop(m_arr,m_top);
//				if(m_top<0)
//					return false;
//				b_top = pop(b_arr,b_top);
//				if(b_top<0)
//					return false;
//		if(s_arr[0]==0 & m_arr[0]==0 & b_arr[0]==0)
//			return true;
	
//	public static int push(char[] array,Integer top,char key){
//		top++;
//		array[top]=key;
//		return top;
//	}
//	
//	public static char pop(char[] array,Integer top){
//		if(top<0)
//			return 0;
//		char key = array[top];
//		array[top]=0;
//		top--;
//		return key;
//	}

}
