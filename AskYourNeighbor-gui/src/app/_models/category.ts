export class Category {

  private _id:number;
  private _name: string;

  constructor(categoryName: string) {
    this._name = categoryName;
  }

  get id(): number {
    return this._id;
  }

  get name(): string {
    return this._name;
  }

  set name(value: string) {
    this._name = value;
  }
}
