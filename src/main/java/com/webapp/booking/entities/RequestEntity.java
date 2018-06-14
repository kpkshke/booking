package com.webapp.booking.entities;

import com.webapp.booking.enums.RequestStatus;

import java.time.LocalDateTime;

public class RequestEntity {
    private Integer requestID;
    private LocalDateTime checkInDate;
    private LocalDateTime checkOutDate;
    private RequestStatus requestStatus;
    private Integer roomID;
    private LocalDateTime requestDate;
}