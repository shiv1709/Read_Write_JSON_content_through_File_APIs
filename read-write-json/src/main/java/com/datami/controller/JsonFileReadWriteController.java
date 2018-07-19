package com.datami.controller;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 
 * @author Shiv Pratap Singh
 *
 */

@Api(tags = " JSON File Read Write Controller")
@RestController
public class JsonFileReadWriteController {
	
	@ApiOperation(value = "Write JSON to file", produces = "application/json", consumes = "application/json")
	@RequestMapping(value = "/writeToFile/filename/{filename}", method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<JSONObject> insertJsonToFile(
			final @RequestBody String requestBody,
			@ApiParam(value = "filename", required = true) final @PathVariable String filename) throws IOException, ParseException {
			BufferedWriter out = null;
			JSONObject jsonObject = new JSONObject();
			try {
				FileWriter fstream = new FileWriter("/Users/shivpratapsingh/Desktop/" + filename + ".json",true); //true tells to append data.
			    out = new BufferedWriter(fstream);
			    
			    JSONParser parser = new JSONParser();
			    Object obj = parser.parse(requestBody);
			    jsonObject =  (JSONObject) obj;
			    
			    out.write(jsonObject.toJSONString());
				//System.out.println("Successfully Written JSON Object to File");
				//System.out.println("\nJSON Object: " + requestBody);
			    
			    return new ResponseEntity<JSONObject>(jsonObject, HttpStatus.OK);
			
		}
		catch( IOException ex) {
			ex.printStackTrace();
		}
		finally
		{
		    if(out != null) {
		        out.close();
		    }
		}
			
			JSONObject output = new JSONObject();
	        output.put("Error", "FileNotFoundException/IOException/Any other Exception");
	        return new ResponseEntity<JSONObject>(output,HttpStatus.NOT_FOUND);
				
}
	
	@ApiOperation(value = "Read JSON from file", produces = "application/json")
	@RequestMapping(value = "/read/filename/{filename}", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody ResponseEntity<JSONObject> readJsonFromFile(
			@ApiParam(value = "filename", required = true) final @PathVariable String filename) throws ParseException {
			
		JSONParser parser = new JSONParser();

        try {
        	Object obj = parser.parse(new FileReader("/Users/shivpratapsingh/Desktop/" + filename + ".json"));

            JSONObject jsonObject =  (JSONObject) obj ;
            
            return new ResponseEntity<JSONObject>(jsonObject, HttpStatus.OK);
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch(Exception e) {
        	e.printStackTrace();
        }
		//return ResponseEntity.ok(HttpStatus.OK);
        JSONObject output = new JSONObject();
        output.put("Error", "FileNotFoundException/IOException/Any other Exception");
        return new ResponseEntity<JSONObject>(output,HttpStatus.NOT_FOUND);
	}

}
