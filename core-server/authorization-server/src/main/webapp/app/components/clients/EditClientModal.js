// @flow
'use strict';

import React, {Component} from 'react';
import PropTypes from 'prop-types';

import {addSaga, removeSaga} from '../../state/SagaManager';
import {SAVE_CLIENT_SUCCEED} from '../../state/actions/client';

import {type grantTypeType} from '../../../common/types/grantType.type';
import {type authorityType} from '../../../common/types/authority.type';
import {type scopeType} from '../../../common/types/scope.type';

import uuidv4 from 'uuid/v4';
import isEmpty from 'lodash/isEmpty';

import extractMultiSelectedOptions from '../../utils/form/extractMultiSelectedOptions';

import Modal from 'react-bootstrap/lib/Modal';
import Form from 'react-bootstrap/lib/Form';
import FormGroup from 'react-bootstrap/lib/FormGroup';
import Col from 'react-bootstrap/lib/Col';
import ControlLabel from 'react-bootstrap/lib/ControlLabel';
import InputGroup from 'react-bootstrap/lib/InputGroup';
import FormControl from 'react-bootstrap/lib/FormControl';
import Glyphicon from 'react-bootstrap/lib/Glyphicon';
import HelpBlock from 'react-bootstrap/lib/HelpBlock';
import Button from 'react-bootstrap/lib/Button';

import CollapsableAlert from '../../../common/components/CollapsableAlert';

require('bootstrap/dist/css/bootstrap.css');

type EditClientModalState = {
  id?: number;
  clientId: string;
  secret: string;
  accessTokenValiditySeconds: string;
  refreshTokenValiditySeconds: string;
  redirectUri: string;
  grantTypes: grantTypeType[];
  allGrantTypes: grantTypeType[];
  authorities: authorityType[];
  allAuthorities: authorityType[];
  scopes: scopeType[];
  allScopes: scopeType[];
  errors: {
    clientId: string;
    secret: string;
    accessTokenValiditySeconds: string;
    refreshTokenValiditySeconds: string;
    redirectUri: string;
    grantTypes: string;
    authorities: string;
    scopes: string;
    form: string
  };
  isValid: boolean;
  isLoading: boolean;
  closeSagaId: string;
}

class EditClientModal extends Component<EditClientModal.propTypes, EditClientModalState> {
  onChange: () => void;
  handleMultipleSelected: (eventTarget: HTMLElement) => void;
  generateUUID: (target: string) => void;
  validateForm: () => void;
  onSubmit: () => void;

  constructor(props: EditClientModal.propTypes,
      context: EditClientModal.contextTypes) {
    super(props, context);

    this.state = {
      clientId: '',
      secret: '',
      accessTokenValiditySeconds: '',
      refreshTokenValiditySeconds: '',
      redirectUri: '',
      grantTypes: [],
      allGrantTypes: this.props.grantTypes,
      authorities: [],
      allAuthorities: this.props.authorities,
      scopes: [],
      allScopes: this.props.scopes,
      errors: {
        clientId: '',
        secret: '',
        accessTokenValiditySeconds: '',
        refreshTokenValiditySeconds: '',
        redirectUri: '',
        grantTypes: '',
        authorities: '',
        scopes: '',
        form: ''
      },
      isValid: false,
      isLoading: false,
      closeSagaId: addSaga(SAVE_CLIENT_SUCCEED,
          context.router.history.goBack)
    };

    this.onChange = this.onChange.bind(this);
    this.handleMultipleSelected = this.handleMultipleSelected.bind(this);
    this.generateUUID = this.generateUUID.bind(this);
    this.validateForm = this.validateForm.bind(this);
    this.onSubmit = this.onSubmit.bind(this);
  }

  static getDerivedStateFromProps(nextProps: EditClientModal.propTypes,
      prevState: EditClientModalState) {
    return {
      allAuthorities: nextProps.authorities,
      allGrantTypes: nextProps.grantTypes,
      allScopes: nextProps.scopes
    };
  }

  componentWillUnmount() {
    removeSaga(this.state.closeSagaId);
  }

  onChange(event: SyntheticInputEvent<HTMLInputElement>) {
    if (event.target.type === 'select-multiple') {
      this.handleMultipleSelected(event.target);
    } else {
      this.setState({[event.target.name]: event.target.value},
          this.validateForm);
    }
  }

