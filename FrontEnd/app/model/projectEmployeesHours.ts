import { Project }       from './project';

export class ProjectEmployeesHours {

    constructor(
        public project: Project,
        public employees: string,
        public hours: number,
        public pm: string
      ) { }

}
