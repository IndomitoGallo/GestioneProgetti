import { User }       from './user';

export class UserProfiles {

    constructor(
        public user: User,
        public profiles: number[],
        public sessionId: string
      ) { }

}
