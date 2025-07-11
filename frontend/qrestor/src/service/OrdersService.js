import { fetchWrapper } from '@/fetchWrapper.js'

export default class OrdersService {
    constructor() {
        this.url = '/orders/orders'
    }

    getOrdersHistoryByDates(dateFrom, dateTo) {
        return fetchWrapper.get(this.url + '/history?dateFrom=' + dateFrom + '&dateTo=' + dateTo)
    }

    getActiveForDates(dateFrom, dateTo) {
        return fetchWrapper.get(this.url + '/active?dateFrom=' + dateFrom + '&dateTo=' + dateTo)
    }

    getMenuItemsOptionsForMenuItemId(menuItemId) {
        return fetchWrapper.get(this.url + '/all?menuItemId=' + menuItemId)
    }

    addMenuItemOption(menu) {
        return fetchWrapper.post(this.url, menu)
    }

    updateMenuItemOption(menu) {
        return fetchWrapper.put(this.url + '/' + menu.publicId, menu)
    }

    deleteMenuItemOption(publicId) {
        return fetchWrapper.delete(this.url + '/' + publicId)
    }

    changeOrderStatus(orderId, status) {
        return fetchWrapper.post(this.url+'/' + orderId +  '/status?status=' + status)
    }

    placeOrderAndPay(order) {
        return fetchWrapper.post(this.url, order)
    }

    getPaymentUrl(orderId) {
        return fetchWrapper.get('/paymentor/payment/order/' + orderId + '/getPaymentRedirectUrl')
    }
}
