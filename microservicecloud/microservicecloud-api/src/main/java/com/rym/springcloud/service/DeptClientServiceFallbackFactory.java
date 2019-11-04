package com.rym.springcloud.service;
 
import java.util.List;
 
import org.springframework.stereotype.Component;

import com.rym.sringcloud.entity.Dept;

import feign.hystrix.FallbackFactory;
 
@Component//不要忘记添加，不要忘记添加
public class DeptClientServiceFallbackFactory implements FallbackFactory<DeptClientService>
{
	@Override
	public DeptClientService create(Throwable throwable) {
		return new DeptClientService() {
			@Override
			public Dept get(long id) {
				Dept errorObj = new Dept();
				errorObj.setDeptno(id);
				errorObj.setDname("该ID:" + id + "没有对应的信息");
				errorObj.setDb_source("no this database in Mysql");
				return errorObj;
			}

			@Override
			public List<Dept> list() {
				return null;
			}

			@Override
			public boolean add(Dept dept) {
				return false;
			}
		};
	}
}
