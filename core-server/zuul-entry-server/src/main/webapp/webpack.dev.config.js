const webpack = require('webpack');
const path = require('path');

const BUILD_DIR = path.resolve(__dirname, 'public');
const NODE_DIR = path.resolve(__dirname, 'node_modules');
const TEST_DIR = path.resolve(__dirname, '__tests__');

const APP_DIR = path.resolve(__dirname, 'app');

const ENV = 'development';

module.exports = {
  entry: {
    app: APP_DIR
  },
  output: {
    path: BUILD_DIR,
    filename: '[name].js'
  },
  module: {
    rules: [
      {
        test: /\.css$/,
        loader: 'style-loader!css-loader'
      }, {
        test: /\.(jpe?g|png|gif|svg)$/i,
        loader: 'file-loader?publicPath=public/'
      }, {
        test: /\.(woff|woff2|eot|ttf)$/,
        loader: 'url-loader?limit=100000'
      }
    ]
  },
  mode: ENV,
  plugins: [
    new webpack.optimize.LimitChunkCountPlugin({
      maxChunks: 1
    }),
    new webpack.ProvidePlugin({
      $: 'jquery',
      jQuery: 'jquery'
    })
  ]
};
