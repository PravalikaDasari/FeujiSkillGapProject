package com.skillset.dto;

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
public class GapDto {

	   private Integer employeeId;
	   private String employeeName;
	   private String employeeEmail;
	   private String designation;
	   private String email;
	    private String skillName;
	    private String description;
	    private Integer skillCategoryId;
	    private Integer exCompetencyLevelId;
	    private Integer acCompetencyLevelId;
	    private String exReferenceDetailsValues;
	    private String acReferenceDetailsValues;
	   
	    
//
//		List<EmployeeSkillDto> l = new ArrayList();
//
//		EmployeeSkillDto t = new EmployeeSkillDto();
//		t.setFirstName("supriya");
//		t.setDesignation("sa");
//		t.setCompetencyLevelId(1);
//		t.setSkillName("python");
//
//		EmployeeSkillDto t1 = new EmployeeSkillDto();
//		t.setFirstName("supriya");
//		t.setDesignation("sa");
//		t.setCompetencyLevelId(1);
//		t.setSkillName("html");
//
//		EmployeeSkillDto t2 = new EmployeeSkillDto();
//		t.setFirstName("supriya");
//		t.setDesignation("sa");
//		t.setCompetencyLevelId(1);
//		t.setSkillName("java");
//
//		l.add(t);
//		l.add(t1);
//
//		l.add(t2);
//
//	 
//	 
//		Map<String, EmployeeSkillDetailsDto> m = new HashMap<>();
//
//		for (EmployeeSkillDto f : l) {
//
//			String empName = f.getFirstName();
//			if (m.get(empName) == null) {
//				EmployeeSkillDetailsDto dto = new EmployeeSkillDetailsDto();
//				dto.setFirstnName(empName);
//				dto.setEmployeeId(f.getEmployeeId());
//				dto.setDesignation(f.getDesignation());
//				List<String> skillNamelist = new ArrayList();
//
//				skillNamelist.add(f.getSkillName());
//				m.put(empName, dto);
//			} else {
//				EmployeeSkillDetailsDto dto = m.get(empName);
//				dto.setFirstnName(empName);
//				dto.setDesignation(f.getDesignation());
//				List<String> skillNamelist = new ArrayList();
//
//				skillNamelist.add(f.getSkillName());
//			}
//
//		}
//
//	 
//	}
}
