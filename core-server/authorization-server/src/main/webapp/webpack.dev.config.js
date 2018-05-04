const webpack = require('webpack');
const path = require('path');

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
  entry: [
    'babel-polyfill',
    {
      app: APP,
      authorize: AUTHORIZE_APP,
      signin: SIGNIN_APP,
      signup: SIGNUP_APP
    }
  ],
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
    })
  ]
};