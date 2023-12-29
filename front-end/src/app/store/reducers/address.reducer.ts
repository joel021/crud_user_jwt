import { AddressAction, AddressActionEnum } from "../models/actions/address.action";
import { Address } from "../models/address.model";

const initialState: Array<Address> = [
    {
        id: "uuuiidddd",
        street: "street",
        district: "district",
        state: "state",
        country: "country",
        city: "city",
        number: 2,
        cep: "cep",
        owner: {
            _id: "string",
            email: "string",
            password: "string",
            token: "string",
            authorities: ["APP_ADMIN"],
            role: "APP_ADMIN",
        }
    }
]

export const addressReducer = (
    state: Array<Address> = initialState,
    action: AddressAction
) => {

    switch(action.type) {
        case AddressActionEnum.ADD_ADDRESS:
            return [...state, action.payload];
        default:
            return state;
    }
}