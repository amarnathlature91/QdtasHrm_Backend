package com.qdtas.controller;

import com.qdtas.dto.JsonMessage;
import com.qdtas.dto.ProjectDTO;
import com.qdtas.entity.Project;
import com.qdtas.service.ProjectService;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
@Tag(name = "4. Project")
public class ProjectController {

    @Autowired
    private ProjectService psr;


    @ApiOperation(value = "Add Project", position = 1)
    @Operation(
            description = "Add project",
            summary = "1.Add Project",
            responses = {
                    @ApiResponse(
                            description = "Created",
                            responseCode = "201",
                            content = @io.swagger.v3.oas.annotations.media.Content
                    ),
                    @ApiResponse(
                            description = "Bad Request",
                            responseCode = "400",
                            content = @io.swagger.v3.oas.annotations.media.Content
                    )
            }
    )
    @PostMapping("/add")
    public ResponseEntity<?> createProject(@Valid @RequestBody ProjectDTO pd){
        try {
            psr.addProject(pd);
            return new ResponseEntity(new JsonMessage("Project Added Succefully"),HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity(new JsonMessage("Something Went Wrong"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "Update Project", position = 1)
    @Operation(
            description = "Update project",
            summary = "2.Update Project",
            responses = {
                    @ApiResponse(
                            description = "Updated(OK)",
                            responseCode = "200",
                            content = @io.swagger.v3.oas.annotations.media.Content
                    ),
                    @ApiResponse(
                            description = "Bad Request",
                            responseCode = "400",
                            content = @io.swagger.v3.oas.annotations.media.Content
                    )
            }
    )
    @PostMapping("/update/{pId}")
    public ResponseEntity<?> updateProject(@Valid @RequestBody ProjectDTO pd,@PathVariable long pId){
        try {
            return new ResponseEntity(psr.updateProject(pd,pId),HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity(new JsonMessage("Something Went Wrong"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "Search Project by Name", position = 1)
    @Operation(
            description = "Search project",
            summary = "3.Search Project",
            responses = {
                    @ApiResponse(
                            description = "OK",
                            responseCode = "200",
                            content = @io.swagger.v3.oas.annotations.media.Content
                    ),
                    @ApiResponse(
                            description = "Bad Request",
                            responseCode = "400",
                            content = @io.swagger.v3.oas.annotations.media.Content
                    )
            }
    )
    @PostMapping("/searchProject")
    public ResponseEntity<?> searchByName(@RequestParam("key") String keyword,
                                          @RequestParam(value = "pgn",defaultValue = "1") int pgn,
                                          @RequestParam(value = "sz" ,defaultValue = "10") int size){
        pgn = pgn < 0 ? 0 : pgn-1;
        size = size <= 0 ? 5 : size;
        return new ResponseEntity( psr.searchProjectByName(keyword, pgn, size),HttpStatus.OK );
    }

    @ApiOperation(value = "Assign employee", position = 1)
    @Operation(
            description = "assign employee to project",
            summary = "4.Assign Employee To Project",
            responses = {
                    @ApiResponse(
                            description = "OK",
                            responseCode = "200",
                            content = @io.swagger.v3.oas.annotations.media.Content
                    ),
                    @ApiResponse(
                            description = "Bad Request",
                            responseCode = "400",
                            content = @io.swagger.v3.oas.annotations.media.Content
                    )
            }
    )
    @PostMapping("/assignEmployee")
    public ResponseEntity<?> assignEmp(@RequestParam(value = "empId",required = true) long empId,@RequestParam(value = "pId",required = true) long pId){
        return new ResponseEntity<>(psr.assignEmployee(empId,pId),HttpStatus.OK);
    }

    @ApiOperation(value = "Assign multiple employees", position = 1)
    @Operation(
            description = "assign multile employees to project",
            summary = "5.Assign multiple Employees To Project",
            responses = {
                    @ApiResponse(
                            description = "OK",
                            responseCode = "200",
                            content = @io.swagger.v3.oas.annotations.media.Content
                    ),
                    @ApiResponse(
                            description = "Bad Request",
                            responseCode = "400",
                            content = @io.swagger.v3.oas.annotations.media.Content
                    )
            }
    )
    @PostMapping("/assignAll")
    public ResponseEntity<?> assignAll(@RequestParam("empIds") List<Long> empIds, @RequestParam(value = "pId",required = true) long pId){
        return new ResponseEntity<>(psr.assignEmployees(empIds,pId),HttpStatus.OK);
    }

    @ApiOperation(value = "get all projects", position = 1)
    @Operation(
            description = "get all projects",
            summary = "6.Get ALl Projects",
            responses = {
                    @ApiResponse(
                            description = "OK",
                            responseCode = "200",
                            content = @io.swagger.v3.oas.annotations.media.Content
                    ),
                    @ApiResponse(
                            description = "Bad Request",
                            responseCode = "400",
                            content = @io.swagger.v3.oas.annotations.media.Content
                    )
            }
    )
    @PostMapping("/getAllProjects")
    public ResponseEntity<?> getAll(@RequestParam(value = "pgn",defaultValue = "1") int pgn,
                                    @RequestParam(value = "sz" ,defaultValue = "10") int size){
        pgn = pgn < 0 ? 0 : pgn-1;
        size = size <= 0 ? 5 : size;
        return new ResponseEntity<>(psr.getAllProjects(pgn, size),HttpStatus.OK);
    }

    @ApiOperation(value = "Remove employee from project", position = 1)
    @Operation(
            description = "Remove employee from project",
            summary = "7.Remove employee from project",
            responses = {
                    @ApiResponse(
                            description = "OK",
                            responseCode = "200",
                            content = @io.swagger.v3.oas.annotations.media.Content
                    ),
                    @ApiResponse(
                            description = "Bad Request",
                            responseCode = "400",
                            content = @io.swagger.v3.oas.annotations.media.Content
                    )
            }
    )
    @PostMapping("/removeEmployee")
    public ResponseEntity<?> remove(@RequestParam(value = "empId",required = true) long empId,@RequestParam(value = "pId",required = true) long pId){
        return new ResponseEntity<>(psr.removeEmployee(empId,pId),HttpStatus.OK);
    }

    @ApiOperation(value = "Remove multiple employees from project", position = 1)
    @Operation(
            description = "Remove multiple employees from project",
            summary = "8.Remove multiples employee from project",
            responses = {
                    @ApiResponse(
                            description = "OK",
                            responseCode = "200",
                            content = @io.swagger.v3.oas.annotations.media.Content
                    ),
                    @ApiResponse(
                            description = "Bad Request",
                            responseCode = "400",
                            content = @io.swagger.v3.oas.annotations.media.Content
                    )
            }
    )
    @PostMapping("/removeAll")
    public ResponseEntity<?> removeAll(@RequestParam("empIds") List<Long> empIds, @RequestParam(value = "pId",required = true) long pId){
        return new ResponseEntity<>(psr.removeAll(empIds,pId),HttpStatus.OK);
    }
}
