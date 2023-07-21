import { IOffer } from 'app/shared/model/offer.model';
import { Stack } from 'app/shared/model/enumerations/stack.model';

export interface IEmployee {
  id?: number;
  firstName?: string | null;
  lastName?: string | null;
  location?: string | null;
  stack?: keyof typeof Stack | null;
  externalId?: number | null;
  salary?: number | null;
  offers?: IOffer[] | null;
}

export const defaultValue: Readonly<IEmployee> = {};
