package com.qdtas.service.impl;

import com.qdtas.entity.Project;
import com.qdtas.entity.Timesheet;
import com.qdtas.entity.User;
import com.qdtas.exception.ResourceNotFoundException;
import com.qdtas.repository.TimeSheetRepository;
import com.qdtas.repository.UserRepository;
import com.qdtas.service.ProjectService;
import com.qdtas.service.TimesheetService;
import com.qdtas.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class TimesheetServiceImpl implements TimesheetService {

    @Autowired
    private TimeSheetRepository trp;

    @Autowired
    private UserService urp;

    @Autowired
    private ProjectService psr;

    @Override
    public Timesheet addTimesheet(Timesheet ts) {
        urp.getById(ts.getEmpId());
        psr.getProjectById(ts.getProjectId());
        return trp.save(ts);
    }

    @Override
    public Timesheet getTimesheetById(long tsId) {
        return trp.findById(tsId).orElseThrow(() -> new ResourceNotFoundException("Timesheet", "TimesheetId", String.valueOf(tsId)));
    }

    @Override
    public List<Timesheet> getTimesheetByEmployeeId(long empId) {
        return trp.findAllByEmp(empId);
    }

    @Override
    public void deleteById(long tsId) {
        Timesheet t = getTimesheetById(tsId);
        trp.delete(t);
    }

    @Override
    public Timesheet updateTimesheet(long tsId, Timesheet ts) {
        Timesheet t = getTimesheetById(tsId);

        urp.getById(ts.getEmpId());
        psr.getProjectById(ts.getProjectId());

        t.setEmpId(ts.getEmpId());

        t.setStartTime(ts.getStartTime());

        t.setEndTime(ts.getEndTime());

        t.setDate(ts.getDate());

        t.setProjectId(ts.getProjectId());
        t.setNote(ts.getNote());
        return trp.save(ts);
    }
}
