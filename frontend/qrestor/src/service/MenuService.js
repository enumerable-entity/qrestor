import { fetchWrapper } from '@/fetchWrapper.js'

export default class MenuService {
    constructor() {
        this.url = '/menu/management/menu'
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

    getMenuCombo() {
        return fetchWrapper.get('/menu/dictionary/menusCombo')
    }

    getMenuAggregate(restId) {
        return fetchWrapper.get('/menu/menu/' + restId)
    }

    getMenuItemOptionsInfo(itemId) {
        return fetchWrapper.get('/menu/menu/item/' + itemId)
    }
}
