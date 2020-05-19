package com.project_study.my.common.utils;


import lombok.extern.slf4j.Slf4j;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
@Slf4j
public class ServerTools {

	private static List<String> ips = new ArrayList<String>();
	private static String ipStr;
	private static boolean loaded = false;

	public static void reloadServerInfo() {
//		loaded = false;
		loadServerInfo();
	}

	public static List<String> getServerIPs() {
		loadServerInfo();
		return ips;
	}

	public static String getServerIP() {
		loadServerInfo();
		return ipStr;
	}

	public static void loadServerInfo() {
		if (!loaded) {
			try {
				ips.clear();
				StringBuffer buffer = new StringBuffer();
				int i = 0;
				Enumeration<NetworkInterface> allNetInterfaces = NetworkInterface.getNetworkInterfaces();
				InetAddress ip = null;
				while (allNetInterfaces.hasMoreElements()) {
					NetworkInterface netInterface = allNetInterfaces.nextElement();
					System.out.println(netInterface.getName());
					Enumeration<InetAddress> addresses = netInterface.getInetAddresses();
					while (addresses.hasMoreElements()) {
						ip = addresses.nextElement();
						if (ip != null && ip instanceof Inet4Address) {
							String ipAddr = ip.getHostAddress();
							if (!"127.0.0.1".equals(ipAddr)) {
								ips.add(ipAddr);
								if (i > 0) {
									buffer.append(",");
								}
								i++;
								buffer.append(ipAddr);
							}
						}
					}
				}
				ipStr = buffer.toString();
			} catch (Exception ex) {
				log.error(ex.toString());
			}
			loaded = true;
		}
	}

}
