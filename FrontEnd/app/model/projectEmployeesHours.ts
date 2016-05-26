import { Project }       from './project';
import { User }       from './user';

export class ProjectEmployeesHours {

    constructor(
        public project: Project,
        public employees: User[],
        public hours: number[],
        public pmName: string
      ) { }

}
