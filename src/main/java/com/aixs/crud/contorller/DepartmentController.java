package com.aixs.crud.contorller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aixs.crud.bean.Department;
import com.aixs.crud.bean.Msg;
import com.aixs.crud.service.DepartmentService;

/**
 * ����Ͳ����йص�����
 * @author AixsCode
 *
 */
@Controller
public class DepartmentController {

	
	@Autowired
	private DepartmentService departmentService;
	/**
	 * �������в�����Ϣ
	 */
	@RequestMapping("/depts")
	@ResponseBody
	public Msg getDepts()
	{
		List<Department> list =departmentService.getDepts();
		return Msg.success().add("depts", list);
	}
}
