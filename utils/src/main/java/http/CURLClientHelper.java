package http;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class CURLClientHelper {

	public static String send(String url, String user) {
		String[] cmd = {"curl", "-i", "-u", user, "-H", "content-type:application/json", url};
		ProcessBuilder pb = new ProcessBuilder(cmd);
		pb.redirectErrorStream(true);
		StringBuilder sb = new StringBuilder();
		Process p = null;
		try {
			p = pb.start();
			BufferedReader br = null;
			String line = null;
			br = new BufferedReader(new InputStreamReader(p.getInputStream()));
			while((line = br.readLine()) != null) {
				sb.append(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sb.toString();
	}
	
	public static void main(String[] args) {
		System.out.println(send("http://192.168.31.123:15672/api/vhosts", "coreland:C0reL@nd-401"));
	}
}
