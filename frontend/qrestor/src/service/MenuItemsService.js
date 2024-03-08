import { fetchWrapper } from '@/fetchWrapper.js'

export default class MenuItemsService {
    constructor() {
        this.url = '/menu/management/menu-items'
    }

    getAllMenuItems() {
        return fetchWrapper.get(this.url)
    }

    getMenuItemsForMenuId() {
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

    getAllCategories() {
        return fetchWrapper.get('/menu/category')
    }
}
