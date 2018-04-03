// @flow
'use strict';

import React, {Component} from 'react';
import PropTypes from 'prop-types';

import type grantTypeType from '../../../common/types/grantTypeType';
import type authorityType from '../../../common/types/authorityType';
import type scopeType from '../../../common/types/scopeType';

import axios from 'axios';

import isEmpty from 'lodash/isEmpty';

import extractMultiSelectedOptions from '../../utils/form/extractMultiSelectedOptions';

import Modal from 'react-bootstrap/lib/Modal';
import Form from 'react-bootstrap/lib/Form';
import FormGroup from 'react-bootstrap/lib/FormGroup'
import Col from 'react-bootstrap/lib/Col';
import ControlLabel from 'react-bootstrap/lib/ControlLabel';
import FormControl from 'react-bootstrap/lib/FormControl';
import HelpBlock from 'react-bootstrap/lib/HelpBlock';
import Button from 'react-bootstrap/lib/Button';

import CollapsableAlert from '../../../common/components/CollapsableAlert';

require('bootstrap/dist/css/bootstrap.css');

type EditClientModalState = {
  id: ?number,
  clientId: string,
  secret: string,
  accessTokenValidity: string,
  refreshTokenValidity: string,
  redirectUri: string,
  grantTypes: grantTypeType[],
  allGrantTypes: grantTypeType[],
  authorities: authorityType[],
  allAuthorities: authorityType[],
  scopes: scopeType[],
  allScopes: scopeType[],
  errors: {
    clientId: string,
    secret: string,
    accessTokenValidity: string,
    refreshTokenValidity: string,
    redirectUri: string,
    grantTypes: string,
    authorities: string,
    scopes: string,
    form: string
  },
  isValid: boolean,
  isLoading: boolean
}

class EditClientModal extends Component<EditClientModal.propTypes, EditClientModalState> {
  onChange: () => void;
  handleMultipleSelected: (eventTarget: HTMLElement) => void;
  validateForm: () => void;
  onSubmit: () => void;

  constructor(props: EditClientModal.propTypes) {
    super(props);

    this.state = {
      id: null,
      clientId: '',
      secret: '',
      accessTokenValidity: '',
      refreshTokenValidity: '',
      redirectUri: '',
      grantTypes: [],
      allGrantTypes: [],
      authorities: [],
      allAuthorities: [],
      scopes: [],
      allScopes: [],
      errors: {
        clientId: '',
        secret: '',
        accessTokenValidity: '',
        refreshTokenValidity: '',
        redirectUri: '',
        grantTypes: '',
        authorities: '',
        scopes: '',
        form: ''
      },
      isValid: false,
      isLoading: false
    }

    this.onChange = this.onChange.bind(this);
    this.handleMultipleSelected = this.handleMultipleSelected.bind(this);
    this.validateForm = this.validateForm.bind(this);
    this.onSubmit = this.onSubmit.bind(this);
  }

  componentWillMount() {
    axios.all([this.props.loadGrantTypes(), this.props.loadAuthorities(), this.props.loadScopes()]).then(
      axios.spread((grantTypes, authorities, scopes) => this.setState({
        allGrantTypes: grantTypes.data.content,
        allAuthorities: authorities.data.content,
        allScopes: scopes.data.content
      }))).catch(error => this.setState({errors: {form: error.response.data.message}}));
  }

  onChange(event: SyntheticInputEvent<HTMLInputElement>) {
    if (event.target.type === 'select-multiple') {
      this.handleMultipleSelected(event.target);
    } else {
      this.setState({[event.target.name]: event.target.value}, this.validateForm);
    }
  }

  handleMultipleSelected(eventTarget: { name: string, selectedOptions: string[] }) {
    const {allGrantTypes, allAuthorities, allScopes} = this.state;

    switch (eventTarget.name) {
      case 'grantTypes':
        extractMultiSelectedOptions(eventTarget, allGrantTypes, (selectedGrantTypes) => {
          this.setState({grantTypes: selectedGrantTypes}, this.validateForm);
        });
        break;
      case 'authorities':
        extractMultiSelectedOptions(eventTarget, allAuthorities, (selectedAuthorities) => {
          this.setState({authorities: selectedAuthorities}, this.validateForm);
        });
        break;
      case 'scopes':
        extractMultiSelectedOptions(eventTarget, allScopes, (selectedScopes) => {
          this.setState({scopes: selectedScopes}, this.validateForm);
        });
        break;
    }
  }

