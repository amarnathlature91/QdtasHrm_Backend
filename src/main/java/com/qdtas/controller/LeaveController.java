package com.qdtas.controller;

import com.qdtas.dto.LeaveDTO;
import com.qdtas.entity.Leave;
import com.qdtas.service.LeaveService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/leave")
public class LeaveController {
    @Autowired
    private LeaveService leaveRequestService;

    @GetMapping("/getAllLeaves")
    public List<Leave> getAllLeaveRequests() {
        return leaveRequestService.getAllLeaveRequests();
    }

    @PostMapping("/create")
    public Leave createLeaveRequest(@Valid @RequestBody LeaveDTO leaveRequest) {
        return leaveRequestService.createLeaveRequest(leaveRequest);
    }
    @PutMapping("/{LeaveId}")
    public Leave updateLeaveRequest(@PathVariable Long LeaveId, @RequestBody LeaveDTO updatedLeaveRequest) {
        return leaveRequestService.updateLeaveRequest(LeaveId, updatedLeaveRequest);
    }

    @DeleteMapping("/{LeaveId}")
    public void deleteLeaveRequest(@PathVariable Long LeaveId) {
        leaveRequestService.deleteLeaveRequest(LeaveId);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/approve/{LeaveId}")
    public Leave approveLeaveRequest(@PathVariable Long LeaveId) {
        return leaveRequestService.approveLeaveRequest(LeaveId);
    }

    @PostMapping("/reject/{LeaveId}")
    public Leave rejectLeaveRequest(@PathVariable Long LeaveId) {
        return leaveRequestService.rejectLeaveRequest(LeaveId);
    }
}
