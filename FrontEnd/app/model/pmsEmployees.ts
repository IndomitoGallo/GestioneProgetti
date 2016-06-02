import { User }       from './user';

export class PmsEmployees {

    constructor(
        public pms: User[],
        public employees: User[]
      ) { }

}
