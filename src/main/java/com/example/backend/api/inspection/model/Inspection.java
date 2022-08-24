package com.example.backend.api.inspection.model;

import com.example.backend.api.inspection.question.model.Question;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
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



}
