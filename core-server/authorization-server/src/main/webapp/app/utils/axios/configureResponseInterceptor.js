// @flow

import axios from 'axios';

import {
  addInsufficientPermissionFlashMessage,
  addInvalidRequestFlashMessage,
  addServerNotRespondingFlashMessage
} from '../../state/queries/flashmessage';

export default function configureResponseInterceptor() {
  axios.interceptors.response.use(undefined, (error) => {
    if (error.response) {
      switch (error.response.status) {
        case 403:
          addInsufficientPermissionFlashMessage();
          break;
        default:
          return Promise.reject(error);
      }
    } else if (error.request) {
      addServerNotRespondingFlashMessage();
    } else {
      addInvalidRequestFlashMessage(error.message);
    }

    return error.response;
  });
}
