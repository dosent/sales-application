import './home.scss';

import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';

import { Row, Col, Alert, Table, Button } from 'reactstrap';

import { useAppDispatch, useAppSelector } from 'app/config/store';
import { getForSales } from 'app/entities/forsale/forsale.reducer';

export const Home = () => {
  const account = useAppSelector(state => state.authentication.account);

  const dispatch = useAppDispatch();
  const location = useLocation();
  const navigate = useNavigate();

  const forSaleList = useAppSelector(state => state.forsale.entities);
  const loading = useAppSelector(state => state.forsale.entities);

  const getAllForSales = () => {
    dispatch(getForSales({}));

    useEffect(() => {
      getAllForSales();
    }, []);
  };
  return (
    <Row>
      <Col md="12">
        <h2>Предложение от BackEnd'a</h2>
        <div>Обновить список {account.login}</div>
        <div className="table-responsive">
          {forSaleList && forSaleList.length > 0
            ? 'sdsds'
            : !loading && <div className="alert alert-warning">Нет предложений на данный момент</div>}
        </div>
        <div>
          <Table>
            <thead>
              <tr>
                <th>#</th>
                <th>Специалист</th>
                <th>Стек</th>
                <th>Ставка на первых 3 месяца</th>
                <th>Целевая ставка</th>
              </tr>
            </thead>
            <tbody>
              <tr>
                <th scope="row">1</th>
                <td>
                  <a className="icon-link" href="#">
                    <i className="fa-solid fa-question-circle"></i>
                    Mark
                  </a>
                </td>
                <td>----------------------------------</td>
                <td>@---------------------------------------</td>
                <td>------------------</td>
              </tr>
            </tbody>
          </Table>
        </div>
        <p>У вас есть вопросы?</p>
      </Col>
    </Row>
  );
};

export default Home;
