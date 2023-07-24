import axios from 'axios';
import { createAsyncThunk, isFulfilled } from '@reduxjs/toolkit';
import { createEntitySlice, EntityState, IQueryParams, serializeAxiosError } from 'app/shared/reducers/reducer.utils';
import { IForSale, defaultValue } from 'app/shared/model/forsale.model';
import { ASC } from 'app/shared/util/pagination.constants';

const apiUrl = 'api/forsales';

const initialState: EntityState<IForSale> = {
  loading: false,
  errorMessage: null,
  entities: [],
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

// Actions
export const getForSales = createAsyncThunk('forsale/fetch_entity_list', async ({}: IQueryParams) => {
  const requestUrl = apiUrl + '?' + 'cacheBuster=' + new Date().getTime();
  return axios.get<IForSale[]>(requestUrl);
});

export const ForSaleSlice = createEntitySlice({
  name: 'forsale',
  initialState,
  extraReducers(builder) {
    builder.addMatcher(isFulfilled(getForSales), (state, action) => {
      const { data } = action.payload;

      return {
        ...state,
        loading: false,
        entities: data.sort((a, b) => {
          if (!action.meta?.arg?.sort) {
            return 1;
          }
          const order = action.meta.arg.sort.split(',')[1];
          const predicate = action.meta.arg.sort.split(',')[0];
          return order === ASC ? (a[predicate] < b[predicate] ? -1 : 1) : b[predicate] < a[predicate] ? -1 : 1;
        }),
      };
    });
  },
});

export const { reset } = ForSaleSlice.actions;

// Reducer
export default ForSaleSlice.reducer;
