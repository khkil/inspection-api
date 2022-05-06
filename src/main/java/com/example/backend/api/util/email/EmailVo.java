package com.example.backend.api.util.email;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

@Data
public class EmailVo {

    private String toEmail;
    private String title;
    private String message;
    private String authKey;

}
