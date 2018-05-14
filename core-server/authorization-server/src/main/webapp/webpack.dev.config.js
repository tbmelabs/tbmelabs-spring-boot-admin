const webpack = require('webpack');
const path = require('path');

const HtmlWebpackPlugin = require('html-webpack-plugin');

const BUILD_DIR = path.resolve(__dirname, 'public');
const NODE_DIR = path.resolve(__dirname, 'node_modules');
const TEST_DIR = path.resolve(__dirname, '__tests__');

const APP = path.resolve(__dirname, 'app');
const COMMON_UTILS = path.resolve(__dirname, 'common');
const AUTHORIZE_APP = path.resolve(__dirname, 'authorize');
const SIGNIN_APP = path.resolve(__dirname, 'signin');
const SIGNUP_APP = path.resolve(__dirname, 'signup');

const ENV = 'development';

module.exports = {
  entry: {
    app: APP,
    authorize: AUTHORIZE_APP,
    signin: ['babel-polyfill', SIGNIN_APP],
    signup: ['babel-polyfill', SIGNUP_APP]
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
          APP,
          COMMON_UTILS,
          AUTHORIZE_APP,
          SIGNIN_APP,
          SIGNUP_APP
        ],
        loader: 'babel-loader',
        test: /\.js$/,
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
        loader: 'style-loader!css-loader',
        test: /\.css$/
      }, {
        loader: 'file-loader?publicPath=public/',
        test: /\.(jpe?g|png|svg|ai)$/
      }, {
        loader: 'url-loader?limit=100000',
        test: /\.(woff|woff2|eot|ttf)$/
      }
    ]
  },
  plugins: [
    new webpack.DefinePlugin({
      'process.env': {
        'NODE_ENV': JSON.stringify(ENV)
      }
    }),
    new webpack.optimize.LimitChunkCountPlugin({
      maxChunks: 1
    }),
    new HtmlWebpackPlugin({
      name: 'TBME TV | Account Management',
      chunks: ['app'],
      filename: '../index.js.html',
      templates: 'templates/index.js.template.ejs'
    }),
    new HtmlWebpackPlugin({
      name: 'TBME TV | Authorize Application',
      chunks: ['authorize'],
      filename: '../authorize.html',
      template: 'templates/authorize.template.ejs'
    }),
    new HtmlWebpackPlugin({
      name: 'TBME TV | Signin',
      chunks: ['signin'],
      filename: '../signin.html',
      template: 'templates/signin.template.ejs'
    }),
    new HtmlWebpackPlugin({
      name: 'TBME TV | Signin',
      chunks: ['signup'],
      filename: '../signup.html',
      template: 'templates/signup.template.ejs'
    })
  ]
};