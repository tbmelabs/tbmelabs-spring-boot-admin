var webpack = require('karma-webpack');

module.exports = function (config) {
  config.set({
    browsers: ['PhantomJS2'],
    files: [
      'node_modules/babel-polyfill/browser.js',
      'tests.webpack.js'
    ],
    frameworks: ['chai','mocha'],
    plugins: [
      'karma-chai',
      'karma-coverage',
      'karma-mocha',
      'karma-mocha-reporter',
      'karma-phantomjs2-launcher',
      'karma-sourcemap-loader',
      'karma-webpack'
    ],
    preprocessors: {
      'tests.webpack.js': ['webpack', 'sourcemap']
    },
    reporters: ['coverage', 'mocha'],
    singleRun: true,
    webpack: {
      devtool: 'inline-source-map',
      module: {
        loaders: [
          {
            test: /\.js$/,
            loader: 'babel-loader',
            query: {
              plugins: ['transform-object-rest-spread'],
              presets: ['env', 'react']
            }
          }, {
            test: /\.css$/,
            loader: "style-loader!css-loader"
          }, {
            test: /\.(jpe?g|png|svg|ai)$/,
            loader: "file-loader?publicPath=public/"
          }, {
            test: /\.(woff|woff2|eot|ttf)$/,
            loader: 'url-loader?limit=100000'
          }
        ]
      }
    },
    webpackServer: {
      noInfo: true
    }
  });
};