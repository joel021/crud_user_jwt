import { Action } from "@ngrx/store";
import { Address } from "../address.model";

export enum AddressActionEnum {
    ADD_ADDRESS = '[Address] Add ADDRESS',
}

export class AddAddressAction implements Action {

    readonly type = AddressActionEnum.ADD_ADDRESS;

    constructor(public payload: Address) {
        
    }
}

export type AddressAction = AddAddressAction;
