const ORDER_API = 'http://localhost:8080/orders';
const CUSTOMER_SEARCH_API = 'http://localhost:8080/customers/search';
const PRODUCT_SEARCH_API = 'http://localhost:8080/products/search';

window.initOrders = function () {
    loadOrders();
    bindOrderForm();
};

window.openOrderModal = function (id = null) {
    const form = document.getElementById('orderForm');
    form.reset();
    form.orderId.value = '';

    document.getElementById('customerSelect').innerHTML = '';
    document.getElementById('productSelect').innerHTML = '';

    if (id) {
        fetch(`${ORDER_API}/${id}`)
            .then(res => res.json())
            .then(order => {
                form.orderId.value = order.id;
                form.quantityInput.value = order.quantity;
                form.priceInput.value = order.totalPrice;

                const customerOption = document.createElement("option");
                customerOption.value = order.customer.id;
                customerOption.text = order.customer.name;
                customerOption.selected = true;
                form.customerSelect.appendChild(customerOption);

                const productOption = document.createElement("option");
                productOption.value = order.product.id;
                productOption.text = order.product.name;
                productOption.selected = true;
                form.productSelect.appendChild(productOption);

                bootstrap.Modal.getOrCreateInstance(document.getElementById('orderModal')).show();
            });
    } else {
        bootstrap.Modal.getOrCreateInstance(document.getElementById('orderModal')).show();
    }
};

window.deleteOrder = function (id) {
    if (!confirm('Siparişi silmek istediğinizden emin misiniz?')) return;

    fetch(`${ORDER_API}/${id}`, { method: 'DELETE' })
        .then(() => {
            alert('Sipariş silindi');
            loadOrders();
        })
        .catch(err => alert('Silinemedi: ' + err.message));
};

function loadOrders() {
    fetch(ORDER_API)
        .then(res => res.json())
        .then(data => {
            const tbody = document.getElementById('orderTableBody');
            tbody.innerHTML = '';
            data.forEach(order => {
                const row = document.createElement('tr');
                row.innerHTML = `
                    <td>${order.customer.name} ${order.customer.surname}</td>
                    <td>${order.product.fullName}</td>
                    <td>${order.quantity}</td>
                    <td>${order.totalPrice.toFixed(2)} TL</td>
                    <td>${order.orderDate ? order.orderDate.replace("T", " ").slice(0, 16) : '-'}</td>
                    <td>
                        <button class="btn btn-sm btn-warning me-1" onclick="openOrderModal(${order.id})">Düzenle</button>
                        <button class="btn btn-danger btn-sm" onclick="deleteOrder(${order.id})">Sil</button>
                    </td>
                `;
                tbody.appendChild(row);
            });
        });
}

function bindOrderForm() {
    const form = document.getElementById('orderForm');
    form.addEventListener('submit', function (e) {
        e.preventDefault();

        const orderId = form.orderId.value;
        const order = {
            customer: { id: form.customerSelect.value },
            product: { id: form.productSelect.value },
            quantity: parseInt(form.quantityInput.value),
            totalPrice: parseFloat(form.priceInput.value)
        };

        const method = orderId ? 'PUT' : 'POST';
        const url = orderId ? `${ORDER_API}/${orderId}` : ORDER_API;

        fetch(url, {
            method: method,
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(order)
        })
            .then(res => {
                if (!res.ok) throw new Error("Sipariş kaydedilemedi.");
                return res.json();
            })
            .then(() => {
                alert('Sipariş başarıyla kaydedildi');
                bootstrap.Modal.getInstance(document.getElementById('orderModal')).hide();
                loadOrders();
            })
            .catch(err => alert(err.message));
    });
}

window.searchCustomers = function (query) {
    fetch(`${CUSTOMER_SEARCH_API}?name=${query}`)
        .then(res => res.json())
        .then(data => {
            const select = document.getElementById("customerSelect");
            select.innerHTML = '';
            data.forEach(customer => {
                const option = document.createElement("option");
                option.value = customer.id;
                option.text = `${customer.name} ${customer.surname}`; // 👈 burada birleşik gösterim
                select.appendChild(option);
            });
        });
};


window.searchProducts = function (query) {
    fetch(`${PRODUCT_SEARCH_API}?fullName=${query}`)
        .then(res => res.json())
        .then(data => {
            const select = document.getElementById("productSelect");
            select.innerHTML = '';
            data.forEach(product => {
                const option = document.createElement("option");
                option.value = product.id;
                option.text = product.fullName;
                select.appendChild(option);
            });
        });
};
