package controllers.rmi.entities;

import controllers.rmi.entities.Profile;

public enum Action {
    CreateInstance {
        @Override
        public void execute(Profile profile) {
            
        }
    },DeleteInstance {
        @Override
        public void execute(Profile profile) {
        }
    };
    
    
    
  
    
    public abstract void execute(Profile profile);
}
