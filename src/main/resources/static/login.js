async function login() {
    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;

    if (!username || !password) {
        showMsg('Please enter username and password', 'error');
        return;
    }

    const credentials = btoa(`${username}:${password}`);

    try {
        const res = await fetch('http://localhost:8080/api/claims', {
            headers: {
                'Authorization': `Basic ${credentials}`
            }
        });

        if (res.ok) {
            // Save credentials for admin.js to use
            sessionStorage.setItem('authToken', credentials);
            window.location.href = 'admin.html';
        } else {
            showMsg('❌ Invalid username or password', 'error');
        }
    } catch (err) {
        showMsg('❌ Cannot connect to server', 'error');
    }
}

function showMsg(msg, type) {
    const el = document.getElementById('loginMsg');
    el.textContent = msg;
    el.className = `message ${type}`;
}