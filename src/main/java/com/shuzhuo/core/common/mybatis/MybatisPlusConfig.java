package com.shuzhuo.core.common.mybatis;

import javax.sql.DataSource;

import com.shuzhuo.core.common.config.ApplicationYmlConfig;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import com.baomidou.mybatisplus.entity.GlobalConfiguration;
import com.baomidou.mybatisplus.mapper.LogicSqlInjector;
import com.baomidou.mybatisplus.plugins.OptimisticLockerInterceptor;
import com.baomidou.mybatisplus.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.plugins.PerformanceInterceptor;
import com.baomidou.mybatisplus.plugins.SqlExplainInterceptor;
import com.baomidou.mybatisplus.spring.MybatisMapperRefresh;
import com.baomidou.mybatisplus.spring.MybatisSqlSessionFactoryBean;

/**
 *
 * @author chenshuzhuo
 * @date 2017-11-02
 * @version 0.2
 */
@Configuration
@EnableTransactionManagement
@MapperScan("com.pjDemo.core.system.**.mapper*")
public class MybatisPlusConfig {

	private static final Logger logger = LoggerFactory.getLogger(MybatisPlusConfig.class);

	private static final String MAPPER_LOCATIONS = "classpath:mybatis/**/*Mapper.xml";

	@Autowired
	private ApplicationYmlConfig applicationYmlConfig;

	@Bean
	public MybatisMapperRefresh mybatisMapperRefresh(SqlSessionFactory sqlSessionFactory) throws Exception{
		if(logger.isDebugEnabled()){
			logger.debug("mybatis mapperRefresh enabled:{}", applicationYmlConfig.isMapperRefresh());
		}
		return new MybatisMapperRefresh(sqlSessionFactory, applicationYmlConfig.isMapperRefresh());
	}

	@Bean
	public PerformanceInterceptor performanceInterceptor() {
		PerformanceInterceptor performanceInterceptor = new PerformanceInterceptor();
		performanceInterceptor.setMaxTime(5000);
		performanceInterceptor.setWriteInLog(true);
		return performanceInterceptor;
	}

	@Bean
	public SqlExplainInterceptor sqlExplainInterceptor() {
		SqlExplainInterceptor sqlExplainInterceptor = new SqlExplainInterceptor();
		sqlExplainInterceptor.setStopProceed(true);
		return sqlExplainInterceptor;
	}

	@Bean
	public PaginationInterceptor paginationInterceptor() {
		PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
		paginationInterceptor.setDialectType("mysql");
		return paginationInterceptor;
	}

	@Bean
	public OptimisticLockerInterceptor optimisticLockerInterceptor() {
		return new OptimisticLockerInterceptor();
	}

	@Bean
	public GlobalConfiguration globalConfiguration() {
		GlobalConfiguration globalConfig = new GlobalConfiguration(new LogicSqlInjector());
		globalConfig.setLogicDeleteValue("-1");
		globalConfig.setLogicNotDeleteValue("0");
		globalConfig.setRefresh(true);
		globalConfig.setCapitalMode(true);
		globalConfig.setFieldStrategy(2);
		globalConfig.setDbColumnUnderline(true);
		globalConfig.setMetaObjectHandler(new MyMetaObjectHandler());
		return globalConfig;
	}

	@Bean
	public SqlSessionFactory sqlSessionFactory(@Qualifier(value = "globalConfiguration") GlobalConfiguration globalConfiguration,@Qualifier(value = "dataSource") DataSource dataSource) throws Exception {
		String configLocation = "classpath:mybatis-sqlconfig.xml";
		String typeAliasesPackage = "com.pjDemo.core.system.**.entity";
		MybatisSqlSessionFactoryBean sqlSessionFactory = new MybatisSqlSessionFactoryBean();
		sqlSessionFactory.setDataSource(dataSource);
		sqlSessionFactory.setGlobalConfig(globalConfiguration);
		Interceptor[] interceptor = { paginationInterceptor(), sqlExplainInterceptor(), performanceInterceptor(),optimisticLockerInterceptor() };
		sqlSessionFactory.setPlugins(interceptor);
		ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		try {
			sqlSessionFactory.setMapperLocations(resolver.getResources(MAPPER_LOCATIONS));
			sqlSessionFactory.setConfigLocation(resolver.getResource(configLocation));
			sqlSessionFactory.setTypeAliasesPackage(typeAliasesPackage);
			return sqlSessionFactory.getObject();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

}
