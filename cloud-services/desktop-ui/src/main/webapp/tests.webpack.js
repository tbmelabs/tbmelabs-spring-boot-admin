// Require spec tests
const spec = require.context('./__tests__', true, /\.spec\.js$/);
spec.keys().forEach(spec);

// Require helpers
const testHelpers = require.context('./__tests__/helpers');
testHelpers.keys().forEach(testHelpers);