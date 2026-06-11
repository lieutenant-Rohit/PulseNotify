package com.pulsenotify.delivery.entity;

import com.pulsenotify.notification.entity.Channel;
import com.pulsenotify.notification.entity.Notification;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "delivery_log", indexes = {
        @Index(name = "idx_delivery_log_notification_id", columnList = "notification_id"),
        @Index(name = "idx_delivery_log_status", columnList = "status")
})
@Getter
@Setter
@NoArgsConstructor
public class DeliveryLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "notification_id", nullable = false)
    private Notification notification;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Channel channel;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private DeliveryStatus status;

    // Attempt number: 1, 2, 3 (for retry tracking)
    @Column(nullable = false)
    private int attemptNumber = 1;

    // Null on success, populated on failure
    @Column(length = 1000)
    private String errorMessage;

    @Column(nullable = false)
    private LocalDateTime attemptedAt = LocalDateTime.now();
}