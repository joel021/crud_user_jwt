import { addAddress } from "../actions/address.action";
import { State } from "../state.model";

export const addressReducer = (
    state: State = { addresses: [] },
    action
) : State => {

    switch(action.type) {
        case addAddress.type:
            return {...state, addresses: [...state.addresses, action.address] };
        default:
            return state;
    }
}