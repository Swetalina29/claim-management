package com.example.claim_management.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.claim_management.model.Claim;
import com.example.claim_management.model.Claim.ClaimStatus;
import com.example.claim_management.service.ClaimService;

@RestController
@RequestMapping("/api/claims")
@CrossOrigin(origins = "*")  
public class ClaimController {
	
	@Autowired
    private ClaimService claimService;
	
	@PostMapping
    public ResponseEntity<Claim> submitClaim(@RequestBody Claim claim) {
        Claim saved = claimService.submitClaim(claim);
        return ResponseEntity.ok(saved);
    }

	@GetMapping
    public ResponseEntity<List<Claim>> getAllClaims() {
        return ResponseEntity.ok(claimService.getAllClaims());
    }
	
	@GetMapping("/{id}")
    public ResponseEntity<Claim> getClaimById(@PathVariable Long id) {
        return claimService.getClaimById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
	
	@GetMapping("/status/{status}")
    public ResponseEntity<List<Claim>> getByStatus(@PathVariable ClaimStatus status) {
        return ResponseEntity.ok(claimService.getClaimsByStatus(status));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Claim> updateStatus(
            @PathVariable Long id,
            @RequestBody Map<String, String> body) {

        ClaimStatus status = ClaimStatus.valueOf(body.get("status"));
        String remarks = body.get("remarks");
        return ResponseEntity.ok(claimService.updateClaimStatus(id, status, remarks));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClaim(@PathVariable Long id) {
        claimService.deleteClaim(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<Claim>> search(@RequestParam String name) {
        return ResponseEntity.ok(claimService.searchByName(name));
    }
}
