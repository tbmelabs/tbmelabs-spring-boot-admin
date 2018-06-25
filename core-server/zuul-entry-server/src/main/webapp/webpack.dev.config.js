const webpack = require('webpack');
const path = require('path');

const HtmlWebpackPlugin = require('html-webpack-plugin');

const BUILD_DIR = path.resolve(__dirname, 'public');
const NODE_DIR = path.resolve(__dirname, 'node_modules');
const TEST_DIR = path.resolve(__dirname, '__tests__');

const APP_DIR = path.resolve(__dirname, 'app');
const LANDING_PAGE_DIR = path.resolve(__dirname, 'landing-page');

const ENV = 'development';

module.exports = {
  entry: {
	app: ['babel-polyfill', APP_DIR],
    landingPage: ['babel-polyfill', LANDING_PAGE_DIR]
  },
  output: {
    path: BUILD_DIR,
    filename: '[name].js'
  },
  module: {
    rules: [
      {
        test: /\.js$/,
        exclude: [
          NODE_DIR,
          TEST_DIR
        ],
        include: [
          APP_DIR,
          LANDING_PAGE_DIR
        ],
        loader: 'babel-loader',
        options: {
          plugins: [
            'babel-plugin-syntax-dynamic-import',
            'transform-flow-strip-types',
            'transform-object-rest-spread'
          ],
          presets: [
            'env',
            'react',
            'flow'
          ]
        }
      }, {
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
    }),
    new HtmlWebpackPlugin({
      chunks: ['app'],
      filename: '../index.html',
      template: 'templates/app.template.ejs'
    }),
    new HtmlWebpackPlugin({
      chunks: ['landingPage'],
      filename: '../landing-page.html',
      template: 'templates/landing-page.template.ejs'
    })
  ]
};
