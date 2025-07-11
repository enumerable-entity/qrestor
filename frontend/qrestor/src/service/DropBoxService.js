export class CountryService {
  getCountries() {
    return fetch('/data/countries.json')
      .then((res) => res.json())
      .then((d) => d.data)
  }
}

export class CurrencyService {
  getCurencies() {
    return fetch('/data/curencies.json')
      .then((res) => res.json())
      .then((d) => d.data)
  }
}