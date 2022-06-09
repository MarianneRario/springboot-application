package com.caramellow.location.locationweb.util;

public interface EmailUtil {

    // contains one method
    // toAddress -> to whom the email should be sent
    // subject -> subject of the email
    // body -> body email
    void sendEmail(String toAddress, String subject, String body);
}
