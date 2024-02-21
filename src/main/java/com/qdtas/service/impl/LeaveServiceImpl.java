package com.qdtas.service.impl;

import com.qdtas.entity.Leave;
import com.qdtas.repository.LeaveRepository;
import com.qdtas.service.LeaveService;
import com.qdtas.utility.LeaveStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LeaveServiceImpl implements LeaveService {
    @Autowired
    private LeaveRepository leaveRequestRepository;

    public List<Leave> getAllLeaveRequests() {
        return leaveRequestRepository.findAll();
    }

    public Leave createLeaveRequest(Leave leaveRequest) {
        return leaveRequestRepository.save(leaveRequest);
    }

    public Leave updateLeaveRequest(Long id, Leave updatedLeaveRequest) {
        Leave existingLeaveRequest = leaveRequestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Leave request not found"));
        existingLeaveRequest.setEmployee(updatedLeaveRequest.getEmployee());
        existingLeaveRequest.setStartDate(updatedLeaveRequest.getStartDate());
        existingLeaveRequest.setEndDate(updatedLeaveRequest.getEndDate());
        return leaveRequestRepository.save(existingLeaveRequest);
    }

    public void deleteLeaveRequest(Long id) {
        leaveRequestRepository.deleteById(id);
    }

    public Leave approveLeaveRequest(Long id) {
        Leave leaveRequest = leaveRequestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Leave request not found"));

        leaveRequest.setStatus(LeaveStatus.APPROVED.name());

        return leaveRequestRepository.save(leaveRequest);
    }

    public Leave rejectLeaveRequest(Long id) {
        Leave leaveRequest = leaveRequestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Leave request not found"));
        leaveRequest.setStatus(LeaveStatus.REJECTED.name());
        return leaveRequestRepository.save(leaveRequest);
    }
}
