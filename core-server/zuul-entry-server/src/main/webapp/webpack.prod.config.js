const webpack = require('webpack');
const path = require('path');

const UglifyJSPlugin = require('uglifyjs-webpack-plugin');

const imageminLoader = require('imagemin-webpack').imageminLoader;
const imageminMozjpeg = require('imagemin-mozjpeg');

const BUILD_DIR = path.resolve(__dirname, 'public');
const NODE_DIR = path.resolve(__dirname, 'node_modules');
const TEST_DIR = path.resolve(__dirname, '__tests__');

const APP_DIR = path.resolve(__dirname, 'app');

const ENV = 'production';

module.exports = {
  entry: {
    app: [
      `${APP_DIR}/styles/tbme-tv.css`,
      'bootstrap/dist/css/bootstrap.min.css',
      'popper.js/dist/popper.js'
    ]
  },
  output: {
    path: BUILD_DIR,
    filename: '[name].js',
    chunkFilename: '[chunkhash].js'
  },
  module: {
    rules: [
      {
        test: /\.css$/,
        loader: 'style-loader!css-loader'
      }, {
        test: /\.(jpe?g|png|gif|svg)$/i,
        use: [
          {
            loader: 'file-loader?publicPath=public/'
          }, {
            loader: imageminLoader,
            options: {
              bail: false,
              imageminOptions: {
                plugins: [
                  imageminMozjpeg()
                ]
              }
            }
          }
        ]
      }, {
        test: /\.(woff|woff2|eot|ttf)$/,
        loader: 'url-loader?limit=100000'
      }
    ]
  },
  mode: ENV,
  optimization: {
    splitChunks: {
      cacheGroups: {
        commons: {
          chunks: 'initial',
          minChunks: 2,
        }
      }
    }
  },
  plugins: [
    new UglifyJSPlugin(),
    new webpack.ProvidePlugin({
      $: 'jquery',
      jQuery: 'jquery'
    })
  ]
};
