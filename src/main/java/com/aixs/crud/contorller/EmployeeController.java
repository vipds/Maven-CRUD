package com.aixs.crud.contorller;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aixs.crud.bean.Employee;
import com.aixs.crud.bean.Msg;
import com.aixs.crud.service.EmployeeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * ����Ա������
 * @author AixsCode
 *
 */
@Controller
public class EmployeeController {
    
	@Autowired
	private EmployeeService employeeService;
	
	
	@RequestMapping(value="/emp/{ids}",method=RequestMethod.DELETE)
	public Msg deleteEmpById(@PathVariable("ids")String ids)
	{
		//����ɾ��
		if(ids.contains("-"))
		{
			
			List<Integer> del_ids=new ArrayList<Integer>();
			
			String[] str_ids=ids.split("-");
			//��װid�ļ���
			for(String string : str_ids)
			{
				del_ids.add(Integer.parseInt(string));
			}
			employeeService.deleteBatch(del_ids);
		}
		else {
			Integer id=Integer.parseInt(ids);
			employeeService.deleteEmp(id);
		}
		return Msg.success();
		
	}
	
	@ResponseBody
	@RequestMapping(value="/emp/{empId}",method=RequestMethod.PUT)
	public Msg saveEmp(Employee employee)
	{
		employeeService.update(employee);
		return Msg.success();
		
	}
	@RequestMapping(value="/emp/{id}",method=RequestMethod.GET)
	public Msg getEmp(@PathVariable("id")Integer id)
	{
		Employee employee =employeeService.getEmp(id);
		return Msg.success().add("emp", employee);
	}
	
	/**
	 * ����û����Ƿ����
	 * @param empName
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/checkuser")
	public Msg Checkuser(@RequestParam("empName") String empName)
	{
		//���ж��û����Ƿ��ǺϷ��ı��ʽ
		String regx="(^[a-zA-Z0-9_-]{6,16}$)|(^[\u2E80-\u9FFF]{2,5})";
		if(!empName.matches(regx))
		{
			return Msg.fail().add("va_msg", "�û���������6-16λ���ֺ���ĸ����ϻ���2-5λ����");	
		}
		boolean b=employeeService.checkUser(empName);
		if(b)
		{
			return Msg.success();
		}
		else
		{
			return Msg.fail().add("va_msg", "�û���������");
		}	
	}
	/**
	 * 
	 * Ա������
	 * @return
	 */
	
	@RequestMapping(value="/emp",method=RequestMethod.POST)
	@ResponseBody
	public Msg saveEmp(@Valid Employee employee,BindingResult result)
	{   
		if(result.hasErrors())
		{
			Map<String, Object> map=new HashMap<String, Object>();
			List<FieldError> errors=result.getFieldErrors();
			for(FieldError fieldError:errors)
			{
				System.out.println("������ֶ���"+fieldError.getField());
				System.out.println("�������Ϣ"+fieldError.getDefaultMessage());
				map.put(fieldError.getField(), fieldError.getDefaultMessage());
			}
			return Msg.fail();
		}
		else {
			employeeService.saveEmp(employee);
			return Msg.success();
		}
	}
	/**
	 * ����json�ַ���
	 * responseBody �����Զ������󷵻�Ϊjson�ַ���
	 */
	@RequestMapping("/emps")
	@ResponseBody
	public Msg getEmpsWithJson(@RequestParam(value="pn",defaultValue="1") Integer pn)
	{
		PageHelper.startPage(pn, 5);
		
		List<Employee> emps=employeeService.getAll();
		
		PageInfo page=new PageInfo(emps,5);
		
		return Msg.success().add("pageInfo", page);
	}
	
	/**
	 * 
	 * ��ѯԱ������
	 */
	//@RequestMapping("/emps")
	public String getEmps(@RequestParam(value="pn",defaultValue="1") Integer pn,Model model)
	{
		//��ҳ��ѯ
		//��ѯ֮ǰ��Ҫ����ҳ�룬�Լ�ÿҳ�Ĵ�С
		PageHelper.startPage(pn, 5);
		List<Employee> emps=employeeService.getAll();
		//pageInfo��װ��ѯ��Ľ��
		//��װ����ϸ�ķ�ҳ��Ϣ
		PageInfo page=new PageInfo(emps,5);
		model.addAttribute("pageInfo", page);
		return "list";
	}
	
}
