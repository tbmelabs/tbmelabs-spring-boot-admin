var spec = require.context('./__tests__', true, /\.spec\.js$/);
spec.keys().forEach(spec);

var testHelpers = require.context('./__tests__/helpers');
testHelpers.keys().forEach(testHelpers);