package com.edost.urlshortner;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.List;
import java.util.Optional;

import org.apache.commons.validator.routines.UrlValidator;
import org.joda.time.LocalDate;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;

public class UrlServiceImplimentation {

	
	DynamoDBMapper mapper = DynamoConfig.mapper();
	
	
	
	public UrlModel getUrl(String urlid) {
		try {
			UrlModel load = mapper.load(UrlModel.class, urlid);
			if (load!=null) {
				return load;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	public UrlModel createShortner(String longUrl) {
		try {
//			 Map<String, String> expressionAttributesNames = new HashMap<>();
//			 expressionAttributesNames.put("#longUrl", "longUrl");
//			 
//			  Map<String, AttributeValue> expressionAttributeValues = new HashMap<>();
//		      expressionAttributeValues.put(":longUrl", new AttributeValue().withS(longUrl));
			  
//		      DynamoDBQueryExpression<UrlModel> queryExpression = new DynamoDBQueryExpression<UrlModel>()
//		    		  	.withIndexName("longUrl")
//		                .withKeyConditionExpression("#longUrl = :longUrl")
//		                .withExpressionAttributeNames(expressionAttributesNames)
//		                .withExpressionAttributeValues(expressionAttributeValues);
////		      mapper.scan(UrlModel.class, queryExpression);
//		      PaginatedQueryList<UrlModel> query = mapper.query(UrlModel.class, queryExpression);
//			 System.out.println(query);
			
			UrlModel model1 =  urlExistOrNot(longUrl);
			
			if(model1 != null) {
				return model1;
			}else {
				UrlModel model = new UrlModel();
				model.setUrlId(generateNumber());
				model.setLongUrl(longUrl);
				model.setDateCreated(LocalDate.now().toString());
				model.setDateExpire(LocalDate.now().plusDays(15).toString());
				mapper.save(model);
				return model;	
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
		
	}
	
	 UrlModel urlExistOrNot(String longUrl) {
		List<UrlModel> list = mapper.scan(UrlModel.class, new DynamoDBScanExpression());
		Optional<UrlModel> model1 = list.stream().filter((model) -> model.getLongUrl().equals(longUrl)).findAny();
		if (model1.isPresent()) {
			return model1.get();
		}else {
			return null;
		}
	}


	private String  generateNumber() {
        StringBuilder generatedOTP = new StringBuilder();
        SecureRandom secureRandom = new SecureRandom();

        try {

            secureRandom = SecureRandom.getInstance(secureRandom.getAlgorithm());

            for (int i = 0; i < 6; i++) {
                generatedOTP.append(secureRandom.nextInt(9));
            }
            if(generatedOTP.charAt(0)=='0') {
                generatedOTP.setCharAt(0, '7');
            }
            
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return generatedOTP.toString();
    }
}
