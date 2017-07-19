'use strict';

import React from 'react';
import PropTypes from 'prop-types';

import Table from 'react-bootstrap/lib/Table';
import Glyphicon from 'react-bootstrap/lib/Glyphicon';

import {SORT_ASC, SORT_DESC} from '../../../utils/sorting/types';
import sortArray from '../../../utils/sorting/sortArray';

require('bootstrap/dist/css/bootstrap.css');

class SortingTable extends React.Component {
  constructor(props) {
    super(props);

    this.state = {
      data: this.props.data,
      sort: 'id',
      order: SORT_ASC
    }

    this.changeSort = this.changeSort.bind(this);
    this.sortData = this.sortData.bind(this);
  }

  componentWillMount() {
    this.sortData();
  }

  changeSort(event) {
    const key = event.target.id.replace('sort-', '');

    const sort = this.state.sort;
    const order = this.state.order;

    var newOrder = SORT_ASC;

    if (key === sort && order === SORT_ASC) {
      newOrder = SORT_DESC;
    }

    this.setState({sort: key, order: newOrder}, this.sortData);
  }

  sortData() {
    const data = this.state.data;
    const key = this.state.sort;
    const order = this.state.order;

    const sortedData = sortArray(data, key, order)

    this.setState({data: sortedData});
  }

  render() {
    const data = this.state.data;

    return (
      <Table responsive>
        <thead>
        <tr>
          {
            Object.keys(data[0]).map(key => {
              if (key !== '_links') {
                return (
                  <th key={key}>
                    {key} {key === this.state.sort && this.state.order === SORT_DESC ?
                    <Glyphicon glyph='chevron-down' id={'sort-' + key} onClick={this.changeSort}/> :
                    <Glyphicon glyph='chevron-up' id={'sort-' + key} onClick={this.changeSort}/>}
                  </th>
                );
              }
            })
          }
        </tr>
        </thead>
        <tbody>
        {
          data.map(value => {
            return (
              <tr key={value.id}>
                {
                  Object.keys(value).map(key => {
                    if (key !== '_links') {
                      return (
                        <td key={value.id + '-' + key}>{value[key]}</td>
                      );
                    }
                  })
                }
              </tr>
            );
          })
        }
        </tbody>
      </Table>
    );
  }
}

SortingTable.propTypes = {
  data: PropTypes.array.isRequired
}

export default SortingTable;