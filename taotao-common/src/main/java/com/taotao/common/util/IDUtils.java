package com.taotao.common.util;
import java.util.Random;
/**
 * @author chenlin
 */
public class IDUtils {
	public static String genImageName() {
		long millis = System.currentTimeMillis();
		Random random = new Random();
		int end3 = random.nextInt(999);
		return millis + String.format("%03d", end3);
	}

	/**
	 * 商品id生成
	 */
	public static long genItemId() {
		long millis = System.currentTimeMillis();
		Random random = new Random();
		int end2 = random.nextInt(99);
		String str = millis + String.format("%02d", end2);
		return Long.parseLong(str);
	}
}