  validateForm() {
    const {clientId, secret, accessTokenValidity, refreshTokenValidity, redirectUri, grantTypes, authorities, scopes, errors} = this.state;

    this.setState({
      isValid: isEmpty(errors.clientId) && isEmpty(errors.secret) && isEmpty(errors.accessTokenValidity) && isEmpty(errors.refreshTokenValidity) && isEmpty(errors.redirectUri)
      && !!clientId && !!secret && !!accessTokenValidity && !!refreshTokenValidity && !!redirectUri && !isEmpty(grantTypes) && !isEmpty(authorities) && !isEmpty(scopes)
    });
  }

  onSubmit(event: SyntheticInputEvent<HTMLInputElement>) {
    event.preventDefault();

    const {clientId, secret, accessTokenValidity, refreshTokenValidity, redirectUri, grantTypes, authorities, scopes, isValid} = this.state;
    const {texts} = this.props;

    if (isValid) {
      this.props.saveClient({
        clientId: clientId,
        secret: secret,
        accessTokenValidity: accessTokenValidity,
        refreshTokenValidity: refreshTokenValidity,
        redirectUri: redirectUri,
        grantTypes: grantTypes,
        authorities: authorities,
        scopes: scopes
      }).then(
        response => {
          this.props.addFlashMessage({
            type: 'success',
            title: texts.client_added_title,
            text: texts.client_added_text
          });

          this.context.router.history.goBack();
        }, error => this.setState({errors: {form: error.response.data.message}})
      )
    } else {
      this.setState({errors: {form: texts.errors.form_invalid}, isValid: false});
    }
  }

