var webpack = require('webpack');
var path = require('path');

module.exports = function (config) {
  config.set({
    browsers: [
      'PhantomJS'
    ],
    frameworks: [
      'jasmine'
    ],
    plugins: [
      'karma-jasmine',
      'karma-phantomjs-launcher',
      'karma-sourcemap-loader',
      'karma-webpack'
    ],
    files: [
      'tests.webpack.js'
    ],
    preprocessors: {
      'tests.webpack.js': ['webpack', 'sourcemap']
    },
    reporters: ['dots'],
    webpack: {
      devtool: 'inline-source-map',
      module: {
        loaders: [
          {
            test: /\.js$/,
            loader: 'babel-loader',
            query: {
              presets: ['react', 'es2015']
            }
          }, {
            test: /\.css$/,
            loader: "style-loader!css-loader"
          }, {
            test: /\.(png|woff|woff2|eot|ttf|svg)$/,
            loader: 'url-loader?limit=100000'
          }
        ]
      }
    },
    singleRun: true,
    webpackServer: {
      noInfo: true
    }
  });
};