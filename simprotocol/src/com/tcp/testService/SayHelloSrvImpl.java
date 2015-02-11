package com.tcp.testService;

public class SayHelloSrvImpl implements SayHelloSrv {

	@Override
	public String sayHello(String helloArg) {
		if(helloArg.equals("hello")){
			return "hello";
		}else{
			return helloArg;
		}

	}

}
