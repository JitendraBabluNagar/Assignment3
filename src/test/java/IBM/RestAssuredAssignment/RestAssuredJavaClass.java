package IBM.RestAssuredAssignment;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertTrue;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import Util.DataProviderExcel;
import Util.NestedDataProviderExcel;
import io.restassured.RestAssured;
import io.restassured.response.Response;
//import pojopackage.pojoclass;

public class RestAssuredJavaClass{
	

	@Test(enabled = false, dataProvider = "valuesfromExcel")
	public void datasourceexample(String firstname, String lastname, String stream) {
		RestAssured.baseURI = "http://localhost:3000/";
		JSONObject rootbodyobject = new JSONObject();

		/*
		 * System.out.println("F"+ firstname); System.out.println("F"+ lastname);
		 * System.out.println("F"+ stream);
		 */
		rootbodyobject.put("firstname", firstname);
		rootbodyobject.put("lastname", lastname);
		rootbodyobject.put("stream", stream);

		given().contentType("Application/JSON").body(rootbodyobject.toJSONString()).when().post("students")
				.then().log().all();

	}
	
	@DataProvider(name = "valuesfromExcel")
	  public Object[][] exceldata() throws IOException { 
		  Object[][] data = DataProviderExcel.gettestdata(); 
	  return data;
	  
	  }
	
	@DataProvider(name = "NestedvaluesfromExcel")	
	  public Object[][] Nestedexceldata() throws IOException { 
		  Object[][] nesteddata = NestedDataProviderExcel.gettestdata(); 
	  return nesteddata;
	  
	  }
	 
	  

	@Test(enabled = false)
	public void soapexample() throws IOException {
		RestAssured.baseURI = "http://www.dneonline.com";

		FileInputStream fis = new FileInputStream(".\\Payload\\addreq.xml");

		given()
			.headers("content-type","text/xml")
			.body(IOUtils.toString(fis, "UTF-8")).
		when()
			.post("/calculator.asmx").
		then().assertThat().statusCode(200)
			.log().all();
	}
	
	@Test(enabled = true, dataProvider = "NestedvaluesfromExcel")
	public void jsonbodyNested(String firstname, String lastname, String Nickname) {

		RestAssured.baseURI = "http://localhost:3000/";
		JSONObject rootbodyobject = new JSONObject();
		JSONObject categoryobject = new JSONObject();
		JSONObject tagsobject = new JSONObject();
		rootbodyobject.put("id", 0);
		rootbodyobject.put("name", firstname);
		rootbodyobject.put("status", "available");

		String name = "Jitu";
		categoryobject.put("id", 0);
		categoryobject.put("name", lastname);

		tagsobject.put("id", 0);
		tagsobject.put("name", Nickname);

		// Adding the Category and Tags object into the Rootbody
		rootbodyobject.put("category", categoryobject);
		rootbodyobject.put("tags", tagsobject);

		// JSON Array Body
		JSONArray arraybody = new JSONArray();
		arraybody.add("AnyValue");
		arraybody.add("values1");

		// Adding the Arrayobject into Root body
		rootbodyobject.put("photoUrls", arraybody);

		System.out.println(rootbodyobject);

		
		/*
		 * given() .body(rootbodyobject.toJSONString())
		 * .headers("content-type","Application/JSON"). when() .post("students"). then()
		 * .log() .all();
		 */
		
		given().contentType("Application/JSON").body(rootbodyobject.toJSONString()).when().post("students")
		.then().log().all();
		
	}



}
