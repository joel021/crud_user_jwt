import { Address } from "../models/address.model"
import { createAction, props } from '@ngrx/store'

export const getBooksByUserId = createAction('[Address] Find Address By User Id', props<{ userId: string }>())
export const addAddress = createAction('[Address] Add Address', props<{ address: Address }>())
export const updateAddress = createAction('[Address] Update Address', props<{ address: Address }>())
export const deleteAddress = createAction('[Address] Delete Address', props<{ id: number }>())
