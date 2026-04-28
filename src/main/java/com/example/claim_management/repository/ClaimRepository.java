package com.example.claim_management.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.claim_management.model.Claim;
import com.example.claim_management.model.Claim.ClaimStatus;

@Repository
public interface ClaimRepository extends JpaRepository<Claim, Long>{

	List<Claim> findByStatus(ClaimStatus status);
    List<Claim> findByEmail(String email);
    List<Claim> findByClaimantNameContainingIgnoreCase(String name);
}
