//package crEmgcy;
//import java.util.Iterator;
//import java.util.List;
//
//import org.elasticsearch.index.query.QueryStringQueryBuilder;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import com.emgcy.core.Application;
//import com.emgcy.core.system.es.bean.Article;
//import com.emgcy.core.system.es.dao.ArticleSearchRepository;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest( classes = Application.class )
//public class TestelasticaearchApplicationTests {
//    
//	@Autowired
//    private ArticleSearchRepository articleSearchRepository;
//
//
//    @Test
//    public void testSearch() {
//        String queryString = "springboot 书籍";//搜索关键字
//        QueryStringQueryBuilder builder = new QueryStringQueryBuilder(queryString);
//        Iterable<Article> searchResult = articleSearchRepository.search(builder);
//        Iterator<Article> iterator = searchResult.iterator();
//        while (iterator.hasNext()) {
//            System.out.println(iterator.next());
//        }
//    }
//    
//    @Test
//    public void testSearchTitle(){
//        String queryString="springboot";//搜索关键字
//        List<Article> searchResult = articleSearchRepository.findByTitle(queryString);
//        Iterator<Article> iterator = searchResult.iterator();
//        while(iterator.hasNext()){
//            System.out.println(iterator.next());
//        }
//    }
//}