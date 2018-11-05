package com.shuzhuo.core.system.attachment.mapper;

import java.util.List;

import com.shuzhuo.core.system.attachment.entity.Attachment;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;


/**
 * <p>
  * 附件表 Mapper 接口
 * </p>
 *
 * @author chenshuzhuo
 * @since 2017-11-20
 */
public interface AttachmentMapper extends BaseMapper<Attachment> {

	List<Attachment> selectByMdFiveList(@Param("list")List<String> mdFiveList,@Param("userId") Long userId);
	
	
	
}