import { Project }       from './project';
import { User }       from './user';

export class ProjectEmployees {

    constructor(
        public project: Project,
        public employees: number[],
        public sessionId: string
      ) { }

}
