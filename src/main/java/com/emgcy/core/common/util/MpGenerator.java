package com.emgcy.core.common.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.FileOutConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.TemplateConfig;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.config.rules.DbType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.subversion.core.SubVersion;

/**
 * <p>
 * mybatis plus 代码自动生成工具类
 * </p>
 * 
 * @author chenshuzhuo
 * @date 2017-10-18
 * @version 0.3
 */
public class MpGenerator {

	// 数据库配置
	// private static String JDBC_URL = "jdbc:mysql://127.0.0.1:3306/cr_emgcy?characterEncoding=utf8";
	private static String JDBC_URL = "jdbc:mysql://192.168.1.20:3306/cr_emgcy?characterEncoding=utf8";

	private static String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	private static String JDBC_USERNAME = "root";
	private static String JDBC_PASSWORD = "123456";

	// 基本where true:默认不开启
	private static boolean baseFlag = false;

	// mybatis plus where 默认开启
	private static boolean ewFlag = true;

	// 一键生成curd
	private static boolean controllerAuto = true;

	// 生成的包路径
	private static String PACKAGEDIR = "com.emgcy.core.system";

	// xml 生成路径
	private static String MYBATIS_XML = System.getProperty("user.dir") + "/src/main/resources/mybatis/";

	
	/**
	 * <p>
	 * 跟据数据库表自动生成java代码和mapper文件
	 * </p>
	 * 
	 */
	public static void main(String[] args) {
		// 生成test表
		String[] tabArr = new String[] { "cr_video_praise" };
		// 模块名称
		String moduleName = "video";
		// 修改为开启状态
		// baseFlag=true;

		// 修改为自动生成curd
//		 controllerAuto=true;

		execute(moduleName, tabArr);
	}

	/**
	 * 执行
	 * 
	 * @param moduleName
	 *            模块名
	 * @param tabArr
	 *            生成的表
	 */
	private static void execute(String moduleName, String[] tabArr) {

		AutoGenerator mpg = new AutoGenerator();

		// 全局配置
		GlobalConfig gc = new GlobalConfig();
		gc.setOutputDir(System.getProperty("user.dir") + "/src/main/java");
//		gc.setFileOverride(true);
		gc.setActiveRecord(false);
		gc.setEnableCache(false);
		gc.setBaseResultMap(true);
		gc.setBaseColumnList(true);
		gc.setOpen(false);
		gc.setAuthor(SubVersion.getInstance().getCurrentConnectName());
		mpg.setGlobalConfig(gc);

		// 数据源配置
		DataSourceConfig dsc = new DataSourceConfig();
		dsc.setDbType(DbType.MYSQL);
		dsc.setTypeConvert(new MySqlTypeConvert() {
			// 自定义数据库表字段类型转换【可选】
			@Override
			public DbColumnType processTypeConvert(String fieldType) {
				System.out.println("转换类型：" + fieldType);
				// 注意！！processTypeConvert 存在默认类型转换，如果不是你要的效果请自定义返回、非如下直接返回。
				return super.processTypeConvert(fieldType);
			}
		});
		dsc.setDriverName(JDBC_DRIVER);
		dsc.setUsername(JDBC_USERNAME);
		dsc.setPassword(JDBC_PASSWORD);
		dsc.setUrl(JDBC_URL);
		mpg.setDataSource(dsc);

		// 策略配置
		StrategyConfig strategy = new StrategyConfig();
		strategy.setTablePrefix(new String[] { "cr_", "sys_" });// 此处可以修改为您的表前缀
		strategy.setNaming(NamingStrategy.underline_to_camel);// 表名生成策略
		strategy.setInclude(tabArr);
		strategy.setLogicDeleteFieldName("del_flag");
		// strategy.setExclude(new String[]{"test"}); // 排除生成的表
		// 自定义实体父类
		strategy.setSuperControllerClass("com.emgcy.core.system.base.AbstractController");
		strategy.setSuperEntityClass("com.emgcy.core.system.base.BaseEntity");
		// 自定义实体，公共字段
		strategy.setSuperEntityColumns(new String[] { "create_by", "create_time", "update_by", "update_time",
				"del_flag", "remark" });
		// 自定义 mapper 父类
		// strategy.setSuperMapperClass("com.baomidou.demo.TestMapper");
		// 自定义 service 父类
		// strategy.setSuperServiceClass("com.baomidou.demo.TestService");
		// 自定义 service 实现类父类
		// strategy.setSuperServiceImplClass("com.baomidou.demo.TestServiceImpl");
		// 自定义 controller 父类
		// strategy.setSuperControllerClass("com.baomidou.demo.TestController");
		// 【实体】是否生成字段常量（默认 false）
		// public static final String ID = "test_id";
		// strategy.setEntityColumnConstant(true);
		// 【实体】是否为构建者模型（默认 false）
		// public User setName(String name) {this.name = name; return this;}
		// strategy.setEntityBuilderModel(true);
		mpg.setStrategy(strategy);

		// 包配置
		PackageConfig pc = new PackageConfig();
		pc.setParent(PACKAGEDIR);
		pc.setModuleName(moduleName);
		mpg.setPackageInfo(pc);

		InjectionConfig cfg = new InjectionConfig() {
			@Override
			public void initMap() {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("ewFlag", ewFlag);
				map.put("baseFlag", baseFlag);
				this.setMap(map);
			}
		};

		List<FileOutConfig> focList = new ArrayList<FileOutConfig>();
		focList.add(new FileOutConfig("/template/mapper.xml.vm") {
			@Override
			public String outputFile(TableInfo tableInfo) {
				return MYBATIS_XML + tableInfo.getEntityName() + "Mapper.xml";
			}
		});
		cfg.setFileOutConfigList(focList);
		mpg.setCfg(cfg);

		TemplateConfig tc = new TemplateConfig();
		if (controllerAuto) {
			tc.setController("/template/controllerAuto.java.vm");
		} else {
			tc.setController("/template/controller.java.vm");
		}
		tc.setEntity("/template/entity.java.vm");
		tc.setMapper("/template/mapper.java.vm");
		tc.setXml(null);
		tc.setService("/template/service.java.vm");
		tc.setServiceImpl("/template/serviceImpl.java.vm");
		mpg.setTemplate(tc);

		// 执行生成
		mpg.execute();

	}

}