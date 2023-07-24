import { Stack } from 'app/shared/model/enumerations/stack.model';

export interface IForSale {
  firstName?: string | null;
  lastName?: string | null;
  stack?: keyof typeof Stack | null;
  targetRate?: number | null;
  targetRate3Mounts?: number | null;
  urlCV?: string | null;
}

export const defaultValue: Readonly<IForSale> = {};
