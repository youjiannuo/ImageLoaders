package com.yjn.text;
public class JniClient {
	static {
		System.loadLibrary("yjn_ndk");
	}
public static native int []getFasetBlurPix(int pixs[] , int w, int h , int radius);
}