package com.example.backend.api.inspection.model;

import com.example.backend.api.inspection.question.model.Question;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Inspection {
    @Id
    @Column(name = "inspection_idx", nullable = false)
    private Integer inspectionIdx;

    @Column(name = "inspection_name")
    private String inspectionName;

    @Column(name = "pay_yn")
    private String payYn;

    @Column(name = "octagnosis_yn")
    private String octagnosisYn;

    @Column(name = "show_yn")
    private String showYn;

    @Column(name = "rank_count")
    private Integer rankCount;

 /*   @JsonIgnore
    private Integer totalPage;*/

    @OneToMany
    @JsonIgnore
    @JoinColumn(name = "inspection_idx")
    private List<Question> questions = new ArrayList<>();

    @Builder
    public Inspection(int inspectionIdx, String inspectionName, String payYn, String octagnosisYn, int rankCount){
        this.inspectionIdx = inspectionIdx;
        this.inspectionName = inspectionName;
        this.payYn = payYn;
        this.octagnosisYn = octagnosisYn;
        this.rankCount = rankCount;
    }

}
