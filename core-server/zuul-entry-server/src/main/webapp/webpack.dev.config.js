const webpack = require('webpack');
const path = require('path');

const HtmlWebpackPlugin = require('html-webpack-plugin');

const BUILD_DIR = path.resolve(__dirname, 'public');
const NODE_DIR = path.resolve(__dirname, 'node_modules');
const TEST_DIR = path.resolve(__dirname, '__tests__');

const APP_DIR = path.resolve(__dirname, 'app');

const ENV = 'development';

module.exports = {
  entry: [
    'babel-polyfill',
    APP_DIR
  ],
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
          APP_DIR
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
        test: /\.(jpe?g|png|svg|ai)$/,
        loader: 'file-loader?publicPath=public/'
      }, {
        test: /\.(woff|woff2|eot|ttf)$/,
        loader: 'url-loader?limit=100000'
      }
    ]
  },
  mode: JSON.stringify(ENV),
  optimization: {
    splitChunks: {
      cacheGroups: {
        commons: {
          chunks: 'initial',
          minChunks: 2,
        },
        vendor: {
          test: NODE_DIR,
          chunks: 'all',
          name: 'vendor'
        }
      }
    }
  },
  plugins: [
    new webpack.optimize.LimitChunkCountPlugin({
      maxChunks: 1
    }),
    new HtmlWebpackPlugin({
      name: 'TBME TV',
      filename: '../index.html',
      template: 'templates/index.template.ejs'
    })
  ]
};
