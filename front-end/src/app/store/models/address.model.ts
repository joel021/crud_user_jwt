import { User } from "./user.model";

export interface Address {
    id: string;
    street: string;
    district: string;
    state: string;
    country: string;
    city: string;
    number: number;
    cep: string;
    owner: User;
}