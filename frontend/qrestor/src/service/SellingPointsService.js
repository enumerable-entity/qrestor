export default class SellingPointsService {
    getProductsSmall() {
        return fetch('/demo/data/products-small.json')
            .then((res) => res.json())
            .then((d) => d.data);
    }

    getSellingPoints() {
        return fetch('/demo/data/products.json')
            .then((res) => res.json())
            .then((d) => d.data);
    }
}
