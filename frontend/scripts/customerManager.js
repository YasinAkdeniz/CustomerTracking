const CUSTOMER_API = 'http://localhost:8080/customers';

window.initCustomers = function () {
    loadCustomers();
    bindCustomerForm();
};

window.openCustomerModal = function (id = null) {
    const form = document.getElementById('customerForm');
    form.reset();
    document.getElementById('customerId').value = '';

    if (id) {
        fetch(`${CUSTOMER_API}/${id}`)
            .then(res => res.json())
            .then(c => {
                form.customerId.value = c.id;
                form.name.value = c.name;
                form.surname.value = c.surname;
                form.email.value = c.email;
                form.phoneNumber.value = c.phoneNumber;
                form.gender.value = c.gender;
                form.identityNumber.value = c.identityNumber ?? '';
                bootstrap.Modal.getOrCreateInstance(document.getElementById('customerModal')).show();
            });
    } else {
        bootstrap.Modal.getOrCreateInstance(document.getElementById('customerModal')).show();
    }
};

window.deleteCustomer = function (id) {
    if (!confirm('Müşteriyi silmek istediğinizden emin misiniz?')) return;

    fetch(`${CUSTOMER_API}/${id}`, { method: 'DELETE' })
        .then(() => {
            alert('Müşteri silindi');
            loadCustomers();
        })
        .catch(err => alert('Silinemedi: ' + err.message));
};

function loadCustomers() {
    fetch(CUSTOMER_API)
        .then(res => res.json())
        .then(data => {
            const tbody = document.getElementById('customerTableBody');
            tbody.innerHTML = '';
            data.forEach(c => {
                const row = document.createElement('tr');
                row.innerHTML = `
                    <td>${c.name}</td>
                    <td>${c.surname}</td>
                    <td>${c.email}</td>
                    <td>${c.phoneNumber}</td>
                    <td>${c.gender}</td>
                    <td>${c.identityNumber || ''}</td>
                    <td>
                        <button class="btn btn-sm btn-warning me-1" onclick="openCustomerModal(${c.id})">Düzenle</button>
                        <button class="btn btn-danger btn-sm" onclick="deleteCustomer(${c.id})">Sil</button>
                    </td>
                `;
                tbody.appendChild(row);
            });
        });
}

function bindCustomerForm() {
    const form = document.getElementById('customerForm');
    form.addEventListener('submit', function (e) {
        e.preventDefault();

        const customerId = form.customerId.value;
        const customer = {
            name: form.name.value,
            surname: form.surname.value,
            email: form.email.value,
            phoneNumber: form.phoneNumber.value,
            gender: form.gender.value,
            identityNumber: form.identityNumber.value ? parseInt(form.identityNumber.value) : null
        };

        const method = customerId ? 'PUT' : 'POST';
        const url = customerId ? `${CUSTOMER_API}/${customerId}` : CUSTOMER_API;

        fetch(url, {
            method: method,
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(customer)
        })
            .then(res => {
                if (!res.ok) throw new Error("Müşteri kaydedilemedi.");
                return res.json();
            })
            .then(() => {
                alert('Müşteri başarıyla kaydedildi');
                bootstrap.Modal.getInstance(document.getElementById('customerModal')).hide();
                loadCustomers();
            })
            .catch(err => alert(err.message));
    });
}
