package jp.co.solxyz.ittl.samples.chattersample.salesforce;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

import jp.co.solxyz.ittl.samples.chattersample.HomeController;
import jp.co.solxyz.ittl.samples.chattersample.salesforce.model.ChatterPosting;
import jp.co.solxyz.ittl.samples.chattersample.salesforce.model.OAuthResult;

public class OAuthConnector {
	
	private static final Logger logger = LoggerFactory.getLogger(OAuthConnector.class);

	private String loginEndpoint = "https://login.salesforce.com/services/oauth2/token";

	private static final String postFormat = "grant_type=password&client_id=%s&client_secret=%s&username=%s&password=%s%s";

	private static final String consumerKey = "3MVG9ZL0ppGP5UrAPxRqNrjRd6qHaHVXa4X4BZkfPOkWHdA.Nu24rByD9VQCD4FHOBl6CvVlt1vGozn17el.b";
	private static final String secretKey = "7032920670240309058";
	protected static final String callbackUrl = "quester://oauth/done";

	public OAuthResult login(String userid, String password,
			String securityToken) {

		HttpURLConnection connection = null;
		BufferedReader br = null;

		OAuthResult result = null;

		try {
			URL url = new URL(loginEndpoint);
			connection = (HttpURLConnection) url.openConnection();

			connection.setRequestMethod("POST");
			connection.setDoInput(true);
			connection.setDoOutput(true);

			// securityToken is null?
			if (securityToken == null)
				securityToken = "";

			String postMessage = String.format(postFormat, consumerKey,
					secretKey, userid, password, securityToken);

			PrintStream ps = new PrintStream(connection.getOutputStream());
			ps.print(postMessage);
			ps.close();

			// レスポンスを受信する
			int iResponseCode = connection.getResponseCode();

			// 接続が確立したとき
			if (iResponseCode == HttpURLConnection.HTTP_OK) {
				// StringBuilder resultBuilder = new StringBuilder();
				// String line = "";

				br = new BufferedReader(new InputStreamReader(
						connection.getInputStream()));

				// Responseを変換
				Gson gson = new Gson();
				result = gson.fromJson(br, OAuthResult.class);
			}
			// 接続が確立できなかったとき
			else {
				logger.info("======== HTTP STATUS is "+iResponseCode);
				
				result = null;
			}

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return result;
	}
	
	public void postToChatter(OAuthResult credential, String message){
		ChatterPosting post = new ChatterPosting(message);
		
	}

	/**
	 * POST送信を行う
	 * @param path path
	 * @param body 本文
	 * @param credential 認証情報
	 */
	public String sendWithPost(String path, String body, OAuthResult credential){
		
		HttpURLConnection connection = null;
		BufferedReader br = null;
	
		String result = null;;
			
		try {
			URL url = new URL(credential.getInstance_url() + path);
			
			connection = (HttpURLConnection) url.openConnection();

			connection.setRequestMethod("POST");
			connection.setDoInput(true);
			connection.setDoOutput(true);

			String postMessage = body;
			
			PrintStream ps = new PrintStream(connection.getOutputStream());
			ps.print(postMessage);
			ps.close();

			// レスポンスを受信する
			int iResponseCode = connection.getResponseCode();

			// 接続が確立したとき
			if (iResponseCode == HttpURLConnection.HTTP_OK) {
				// StringBuilder resultBuilder = new StringBuilder();
				// String line = "";

				br = new BufferedReader(new InputStreamReader(
						connection.getInputStream()));
				
				StringBuilder builder = new StringBuilder();
				
				String _line = null;
				
				while((_line = br.readLine()) != null){
					builder.append(_line+"\r\n");
				}
				
				result = builder.toString();

			}
			// 接続が確立できなかったとき
			else {
				result = null;
			}

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
}
