export class CountryService {
  getCountries() {
    return fetch('/demo/data/countries.json')
      .then((res) => res.json())
      .then((d) => d.data)
  }
}

export class CurrencyService {
  getCurencies() {
    return fetch('/demo/data/curencies.json')
      .then((res) => res.json())
      .then((d) => d.data)
  }
}