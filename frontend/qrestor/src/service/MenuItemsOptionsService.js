import { fetchWrapper } from '@/fetchWrapper.js'

export default class MenuItemsOptionsService {
    constructor() {
        this.url = '/menu/management/item-options'
    }

    getAllMenuItemsOptions() {
        return fetchWrapper.get(this.url)
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

    getItemsOptionsCombo() {
        return fetchWrapper.get('/menu/dictionary/itemOptionsCombo')
    }
}
