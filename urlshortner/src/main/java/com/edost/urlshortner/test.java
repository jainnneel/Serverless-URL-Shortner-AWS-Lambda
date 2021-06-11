package com.edost.urlshortner;

import org.apache.commons.validator.routines.UrlValidator;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.gson.Gson;

public class test {
//  public String handleRequest(Object input, Context context) {
//  context.getLogger().log("Input: " + input);
//  
//  
// 
//  return "Hello from Lambda!";
//}


	public static void main(String[] args) throws ParseException {
		UrlServiceImplimentation implimentation = new UrlServiceImplimentation();
		
//		System.out.println(implimentation.createShortner("https://fsfdsvssfsdf.dsfds.sdfsdf/fdsfdsf/fdsfsdf"));
//		System.out.println(implimentation.getUrl("344674"));
//		implimentation.urlExistOrNot("https://fsfdsssfsssssdf.dsfds.sdfsdf/fdsfdsf/fdsfsdf");
		
		UrlValidator defaultValidator = new UrlValidator();
		
		System.out.println(defaultValidator.isValid("rtwz5qhjf.execute-api.ap-south-1.amazonaws.com/edost/875270"));
		
		JSONParser parser = new JSONParser();

		JSONObject requestJsonObject = (JSONObject) parser.parse("{\r\n"
				+ "    \"longUrl\": \"dsadsadsadsadsadsadsadsadsadsadsadsadsadsadsadsadsadsadsadsadsadsadsadsadsadsadsadsadsadsadsadsadsadsadsadsadsadsadsadsadsadsadsadsadsadsadsadsadsadsadsadsadsadsadsadsadsadsadsadsadsadsadsadsadsadsadsadsadsadsadsadsadsadsadsadsadsadsadsadsadsa.dsa\"\r\n"
				+ "}");

		Gson gson = new Gson();
	
		UrlModel url_model = gson.fromJson(requestJsonObject.toJSONString(), UrlModel.class);
		System.out.println(implimentation.getUrl("875270").getLongUrl().substring(8));
		
	}
}
