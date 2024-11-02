package util;

import java.util.List;

public class ActionProvider {
    public Runnable getActions(List<Runnable> actionList, int actionIndex){
        if(actionIndex > 0 && actionIndex <= actionList.size()) {
            return actionList.get(actionIndex - 1);
        }
        return () -> System.out.println("Invalid option");
    }
}
