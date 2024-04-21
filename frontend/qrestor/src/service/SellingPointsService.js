import { fetchWrapper } from '@/fetchWrapper.js'

export default class SellingPointsService {
    constructor() {
        this.url = '/restaurant/management/restaurant'
    }
    getSellingPointsDictionary() {
        return fetchWrapper.get('/restaurant/dictionary/restaurantCombo')
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

    deleteSellingPoint(publicId) {
        return fetchWrapper.delete(this.url + '/' + publicId)
    }

    getSellingPointInfo(publicId) {
        return fetchWrapper.get('/restaurant/restaurant/' + publicId)
    }

    callWaiter(tableId, sellPointId) {
        return fetchWrapper.get('/kitchenboard/waiter-request?tableNr=' + tableId + '&restaurantId=' + sellPointId)
    }
}
