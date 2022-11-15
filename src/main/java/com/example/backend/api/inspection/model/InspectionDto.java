package com.example.backend.api.inspection.model;

import com.example.backend.api.inspection.question.answer.model.MemberAnswer;
import com.example.backend.api.inspection.question.answer.model.MemberAnswerID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class InspectionDto {


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
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
    @AllArgsConstructor
    public static class Summary {

        private Integer inspectionIdx;
        private String inspectionName;
        private String payYn;
        private String octagnosisYn;
        private String showYn;
        private Integer rankCount;

        public Summary(Inspection inspection){
            this.inspectionIdx = inspection.getInspectionIdx();
            this.inspectionName = inspection.getInspectionName();
            this.payYn = inspection.getPayYn();
            this.octagnosisYn = inspection.getOctagnosisYn();
            this.showYn = inspection.getShowYn();
            this.rankCount = inspection.getRankCount();
        }

    }


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Detail {

        private Integer inspectionIdx;
        private String inspectionName;
        private String payYn;
        private String octagnosisYn;
        private String showYn;
        private Integer rankCount;

        public Detail(Inspection inspection){
            this.inspectionIdx = inspection.getInspectionIdx();
            this.inspectionName = inspection.getInspectionName();
            this.payYn = inspection.getPayYn();
            this.octagnosisYn = inspection.getOctagnosisYn();
            this.showYn = inspection.getShowYn();
            this.rankCount = inspection.getRankCount();
        }

    }

    @Data
    @AllArgsConstructor
    public static class Progress {

        private Integer inspectionIdx;
        private String inspectionName;
        private Integer memberIdx;
        private Integer progress;

    }



}
