package com.rym.springcloud.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.rym.springcloud.service.DeptService;
import com.rym.sringcloud.entity.Dept;
 
@RestController
public class DeptController
{
  @Autowired
  private DeptService service;
  
  @RequestMapping(value="/dept/get/{id}",method=RequestMethod.GET)
  @HystrixCommand(fallbackMethod="processHystrix_Get")
  public Dept get(@PathVariable("id") Long id)
  {
	  Dept dept = this.service.get(id);
	  if(dept == null) {
		  throw new RuntimeException("该ID:"+ id +"没有对应的信息");
		  
	  }
   return service.get(id);
  }
  
  public Dept processHystrix_Get(@PathVariable("id")Long id) {
	  Dept errorObj = new Dept();
	  errorObj.setDeptno(id);
	  errorObj.setDname("该ID:"+ id +"没有对应的信息");
	  errorObj.setDb_source("no this database in Mysql");
	  return errorObj;
  }
}
 

