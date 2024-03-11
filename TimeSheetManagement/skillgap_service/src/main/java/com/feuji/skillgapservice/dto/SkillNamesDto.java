package com.feuji.skillgapservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class SkillNamesDto 
{
	private String skillName;
	private String skillType;
	private Integer skillTypeId;
}
