package security;


public class study {
	int a=10;
	int b=20;
	
	void display() {
		
	}
	
	class child extends study{
		int a=30;
		void show() {
			System.out.println(a);
			System.out.println(super.a);
			System.out.println(this.a);
		}
	}
	
	
	
	
	
	public static void main(String[] args) {

	}

}
