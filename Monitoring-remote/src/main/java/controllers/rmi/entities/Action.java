package controllers.rmi.entities;



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
