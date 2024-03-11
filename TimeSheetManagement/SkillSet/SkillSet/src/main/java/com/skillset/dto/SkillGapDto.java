package com.skillset.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SkillGapDto {

     private Integer employeeId;
     private String email;
     private List<String> skillName;	
     private List<Integer> exCompetencyLevelId;
     private List<Integer> acCompetencyLevelId;
     private List<String>  exReferencedetailsValues;
     private List<String>  acReferenceDetailsValues;
}
