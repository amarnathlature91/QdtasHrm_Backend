package com.qdtas.service.impl;

import com.qdtas.entity.Department;
import com.qdtas.entity.User;
import com.qdtas.exception.UserNotFoundException;
import com.qdtas.repository.DepartmentRepository;
import com.qdtas.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentRepository drp;

    @Override
    public Department create(Department dp) {
        return drp.save(dp);
    }

    @Override
    public Department getById(long deptId) {
        return drp.findById(deptId).orElseThrow(()-> new UserNotFoundException("Department Not Found"));
    }

    @Override
    public Department updateById(long deptId, Department dp) {
        Department d = drp.getById(deptId);
        d.setDeptName(dp.getDeptName());
        return drp.save(d);
    }

    @Override
    public String deleteById(long deptId) {
        try {
            drp.deleteById(deptId);
            return "Department Deleted Successfully";
        }
        catch(Exception exe){
            return "Something Went Wrong";
        }
    }
    @Override
    public Set<Department> getAllDepartments() {
        return new HashSet<>(drp.findAll());
    }

    @Override
    public List<User> getAllUsers(long deptId) {
        Department d = getById(deptId);
        return new ArrayList<>(d.getEmployees());
    }
}
