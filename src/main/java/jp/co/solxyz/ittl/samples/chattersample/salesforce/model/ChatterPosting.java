package jp.co.solxyz.ittl.samples.chattersample.salesforce.model;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Chatter送信を行うためのBean
 * @author Hikaru Sato
 *
 */
@Data
public class ChatterPosting {

	@Getter @Setter
	private Body body;
	
	@Getter @Setter
	private String feedElementType;
	
	@Getter @Setter
	private String subjectId;
	
	@Data
	@AllArgsConstructor
	public class MessageSegment{
		@Getter @Setter
		private String type;
		@Getter @Setter
		private String text;
	}
	
	@Data
	@NoArgsConstructor
	public class Body{
		@Getter @Setter
		private List<MessageSegment> messageSegments;
	}
	
	/**
	 * Constructor
	 * @param message posting message
	 */
	public ChatterPosting(String message){
		this.body = new Body();
		this.body.messageSegments = new ArrayList<ChatterPosting.MessageSegment>();
		this.body.messageSegments.add(new MessageSegment("text", message));
		
		this.feedElementType = "FeedItem";
		this.subjectId = "me";
	}
	
	/**
	 * Constructor
	 * @param message posting message
	 */
	public ChatterPosting(String message, String subjectId){
		this.body = new Body();
		this.body.messageSegments = new ArrayList<ChatterPosting.MessageSegment>();
		this.body.messageSegments.add(new MessageSegment("text", message));
		
		this.feedElementType = "FeedItem";
		this.subjectId = subjectId;
	}
	
	
}
