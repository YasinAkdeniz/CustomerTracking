const BRAND_API = 'http://localhost:8080/brands';

window.initBrands = function () {
    loadBrands();
    bindBrandForm();
};

window.openBrandModal = function (id = null) {
    const form = document.getElementById('brandForm');
    form.reset();
    document.getElementById('brandId').value = '';

    if (id) {
        fetch(`${BRAND_API}/${id}`)
            .then(res => res.json())
            .then(b => {
                form.brandId.value = b.id;
                form.brandName.value = b.name;
                bootstrap.Modal.getOrCreateInstance(document.getElementById('brandModal')).show();
            });
    } else {
        bootstrap.Modal.getOrCreateInstance(document.getElementById('brandModal')).show();
    }
};

window.deleteBrand = function (id) {
    if (!confirm('Markayı silmek istediğinizden emin misiniz?')) return;

    fetch(`${BRAND_API}/${id}`, { method: 'DELETE' })
        .then(() => {
            alert('Marka silindi');
            loadBrands();
        })
        .catch(err => alert('Silinemedi: ' + err.message));
};

function loadBrands() {
    fetch(BRAND_API)
        .then(res => res.json())
        .then(data => {
            const tbody = document.getElementById('brandTableBody');
            tbody.innerHTML = '';
            data.forEach(b => {
                const row = document.createElement('tr');
                row.innerHTML = `
                    <td>${b.name}</td>
                    <td>
                        <button class="btn btn-sm btn-warning me-1" onclick="openBrandModal(${b.id})">Düzenle</button>
                        <button class="btn btn-danger btn-sm" onclick="deleteBrand(${b.id})">Sil</button>
                    </td>
                `;
                tbody.appendChild(row);
            });
        });
}

function bindBrandForm() {
    const form = document.getElementById('brandForm');
    form.addEventListener('submit', function (e) {
        e.preventDefault();

        const brandId = form.brandId.value;
        const brand = {
            name: form.brandName.value
        };

        const method = brandId ? 'PUT' : 'POST';
        const url = brandId ? `${BRAND_API}/${brandId}` : BRAND_API;

        fetch(url, {
            method: method,
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(brand)
        })
            .then(res => {
                if (!res.ok) throw new Error("Marka kaydedilemedi.");
                return res.json();
            })
            .then(() => {
                alert('Marka başarıyla kaydedildi');
                bootstrap.Modal.getInstance(document.getElementById('brandModal')).hide();
                loadBrands();
            })
            .catch(err => alert(err.message));
    });
}
