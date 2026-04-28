package com.example.claim_management.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.claim_management.model.Claim;
import com.example.claim_management.model.Claim.ClaimStatus;
import com.example.claim_management.repository.ClaimRepository;

@Service
public class ClaimService {

	@Autowired
	private ClaimRepository claimRepository ;
	
	public Claim submitClaim(Claim claim) {
        return claimRepository.save(claim);
    }
	
	public List<Claim> getAllClaims() {
        return claimRepository.findAll();
    }
	
	public Optional<Claim> getClaimById(Long id) {
        return claimRepository.findById(id);
    }
	
	public List<Claim> getClaimsByStatus(ClaimStatus status) {
        return claimRepository.findByStatus(status);
    }
	
	public Claim updateClaimStatus(Long id, ClaimStatus status, String remarks) {
        Claim claim = claimRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Claim not found with id: " + id));

        claim.setStatus(status);
        claim.setAdminRemarks(remarks);
        return claimRepository.save(claim);
    }
	
	 public void deleteClaim(Long id) {
	        claimRepository.deleteById(id);
	    }
	 
	 public List<Claim> searchByName(String name) {
	        return claimRepository.findByClaimantNameContainingIgnoreCase(name);
	    }
}
