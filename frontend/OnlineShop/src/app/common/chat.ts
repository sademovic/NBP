export class Chat {

    public id: number;
    public sender: {
        "id": number,
        "firstName": string,
        "lastName": string,
        "email": string
    };
    public receiver: {
        "id": number,
        "firstName": string,
        "lastName": string,
        "email": string
    }
    public messages: any;

    constructor ( ) {

    }
}