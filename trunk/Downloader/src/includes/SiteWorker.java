package includes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SiteWorker {

	public static StringBuilder lerSite(String site) {
		StringBuilder sb = new StringBuilder();
		URL url;
		
		try {
			url = new URL(site);

			InputStream is = url.openStream();
			InputStreamReader isr = new InputStreamReader(is, StandardCharsets.UTF_8);
			BufferedReader br = new BufferedReader(isr);
			String linha;
			while ( (linha = br.readLine()) != null) {
				sb.append(linha+"\n");
			}

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return sb;
	}
}
