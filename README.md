
**此项目已经废弃**
	
##基本创建表语句
#其中 "create_by", "create_time","update_by","update_time","del_flag","remark",为公共字段


#--------------------------------优雅的分割线------------------------------------------
~~~
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
~~~
#----------------------------------------------------------------chenshuzhuo---------------


