package com.statista.code.challenge.notification;

import com.statista.code.challenge.business.Booking;
import com.statista.code.challenge.business.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MockEmailNotificationService implements NotificationService {

    @Override
    public void send(Booking booking) {
        log.info("This is where an email to {} would have been sent.", booking.email());
    }
}
