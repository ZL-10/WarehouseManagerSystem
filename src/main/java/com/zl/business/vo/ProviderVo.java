package com.zl.business.vo;

import com.zl.business.domain.Provider;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ProviderVo extends Provider {

	/*
	 *  
	 */
	private static final long serialVersionUID = 1L;

	private Integer page = 1;
	private Integer limit = 10;

	private Integer[] ids;

}
