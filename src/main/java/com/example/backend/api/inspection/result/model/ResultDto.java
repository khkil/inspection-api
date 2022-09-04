package com.example.backend.api.inspection.result.model;

import com.example.backend.api.inspection.question.model.Question;
import com.example.backend.api.inspection.question.model.QuestionDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class ResultDto {


    @Data
    @AllArgsConstructor
    public static class Summary{

        private Integer resultIdx;
        private String resultName;
        private String resultTitle;

        public Summary(Result result){
            this.resultIdx = result.getResultIdx();
            this.resultName = result.getResultName();
            this.resultTitle = result.getResultTitle();
        }

    }

    @Data
    @AllArgsConstructor
    public static class Detail{

        private Integer resultIdx;
        private String resultName;
        private String resultTitle;
        private String mainSentence;
        private String subSentence;
        private String goodKeyword;
        private String badKeyword;
        private List<QuestionDto.Summary> questionList;

        public Detail(Result result){

            this.resultIdx = result.getResultIdx();
            this.resultName = result.getResultName();
            this.resultTitle = result.getResultTitle();
            this.mainSentence = result.getMainSentence();
            this.subSentence = result.getSubSentence();
            this.goodKeyword = result.getGoodKeyword();
            this.badKeyword = result.getBadKeyword();
            this.questionList = result.getQuestionList().stream().map(r -> new QuestionDto.Summary(r)).collect(Collectors.toList());
        }

    }

}
