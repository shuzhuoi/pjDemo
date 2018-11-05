//package crEmgcy;
//
//import static org.junit.Assert.assertEquals;
//
//import javax.ws.rs.client.Client;
//import javax.ws.rs.core.MultivaluedMap;
//
//import org.apache.catalina.WebResource;
//import org.glassfish.jersey.client.ClientConfig;
//import org.junit.Test;
//
//import com.alibaba.fastjson.JSONException;
//import com.alibaba.fastjson.JSONObject;
//
////public class test {
////	private ClientConfig config = new DefaultClientConfig();  
////    private Client client = Client.create(config);  
////    private MultivaluedMap<String, String> param = new MultivaluedMapImpl();  
////    private WebResource webResource = client.resource("http://localhost:8080/sdkapp"); 
////    
////    @Test  
////    public void testGetUid() throws JSONException {  
////          
////        param.add("token", "123");  
////        param.add("appid", "123");  
////        param.add("physical_id_type", "123");  
////        param.add("physical_id", "123");  
////          
////  
////        String json = webResource.path("/mapi").post(String.class,param); 
////        JSONObject dataJson = new JSONObject(json);  
////        assertEquals("1000", dataJson.get("return_code"));  
////  
////    }  
//}
