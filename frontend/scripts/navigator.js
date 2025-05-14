window.showSection = function (section) {
    loadContent(section);
};

function loadContent(section) {
    fetch(`templates/${section}.html`)
        .then(res => {
            if (!res.ok) throw new Error('Şablon dosyası bulunamadı!');
            return res.text();
        })
        .then(html => {
            document.getElementById('content-placeholder').innerHTML = html;

            switch (section) {
                case 'customers':
                    import('./customerManager.js').then(() => {
                        window.initCustomers && window.initCustomers();
                    });
                    break;
                case 'products':
                    import('./productManager.js').then(() => {
                        window.initProducts && window.initProducts();
                    });
                    break;
                case 'orders':
                    import('./orderManager.js').then(() => {
                        window.initOrders && window.initOrders();
                    });
                    break;
                case 'brands':
                    import('./brandManager.js').then(() => {
                        window.initBrands && window.initBrands();
                    });
                    break;
                default:
                    break;
            }
        })
        .catch(err => {
            document.getElementById('content-placeholder').innerHTML = `<div class="alert alert-danger">${err.message}</div>`;
        });
}
