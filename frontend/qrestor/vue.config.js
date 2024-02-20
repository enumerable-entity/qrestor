module.exports = {
  configureWebpack: {
    devtool: 'source-map'
  },
  publicPath: process.env.NODE_ENV === 'production' ? '/production-sub-path/' : 'http://localhost:8080/'
}
