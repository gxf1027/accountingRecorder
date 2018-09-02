package cn.gxf.spring.motan.test;

public class SayHiImpl implements SayHi{
	
	@Override
	public void say(String str){
		System.out.println("say: " + str);
	}
}
