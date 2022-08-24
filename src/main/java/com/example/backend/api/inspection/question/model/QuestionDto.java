package com.example.backend.api.inspection.question.model;

import com.example.backend.api.inspection.model.Inspection;
import com.example.backend.api.inspection.question.answer.model.Answer;
import com.example.backend.util.enumerator.QuestionType;
import com.example.backend.util.enumerator.converters.QuestionTypeConverter;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class QuestionDto {

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

        private Integer questionIdx;
        private Integer questionNumber;
        private String questionText;
        private String octagnosisYn;
        private Integer questionOrder;
        private String delYn;

        public Response(Question question){
            this.questionIdx = question.getQuestionIdx();
        }


    }



}
