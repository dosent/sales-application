import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IEmployee } from 'app/shared/model/employee.model';
import { getEntities as getEmployees } from 'app/entities/employee/employee.reducer';
import { IOffer } from 'app/shared/model/offer.model';
import { getEntity, updateEntity, createEntity, reset } from './offer.reducer';

export const OfferUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const employees = useAppSelector(state => state.employee.entities);
  const offerEntity = useAppSelector(state => state.offer.entity);
  const loading = useAppSelector(state => state.offer.loading);
  const updating = useAppSelector(state => state.offer.updating);
  const updateSuccess = useAppSelector(state => state.offer.updateSuccess);

  const handleClose = () => {
    navigate('/offer');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getEmployees({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.activityBeforeDate = convertDateTimeToServer(values.activityBeforeDate);

    const entity = {
      ...offerEntity,
      ...values,
      employee: employees.find(it => it.id.toString() === values.employee.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {
          activityBeforeDate: displayDefaultDateTime(),
        }
      : {
          ...offerEntity,
          activityBeforeDate: convertDateTimeFromServer(offerEntity.activityBeforeDate),
          employee: offerEntity?.employee?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="salesApp.offer.home.createOrEditLabel" data-cy="OfferCreateUpdateHeading">
            <Translate contentKey="salesApp.offer.home.createOrEditLabel">Create or edit a Offer</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="offer-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField label="Curet Rate" id="offer-curetRate" name="curetRate" data-cy="curetRate" type="text" />
              <ValidatedField label="Target Rate" id="offer-targetRate" name="targetRate" data-cy="targetRate" type="text" />
              <ValidatedField
                label="Curent Rate Pesent"
                id="offer-curentRatePesent"
                name="curentRatePesent"
                data-cy="curentRatePesent"
                type="text"
              />
              <ValidatedField
                label="Target Rate Pesent"
                id="offer-targetRatePesent"
                name="targetRatePesent"
                data-cy="targetRatePesent"
                type="text"
              />
              <ValidatedField label="Unbilibli Day 1" id="offer-unbilibliDay1" name="unbilibliDay1" data-cy="unbilibliDay1" type="text" />
              <ValidatedField label="Url CV" id="offer-urlCV" name="urlCV" data-cy="urlCV" type="text" />
              <ValidatedField
                label="Activity Before Date"
                id="offer-activityBeforeDate"
                name="activityBeforeDate"
                data-cy="activityBeforeDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField id="offer-employee" name="employee" data-cy="employee" label="Employee" type="select">
                <option value="" key="0" />
                {employees
                  ? employees.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/offer" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default OfferUpdate;
