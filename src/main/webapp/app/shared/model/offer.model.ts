import dayjs from 'dayjs';
import { IEmployee } from 'app/shared/model/employee.model';

export interface IOffer {
  id?: number;
  curetRate?: number | null;
  targetRate?: number | null;
  curentRatePesent?: number | null;
  targetRatePesent?: number | null;
  unbilibliDay1?: number | null;
  urlCV?: string | null;
  activityBeforeDate?: string | null;
  employee?: IEmployee | null;
}

export const defaultValue: Readonly<IOffer> = {};
