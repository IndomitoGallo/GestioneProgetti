export class User {

    constructor(
        public id:number,
        public username:string,
        public password:string,
        public email:string,
        public name:string,
        public surname:string,
        public skill:string,
        public isDeactivated:boolean,
        public seniority: number
      ) { }

}
