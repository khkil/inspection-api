package com.example.backend.api.inspection.progress;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProgressDto {

    private String inspectionName;
    private Long progress;

    public interface History{

        Long getCurrentPage();
        Long getTotalPage();
    }

}
