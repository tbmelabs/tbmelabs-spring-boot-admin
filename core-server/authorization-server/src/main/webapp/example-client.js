const net = require('net');

const client = net.createConnection(9090, 'localhost', () => {
  const renderOptions = {
    id: 1, url: '/path',
    document: '<app-root></app-root>'
  };
  client.write(JSON.stringify(renderOptions));
});

client.on('data', data => {
  console.log('response: ' + data.toString());
});
