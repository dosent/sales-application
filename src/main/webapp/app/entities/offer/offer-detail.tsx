import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './offer.reducer';

export const OfferDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const offerEntity = useAppSelector(state => state.offer.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="offerDetailsHeading">
          <Translate contentKey="salesApp.offer.detail.title">Offer</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{offerEntity.id}</dd>
          <dt>
            <span id="curetRate">
              <Translate contentKey="salesApp.offer.curetRate">Curet Rate</Translate>
            </span>
          </dt>
          <dd>{offerEntity.curetRate}</dd>
          <dt>
            <span id="targetRate">
              <Translate contentKey="salesApp.offer.targetRate">Target Rate</Translate>
            </span>
          </dt>
          <dd>{offerEntity.targetRate}</dd>
          <dt>
            <span id="curentRatePesent">
              <Translate contentKey="salesApp.offer.curentRatePesent">Curent Rate Pesent</Translate>
            </span>
          </dt>
          <dd>{offerEntity.curentRatePesent}</dd>
          <dt>
            <span id="targetRatePesent">
              <Translate contentKey="salesApp.offer.targetRatePesent">Target Rate Pesent</Translate>
            </span>
          </dt>
          <dd>{offerEntity.targetRatePesent}</dd>
          <dt>
            <span id="unbilibliDay1">
              <Translate contentKey="salesApp.offer.unbilibliDay1">Unbilibli Day 1</Translate>
            </span>
          </dt>
          <dd>{offerEntity.unbilibliDay1}</dd>
          <dt>
            <span id="urlCV">
              <Translate contentKey="salesApp.offer.urlCV">Url CV</Translate>
            </span>
          </dt>
          <dd>{offerEntity.urlCV}</dd>
          <dt>
            <span id="activityBeforeDate">
              <Translate contentKey="salesApp.offer.activityBeforeDate">Activity Before Date</Translate>
            </span>
          </dt>
          <dd>
            {offerEntity.activityBeforeDate ? (
              <TextFormat value={offerEntity.activityBeforeDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <Translate contentKey="salesApp.offer.employee">Employee</Translate>
          </dt>
          <dd>{offerEntity.employee ? offerEntity.employee.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/offer" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/offer/${offerEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default OfferDetail;
