var context = require.context('./src/main/webapp/__tests__', true, /-test\.js$/);
context.keys().forEach(context);