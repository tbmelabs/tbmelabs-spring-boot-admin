var context = require.context('./src/main/webapp', true, /-test\.js$/);
context.keys().forEach(context);