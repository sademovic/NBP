export class User {

    public firstName : string;
    public lastName : string;
    public email : string;
    public password : string;
    public location : string;
    public phoneNumber : string;
    public phoneNumberNum : number;
    public role : string;

    constructor () { this.role = 'USER' }
}