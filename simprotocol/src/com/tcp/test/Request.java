package com.tcp.test;

public class Request {	
private byte encode;
private String command;
private int commandLength;

public byte getEncode() {
	return encode;
}
public void setEncode(byte encode) {
	this.encode = encode;
}
public String getCommand() {
	return command;
}
public void setCommand(String command) {
	this.command = command;
}
public int getCommandLength() {
	return commandLength;
}
public void setCommandLength(int commandLength) {
	this.commandLength = commandLength;
}

}
