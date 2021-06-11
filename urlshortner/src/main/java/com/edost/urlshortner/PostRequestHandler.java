package com.edost.urlshortner;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.validator.routines.UrlValidator;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.google.gson.Gson;

public class PostRequestHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

	@Override
	public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent input, Context context) {
		APIGatewayProxyResponseEvent apiGatewayProxyResponseEvent = new APIGatewayProxyResponseEvent();
//		Map<String, String> pathParameters = input.getPathParameters();
//		System.out.println(pathParameters);
		
		try {
			String requestString = input.getBody();
			System.out.println(requestString);
			
			JSONParser parser = new JSONParser();

			JSONObject requestJsonObject = (JSONObject) parser.parse(requestString);

			Gson gson = new Gson();
		
			UrlModel url_model = gson.fromJson(requestJsonObject.toJSONString(), UrlModel.class);
			
			UrlValidator defaultValidator = new UrlValidator();
			
			Map<Object, Object> responseBody = new HashMap<Object, Object>();
			if (defaultValidator.isValid(url_model.getLongUrl())) {
				responseBody.put("respone", (JSONObject) new JSONParser().parse(new Gson().toJson(new UrlServiceImplimentation().createShortner(url_model.getLongUrl()))));
				generateResponse(apiGatewayProxyResponseEvent, new JSONObject(responseBody).toJSONString());
			}else {
				responseBody.put("respone", (JSONObject) new JSONParser().parse(new Gson().toJson(new ErrorResponse("Wrong url"))));
				generateResponse(apiGatewayProxyResponseEvent, new JSONObject(responseBody).toJSONString());
			}
			
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		//		apiGatewayProxyResponseEvent.setBody(requestString + " "+ pathParameters);
		
		return apiGatewayProxyResponseEvent;
	}
	
	private void generateResponse(APIGatewayProxyResponseEvent apiGatewayProxyResponseEvent, String requestMessage) {
		apiGatewayProxyResponseEvent
				.setHeaders(Collections.singletonMap("timeStamp", String.valueOf(System.currentTimeMillis())));
		apiGatewayProxyResponseEvent.setHeaders(Collections.singletonMap("Access-Control-Expose-Headers",
				"Access-Control-*, Origin, X-Requested-With, Content-Type, Accept, Authorization"));
		apiGatewayProxyResponseEvent
				.setHeaders(Collections.singletonMap("Access-Control-Allow-Origin", "*"));
		apiGatewayProxyResponseEvent
				.setHeaders(Collections.singletonMap("Access-Control-Allow-Origin", "*"));
		apiGatewayProxyResponseEvent.setHeaders(Collections.singletonMap("Access-Control-Allow-Methods",
				"HEAD, GET, POST, OPTIONS, PUT, PATCH, DELETE"));
		apiGatewayProxyResponseEvent.setHeaders(Collections.singletonMap("Access-Control-Allow-Headers",
				"Access-Control-*, Origin, X-Requested-With, Content-Type, Accept, Authorization"));
		apiGatewayProxyResponseEvent.setHeaders(Collections.singletonMap("Access-Control-Allow-Credentials", "true"));

		apiGatewayProxyResponseEvent.setStatusCode(200);
		apiGatewayProxyResponseEvent.setBody(requestMessage);
	}

}
