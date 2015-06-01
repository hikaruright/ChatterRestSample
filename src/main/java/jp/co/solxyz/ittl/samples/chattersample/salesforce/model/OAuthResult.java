package jp.co.solxyz.ittl.samples.chattersample.salesforce.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;

/**
 * OAuth情報を格納
 * @author Hikaru Sato
 *
 */
@Data
@AllArgsConstructor
@ToString
public class OAuthResult{
	
	@Getter private String id;
	@Getter private String issued_at;
	@Getter private String instance_url;
	@Getter private String signature;
	@Getter private String access_token;
	
}