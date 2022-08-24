package com.example.backend.api.inspection.question.model;

import com.example.backend.api.inspection.model.Inspection;
import com.example.backend.api.inspection.question.answer.model.Answer;
import com.example.backend.api.inspection.question.answer.model.AnswerDto;
import com.example.backend.util.enumerator.QuestionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    @AllArgsConstructor
    public static class Summary {

        private Integer questionIdx;
        private Integer questionNumber;
        private String questionText;
        private String delYn;

        public Summary(Question question) {
            this.questionIdx = question.getQuestionIdx();
            this.questionNumber = question.getQuestionNumber();
            this.questionText = question.getQuestionText();
            this.delYn = question.getDelYn();
        }
    }

    @Data
    @AllArgsConstructor
    public static class Detail {

        private Integer questionIdx;
        private Integer questionNumber;
        private String questionText;
        private Integer questionOrder;
        private String delYn;
        private QuestionType questionType;
        private QuestionType answerType;
        private List<AnswerDto.Summary> answers;

        public Detail(Question question) {
            this.questionIdx = question.getQuestionIdx();
            this.questionNumber = question.getQuestionNumber();
            this.questionText = question.getQuestionText();
            this.questionOrder = question.getQuestionOrder();
            this.delYn = question.getDelYn();
            this.answers = question.getAnswers().stream().map(v -> new AnswerDto.Summary(v)).collect(Collectors.toList());
        }
    }



}
