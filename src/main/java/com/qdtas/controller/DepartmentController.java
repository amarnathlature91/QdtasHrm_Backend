package com.qdtas.controller;

import com.qdtas.entity.Department;
import com.qdtas.service.DepartmentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dept")
public class DepartmentController {

    @Autowired
    private DepartmentService dsr;

    @PostMapping("/add")
    public ResponseEntity<?> register(@Valid @RequestBody Department dt) {
        return new ResponseEntity<>(dsr.create(dt), HttpStatus.CREATED);
    }

    @GetMapping("/get/{deptId}")
    public ResponseEntity<?> getById(@PathVariable long deptId) {
        return new ResponseEntity<>(dsr.getById(deptId), HttpStatus.OK);
    }

    @PutMapping("/update/{deptId}")
    public ResponseEntity<?> update(@PathVariable("deptId") long deptId, @RequestBody Department dt) {
        return new ResponseEntity<>(dsr.updateById(deptId,dt), HttpStatus.OK);
    }
    @DeleteMapping("/delete/{deptId}")
    public ResponseEntity<?> delete(@PathVariable long deptId) {
        return new ResponseEntity<>(dsr.deleteById(deptId), HttpStatus.OK);
    }
    @GetMapping("/getAllDepartments")
    public ResponseEntity<?> getAll() {
        return new ResponseEntity<>(dsr.getAllDepartments(), HttpStatus.OK);
    }
    @GetMapping("/getAllEmpByDept/{deptId}")
    public ResponseEntity<?> getAllUsers(@PathVariable long deptId) {
        return new ResponseEntity<>(dsr.getAllUsers(deptId), HttpStatus.OK);
    }
}
