package com.nikulin.MNikulinJarSoftTest.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer requestId;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "banner_id")
    private Banner banner;
    @Column(columnDefinition = "text", nullable = false)
    private String userAgent;
    @Column(nullable = false)
    private String ipAddress;
    @Column(nullable = false)
    private Date date;
}
