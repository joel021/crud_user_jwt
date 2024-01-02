import { Address } from "./models/address.model";

export interface State {
    readonly addresses: Array<Address>;
}