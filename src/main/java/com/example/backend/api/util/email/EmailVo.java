package com.example.backend.api.util.email;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

@Data
public class EmailVo {

    private String to;
    private String title;
    private String message;

}
