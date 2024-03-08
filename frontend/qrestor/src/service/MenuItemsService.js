import { fetchWrapper } from '@/fetchWrapper.js'

export default class MenuItemsService {
    constructor() {
        this.url = '/menu/management/menu-items'
    }

    getAllMenuItems() {
        return fetchWrapper.get(this.url)
    }

    getMenuItemsForMenuId(publicId) {
        return fetchWrapper.get(this.url + '/menu/' + publicId)
    }

    addMenuItem(menu) {
        return fetchWrapper.post(this.url, menu)
    }

    updateMenuItem(menu) {
        return fetchWrapper.put(this.url + '/' + menu.publicId, menu)
    }

    deleteMenuItem(publicId) {
        return fetchWrapper.delete(this.url + '/' + publicId)
    }

    getAllCategories() {
        return fetchWrapper.get('/menu/category')
    }
}
