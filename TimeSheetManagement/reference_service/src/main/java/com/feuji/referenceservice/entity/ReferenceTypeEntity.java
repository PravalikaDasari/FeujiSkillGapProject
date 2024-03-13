package com.feuji.referenceservice.entity;

import java.sql.Timestamp;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
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
@Entity
@Table(name = "timesheet_entry_system")
public class ReferenceTypeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "reference_type_id")
	private Integer referenceTypeId;

	@Column(name = "reference_type_name")
	private String referenceTypeName;

	@OneToMany(mappedBy = "referenceType", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ReferenceDetailsEntity> referenceDetailsList;

	@Column(name = "uuid")
	private String uuid;

	@Column(name = "is_deleted")
	private Boolean isDeleted;

	@Column(name = "created_by")
	private String createdBy;

	@Column(name = "created_on")
	private Timestamp createdOn;

	@Column(name = "modified_by")
	private String modifiedBy;

	@Column(name = "modified_on")
	private Timestamp modifiedOn;
}
