package org.sc.manager.commands;

import org.sc.Request;
import org.sc.manager.DBmanager;
import org.sc.manager.UserManager;

public class LogIn implements Command{
    @Override
    public String execute(Request request) throws Exception {
        if (UserManager.isAthCorrect(request.getLogin(), request.getPassword())){
            return "true " + DBmanager.getUserId(request.getLogin());
        } else {
            return "false";
        }
    }

    @Override
    public String getName() {
        return "";
    }

    @Override
    public String getDescription() {
        return "";
    }
}
