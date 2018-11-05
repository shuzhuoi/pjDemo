-------------------------------修改sql 更新文件-----------------------------------

/*==============================================================*/
/* 
 * Table   : cr_test
 * Author  : chenshuzhuo                               
 * Date    : 2017-12-12
 * Describe: 创建test表
 *                                                              */
/*==============================================================*/
CREATE TABLE `cr_test` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `create_by` BIGINT(20) DEFAULT NULL COMMENT '创建人',
  `create_time` DATETIME DEFAULT NULL COMMENT '创建时间',
  `update_by` BIGINT(20) DEFAULT NULL COMMENT '修改人',
  `update_time` DATETIME DEFAULT NULL COMMENT '修改时间',
  `del_flag` TINYINT(1) DEFAULT '0' COMMENT '删除标识(0:未删除,-1:已删除)',
  `remark` VARCHAR(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='test表'