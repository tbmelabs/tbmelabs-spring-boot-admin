import * as net from 'net';

let clientReady = false;

const client = net.createConnection(9090, 'localhost', () => {
  clientReady = true;
});

client.on('data', data => {
  console.log('response: ' + data.toString());
});

const requestPage = (id: string, uri: string) => {
  if (!clientReady) {
    throw new Error('Client is not ready yet!');
  }

  const renderOptions = {
    id: id,
    url: uri,
    document: '<app-root></app-root>'
  };

  client.write(JSON.stringify(renderOptions));
}

export {requestPage}
