package com.LTUC.Eventure.services.AdminService;

import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public interface AdminService {
    public void updateStatus_unpaid_toCancelled();

    public void clearFinishedEvents();

    public LocalDate subtractTwoDays(String date);

    public boolean compareToCurrentDate(LocalDate date);
}