// @flow

import axios from 'axios';

import {REST_API_BASE_PATH} from '../../config';

import {type clientType} from '../../../common/types/client.type';

export default function (id: number): Promise<clientType> {
  return axios.get(`${REST_API_BASE_PATH}/clients/${id}`).then(
      response => response.data
  );
}
