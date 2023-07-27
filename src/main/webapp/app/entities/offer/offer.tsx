import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, SORT } from 'app/shared/util/pagination.constants';
import { overrideSortStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './offer.reducer';

export const Offer = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(location, 'id'), location.search));

  const offerList = useAppSelector(state => state.offer.entities);
  const loading = useAppSelector(state => state.offer.loading);

  const getAllEntities = () => {
    dispatch(
      getEntities({
        sort: `${sortState.sort},${sortState.order}`,
      })
    );
  };

  const sortEntities = () => {
    getAllEntities();
    const endURL = `?sort=${sortState.sort},${sortState.order}`;
    if (location.search !== endURL) {
      navigate(`${location.pathname}${endURL}`);
    }
  };

  useEffect(() => {
    sortEntities();
  }, [sortState.order, sortState.sort]);

  const sort = p => () => {
    setSortState({
      ...sortState,
      order: sortState.order === ASC ? DESC : ASC,
      sort: p,
    });
  };

  const handleSyncList = () => {
    sortEntities();
  };

  const getSortIconByFieldName = (fieldName: string) => {
    const sortFieldName = sortState.sort;
    const order = sortState.order;
    if (sortFieldName !== fieldName) {
      return faSort;
    } else {
      return order === ASC ? faSortUp : faSortDown;
    }
  };

  return (
    <div>
      <h2 id="offer-heading" data-cy="OfferHeading">
        <Translate contentKey="salesApp.offer.home.title">Offers</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="salesApp.offer.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/offer/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="salesApp.offer.home.createLabel">Create new Offer</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {offerList && offerList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="salesApp.offer.id">ID</Translate> <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('curetRate')}>
                  <Translate contentKey="salesApp.offer.curetRate">Curet Rate</Translate>
                  {'Ставка на 3 месяца '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('curetRate')} />
                </th>
                <th className="hand" onClick={sort('targetRate')}>
                  <Translate contentKey="salesApp.offer.targetRate">Target Rate</Translate>
                  {'Целевая ставка (минимальная ставка) '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('targetRate')} />
                </th>
                {/*                <th className="hand" onClick={sort('curentRatePesent')}>
                  <Translate contentKey="salesApp.offer.curentRatePesent">Curent Rate Pesent</Translate>{'Доходность от текущей ставки в % '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('curentRatePesent')} />
                </th>*/}
                <th className="hand" onClick={sort('targetRatePesent')}>
                  <Translate contentKey="salesApp.offer.targetRatePesent">Target Rate Pesent</Translate>
                  {'Доходность от целевой ставки в % '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('targetRatePesent')} />
                </th>
                {/*                <th className="hand" onClick={sort('unbilibliDay1')}>
                  <Translate contentKey="salesApp.offer.unbilibliDay1">Unbilibli Day 1</Translate>{'Сколько дней не билиться'}
                  <FontAwesomeIcon icon={getSortIconByFieldName('unbilibliDay1')} />
                </th>*/}
                <th className="hand" onClick={sort('urlCV')}>
                  Ссылка на CV
                  <Translate contentKey="salesApp.offer.urlCV">Url CV</Translate> <FontAwesomeIcon icon={getSortIconByFieldName('urlCV')} />
                </th>
                {/*                <th className="hand" onClick={sort('activityBeforeDate')}>
                  <Translate contentKey="salesApp.offer.activityBeforeDate">Activity Before Date</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('activityBeforeDate')} />
                </th>*/}
                <th>
                  Предложение действительно до
                  <Translate contentKey="salesApp.offer.employee">Employee</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {offerList.map((offer, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/offer/${offer.id}`} color="link" size="sm">
                      {offer.id}
                    </Button>
                  </td>
                  <td>{offer.curetRate}</td>
                  <td>{offer.targetRate}</td>
                  {/*                  <td>{offer.curentRatePesent}</td>*/}
                  <td>{offer.targetRatePesent}</td>
                  {/*                  <td>{offer.unbilibliDay1}</td>*/}
                  <td>
                    <a target="_blank" href={offer.urlCV}>
                      ссылка
                    </a>
                  </td>
                  <td>
                    {offer.activityBeforeDate ? <TextFormat type="date" value={offer.activityBeforeDate} format={APP_DATE_FORMAT} /> : null}
                  </td>
                  {/*<td>{offer.employee ? <Link to={`/employee/${offer.employee.id}`}>{offer.employee.id}</Link> : ''}</td>*/}
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/offer/${offer.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/offer/${offer.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/offer/${offer.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="salesApp.offer.home.notFound">No Offers found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Offer;