  handleMultipleSelected(eventTarget: { name: string, selectedOptions: string[] }) {
    const {allGrantTypes, allAuthorities, allScopes} = this.state;

    switch (eventTarget.name) {
      case 'grantTypes':
        extractMultiSelectedOptions(eventTarget, allGrantTypes,
            (selectedGrantTypes) => {
              this.setState({grantTypes: selectedGrantTypes},
                  this.validateForm);
            });
        break;
      case 'authorities':
        extractMultiSelectedOptions(eventTarget, allAuthorities,
            (selectedAuthorities) => {
              this.setState({authorities: selectedAuthorities},
                  this.validateForm);
            });
        break;
      case 'scopes':
        extractMultiSelectedOptions(eventTarget, allScopes,
            (selectedScopes) => {
              this.setState({scopes: selectedScopes}, this.validateForm);
            });
        break;
    }
  }

  generateUUID(target: string) {
    this.setState({[target]: uuidv4()});
  }

  validateForm() {
    const {clientId, secret, accessTokenValiditySeconds, refreshTokenValiditySeconds, redirectUri, grantTypes, authorities, scopes, errors} = this.state;

    this.setState({
      isValid: isEmpty(errors.clientId) && isEmpty(errors.secret) && isEmpty(
          errors.accessTokenValiditySeconds) && isEmpty(
          errors.refreshTokenValiditySeconds)
      && isEmpty(errors.redirectUri)
      && !!clientId && !!secret && !!accessTokenValiditySeconds
      && !!refreshTokenValiditySeconds && !!redirectUri && !isEmpty(grantTypes)
      && !isEmpty(authorities) && !isEmpty(scopes)
    });
  }

  onSubmit(event: SyntheticInputEvent<HTMLInputElement>) {
    event.preventDefault();

    const {clientId, secret, accessTokenValiditySeconds, refreshTokenValiditySeconds, redirectUri, grantTypes, authorities, scopes, isValid} = this.state;
    const {texts} = this.props;

    if (isValid) {
      this.props.saveClient({
        clientId: clientId,
        secret: secret,
        accessTokenValiditySeconds: accessTokenValiditySeconds,
        refreshTokenValiditySeconds: refreshTokenValiditySeconds,
        redirectUris: redirectUri.split(';'),
        grantTypes: grantTypes,
        grantedAuthorities: authorities,
        scopes: scopes
      });
      // .then(
      //     response => {
      //       this.props.addFlashMessage({
      //         type: 'success',
      //         title: texts.client_added_title,
      //         text: texts.client_added_text
      //       });
      //
      //       this.context.router.history.goBack();
      //     },
      //     error => this.setState({errors: {form: error.response.data.message}})
      // );
    } else {
      this.setState(
          {errors: {form: texts.errors.form_invalid}, isValid: false});
    }
  }

