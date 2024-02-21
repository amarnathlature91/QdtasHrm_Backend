package com.qdtas.service;

import com.qdtas.entity.Leave;

import java.util.List;

public interface LeaveService {

    public List<Leave> getAllLeaveRequests();

    public Leave createLeaveRequest(Leave leaveRequest);

    public Leave updateLeaveRequest(Long id, Leave updatedLeaveRequest);

    public void deleteLeaveRequest(Long id);

    public Leave approveLeaveRequest(Long id);

    public Leave rejectLeaveRequest(Long id);
}
