package utils;

import java.util.Random;

public class Utils {
	public static String generateRandomColor() {
		Random rd = new Random();
		String r = Integer.toHexString(rd.nextInt(156)).toUpperCase();
		String g = Integer.toHexString(rd.nextInt(156)).toUpperCase();
		String b = Integer.toHexString(rd.nextInt(156)).toUpperCase();
			
		// 编码有效性控制
		r = r.length() == 1 ? "0" + r : r;
		g = g.length() == 1 ? "0" + g : g;
		b = b.length() == 1 ? "0" + b : b;
			
		// 拼接
		return '#'+r + g + b;
	}
}
