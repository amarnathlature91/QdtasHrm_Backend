package com.qdtas.service.impl;

import com.qdtas.dto.LeaveDTO;
import com.qdtas.entity.Leave;
import com.qdtas.entity.User;
import com.qdtas.exception.ResourceNotFoundException;
import com.qdtas.repository.LeaveRepository;
import com.qdtas.repository.UserRepository;
import com.qdtas.service.LeaveService;
import com.qdtas.service.UserService;
import com.qdtas.utility.LeaveStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LeaveServiceImpl implements LeaveService {
    @Autowired
    private LeaveRepository leaveRequestRepository;

    @Autowired
    private UserService usr;

    public List<Leave> getAllLeaveRequests() {
        return leaveRequestRepository.findAll();
    }

    public Leave createLeaveRequest(long empId,LeaveDTO leaveRequest) {
        Leave l =new Leave();
        l.setStatus(LeaveStatus.PENDING.name());
        l.setReason(leaveRequest.getReason());
        l.setStartDate(leaveRequest.getStartDate());
        l.setEndDate(leaveRequest.getEndDate());
        User u = usr.getById(empId);
        l.setEmployee(u);
        return leaveRequestRepository.save(l);
    }

    public Leave updateLeaveRequest(Long id, LeaveDTO updatedLeaveRequest) {
        Leave existingLeaveRequest = leaveRequestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Leave request not found"));
        existingLeaveRequest.setEmployee(usr.getById(updatedLeaveRequest.getEmployeeId()));
        existingLeaveRequest.setStartDate(updatedLeaveRequest.getStartDate());
        existingLeaveRequest.setReason(updatedLeaveRequest.getReason());
        existingLeaveRequest.setEndDate(updatedLeaveRequest.getEndDate());
        return leaveRequestRepository.save(existingLeaveRequest);
    }

    public void deleteLeaveRequest(Long id) {
        leaveRequestRepository.deleteById(id);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Leave approveLeaveRequest(Long id) {
        Leave leaveRequest = leaveRequestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Leave request not found"));

        leaveRequest.setStatus(LeaveStatus.APPROVED.name());

        return leaveRequestRepository.save(leaveRequest);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public Leave rejectLeaveRequest(Long id) {
        Leave leaveRequest = leaveRequestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Leave request not found"));
        leaveRequest.setStatus(LeaveStatus.REJECTED.name());
        return leaveRequestRepository.save(leaveRequest);
    }
}
