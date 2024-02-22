package com.qdtas.service;

import com.qdtas.dto.LeaveDTO;
import com.qdtas.entity.Leave;

import java.util.List;

public interface LeaveService {

    public List<Leave> getAllLeaveRequests();

    public Leave createLeaveRequest(LeaveDTO leaveRequest);

    public Leave updateLeaveRequest(Long id, LeaveDTO updatedLeaveRequest);

    public void deleteLeaveRequest(Long id);

    public Leave approveLeaveRequest(Long id);

    public Leave rejectLeaveRequest(Long id);
}