  render() {
    const {allGrantTypes, allAuthorities, allScopes, isValid, isLoading, errors} = this.state;
    const {texts} = this.props;

    return (
        <Modal.Dialog>
          <Modal.Header>
            <Modal.Title>{this.state.id ? texts.update_title
                : texts.create_title}</Modal.Title>
          </Modal.Header>
          <Modal.Body>
            <Form onSubmit={this.onSubmit} horizontal>
              <CollapsableAlert style='danger'
                                title={this.state.id ? texts.errors.update_title
                                    : texts.errors.create_title}
                                message={errors.form} collapse={!!errors.form}/>

              <FormGroup controlId='clientId'
                         validationState={!!errors.clientId ? 'error' : null}>
                <HelpBlock>{errors.clientId}</HelpBlock>
                <Col componentClass={ControlLabel} sm={4}>
                  {texts.client_id}
                </Col>
                <Col sm={6}>
                  <InputGroup>
                    <FormControl name='clientId' type='text'
                                 value={this.state.clientId}
                                 onChange={this.onChange} required/>
                    <FormControl.Feedback/>
                    <InputGroup.Addon className='clickable'
                                      onClick={() => this.generateUUID(
                                          'clientId')}>
                      <Glyphicon glyph='repeat'/>
                    </InputGroup.Addon>
                  </InputGroup>
                </Col>
              </FormGroup>

              <FormGroup controlId='secret'
                         validationState={!!errors.secret ? 'error' : null}>
                <HelpBlock>{errors.secret}</HelpBlock>
                <Col componentClass={ControlLabel} sm={4}>
                  {texts.secret}
                </Col>
                <Col sm={6}>
                  <InputGroup>
                    <FormControl name='secret' type='password'
                                 value={this.state.secret}
                                 onChange={this.onChange} required/>
                    <FormControl.Feedback/>
                    <InputGroup.Addon className='clickable'
                                      onClick={() => this.generateUUID(
                                          'secret')}>
                      <Glyphicon glyph='repeat'/>
                    </InputGroup.Addon>
                  </InputGroup>
                </Col>
              </FormGroup>

              <FormGroup controlId='accessTokenValiditySeconds'
                         validationState={!!errors.accessTokenValiditySeconds
                             ? 'error'
                             : null}>
                <HelpBlock>{errors.accessTokenValiditySeconds}</HelpBlock>
                <Col componentClass={ControlLabel} sm={4}>
                  {texts.access_token_validity}
                </Col>
                <Col sm={6}>
                  <FormControl name='accessTokenValiditySeconds' type='number'
                               value={this.state.accessTokenValiditySeconds}
                               onChange={this.onChange} required/>
                  <FormControl.Feedback/>
                </Col>
              </FormGroup>

              <FormGroup controlId='refreshTokenValiditySeconds'
                         validationState={!!errors.refreshTokenValiditySeconds
                             ? 'error' : null}>
                <HelpBlock>{errors.refreshTokenValiditySeconds}</HelpBlock>
                <Col componentClass={ControlLabel} sm={4}>
                  {texts.refresh_token_validity}
                </Col>
                <Col sm={6}>
                  <FormControl name='refreshTokenValiditySeconds' type='number'
                               value={this.state.refreshTokenValiditySeconds}
                               onChange={this.onChange} required/>
                  <FormControl.Feedback/>
                </Col>
              </FormGroup>

              <FormGroup controlId='redirectUri'
                         validationState={!!errors.redirectUri ? 'error'
                             : null}>
                <HelpBlock>{errors.redirectUri}</HelpBlock>
                <Col componentClass={ControlLabel} sm={4}>
                  {texts.redirect_uri}
                </Col>
                <Col sm={6}>
                  <FormControl name='redirectUri' type='text'
                               value={this.state.redirectUri}
                               onChange={this.onChange} required/>
                  <FormControl.Feedback/>
                </Col>
              </FormGroup>

              <FormGroup controlId='grantTypes'
                         validationState={!!errors.grantTypes ? 'error' : null}>
                <HelpBlock>{errors.grantTypes}</HelpBlock>
                <Col componentClass={ControlLabel} sm={4}>
                  {texts.grant_types}
                </Col>
                <Col sm={6}>
                  <FormControl name='grantTypes' onChange={this.onChange}
                               componentClass='select' multiple required>
                    {allGrantTypes.map((grantType) => {
                      return (
                          <option key={grantType.id}
                                  value={grantType.id}>{grantType.name}</option>
                      );
                    })}
                  </FormControl>
                  <FormControl.Feedback/>
                </Col>
              </FormGroup>

              <FormGroup controlId='authorities'
                         validationState={!!errors.authorities ? 'error'
                             : null}>
                <HelpBlock>{errors.authorities}</HelpBlock>
                <Col componentClass={ControlLabel} sm={4}>
                  {texts.authorities}
                </Col>
                <Col sm={6}>
                  <FormControl name='authorities' onChange={this.onChange}
                               componentClass='select' multiple required>
                    {allAuthorities.map((authority) => {
                      return (
                          <option key={authority.id}
                                  value={authority.id}>{authority.name}</option>
                      );
                    })}
                  </FormControl>
                  <FormControl.Feedback/>
                </Col>
              </FormGroup>

              <FormGroup controlId='scopes'
                         validationState={!!errors.scopes ? 'error' : null}>
                <HelpBlock>{errors.scopes}</HelpBlock>
                <Col componentClass={ControlLabel} sm={4}>
                  {texts.scopes}
                </Col>
                <Col sm={6}>
                  <FormControl name='scopes' onChange={this.onChange}
                               componentClass='select' multiple required>
                    {allScopes.map((scope) => {
                      return (
                          <option key={scope.id}
                                  value={scope.id}>{scope.name}</option>
                      );
                    })}
                  </FormControl>
                  <FormControl.Feedback/>
                </Col>
              </FormGroup>

              <FormGroup className='link-group'>
                <Col smOffset={6} sm={2}>
                  <Button bsStyle='danger' className='pull-right'
                          onClick={this.context.router.history.goBack}>
                    {texts.cancel_button_text}
                  </Button>
                </Col>
                <Col sm={2}>
                  <Button type='submit' bsStyle='primary' className='pull-right'
                          disabled={!isValid || isLoading}
                          onClick={isValid && !isLoading ? this.onSubmit
                              : null}>
                    {this.state.id && !isLoading ? texts.update_button_text
                        : !this.state.id && !isLoading
                            ? texts.create_button_text
                            : texts.button_loading_text}
                  </Button>
                </Col>
              </FormGroup>
            </Form>
          </Modal.Body>
        </Modal.Dialog>
    );
  }
}

EditClientModal.propTypes = {
  authorities: PropTypes.array.isRequired,
  grantTypes: PropTypes.array.isRequired,
  scopes: PropTypes.array.isRequired,
  addFlashMessage: PropTypes.func.isRequired,
  saveClient: PropTypes.func.isRequired,
  texts: PropTypes.object.isRequired
};

EditClientModal.contextTypes = {
  router: PropTypes.object.isRequired
};

export default EditClientModal;
