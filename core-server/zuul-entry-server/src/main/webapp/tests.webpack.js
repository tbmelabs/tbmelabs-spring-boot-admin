var context = require.context('./__tests__', true, /.test\.js$/);
context.keys().forEach(context);

var testHelpers = require.context('./__tests__/helpers');
testHelpers.keys().forEach(testHelpers);