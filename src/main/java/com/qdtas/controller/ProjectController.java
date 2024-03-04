package com.qdtas.controller;

import com.qdtas.dto.JsonMessage;
import com.qdtas.dto.ProjectDTO;
import com.qdtas.entity.Project;
import com.qdtas.service.ProjectService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/project")
@CrossOrigin
public class ProjectController {

    @Autowired
    private ProjectService psr;

    @PostMapping("/add")
    public ResponseEntity<?> createProject(@Valid @RequestBody ProjectDTO pd){
        try {
            psr.addProject(pd);
            return new ResponseEntity(new JsonMessage("Project Added Succefully"),HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity(new JsonMessage("Something Went Wrong"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/update/{pId}")
    public ResponseEntity<?> updateProject(@Valid @RequestBody ProjectDTO pd,@PathVariable long pId){
        try {
            return new ResponseEntity(psr.updateProject(pd,pId),HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity(new JsonMessage("Something Went Wrong"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/searchProject")
    public ResponseEntity<?> searchByName(@RequestParam("key") String keyword,
                                          @RequestParam(value = "pgn",defaultValue = "1") int pgn,
                                          @RequestParam(value = "sz" ,defaultValue = "10") int size){
        pgn = pgn < 0 ? 0 : pgn-1;
        size = size <= 0 ? 5 : size;
        return new ResponseEntity( psr.searchProjectByName(keyword, pgn, size),HttpStatus.OK );
    }

    @PostMapping("/assignEmployee")
    public ResponseEntity<?> assignEmp(@RequestParam(value = "empId",required = true) long empId,@RequestParam(value = "pId",required = true) long pId){
        return new ResponseEntity<>(psr.assignEmployee(empId,pId),HttpStatus.OK);
    }

    @PostMapping("/assignAll")
    public ResponseEntity<?> assignAll(@RequestParam("empIds") List<Long> empIds, @RequestParam(value = "pId",required = true) long pId){
        return new ResponseEntity<>(psr.assignEmployees(empIds,pId),HttpStatus.OK);
    }

    @PostMapping("/getAllProjects")
    public ResponseEntity<?> getAll(@RequestParam(value = "pgn",defaultValue = "1") int pgn,
                                    @RequestParam(value = "sz" ,defaultValue = "10") int size){
        pgn = pgn < 0 ? 0 : pgn-1;
        size = size <= 0 ? 5 : size;
        return new ResponseEntity<>(psr.getAllProjects(pgn, size),HttpStatus.OK);
    }

    @PostMapping("/removeEmployee")
    public ResponseEntity<?> remove(@RequestParam(value = "empId",required = true) long empId,@RequestParam(value = "pId",required = true) long pId){
        return new ResponseEntity<>(psr.removeEmployee(empId,pId),HttpStatus.OK);
    }

    @PostMapping("/removeAll")
    public ResponseEntity<?> removeAll(@RequestParam("empIds") List<Long> empIds, @RequestParam(value = "pId",required = true) long pId){
        return new ResponseEntity<>(psr.removeAll(empIds,pId),HttpStatus.OK);
    }
}
