import { fetchWrapper } from '@/fetchWrapper.js'

export default class SellingPointsService {
    constructor() {
        this.url = '/api/restaurant/management/restaurant'
    }
    getProductsSmall() {
        return fetch('/demo/data/products-small.json')
            .then((res) => res.json())
            .then((d) => d);
    }

    getpoin() {
        return fetch('/demo/data/products.json')
            .then((res) => res.json())
            .then((d) => d.data);
    }

    getSellingPoints() {
        return fetchWrapper.get(this.url)
    }

    addSellingPoint(sellingPoint) {
        return fetchWrapper.post(this.url, sellingPoint)
    }

    updateSellingPoint(sellingPoint) {
        return fetchWrapper.put(this.url + '/' + sellingPoint.publicId, sellingPoint)
    }
}
