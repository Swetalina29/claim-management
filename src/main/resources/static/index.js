const API = 'http://localhost:8080/api/claims';

      async function submitClaim() {
      		const claim = {
                claimantName: document.getElementById('claimantName').value,
                email:        document.getElementById('email').value,
                claimType:    document.getElementById('claimType').value,
                amount:       parseFloat(document.getElementById('amount').value),
                description:  document.getElementById('description').value
            };
			
			if (!claim.claimantName || !claim.email || !claim.claimType) 
			{
			    showMessage('Please fill in all required fields!', 'error');
			    return;
			}
	
			try {
				  const res  = await fetch(API, {
					method:  'POST',
					headers: { 'Content-Type': 'application/json' },
					body:    JSON.stringify(claim)
					});
					
					const data = await res.json();
					showMessage(`✅ Claim submitted! Your Claim ID is: ${data.id}`, 'success');
					
			} catch (err) {
				showMessage('❌ Error submitting claim. Try again.', 'error');
				}
		}
				
					  
		async function trackClaim() {
			const id = document.getElementById('trackId').value;
			if (!id) { 
				alert('Enter a Claim ID'); 
				return; 
			}

			try {
				const res  = await fetch(`${API}/${id}`);
				if (!res.ok) {
					 document.getElementById('trackResult').innerHTML = '<p class="error">Claim not found.</p>'; 
					 return; 
				 }
				 const c    = await res.json();

				document.getElementById('trackResult').innerHTML = `
													                 <div class="claim-card">
													                     <p><b>Name:</b> ${c.claimantName}</p>
													                     <p><b>Type:</b> ${c.claimType}</p>
													                     <p><b>Amount:</b> ₹${c.amount}</p>
													                     <p><b>Status:</b> <span class="badge badge-${c.status.toLowerCase()}">${c.status}</span></p>
													                     <p><b>Remarks:</b> ${c.adminRemarks || 'No remarks yet'}</p>
													                 </div>`;
	         } catch (err) {
					  document.getElementById('trackResult').innerHTML = '<p class="error">Error fetching claim.</p>';
	         }
	     }
									 
		 function showMessage(msg, type) {
		             const el = document.getElementById('message');
		             el.textContent  = msg;
		             el.className    = `message ${type}`;
		         }					
									 