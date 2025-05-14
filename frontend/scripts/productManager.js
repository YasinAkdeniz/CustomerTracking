const PRODUCT_API = 'http://localhost:8080/products';
const BRAND_API = 'http://localhost:8080/brands';

window.initProducts = function () {
    loadProducts();
    loadBrands();
    bindProductForm();

    document.querySelectorAll('input[name="type"]').forEach(radio => {
        radio.addEventListener('change', () => {
            document.getElementById('opticalSubType').classList.toggle('d-none', radio.value !== 'OPTICAL');
        });
    });
};

window.openProductModal = function (id = null) {
    const form = document.getElementById('productForm');
    form.reset();
    document.getElementById('productId').value = '';
    document.getElementById('opticalSubType').classList.add('d-none');

    if (id) {
        fetch(`${PRODUCT_API}/${id}`)
            .then(res => res.json())
            .then(p => {
                form.productId.value = p.id;
                form.productName.value = p.fullName;
                form.productPrice.value = p.price;
                form.productStock.value = p.stockAmount;
                form.productBrand.value = p.brand?.id || '';

                const typeInput = form.querySelector(`input[name="type"][value="${p.productType}"]`);
                if (typeInput) typeInput.checked = true;

                if (p.productType === 'OPTICAL') {
                    document.getElementById('opticalSubType').classList.remove('d-none');
                    form.opticalType.value = p.productSubType || '';
                }

                bootstrap.Modal.getOrCreateInstance(document.getElementById('productModal')).show();
            });
    } else {
        bootstrap.Modal.getOrCreateInstance(document.getElementById('productModal')).show();
    }
};

window.deleteProduct = function (id) {
    if (!confirm('Silmek istediğinizden emin misiniz?')) return;

    fetch(`${PRODUCT_API}/${id}`, { method: 'DELETE' })
        .then(() => {
            alert('Ürün silindi');
            loadProducts();
        })
        .catch(err => alert('Silinemedi: ' + err.message));
};

function loadProducts() {
    fetch(PRODUCT_API)
        .then(res => res.json())
        .then(data => {
            const tbody = document.getElementById('productTableBody');
            tbody.innerHTML = '';
            data.forEach(p => {
                const row = document.createElement('tr');
                row.innerHTML = `
                    <td>${p.fullName}</td>
                    <td>${p.brand?.name || ''}</td>
                    <td>${p.price} ₺</td>
                    <td>${p.stockAmount}</td>
                    <td>${p.productType}${p.productType === 'OPTICAL' && p.productSubType ? ' - ' + p.productSubType : ''}</td>
                    <td>
                        <button class="btn btn-sm btn-warning me-1" onclick="openProductModal(${p.id})">Düzenle</button>
                        <button class="btn btn-danger btn-sm" onclick="deleteProduct(${p.id})">Sil</button>
                    </td>
                `;
                tbody.appendChild(row);
            });
        });
}

function loadBrands() {
    fetch(BRAND_API)
        .then(res => res.json())
        .then(data => {
            const select = document.getElementById('productBrand');
            select.innerHTML = '<option value="">Seçiniz</option>';
            data.forEach(b => {
                const option = document.createElement('option');
                option.value = b.id;
                option.textContent = b.name;
                select.appendChild(option);
            });
        });
}

function bindProductForm() {
    const form = document.getElementById('productForm');
    form.addEventListener('submit', function (e) {
        e.preventDefault();

        const productId = form.productId.value;

        const product = {
            fullName: form.productName.value,
            price: parseFloat(form.productPrice.value),
            stockAmount: parseInt(form.productStock.value),
            productType: form.querySelector('input[name="type"]:checked').value,
            brand: { id: parseInt(form.productBrand.value) }
        };

        if (product.productType === 'OPTICAL') {
            product.productSubType = form.opticalType.value;
        }

        const method = productId ? 'PUT' : 'POST';
        const url = productId ? `${PRODUCT_API}/${productId}` : PRODUCT_API;

        fetch(url, {
            method: method,
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(product)
        })
            .then(res => {
                if (!res.ok) throw new Error("Ürün kaydedilemedi.");
                return res.json();
            })
            .then(() => {
                alert('Ürün başarıyla kaydedildi');
                bootstrap.Modal.getInstance(document.getElementById('productModal')).hide();
                loadProducts();
            })
            .catch(err => alert(err.message));
    });
}
