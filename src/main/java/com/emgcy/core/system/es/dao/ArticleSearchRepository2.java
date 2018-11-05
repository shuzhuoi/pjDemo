//package com.emgcy.core.system.es.dao;
//
//import java.util.List;
//
//import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
//
//import com.emgcy.core.system.es.bean.Article;
//
//
//public interface ArticleSearchRepository2 extends ElasticsearchRepository<Article, Long> {
//
//	 List<Article> findByTitle(String title);
//	 
//	 List<Article> findByAbstracts(String title);
//}