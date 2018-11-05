
# crEmgcy 

**************************************说明***************************************

## 接口设计原则
	a). 接口说明:
		查询类接口统一使用get请求
		回写类接口统一使用post请求
		对外接口一律写在 com.shuzhuo.core.interfaces.restful 包下
		
	b). 接口地址
		简单明了,单词超过10个字母时使用简称(单词前4-6个字母)
		接口地址可使用驼峰规则命名
		
	c). 接口数据
		返回json格式尽量使用 com.shuzhuo.core.common.base BaseMsg 类返回
		
	d). 字典和医院信息可以在ehcache缓存中取，具体可参考com.shuzhuo.core.ehcache 包下 DictUtils 和 HospitalUtils 两个类
	
	e). sql语句一旦更新了必须写更新 项目下 "sql重要更新说明.sql" 文件，以便项目组成员知道

#----------------------------------------------------------------chenshuzhuo---------------
#----------------------------------------------------------------2018-03-20----------------  
	
##基本创建表语句
#其中 "create_by", "create_time","update_by","update_time","del_flag","remark",为公共字段

#--------------------------------优雅的分割线------------------------------------------

CREATE TABLE `test` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `create_by` BIGINT(20) DEFAULT NULL COMMENT '创建人',
  `create_time` DATETIME DEFAULT NULL COMMENT '创建时间',
  `update_by` BIGINT(20) DEFAULT NULL COMMENT '修改人',
  `update_time` DATETIME DEFAULT NULL COMMENT '修改时间',
  `del_flag` TINYINT(1) DEFAULT '0' COMMENT '删除标识(0:未删除,-1:已删除)',
  `remark` VARCHAR(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='test表'
#----------------------------------------------------------------chenshuzhuo---------------
#----------------------------------------------------------------2017-11-17----------------                                                                         



*************************************** 特别说明***************************************

