package com.example.demo.repository;

import com.example.demo.entity.UserTracking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserTrackingRepository extends JpaRepository<UserTracking, Long> {
}