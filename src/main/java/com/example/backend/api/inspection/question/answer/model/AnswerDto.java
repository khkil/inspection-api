package com.example.backend.api.inspection.question.answer.model;

import com.example.backend.api.inspection.model.Inspection;
import com.example.backend.api.inspection.question.model.Question;
import com.example.backend.util.enumerator.QuestionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class AnswerDto {

    /*@Data
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

    }*/

    @Data
    @AllArgsConstructor
    public static class Summary {

        private int answerIdx;
        private int questionIdx;
        private String answerText;
        private int answerScore;
        private String delYn;

        public Summary(Answer answer) {
            this.answerIdx = answer.getAnswerIdx();
            this.questionIdx = answer.getQuestionIdx();
            this.answerText = answer.getAnswerText();
            this.answerScore = answer.getAnswerScore();
            this.delYn = answer.getDelYn();
        }

    }

    @Data
    @AllArgsConstructor
    public static class Detail {

        private int answerIdx;
        private int questionIdx;
        private String answerText;
        private int answerScore;
        private String delYn;

        public Detail(Answer answer) {
            this.answerIdx = answer.getAnswerIdx();
            this.questionIdx = answer.getQuestionIdx();
            this.answerText = answer.getAnswerText();
            this.answerScore = answer.getAnswerScore();
            this.delYn = answer.getDelYn();
        }

    }


}
