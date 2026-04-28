package com.example.claim_management.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "claims")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Claim {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY )
	private Long id;
	
	@Column(nullable = false)
	private String claimantName;
	
	@Column(nullable = false)
	private String email;
	
	@Column(nullable = false)
	private String claimType;
	
	@Column(length = 1000)
    private String description;
	
	private Double amount;
	
	@Enumerated(EnumType.STRING)
    private ClaimStatus status; 
	
	private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    private String adminRemarks; 
    
    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.status = ClaimStatus.PENDING;
    }
    
    @PreUpdate
    public void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public enum ClaimStatus {
        PENDING, APPROVED, REJECTED, UNDER_REVIEW
    }
	
	
}
