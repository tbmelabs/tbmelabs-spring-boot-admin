const net = require('net');

const client = net.createConnection(9090, 'localhost', () => {
  const renderOptions = {
    id: 1, url: '/',
    document: '<app-root></app-root>'
  };
  client.write(JSON.stringify(renderOptions));
});

client.on('data', data => {
  const res = JSON.parse(data.toString());
});
