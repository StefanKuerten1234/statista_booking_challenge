package com.statista.code.challenge.business;

import org.springframework.stereotype.Component;

@Component
public class BusinessRegistry {

    public BusinessDepartment getDepartment(String departmentName) {
        return switch (departmentName) {
            case "sales" -> booking -> "Voucher code for your next booking is HIRING23" ;
            default ->
                    booking -> String.format("Nothing has been done since the department %s is unknown", booking.department());
        };
    }

}
