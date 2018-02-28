var webpack = require('webpack');
var path = require('path');

var BUILD_DIR = path.resolve(__dirname, 'public');
var NODE_DIR = path.resolve(__dirname, 'node_modules');
var TEST_DIR = path.resolve(__dirname, '__tests__');

var COMMON_UTILS = path.resolve(__dirname, 'common');
var AUTHORIZE_APP = path.resolve(__dirname, 'authorize');
var SIGNIN_APP = path.resolve(__dirname, 'signin');
var SIGNUP_APP = path.resolve(__dirname, 'signup');

module.exports = {
  entry: {
    authorize: AUTHORIZE_APP,
    signin: SIGNIN_APP,
    signup: SIGNUP_APP
  },
  output: {
    path: BUILD_DIR,
    filename: '[name].js'
  },
  module: {
    rules: [
      {
        exclude: [
          NODE_DIR,
          TEST_DIR
        ],
        include: [
          COMMON_UTILS,
          AUTHORIZE_APP,
          SIGNIN_APP,
          SIGNUP_APP
        ],
        loader: 'babel-loader',
        test: /\.js$/,
        options: {
          plugins: [
            'transform-object-rest-spread'
          ],
          presets: [
            'env',
            'react'
          ]
        }
      }, {
        loader: "style-loader!css-loader",
        test: /\.css$/
      }, {
        loader: "file-loader?publicPath=public/",
        test: /\.(jpe?g|png|svg|ai)$/
      }, {
        loader: 'url-loader?limit=100000',
        test: /\.(woff|woff2|eot|ttf)$/
      }
    ]
  },
  plugins: [
    new webpack.ProvidePlugin({
      $: "jquery",
      jQuery: "jquery"
    })
  ]
};