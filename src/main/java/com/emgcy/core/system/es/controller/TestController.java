//package com.emgcy.core.system.es.controller;
//import java.util.Date;
//import java.util.Iterator;
//import java.util.List;
//
//import org.elasticsearch.index.query.QueryBuilders;
//import org.elasticsearch.index.query.QueryStringQueryBuilder;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
//import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
//import org.springframework.data.elasticsearch.core.query.SearchQuery;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.emgcy.core.system.es.bean.Article;
//import com.emgcy.core.system.es.bean.Author;
//import com.emgcy.core.system.es.bean.Tutorial;
//import com.emgcy.core.system.es.dao.ArticleSearchRepository2;
//
///**
// * Created by admin on 17/6/1.
// */
//@RestController
//public class TestController {
//    @Autowired
//    private ArticleSearchRepository2 articleSearchRepository;
//    
//    @Autowired
//    ElasticsearchTemplate elasticsearchTemplate;
//
//    @RequestMapping("/add")
//    public void testSaveArticleIndex() {
//        System.out.println("TestController.testSaveArticleIndex()");
//    	Author author = new Author();
//        author.setId(1L);
//        author.setName("tianshouzhi");
//        author.setRemark("java developer");
//
//        Tutorial tutorial = new Tutorial();
//        tutorial.setId(1L);
//        tutorial.setName("elastic search");
//
//        
//        Article article = new Article();
//        article.setId(1L);
//        article.setTitle("springboot integreate elasticsearch");
//        article.setAbstracts("springboot integreate elasticsearch is very easy");
//        article.setTutorial(tutorial);
//        article.setAuthor(author);
//        article.setContent("elasticsearch based on lucene,"
//                + "spring-data-elastichsearch based on elaticsearch"
//                + ",this tutorial tell you how to integrete springboot with spring-data-elasticsearch");
//        article.setPostTime(new Date());
//        article.setClickCount(1L);
//        
////        Article article1 = new Article();
////        article1.setId(1L);
////        article1.setTitle("springboot 书籍");
////        article1.setAbstracts("springboot的书");
////        article1.setTutorial(tutorial);
////        article1.setAuthor(author);
////        article1.setContent("elasticsearch based on lucene");
////        article1.setPostTime(new Date());
////        article1.setClickCount(1L);
//
//        articleSearchRepository.save(article);
////        articleSearchRepository.save(article1);
//    }
//    
//    @RequestMapping("/add2")
//    public void add2() {
//        System.out.println("22222222");
//    	Author author = new Author();
//        author.setId(1L);
//        author.setName("tianshouzhi22");
//        author.setRemark("java developer222");
//
//        Tutorial tutorial = new Tutorial();
//        tutorial.setId(1L);
//        tutorial.setName("elastic search222");
//
//        
//        Article article = new Article();
//        article.setId(1L);
//        article.setTitle("springboot2 integreate elasticsearch");
//        article.setAbstracts("springboot2 integreate elasticsearch is very easy");
//        article.setTutorial(tutorial);
//        article.setAuthor(author);
//        article.setContent("elasticsearch based on lucene,"
//                + "spring-data-elastichsearch based on elaticsearch"
//                + ",this tutorial tell you how to integrete springboot with spring-data-elasticsearch");
//        article.setPostTime(new Date());
//        article.setClickCount(1L);
//        
////        Article article1 = new Article();
////        article1.setId(1L);
////        article1.setTitle("springboot 书籍");
////        article1.setAbstracts("springboot的书");
////        article1.setTutorial(tutorial);
////        article1.setAuthor(author);
////        article1.setContent("elasticsearch based on lucene");
////        article1.setPostTime(new Date());
////        article1.setClickCount(1L);
//
//        articleSearchRepository.save(article);
////        articleSearchRepository.save(article1);
//    }
//
//    @RequestMapping("/query")
//    public void testSearch() {
//       System.out.println("TestController.testSearch()");
//    	String queryString = "springboot";//搜索关键字
//        QueryStringQueryBuilder builder = new QueryStringQueryBuilder(queryString);
//        Iterable<Article> searchResult = articleSearchRepository.search(builder);
//        Iterator<Article> iterator = searchResult.iterator();
//        while (iterator.hasNext()) {
//            System.out.println(iterator.next());
//        }
//    }
//    
//    @RequestMapping("/findByTitle")
//    public void findByTitle(){
//        String queryString="的书";//搜索关键字
//        List<Article> searchResult = articleSearchRepository.findByTitle(queryString);
//        Iterator<Article> iterator = searchResult.iterator();
//        while(iterator.hasNext()){
//            System.out.println(iterator.next());
//        }
//    }
//    
//    @RequestMapping("/findByAbstracts")
//    public void findByAbstracts(){
//    	String queryString="的书";//搜索关键字
//    	List<Article> searchResult = articleSearchRepository.findByAbstracts(queryString);
//    	Iterator<Article> iterator = searchResult.iterator();
//    	while(iterator.hasNext()){
//    		System.out.println(iterator.next());
//    	}
//    }
//    @RequestMapping("/findAll")
//    public void findAll(){
//    	Iterable<Article> findAll = articleSearchRepository.findAll();
//    	Iterator<Article> iterator = findAll.iterator();
//    	while(iterator.hasNext()){
//    		Article next = iterator.next();
//    		if (next!=null) {
//    			Author author = next.getAuthor();
//    			System.out.println(author.getName());
//			}
//    		System.out.println(next);
//    	}
//    }
//    
//    @RequestMapping("/del")
//    public void del(){
//    	System.out.println("del");
//    	Iterable<Article> findAll = articleSearchRepository.findAll();
//    	Iterator<Article> iterator = findAll.iterator();
//    	while(iterator.hasNext()){
//    		System.out.println(iterator.next());
//    	}
//    	 articleSearchRepository.delete(2L);
//    	 
//    	 System.out.println("-----after-----TestController.del()");
//    	findAll = articleSearchRepository.findAll();
//     	iterator = findAll.iterator();
//     	while(iterator.hasNext()){
//     		System.out.println(iterator.next());
//     	}
////    	 elasticsearchTemplate.
//    }
//    
//    @RequestMapping("/queryForList")
//    public void queryForList() {
////    	SearchQuery searchQuery = new NativeSearchQueryBuilder().
////    			withPageable(new PageRequest(1,1))
////    			.withQuery(QueryBuilders.queryStringQuery("spring boot OR 书籍")).build();
//    	 SearchQuery searchQuery=new NativeSearchQueryBuilder()
//    			 	  .withQuery(QueryBuilders.boolQuery()
//    			      .must(QueryBuilders.matchQuery("userId", 1))
//    			      .should(QueryBuilders.termQuery("userId",1)))
//    	              .build();
////    	  SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(boolQuery().must(termQuery("userId", 1)).should(rangeQuery("weight").lt(1)).must(matchQuery("title", 1))).build(); 
//    	List<Article> articles = elasticsearchTemplate.queryForList(searchQuery, Article.class);
//        for (Article article : articles) {
//            System.out.println(article.toString());
//        }
//       
//    }
//}