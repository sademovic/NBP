export class Item {

    public name: string;
    public price: number;
    public description: string;
    public location: string;
    public categoryN: number;
    public preOwned: boolean;
    public arhived: boolean;
    public status: boolean;
    public createdAt: number;

    constructor () {
        this.preOwned = false;
        this.arhived = false;
        this.status = false;

        let d = new Date();
        this.createdAt = d.getTime();
    }
}