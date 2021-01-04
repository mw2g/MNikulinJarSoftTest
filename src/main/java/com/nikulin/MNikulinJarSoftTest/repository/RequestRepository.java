package com.nikulin.MNikulinJarSoftTest.repository;

import com.nikulin.MNikulinJarSoftTest.domain.Request;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface RequestRepository extends JpaRepository<Request, Integer> {
    List<Request> findAllByIpAddressAndUserAgentAndDateAfter(String ipAddress, String userAgent, Date afterDate);
}
