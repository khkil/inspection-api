package com.example.backend.api.inspection.model;

import com.example.backend.api.inspection.question.model.Question;
import com.example.backend.api.inspection.question.model.QuestionDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;


@Data
@NoArgsConstructor
public class InspectionDto {

    @Data
    public static class Request {
        private String inspectionName;
        private String payYn;
        private String octagnosisYn;
        private String showYn;
        private Integer rankCount;

        public Request(Inspection inspection){

            this.inspectionName = inspection.getInspectionName();
            this.payYn = inspection.getPayYn();
            this.octagnosisYn = inspection.getOctagnosisYn();
            this.showYn = inspection.getShowYn();
            this.rankCount = inspection.getRankCount();
        }

    }

    @Data
    public static class Response {

        private Integer inspectionIdx;
        private String inspectionName;
        private String payYn;
        private String octagnosisYn;
        private String showYn;
        private Integer rankCount;

        public Response(Inspection inspection){
            this.inspectionIdx = inspection.getInspectionIdx();
            this.inspectionName = inspection.getInspectionName();
            this.payYn = inspection.getPayYn();
            this.octagnosisYn = inspection.getOctagnosisYn();
            this.showYn = inspection.getShowYn();
            this.rankCount = inspection.getRankCount();
        }

    }


}
