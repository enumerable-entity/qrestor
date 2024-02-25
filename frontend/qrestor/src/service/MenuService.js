import { fetchWrapper } from '@/fetchWrapper.js'

export default class MenuService {
    constructor() {
        this.url = '/menu/menu/'
    }

    getMenus() {
        return fetchWrapper.get(this.url)
    }

    addMenu(menu) {
        return fetchWrapper.post(this.url, menu)
    }

    updateMenu(menu) {
        return fetchWrapper.put(this.url + '/' + menu.publicId, menu)
    }

    deleteMenu(publicId) {
        return fetchWrapper.delete(this.url + '/' + publicId)
    }
}
