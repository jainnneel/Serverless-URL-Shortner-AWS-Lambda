package com.edost.urlshortner;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.google.gson.Gson;

public class GetRequestHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

	public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent input, Context context) {
		APIGatewayProxyResponseEvent apiGatewayProxyResponseEvent = new APIGatewayProxyResponseEvent();
//		Map<String, String> pathParameters = input.getPathParameters();
//		System.out.println(pathParameters);
		
		try {
			Map<String, String> map = input.getPathParameters();
			
			String ids = map.get("uniqueId");
			
			Map<Object, Object> responseBody = new HashMap<Object, Object>();
			
			UrlModel url = new UrlServiceImplimentation().getUrl(ids);
			String urls = "";
			if (url != null) {
				urls = "//"+url.getLongUrl();
				System.out.println(urls);
				if (!urls.equals("")) {
//					responseBody.put("respone", (JSONObject) new JSONParser().parse(new Gson().toJson(url)));
					urls = urls.substring(8);
					apiGatewayProxyResponseEvent.setBody(urls);
				}
			}else {
				String messString = "this url not exists..";
				responseBody.put("respone", (JSONObject) new JSONParser().parse(new Gson().toJson(new ErrorResponse(messString))));
			}
			System.out.println(urls);
			apiGatewayProxyResponseEvent
					.setHeaders(Collections.singletonMap("Location", urls));
			apiGatewayProxyResponseEvent
					.setHeaders(Collections.singletonMap("timeStamp", String.valueOf(System.currentTimeMillis())));
			apiGatewayProxyResponseEvent.setHeaders(Collections.singletonMap("Access-Control-Expose-Headers",
					"Access-Control-*, Origin, X-Requested-With, Content-Type, Accept, Authorization , Location"));
			apiGatewayProxyResponseEvent
					.setHeaders(Collections.singletonMap("Access-Control-Allow-Origin", "*"));
			apiGatewayProxyResponseEvent
					.setHeaders(Collections.singletonMap("Access-Control-Allow-Origin", "*"));
			apiGatewayProxyResponseEvent.setHeaders(Collections.singletonMap("Access-Control-Allow-Methods",
					"HEAD, GET, POST, OPTIONS, PUT, PATCH, DELETE"));
			apiGatewayProxyResponseEvent.setHeaders(Collections.singletonMap("Access-Control-Allow-Headers",
					"Access-Control-*, Origin, X-Requested-With, Content-Type, Accept, Authorization, Location"));
			apiGatewayProxyResponseEvent.setHeaders(Collections.singletonMap("Access-Control-Allow-Credentials", "true"));

			apiGatewayProxyResponseEvent.setStatusCode(302);
//			generateResponse(apiGatewayProxyResponseEvent, new JSONObject(responseBody).toJSONString(),urls);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		//		apiGatewayProxyResponseEvent.setBody(requestString + " "+ pathParameters);
		
		return apiGatewayProxyResponseEvent;
	}
	
	private void generateResponse(APIGatewayProxyResponseEvent apiGatewayProxyResponseEvent, String requestMessage,String urls) {
		System.out.println(urls);
		apiGatewayProxyResponseEvent
				.setHeaders(Collections.singletonMap("Location", urls));
		apiGatewayProxyResponseEvent
				.setHeaders(Collections.singletonMap("timeStamp", String.valueOf(System.currentTimeMillis())));
		apiGatewayProxyResponseEvent.setHeaders(Collections.singletonMap("Access-Control-Expose-Headers",
				"Access-Control-*, Origin, X-Requested-With, Content-Type, Accept, Authorization , Location"));
		apiGatewayProxyResponseEvent
				.setHeaders(Collections.singletonMap("Access-Control-Allow-Origin", "*"));
		apiGatewayProxyResponseEvent
				.setHeaders(Collections.singletonMap("Access-Control-Allow-Origin", "*"));
		apiGatewayProxyResponseEvent.setHeaders(Collections.singletonMap("Access-Control-Allow-Methods",
				"HEAD, GET, POST, OPTIONS, PUT, PATCH, DELETE"));
		apiGatewayProxyResponseEvent.setHeaders(Collections.singletonMap("Access-Control-Allow-Headers",
				"Access-Control-*, Origin, X-Requested-With, Content-Type, Accept, Authorization, Location"));
		apiGatewayProxyResponseEvent.setHeaders(Collections.singletonMap("Access-Control-Allow-Credentials", "true"));

		apiGatewayProxyResponseEvent.setStatusCode(302);
		apiGatewayProxyResponseEvent.setBody(requestMessage);
	}

}