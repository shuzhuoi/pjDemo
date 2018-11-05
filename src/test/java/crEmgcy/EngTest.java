//package crEmgcy;
//import javax.ws.rs.client.Client;
//import javax.ws.rs.client.ClientBuilder;
//import javax.ws.rs.client.Entity;
//import javax.ws.rs.client.WebTarget;
//import javax.ws.rs.core.MediaType;
//import javax.ws.rs.core.Response;
//
//import org.junit.Test;
//
//import com.emgcy.core.interfaces.restful.web.test.User;
//import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
//
//public class EngTest {
//	private static String serverUri = "http://localhost:8081/crEmgcy/rest";
//	 /**  
//     * @param args  
//     */  
//    public static void main(String[] args) {  
//        User user = new User("006", "tianyi", "12355123891");  
////        addUser(user);  
////        getAllUsers();  
////        user = new User("006", "tianyi", "33");  
////        String id = user.getId();  
////        updateUser(user);  
////        getUserById(id);  
////        getAllUsers();  
////        delUser(id);  
////        getAllUsers();  
//  
//    }  
//  
//    /**  
//     * 添加用户  
//     */  
//    @Test
//    public void addUser() {
//    	 User user = new User("006", "tianyi", "12355123891");  
////    	private void addUser(User user) {  
//        System.out.println("****增加用户addUser****");  
//  
//        Client client = ClientBuilder.newClient();  
//        WebTarget target = client.target(serverUri + "/users");  
//        Response response = target.request()  
//                .buildPost(Entity.entity(user, MediaType.APPLICATION_JSON))  
//                .invoke();  
//        response.close();  
//    }  
//  
//    /**  
//     * 删除用户  
//     */  
//    private static void delUser(String id) {  
//        System.out.println("****删除用户****");  
//        Client client = ClientBuilder.newClient();  
//        WebTarget target = client.target(serverUri + "/users/" + id);  
//        Response response = target.request().delete();  
//        response.close();  
//    }  
//  
//    /**  
//     * 修改用户  
//     */  
//    private static void updateUser(User user) {  
//        System.out.println("****修改用户updateUser****");  
//  
//        Client client = ClientBuilder.newClient();  
//        WebTarget target = client.target(serverUri + "/users");  
//        Response response = target.request()  
//                .buildPut(Entity.entity(user, MediaType.APPLICATION_JSON))  
//                .invoke();  
//        response.close();  
//    }  
//  
//    /**  
//     * 根据id查询用户  
//     */  
//    private static void getUserById(String id) {  
//        System.out.println("****根据id查询用户****");  
//        Client client = ClientBuilder.newClient().register(  
//                JacksonJsonProvider.class);// 注册json 支持  
//        WebTarget target = client.target(serverUri + "/users/" + id);  
//        Response response = target.request().get();  
//        User user = response.readEntity(User.class);  
//        System.out.println(user.getId() + user.getUserName());  
//        response.close();  
//    }  
//  
//    /**  
//     * 查询所有用户  
//     */  
//    private static void getAllUsers() {  
//        System.out.println("****查询所有getAllUsers****");  
//  
//        Client client = ClientBuilder.newClient();  
//  
//        WebTarget target = client.target(serverUri + "/users");  
//        Response response = target.request().get();  
//        String value = response.readEntity(String.class);  
//        System.out.println(value);  
//        response.close(); // 关闭连接  
//    }  
//  
//}  