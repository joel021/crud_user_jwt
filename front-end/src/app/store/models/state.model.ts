import { Address } from "./address.model";

export interface State {
    readonly addresses: Array<Address>;
}