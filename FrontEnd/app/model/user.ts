export class User {

    constructor(
        public id:number,
        public name:string,
        public surname:string,
        public username:string,
        public email:string,
        public password:string,
        public skill:string,
        public isDeactivated:boolean,
        public seniority:number
      ) { }

}
