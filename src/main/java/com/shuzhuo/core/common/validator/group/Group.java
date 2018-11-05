package com.shuzhuo.core.common.validator.group;

import javax.validation.GroupSequence;

/**
 * 定义校验顺序，如果AddGroup组失败，则UpdateGroup组不会再校验
 * @author bgy
 * @date 2017-12-5
 */
@GroupSequence({AddGroup.class, UpdateGroup.class, SelectGroup.class})
public interface Group {

}
