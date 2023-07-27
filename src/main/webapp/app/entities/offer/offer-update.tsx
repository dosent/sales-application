import React, { useState, useEffect, FocusEvent } from 'react';
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
import { TRUE } from 'sass';
import { random, round } from 'lodash';

function getRatePesent(targetRate, salary) {
  return round((0.80834 - (salary * 0.00743448 + 206.8965552) / targetRate) * 100);
}

function getEmployee(employees, param) {
  return employees.find(it => it.id.toString() === param.id.toString());
}

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
    entity.curentRatePesent = getRatePesent(entity.curetRate, entity.employee.salary);
    entity.targetRatePesent = getRatePesent(entity.targetRate, entity.employee.salary);
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
              <ValidatedField
                label="Cтавка на 3 месяца"
                id="offer-curetRate"
                name="curetRate"
                data-cy="curetRate"
                type="text"
                onBlur={(event: React.FocusEvent<HTMLInputElement>) => {
                  //this.state.offer.curentRatePesent = getRatePesent(event.target.value,200000);
                  //offerEntity.curentRatePesent = getRatePesent(event.target.value,200000);
                  //console.log(getRatePesent(event.target.value,200000));
                }}
              />
              <p>
                <i>Доходность от ставки на 3 месяца: {offerEntity.curentRatePesent}%</i>
              </p>
              <ValidatedField
                label="Целевая ставка (минимальная ставка)"
                id="offer-targetRate"
                name="targetRate"
                data-cy="targetRate"
                type="text"
              />
              <p>
                <i>Доходность от целевой ставки: {offerEntity.targetRatePesent}%</i>
              </p>
              {/*              <ValidatedField
                label="Сколько дней не билиться"
                id="offer-unbilibliDay1"
                name="unbilibliDay1"
                data-cy="unbilibliDay1"
                type="text"
              />*/}
              <ValidatedField label="Ссылка на CV" id="offer-urlCV" name="urlCV" data-cy="urlCV" type="text" />
              <ValidatedField
                label="Предложение действует до "
                id="offer-activityBeforeDate"
                name="activityBeforeDate"
                data-cy="activityBeforeDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField id="offer-employee" name="employee" data-cy="employee" label="Сотрудник" type="select">
                <option value="" key="0" />
                {employees
                  ? employees.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id + '  ' + otherEntity.firstName + '  ' + otherEntity.lastName}
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
