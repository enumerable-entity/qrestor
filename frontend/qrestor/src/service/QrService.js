import { fetchWrapper } from '@/fetchWrapper.js'

export default class MenuItemsService {
    constructor() {
        this.url = '/qresolver/management/qr'
    }

    getAllQrCodes() {
        return fetchWrapper.get(this.url)
    }

    addQrCode(qrCodeId) {
        return fetchWrapper.post(this.url, qrCodeId)
    }

    updateQrCode(menu) {
        return fetchWrapper.put(this.url + '/' + menu.publicId, menu)
    }

    deleteQrCode(publicId) {
        return fetchWrapper.delete(this.url + '/' + publicId)
    }

}
