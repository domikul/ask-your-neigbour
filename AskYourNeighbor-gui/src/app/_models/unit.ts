export class Unit {

  private _id: number;
  private _name: string;

  constructor(unitName: string) {
    this._name = unitName;
  }

  get id(): number {
    return this._id;
  }

  set id(value: number) {
    this._id = value;
  }

  get name(): string {
    return this._name;
  }

  set name(value: string) {
    this._name = value;
  }
}
