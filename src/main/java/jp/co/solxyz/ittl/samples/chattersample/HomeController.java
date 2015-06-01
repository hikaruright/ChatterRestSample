package jp.co.solxyz.ittl.samples.chattersample;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import jp.co.solxyz.ittl.samples.chattersample.salesforce.OAuthConnector;
import jp.co.solxyz.ittl.samples.chattersample.salesforce.model.ChatterPosting;
import jp.co.solxyz.ittl.samples.chattersample.salesforce.model.OAuthResult;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.gson.Gson;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	  private static final String username="";
	  private static final String password="";
	  private static final String securityToken="";
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		OAuthConnector connector = new OAuthConnector();
		
		// ログインを実施
		OAuthResult _result = connector.login(username, password, securityToken);
		logger.info(_result.toString());
		
		// JSONに変換
		String jsonBody = new Gson().toJson(new ChatterPosting("This is a posted from connected application"));
		logger.info("---------------SEND----------------------");
		logger.info(jsonBody);
		logger.info("-----------------------------------------");
		
		// Chatter送信
		String postResult = connector.sendWithPost("/services/data/v32.0/chatter/feed-elements",jsonBody , _result);
		logger.info(postResult);
		model.addAttribute("postResult", postResult);		
		return "home";
	}
	
}