  render() {
    const {allGrantTypes, allAuthorities, allScopes, isValid, isLoading, errors} = this.state;
    const {texts} = this.props;

    console.log('is-valid state: ', isValid);

    // TODO: How to properly use modal?
    return (
      <Modal.Dialog>
        <Modal.Header>
          <Modal.Title>{this.state.id ? texts.update_title : texts.create_title}</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <Form onSubmit={this.onSubmit} horizontal>
            <CollapsableAlert style='danger'
                              title={this.state.id ? texts.errors.update_title : texts.errors.create_title}
                              message={errors.form} collapse={!!errors.form}/>

            <FormGroup controlId='clientId' validationState={!!errors.clientId ? 'error' : null}>
              <HelpBlock>{errors.clientId}</HelpBlock>
              <Col componentClass={ControlLabel} sm={4}>
                {texts.client_id}
              </Col>
              <Col sm={6}>
                <FormControl name='clientId' type='text' value={this.state.clientId}
                             onChange={this.onChange} required/>
                <FormControl.Feedback/>
              </Col>
            </FormGroup>

            <FormGroup controlId='secret' validationState={!!errors.secret ? 'error' : null}>
              <HelpBlock>{errors.secret}</HelpBlock>
              <Col componentClass={ControlLabel} sm={4}>
                {texts.secret}
              </Col>
              <Col sm={6}>
                <FormControl name='secret' type='password' value={this.state.secret}
                             onChange={this.onChange} required/>
                <FormControl.Feedback/>
              </Col>
            </FormGroup>

            <FormGroup controlId='accessTokenValidity' validationState={!!errors.accessTokenValidity ? 'error' : null}>
              <HelpBlock>{errors.accessTokenValidity}</HelpBlock>
              <Col componentClass={ControlLabel} sm={4}>
                {texts.access_token_validity}
              </Col>
              <Col sm={6}>
                <FormControl name='accessTokenValidity' type='number' value={this.state.accessTokenValidity}
                             onChange={this.onChange} required/>
                <FormControl.Feedback/>
              </Col>
            </FormGroup>

            <FormGroup controlId='refreshTokenValidity'
                       validationState={!!errors.refreshTokenValidity ? 'error' : null}>
              <HelpBlock>{errors.refreshTokenValidity}</HelpBlock>
              <Col componentClass={ControlLabel} sm={4}>
                {texts.refresh_token_validity}
              </Col>
              <Col sm={6}>
                <FormControl name='refreshTokenValidity' type='number' value={this.state.refreshTokenValidity}
                             onChange={this.onChange} required/>
                <FormControl.Feedback/>
              </Col>
            </FormGroup>

            <FormGroup controlId='redirectUri' validationState={!!errors.redirectUri ? 'error' : null}>
              <HelpBlock>{errors.redirectUri}</HelpBlock>
              <Col componentClass={ControlLabel} sm={4}>
                {texts.redirect_uri}
              </Col>
              <Col sm={6}>
                <FormControl name='redirectUri' type='text' value={this.state.redirectUri}
                             onChange={this.onChange} required/>
                <FormControl.Feedback/>
              </Col>
            </FormGroup>

            <FormGroup controlId='grantTypes' validationState={!!errors.grantTypes ? 'error' : null}>
              <HelpBlock>{errors.grantTypes}</HelpBlock>
              <Col componentClass={ControlLabel} sm={4}>
                {texts.grant_types}
              </Col>
              <Col sm={6}>
                <FormControl name='grantTypes' onChange={this.onChange} componentClass='select' multiple required>
                  {allGrantTypes.map((grantType) => {
                    return (
                      <option key={grantType.id} value={grantType.id}>{grantType.name}</option>
                    );
                  })}
                </FormControl>
                <FormControl.Feedback/>
              </Col>
            </FormGroup>

            <FormGroup controlId='authorities' validationState={!!errors.authorities ? 'error' : null}>
              <HelpBlock>{errors.authorities}</HelpBlock>
              <Col componentClass={ControlLabel} sm={4}>
                {texts.authorities}
              </Col>
              <Col sm={6}>
                <FormControl name='authorities' onChange={this.onChange} componentClass='select' multiple required>
                  {allAuthorities.map((authority) => {
                    return (
                      <option key={authority.id} value={authority.id}>{authority.name}</option>
                    );
                  })}
                </FormControl>
                <FormControl.Feedback/>
              </Col>
            </FormGroup>

            <FormGroup controlId='scopes' validationState={!!errors.scopes ? 'error' : null}>
              <HelpBlock>{errors.scopes}</HelpBlock>
              <Col componentClass={ControlLabel} sm={4}>
                {texts.scopes}
              </Col>
              <Col sm={6}>
                <FormControl name='scopes' onChange={this.onChange} componentClass='select' multiple required>
                  {allScopes.map((scope) => {
                    return (
                      <option key={scope.id} value={scope.id}>{scope.name}</option>
                    );
                  })}
                </FormControl>
                <FormControl.Feedback/>
              </Col>
            </FormGroup>

            <FormGroup className='link-group'>
              <Col smOffset={6} sm={2}>
                <Button bsStyle='danger' className='pull-right' onClick={this.context.router.history.goBack}>
                  {texts.cancel_button_text}
                </Button>
              </Col>
              <Col sm={2}>
                <Button type='submit' bsStyle='primary' className='pull-right' disabled={!isValid || isLoading}
                        onClick={isValid && !isLoading ? this.onSubmit : null}>
                  {this.state.id && !isLoading ? texts.update_button_text :
                    !this.state.id && !isLoading ? texts.create_button_text : texts.button_loading_text}
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
  loadGrantTypes: PropTypes.func.isRequired,
  loadAuthorities: PropTypes.func.isRequired,
  loadScopes: PropTypes.func.isRequired,
  addFlashMessage: PropTypes.func.isRequired,
  saveClient: PropTypes.func.isRequired,
  texts: PropTypes.object.isRequired
}

EditClientModal.contextTypes = {
  router: PropTypes.object.isRequired
}

export default EditClientModal;