import { Project }       from './project';
import { User }       from './user';

export class ProjectForm {

    constructor(
        public project: Project,
        public employees: User[],
        public pms: User[],
        public employeesAssociation: number[]
      ) { }

}
