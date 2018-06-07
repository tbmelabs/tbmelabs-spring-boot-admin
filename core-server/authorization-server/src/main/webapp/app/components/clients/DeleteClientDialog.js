// @flow
'use strict';

import React, {Component} from 'react';
import PropTypes from 'prop-types';

import {addSaga, removeSaga} from '../../state/SagaManager';
import {DELETE_CLIENT_SUCCEED} from '../../state/actions/client';

import Modal from 'react-bootstrap/lib/Modal';
import Button from 'react-bootstrap/lib/Button';

require('bootstrap/dist/css/bootstrap.css');

type DeleteClientDialogState = {
  id: number;
  closeSagaId: string;
}

class DeleteClientDialog extends Component<DeleteClientDialog.propTypes, DeleteClientDialogState> {
  onSubmit: () => void;

  constructor(props: DeleteClientDialog.propTypes,
      context: DeleteClientDialog.contextTypes) {
    super(props, context);

    const {existingClient} = props;

    this.state = {
      id: 0,
      closeSagaId: addSaga(DELETE_CLIENT_SUCCEED,
          context.router.history.goBack)
    };

    this.onSubmit = this.onSubmit.bind(this);
  }

  componentWillReceiveProps(nextProps: DeleteClientDialog.propTypes) {
    const {id} = this.state;
    const {existingClient} = nextProps;

    if (existingClient.id && !id) {
      this.setState({
        id: existingClient.id
      });
    }
  }

  componentWillUnmount() {
    removeSaga(this.state.closeSagaId);
  }

  onSubmit(event: SyntheticInputEvent<HTMLInputElement>) {
    event.preventDefault();

    const {id} = this.state;

    this.props.deleteClient(id);
  }

  render() {
    const {id} = this.state;
    const {texts} = this.props;

    if (!id) {
      return null;
    }

    return (
        <Modal.Dialog>
          <Modal.Header>
            <Modal.Title>{texts.modal.delete_title}</Modal.Title>
          </Modal.Header>
          <Modal.Body>
            {`${texts.modal.delete_text}${id}?`}
          </Modal.Body>
          <Modal.Footer>
            <Button bsStyle='danger'
                    onClick={this.context.router.history.goBack}>{texts.modal.cancel_button_text}</Button>
            <Button bsStyle='primary'
                    onClick={this.onSubmit}>{texts.modal.delete_button_text}</Button>
          </Modal.Footer>
        </Modal.Dialog>
    );
  }
}

DeleteClientDialog.propTypes = {
  existingClient: PropTypes.object.isRequired,
  addFlashMessage: PropTypes.func.isRequired,
  deleteClient: PropTypes.func.isRequired,
  texts: PropTypes.object.isRequired
};

DeleteClientDialog.contextTypes = {
  router: PropTypes.object.isRequired
};

export default DeleteClientDialog;
