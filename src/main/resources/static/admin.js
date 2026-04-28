const API = 'http://localhost:8080/api/claims';

const token = sessionStorage.getItem('authToken');
if (!token) {
    window.location.href = 'login.html';
}

const authHeaders = {
    'Content-Type': 'application/json',
    'Authorization': `Basic ${token}`
};

async function loadClaims(filter) {
    const url = filter === 'ALL' ? API : `${API}/status/${filter}`;
    const res = await fetch(url, { headers: authHeaders });
    if (res.status === 401) {
        sessionStorage.removeItem('authToken');
        window.location.href = 'login.html';
        return;
    }
    const claims = await res.json();
    renderClaims(claims);
}

function renderClaims(claims) {
    const container = document.getElementById('claimsContainer');
    if (claims.length === 0) {
        container.innerHTML = '<p>No claims found.</p>';
        return;
    }

    container.innerHTML = claims.map(c => `
        <div class="card claim-card">
            <h3>Claim #${c.id} — ${c.claimantName}</h3>
            <p><b>Email:</b> ${c.email}</p>
            <p><b>Type:</b> ${c.claimType}</p>
            <p><b>Amount:</b> ₹${c.amount}</p>
            <p><b>Description:</b> ${c.description}</p>
            <p><b>Status:</b> <span class="badge badge-${c.status.toLowerCase()}">${c.status}</span></p>
            <p><b>Submitted:</b> ${new Date(c.createdAt).toLocaleDateString()}</p>
            ${c.adminRemarks ? `<p><b>Remarks:</b> ${c.adminRemarks}</p>` : ''}

            <div class="form-group">
                <label>Admin Remarks</label>
                <input type="text" id="remarks-${c.id}" placeholder="Enter remarks...">
            </div>

            <div class="action-buttons">
                <button class="btn btn-success" onclick="updateStatus(${c.id}, 'APPROVED')">✅ Approve</button>
                <button class="btn btn-warning" onclick="updateStatus(${c.id}, 'UNDER_REVIEW')">🔍 Review</button>
                <button class="btn btn-danger"  onclick="updateStatus(${c.id}, 'REJECTED')">❌ Reject</button>
                <button class="btn btn-secondary" onclick="deleteClaim(${c.id})">🗑️ Delete</button>
            </div>
        </div>
    `).join('');
}

async function updateStatus(id, status) {
    const remarks = document.getElementById(`remarks-${id}`).value;
    const res = await fetch(`${API}/${id}/status`, {
        method: 'PUT',
        headers: authHeaders,
        body: JSON.stringify({ status, remarks })
    });
    if (res.status === 401) {
        sessionStorage.removeItem('authToken');
        window.location.href = 'login.html';
        return;
    }
    alert(`Claim #${id} updated to ${status}`);
    loadClaims('ALL');
}

async function deleteClaim(id) {
    if (!confirm(`Are you sure you want to delete Claim #${id}?`)) return;
    const res = await fetch(`${API}/${id}`, {
        method: 'DELETE',
        headers: authHeaders
    });
    if (res.status === 401) {
        sessionStorage.removeItem('authToken');
        window.location.href = 'login.html';
        return;
    }
    alert(`Claim #${id} deleted.`);
    loadClaims('ALL');
}

function logout() {
    sessionStorage.removeItem('authToken');
    window.location.href = 'login.html';
}

document.getElementById('searchInput').addEventListener('input', async function () {
    const name = this.value.trim();
    if (name.length === 0) {
        loadClaims('ALL');
        return;
    }
    const res = await fetch(`${API}/search?name=${name}`, { headers: authHeaders });
    const claims = await res.json();
    renderClaims(claims);
});

loadClaims('ALL');