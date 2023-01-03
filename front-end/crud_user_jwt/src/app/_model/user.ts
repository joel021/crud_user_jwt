export class User {
    _id: string;
    email: string;
    password: string;
    token: string;
    authorities: Array<string>;
    role: string;
}
