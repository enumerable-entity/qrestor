import { fetchWrapper } from '@/fetchWrapper.js'

export default class OptionsPositionsService {
    constructor() {
        this.url = '/menu/management/options-positions'
    }

    getAllOptionsPositions() {
        return fetchWrapper.get(this.url)
    }

    getOptionsPositionsForOptionId(optionId) {
        return fetchWrapper.get(this.url + '/all?optionId=' + optionId)
    }

    addOptionPosition(menu) {
        return fetchWrapper.post(this.url, menu)
    }

    updateOptionPosition(position) {
        return fetchWrapper.put(this.url + '/' + position.publicId, position)
    }

    deleteOptionPosition(publicId) {
        return fetchWrapper.delete(this.url + '/' + publicId)
    }

    getItemsOptionsPositionsCombo() {
        return fetchWrapper.get('/menu/dictionary/itemOptionPositionsCombo')
    }
}
