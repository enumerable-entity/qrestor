import { fetchWrapper } from '@/fetchWrapper.js'

export default class IngredientsService {
    constructor() {
        this.url = '/menu/management/ingredients'
    }

    getIngredients() {
        return fetchWrapper.get(this.url)
    }

    addIngredients(ingredient) {
        return fetchWrapper.post(this.url, ingredient)
    }

    updateIngredients(ingredient) {
        return fetchWrapper.put(this.url + '/' + ingredient.publicId, ingredient)
    }

    deleteIngredients(publicId) {
        return fetchWrapper.delete(this.url + '/' + publicId)
    }

    getIngredientsCombo() {
        return fetchWrapper.get('/menu/dictionary/ingredientsCombo')
    }
}
