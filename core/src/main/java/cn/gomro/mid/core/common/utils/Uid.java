package cn.gomro.mid.core.common.utils;

import java.util.UUID;

public class Uid {
	public static String getUid()
	{		
		return UUID.randomUUID().toString();
	}
}
