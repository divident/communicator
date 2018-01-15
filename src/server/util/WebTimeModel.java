package server.util;

import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.net.ntp.NTPUDPClient;
import org.apache.commons.net.ntp.TimeInfo;

public class WebTimeModel {

	public static String getCurrentDate() {
		try {
			String TIME_SERVER = "1a.ncomputers.org";
			NTPUDPClient timeClient = new NTPUDPClient();
			InetAddress inetAddress = InetAddress.getByName(TIME_SERVER);
			TimeInfo timeInfo = timeClient.getTime(inetAddress);
			long returnTime = timeInfo.getReturnTime();
			Date time = new Date(returnTime);
			SimpleDateFormat dt1 = new SimpleDateFormat("EEE, MMM d");
			return dt1.format(time);
		} catch (Exception e) {
			return "";
		}
	}
}