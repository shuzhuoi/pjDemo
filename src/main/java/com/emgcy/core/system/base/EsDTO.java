package com.emgcy.core.system.base;

import java.io.Serializable;

import javax.validation.constraints.Min;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.NullHandling;
import org.springframework.data.domain.Sort.Order;

import com.emgcy.core.common.validator.ArrayValid;
import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 
 * @author chenshuzhuo
 * @date 2018-01-30
 */
@ApiModel(value="es类")
public class EsDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value="页数")
	@Min(value = 1)
	private int page=1;
	
	@ApiModelProperty(value="每页大小")
	@Min(value = 1)
	private int limit=10;
	
	@ApiModelProperty(value="模糊查询参数")
	private String queryParam;
	
	@ApiModelProperty(value="排序字段")
	private String sortFields;
	
	@ApiModelProperty(value="升降序(asc:升,desc:降)",allowableValues="asc,desc")
	@ArrayValid({"asc","desc"})
	private String order;
	
	@ApiModelProperty(value="分类uuid")
	@Length(max=32,message="分类uuid必需32位")
	private String classifyUuid;
	
	@ApiModelProperty(hidden=true)
	@JsonIgnore
	private Sort sort;
	
	@ApiModelProperty(hidden=true)
	@JsonIgnore
	private Pageable pageable;

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public String getQueryParam() {
		return queryParam;
	}

	public void setQueryParam(String queryParam) {
		this.queryParam = queryParam;
	}

	public String getSortFields() {
		return sortFields;
	}

	public void setSortFields(String sortFields) {
		this.sortFields = sortFields;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public String getClassifyUuid() {
		return classifyUuid;
	}

	public void setClassifyUuid(String classifyUuid) {
		this.classifyUuid = classifyUuid;
	}

	public Sort getSort() {
		if (StringUtils.isNotBlank(sortFields) && StringUtils.isNotBlank(order)) {
            if (Direction.DESC.toString().toLowerCase().equals(order))
            	sort = new Sort(new Order(Direction.DESC,sortFields,NullHandling.NATIVE));
            if (Direction.ASC.toString().toLowerCase().equals(order))
            	sort = new Sort(new Order(Direction.ASC,sortFields,NullHandling.NATIVE));
        }
		return sort;
	}

	public Pageable getPageable() {
		Sort sort=null;
		if ((sort=getSort())!=null)
			return new PageRequest(page-1, limit,sort);
		return new PageRequest(page-1, limit);
	}
	
	
	
	
	
}
