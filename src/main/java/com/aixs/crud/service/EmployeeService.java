package com.aixs.crud.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aixs.crud.bean.Employee;
import com.aixs.crud.bean.EmployeeExample;
import com.aixs.crud.bean.EmployeeExample.Criteria;
import com.aixs.crud.dao.EmployeeMapper;

@Service
public class EmployeeService {
    
	@Autowired
	EmployeeMapper employeeMapper;
	/**
	 * ��ѯ����Ա������(��ҳ��ѯ)
	 * @return
	 */
	public List<Employee> getAll() {
		
		return employeeMapper.selectByExampleWithDept(null);
	}
	/**
	 * ����Ա��
	 * @param employee
	 */
	public void saveEmp(Employee employee) {
		
		employeeMapper.insertSelective(employee);
		
	}
	/**
	 * �����û����Ƿ����
	 * @param empName
	 * @return  true ������������
	 */
	public boolean checkUser(String empName) {
		EmployeeExample example=new EmployeeExample();
		Criteria criteria=example.createCriteria();
		criteria.andEmpNameEqualTo(empName);
		long count=employeeMapper.countByExample(example);
		return count==0;
	}
	public Employee getEmp(Integer id) {
		
		Employee employee=employeeMapper.selectByPrimaryKey(id);
		return null;
	}
	public void update(Employee employee) {
		
		employeeMapper.updateByPrimaryKeySelective(employee);
		
	}
	public void deleteEmp(Integer id) {
		
		employeeMapper.deleteByPrimaryKey(id);
		
	}
	
	public void deleteBatch(List<Integer> ids) {
		
		EmployeeExample example=new EmployeeExample();
		Criteria criteria =example.createCriteria();
		criteria.andEmpIdIn(ids);
		employeeMapper.deleteByExample(example);		
	}
	
}
