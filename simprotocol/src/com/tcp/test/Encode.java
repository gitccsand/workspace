package com.tcp.test;

public enum Encode {
GBK((byte)0),UTF8((byte)1);
private byte value = 1;

private Encode(byte valuse) {
	this.value = valuse;
}

public byte getValue() {
	return value;
}



}
