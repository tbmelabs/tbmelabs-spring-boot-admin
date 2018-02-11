var webpack = require('webpack');
var path = require('path');

var NODE_DIR = path.resolve(__dirname, 'node_modules');
var BUILD_DIR = path.resolve(__dirname, 'public');

var AUTHORIZE_APP = path.resolve(__dirname, 'authorize');
var SIGNIN_APP = path.resolve(__dirname, 'signin');
var SIGNUP_APP = path.resolve(__dirname, 'signup');

var config = {
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
    loaders: [{
      test: /\.js$/,
      exclude: [NODE_DIR],
      include: [
        AUTHORIZE_APP,
        SIGNIN_APP, SIGNUP_APP],
      loader: 'babel-loader',
      query: {
        plugins: [
          'transform-object-rest-spread'
        ],
        presets: [
          'env',
          'react'
        ]
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
    }]
  },
  plugins: [new webpack.ProvidePlugin({
    $: "jquery",
    jQuery: "jquery"
  })]
};

module.exports = config;